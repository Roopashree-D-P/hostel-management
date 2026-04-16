package com.hostel.hostel_management.service;

import com.hostel.hostel_management.model.*;
import com.hostel.hostel_management.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ComplaintService {

    @Autowired private ComplaintRepository complaintRepository;

    public Complaint raiseComplaint(User student, String title,
                                    String description) {
        Complaint c = new Complaint();
        c.setStudent(student);
        c.setTitle(title);
        c.setDescription(description);
        c.setRaisedDate(LocalDate.now());
        c.setStatus(Complaint.Status.RAISED);
        return complaintRepository.save(c);
    }

    public Complaint updateStatus(Long id, Complaint.Status newStatus) {
        Complaint c = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        c.setStatus(newStatus);
        return complaintRepository.save(c);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getStudentComplaints(User student) {
        return complaintRepository.findByStudent(student);
    }
}