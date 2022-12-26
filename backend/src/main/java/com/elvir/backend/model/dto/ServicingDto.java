package com.elvir.backend.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ServicingDto {

    private UUID id;

    private String nameService;

    private String description;

    private Integer timeService;
}
