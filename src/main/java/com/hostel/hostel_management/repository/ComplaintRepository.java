package com.hostel.hostel_management.repository;

import com.hostel.hostel_management.model.Complaint;
import com.hostel.hostel_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStudent(User student);
    List<Complaint> findByStatus(Complaint.Status status);
}