package com.hostel.hostel_management.controller;

import com.hostel.hostel_management.model.User;
import com.hostel.hostel_management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoomController {

    @Autowired private RoomService roomService;
    @Autowired private UserService userService;
    @Autowired private ComplaintService complaintService;
    @Autowired private PaymentService paymentService;

    private User getUser(UserDetails ud) {
        return userService.findByUsername(ud.getUsername());
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(
            @AuthenticationPrincipal UserDetails ud, Model model) {
        User student = getUser(ud);
        model.addAttribute("student", student);
        model.addAttribute("allocations",
                roomService.getStudentAllocations(student));
        model.addAttribute("complaints",
                complaintService.getStudentComplaints(student));
        model.addAttribute("payments",
                paymentService.getStudentPayments(student));
        return "student/dashboard";
    }

    @GetMapping("/student/rooms")
    public String viewRooms(Model model) {
        model.addAttribute("rooms", roomService.getAvailableRooms());
        return "student/rooms";
    }

    @PostMapping("/student/rooms/request/{roomId}")
    public String requestRoom(@PathVariable Long roomId,
                              @AuthenticationPrincipal UserDetails ud) {
        try {
            roomService.requestAllocation(getUser(ud), roomId);
            return "redirect:/student/rooms?success";
        } catch (Exception e) {
            return "redirect:/student/rooms?error";
        }
    }

    @GetMapping("/warden/dashboard")
    public String wardenDashboard(Model model) {
        model.addAttribute("pendingAllocations",
                roomService.getPendingAllocations());
        model.addAttribute("allComplaints",
                complaintService.getAllComplaints());
        return "warden/dashboard";
    }

    @GetMapping("/warden/allocations")
    public String allocations(Model model) {
        model.addAttribute("pending", roomService.getPendingAllocations());
        model.addAttribute("all", roomService.getAllAllocations());
        return "warden/allocations";
    }

    @PostMapping("/warden/allocations/approve/{id}")
    public String approve(@PathVariable Long id) {
        roomService.approveAllocation(id);
        return "redirect:/warden/allocations?approved";
    }

    @PostMapping("/warden/allocations/reject/{id}")
    public String reject(@PathVariable Long id) {
        roomService.rejectAllocation(id);
        return "redirect:/warden/allocations?rejected";
    }
}