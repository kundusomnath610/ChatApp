package com.example.ChatApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ChatApp.entities.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
    
    // Get room using Room Id
    Room findByRoomId(String roomId);

}
