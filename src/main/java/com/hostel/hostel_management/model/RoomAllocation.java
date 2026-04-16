package com.hostel.hostel_management.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "room_allocations")
public class RoomAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate requestDate;
    private LocalDate allocationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status { PENDING, APPROVED, REJECTED }

    public RoomAllocation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    public LocalDate getAllocationDate() { return allocationDate; }
    public void setAllocationDate(LocalDate allocationDate) { this.allocationDate = allocationDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}