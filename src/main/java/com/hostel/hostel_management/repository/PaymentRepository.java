package com.hostel.hostel_management.repository;

import com.hostel.hostel_management.model.Payment;
import com.hostel.hostel_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {
    List<Payment> findByStudent(User student);
    List<Payment> findByStatus(Payment.Status status);
}