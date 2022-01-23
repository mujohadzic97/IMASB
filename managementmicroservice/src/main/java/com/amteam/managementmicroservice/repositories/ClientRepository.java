package com.amteam.managementmicroservice.repositories;

import com.amteam.managementmicroservice.entities.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findById(long id);
    List<Client> findAll();
    Client findByUserName(String userName);
}
