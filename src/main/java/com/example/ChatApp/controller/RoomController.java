package com.example.ChatApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ChatApp.entities.Message;
import com.example.ChatApp.entities.Room;
import com.example.ChatApp.repositories.RoomRepository;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController 
{

    private RoomRepository roomRepository;

    // Create Constructor
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Create Room
    @PostMapping
    public ResponseEntity<?> createRoom( @RequestBody String roomId) 
    {
        if(roomRepository.findByRoomId(roomId) != null)   
        {

            // Room is there
            return ResponseEntity.badRequest().body("Room Alrady is there");

        }

        // Create New Room..
        Room room = new Room();
        room.setRoomId(roomId);
        Room saveRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
        
    }

    // Get Room.. Join
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(
        @PathVariable String roomId) 
    {
        Room room = roomRepository.findByRoomId(roomId);

        if(room == null) {
            return ResponseEntity.badRequest()
                .body("Room Not found!!");
        }

        return ResponseEntity.ok(room);
    }


    // Get message of room...

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "25", required = false) int size
    ){

        Room room = roomRepository.findByRoomId(roomId);

        if(room == null) {
            return ResponseEntity.badRequest().build();
        }


        // get Message ::
        //Pagination :: 
        List<Message> messages = room.getMessage();

        // Calculate the start and end indices for pagination
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        
        // Get the paginated messages
        List<Message> paginatedMessages = messages.subList(start, end);
        
        // Return the paginated messages in the response
        return ResponseEntity.ok(paginatedMessages);
        
    }




}
