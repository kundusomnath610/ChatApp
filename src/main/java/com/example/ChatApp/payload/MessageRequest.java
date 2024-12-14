package com.example.ChatApp.payload;

//import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private String sender;
    private String content;
    private String roomId;
    //private LocalDateTime dateTime;


}
