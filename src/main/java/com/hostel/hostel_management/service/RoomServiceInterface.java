package com.hostel.hostel_management.service;

import com.hostel.hostel_management.model.Room;
import java.util.List;

public interface RoomServiceInterface {
    List<Room> getAvailableRooms();
    Room saveRoom(Room room);
}