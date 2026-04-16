package com.hostel.hostel_management.repository;

import com.hostel.hostel_management.model.RoomAllocation;
import com.hostel.hostel_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomAllocationRepository extends JpaRepository<RoomAllocation, Long> {
    List<RoomAllocation> findByStudent(User student);
    List<RoomAllocation> findByStatus(RoomAllocation.Status status);
}