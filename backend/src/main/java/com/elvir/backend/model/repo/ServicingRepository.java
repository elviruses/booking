package com.elvir.backend.model.repo;

import com.elvir.backend.model.entity.Servicing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicingRepository extends JpaRepository<Servicing, UUID> {


}
