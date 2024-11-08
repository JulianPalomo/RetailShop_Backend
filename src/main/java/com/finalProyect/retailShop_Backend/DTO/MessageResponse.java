package com.finalProyect.retailShop_Backend.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class MessageResponse {
    private List<String> message;
    private Integer Status;
    private String timeStamp;

    public MessageResponse(Integer status) {
        this.Status=status;
        this.message = new ArrayList<>();
        this.timeStamp = Instant.now().toString();
    }

    public MessageResponse(Integer status, String message) {
        this.Status = status;
        this.message = new ArrayList<>();
        this.message.add(message);
        this.timeStamp = Instant.now().toString();
    }

    public MessageResponse() {
        this.timeStamp = Instant.now().toString();
    }

    public void setMessage(String message) {
        this.message = List.of(message);
    }
}


