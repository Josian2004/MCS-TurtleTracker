package com.mcsturtletrackerbackend.locations.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.locations.event.LocationPublisher;
import com.mcsturtletrackerbackend.locations.event.NewLocationEvent;
import com.mcsturtletrackerbackend.locations.exceptions.LocationNotFoundException;
import com.mcsturtletrackerbackend.locations.model.Location;
import com.mcsturtletrackerbackend.locations.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class LocationService {

private final ObjectMapper objectMapper = new ObjectMapper();


    private final LocationRepository locationRepository;
    private List<Location> locations;
    private final LocationPublisher locationPublisher;


    @Autowired
    public LocationService(LocationRepository locationRepository, LocationPublisher locationPublisher) {
        this.locationRepository = locationRepository;
        this.locationPublisher = locationPublisher;
        if (locationRepository != null) {
           locations = locationRepository.findAll();
        }
    }

    public void updateLocalStorage() {
        if (locationRepository != null) {
            locations = locationRepository.findAll();
        }
    }

    public void wipeLocalStorage(){
        locations.clear();
    }


    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void saveLocationsToDB() {
        if (locationRepository != null && locations != null) {
            locationRepository.saveAll(locations);
        }
    }

    public Location GetLocation(int computerId) throws LocationNotFoundException {
        for (Location location:locations) {
            if (location.getComputerId() == computerId) {
                return location;
            }
        }
        throw new LocationNotFoundException();
    }

    public String convertToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public void newLocation(Location location) throws JsonProcessingException {
        addNewLocation(location);
        locationPublisher.PublishNewLocationEvent(new NewLocationEvent(convertToJson(location)));
    }

    public void addNewLocation(Location location) {
        int i = 0;
        for (Location l : locations) {
            if (l.getComputerId() == location.getComputerId()){
                locations.set(i, location);
                return;
            }
            i++;
        }
        locations.add(location);
        locationRepository.save(location);
    }
}