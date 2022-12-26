package com.elvir.backend.model.task;

import lombok.Data;

import java.util.UUID;

@Data
public class SendVerifyCode {

    private UUID uuidClient;

    private Long phone;

    private String chatId;
}
