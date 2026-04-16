package com.hostel.hostel_management.service;

import com.hostel.hostel_management.model.*;
import com.hostel.hostel_management.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired private PaymentRepository paymentRepository;

    public Payment createPayment(User student, double amount,
                                 String month) {
        Payment p = new Payment();
        p.setStudent(student);
        p.setAmount(amount);
        p.setMonth(month);
        p.setStatus(Payment.Status.PENDING);
        return paymentRepository.save(p);
    }

    public Payment makePayment(Long id) {
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        p.setStatus(Payment.Status.PAID);
        p.setPaymentDate(LocalDate.now());
        return paymentRepository.save(p);
    }

    public List<Payment> getStudentPayments(User student) {
        return paymentRepository.findByStudent(student);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}