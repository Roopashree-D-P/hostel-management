package com.hostel.hostel_management.service;

import com.hostel.hostel_management.model.*;
import com.hostel.hostel_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements RoomServiceInterface {

    @Autowired private RoomRepository roomRepository;
    @Autowired private RoomAllocationRepository allocationRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room saveRoom(Room room) {
        room.setAvailable(room.getCurrentOccupancy() < room.getCapacity());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public void requestAllocation(User student, Long roomId) {
        Room room = getRoomById(roomId);
        if (!room.isAvailable())
            throw new RuntimeException("Room not available");
        RoomAllocation allocation = new RoomAllocation();
        allocation.setStudent(student);
        allocation.setRoom(room);
        allocation.setRequestDate(LocalDate.now());
        allocation.setStatus(RoomAllocation.Status.PENDING);
        allocationRepository.save(allocation);
    }

    public void approveAllocation(Long id) {
        RoomAllocation a = allocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        a.setStatus(RoomAllocation.Status.APPROVED);
        a.setAllocationDate(LocalDate.now());
        Room room = a.getRoom();
        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        if (room.getCurrentOccupancy() >= room.getCapacity())
            room.setAvailable(false);
        roomRepository.save(room);
        allocationRepository.save(a);
    }

    public void rejectAllocation(Long id) {
        RoomAllocation a = allocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        a.setStatus(RoomAllocation.Status.REJECTED);
        allocationRepository.save(a);
    }

    public List<RoomAllocation> getAllAllocations() {
        return allocationRepository.findAll();
    }

    public List<RoomAllocation> getPendingAllocations() {
        return allocationRepository.findByStatus(RoomAllocation.Status.PENDING);
    }

    public List<RoomAllocation> getStudentAllocations(User student) {
        return allocationRepository.findByStudent(student);
    }
}