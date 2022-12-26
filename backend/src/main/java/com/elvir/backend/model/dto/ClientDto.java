package com.elvir.backend.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private Long phone;
}
