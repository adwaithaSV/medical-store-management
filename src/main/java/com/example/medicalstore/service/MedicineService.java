package com.example.medicalstore.service;

import com.example.medicalstore.model.Medicine;
import com.example.medicalstore.model.User;
import com.example.medicalstore.repository.MedicineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private static final int MAX_MEDICINES_PER_USER = 5;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public boolean addMedicine(Medicine medicine, User user) {
        if (medicineRepository.countByUser(user) >= MAX_MEDICINES_PER_USER) {
            return false; // User has reached the limit
        }
        medicine.setUser(user);
        medicineRepository.save(medicine);
        return true;
    }

    public Optional<Medicine> getMedicineById(Long id) {
        return medicineRepository.findById(id);
    }

    public Page<Medicine> getMedicinesForUser(User user, int pageNum, int pageSize, String sortBy, String sortDir, String searchQuery) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            return medicineRepository.findByUserAndNameContainingIgnoreCase(user, searchQuery, pageable);
        } else {
            return medicineRepository.findByUser(user, pageable);
        }
    }

    public boolean updateMedicine(Medicine medicine) {
        // You might want to add checks here to ensure the medicine belongs to the current user
        if (medicineRepository.findById(medicine.getId()).isPresent()) {
            medicineRepository.save(medicine);
            return true;
        }
        return false;
    }

    public boolean deleteMedicine(Long id, User user) {
        Optional<Medicine> medicineOptional = medicineRepository.findById(id);
        if (medicineOptional.isPresent() && medicineOptional.get().getUser().equals(user)) {
            medicineRepository.delete(medicineOptional.get());
            return true;
        }
        return false;
    }
}