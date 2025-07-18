package com.example.medicalstore.controller;

import com.example.medicalstore.model.Medicine;
import com.example.medicalstore.model.User;
import com.example.medicalstore.service.MedicineService;
import com.example.medicalstore.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class MedicineController {

    private final MedicineService medicineService;
    private final UserService userService;

    public MedicineController(MedicineService medicineService, UserService userService) {
        this.medicineService = medicineService;
        this.userService = userService;
    }

    private User getCurrentUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Logged in user not found in database."));
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model,
                            @AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "5") int pageSize,
                            @RequestParam(defaultValue = "addedAt") String sortBy,
                            @RequestParam(defaultValue = "desc") String sortDir,
                            @RequestParam(required = false) String search) {

        User currentUser = getCurrentUser(userDetails);
        Page<Medicine> medicinePage = medicineService.getMedicinesForUser(currentUser, pageNum, pageSize, sortBy, sortDir, search);

        model.addAttribute("medicines", medicinePage.getContent());
        model.addAttribute("currentPage", medicinePage.getNumber() + 1);
        model.addAttribute("totalPages", medicinePage.getTotalPages());
        model.addAttribute("totalItems", medicinePage.getTotalElements());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("search", search);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc"); // For toggling sort direction

        return "dashboard";
    }

    @GetMapping("/add-medicine")
    public String showAddMedicineForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "add-medicine";
    }

    @PostMapping("/add-medicine")
    public String addMedicine(@ModelAttribute("medicine") Medicine medicine,
                              @AuthenticationPrincipal UserDetails userDetails,
                              RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(userDetails);
        if (medicineService.addMedicine(medicine, currentUser)) {
            redirectAttributes.addFlashAttribute("message", "Medicine added successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } else {
            redirectAttributes.addFlashAttribute("message", "You have reached the limit of 5 medicines. Cannot add more.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/edit-medicine/{id}")
    public String showEditMedicineForm(@PathVariable("id") Long id,
                                       @AuthenticationPrincipal UserDetails userDetails,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(userDetails);
        Optional<Medicine> medicineOptional = medicineService.getMedicineById(id);

        if (medicineOptional.isEmpty() || !medicineOptional.get().getUser().equals(currentUser)) {
            redirectAttributes.addFlashAttribute("message", "Medicine not found or you don't have permission to edit it.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/dashboard";
        }
        model.addAttribute("medicine", medicineOptional.get());
        return "edit-medicine";
    }

    @PostMapping("/edit-medicine/{id}")
    public String updateMedicine(@PathVariable("id") Long id,
                                 @ModelAttribute("medicine") Medicine medicine,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(userDetails);
        Optional<Medicine> existingMedicineOptional = medicineService.getMedicineById(id);

        if (existingMedicineOptional.isEmpty() || !existingMedicineOptional.get().getUser().equals(currentUser)) {
            redirectAttributes.addFlashAttribute("message", "Medicine not found or you don't have permission to update it.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/dashboard";
        }

        // Set the ID and user for the updated medicine object
        medicine.setId(id);
        medicine.setUser(currentUser);
        medicine.setAddedAt(existingMedicineOptional.get().getAddedAt()); // Preserve original addedAt time

        if (medicineService.updateMedicine(medicine)) {
            redirectAttributes.addFlashAttribute("message", "Medicine updated successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to update medicine.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/delete-medicine/{id}")
    public String deleteMedicine(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(userDetails);
        if (medicineService.deleteMedicine(id, currentUser)) {
            redirectAttributes.addFlashAttribute("message", "Medicine deleted successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Medicine not found or you don't have permission to delete it.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/dashboard";
    }
}