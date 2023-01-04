package com.elvir.library.mq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelegramMessage {

    private String chatId;
    private String message;
}
