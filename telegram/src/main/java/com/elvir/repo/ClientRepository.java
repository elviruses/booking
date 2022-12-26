package com.elvir.repo;

import com.elvir.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findClientByPhone(Long phone);
}
