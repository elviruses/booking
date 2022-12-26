package com.elvir.handlers;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class SendVerifyCode {

    private UUID uuidClient;

    private Long phone;

    private String chatId;
}
