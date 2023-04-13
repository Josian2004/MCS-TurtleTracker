package com.mcsturtletrackerbackend.locations.repository;

import com.mcsturtletrackerbackend.locations.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
}
