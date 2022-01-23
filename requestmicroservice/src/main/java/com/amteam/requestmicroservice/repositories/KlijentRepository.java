package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KlijentRepository extends CrudRepository<Client, Long> {
    Client findById(long id);
    List<Client> findAll();

}
