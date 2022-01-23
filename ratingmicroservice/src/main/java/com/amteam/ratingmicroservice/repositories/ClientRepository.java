package com.amteam.ratingmicroservice.repositories;

import com.amteam.ratingmicroservice.entities.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findById(long id);
    List<Client> findAll();
    Client findByUserName(String userName);
}
