package com.mcsturtletrackerbackend.computers.repository;

import com.mcsturtletrackerbackend.computers.model.Computer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerRepository  extends MongoRepository<Computer, Long> {
    Optional<Computer> findById(long id);

}
