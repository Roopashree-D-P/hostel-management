package com.hostel.hostel_management.controller;

import com.hostel.hostel_management.model.Room;
import com.hostel.hostel_management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private RoomService roomService;
    @Autowired private PaymentService paymentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers",
                userService.getAllUsers().size());
        model.addAttribute("totalRooms",
                roomService.getAllRooms().size());
        model.addAttribute("totalPayments",
                paymentService.getAllPayments().size());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String fullName,
                          @RequestParam String email,
                          @RequestParam String phone,
                          @RequestParam String role) {
        try {
            userService.register(role, username, password,
                    fullName, email, phone);
            return "redirect:/admin/users?success";
        } catch (Exception e) {
            return "redirect:/admin/users?error";
        }
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users?deleted";
    }

    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/rooms";
    }

    @PostMapping("/rooms/add")
    public String addRoom(@RequestParam String roomNumber,
                          @RequestParam String type,
                          @RequestParam int capacity,
                          @RequestParam double feePerMonth) {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setType(type);
        room.setCapacity(capacity);
        room.setCurrentOccupancy(0);
        room.setAvailable(true);
        room.setFeePerMonth(feePerMonth);
        roomService.saveRoom(room);
        return "redirect:/admin/rooms?success";
    }

    @PostMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/admin/rooms?deleted";
    }

    @GetMapping("/payments")
    public String payments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "admin/payments";
    }
}