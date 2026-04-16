package com.hostel.hostel_management.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    private double amount;
    private LocalDate paymentDate;
    private String month;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status { PENDING, PAID }

    public Payment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}