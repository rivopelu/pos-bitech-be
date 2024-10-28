package com.pos.entities.repositories;

import com.pos.entities.ClientContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientContactRepository extends JpaRepository<ClientContact, String> {
}
