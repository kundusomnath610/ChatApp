package com.example.ChatApp.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ChatApp.entities.Message;
import com.example.ChatApp.entities.Room;
import com.example.ChatApp.payload.MessageRequest;
import com.example.ChatApp.repositories.RoomRepository;

@Controller
@CrossOrigin("http://localhost:8081")
public class ChatController {


    private RoomRepository roomRepository;


    
    // Constructor for chat Controller....
    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    //For Sending And Reciving Message..
    @MessageMapping("/sendMessage/{roodId}")
    @SendTo("/topic/room/{roomId}") // Send to Subscribe Message..
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
        ) 
    {
        
        Room room = roomRepository.findByRoomId(request.getRoomId());

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if(room != null) {
            room.getMessage().add(message);
            roomRepository.save(room);
        } else {
            throw new RuntimeException("Room Not found!!!!");
        }

        return message;

    }


}
