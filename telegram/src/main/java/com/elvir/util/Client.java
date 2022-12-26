package com.elvir.util;

import jakarta.persistence.*;

import java.util.UUID;


public class Client {


    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(updatable = false, nullable = false)
    private Long phone;

    @Column(updatable = false)
    private String chatId;
}
