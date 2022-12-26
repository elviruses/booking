package com.elvir.backend.model.repo;

import com.elvir.backend.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findClientByPhone(Long phone);
}
