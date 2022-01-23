package com.amteam.managementmicroservice.repositories;

import com.amteam.managementmicroservice.entities.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findById(long id);
    List<Admin> findAll();
    Admin findByUserName(String userName);
}
