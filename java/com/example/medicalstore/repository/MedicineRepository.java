package com.example.medicalstore.repository;

import com.example.medicalstore.model.Medicine;
import com.example.medicalstore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Page<Medicine> findByUser(User user, Pageable pageable);
    Page<Medicine> findByUserAndNameContainingIgnoreCase(User user, String name, Pageable pageable);
    long countByUser(User user); // For the 5-medicine limit
}