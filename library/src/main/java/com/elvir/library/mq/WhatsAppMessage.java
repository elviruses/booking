package com.elvir.library.mq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WhatsAppMessage {

    private String phone;
    private String message;
}
