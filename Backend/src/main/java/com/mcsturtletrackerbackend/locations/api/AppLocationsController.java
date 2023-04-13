package com.mcsturtletrackerbackend.locations.api;

import com.mcsturtletrackerbackend.ResponseObject;
import com.mcsturtletrackerbackend.locations.exceptions.LocationNotFoundException;
import com.mcsturtletrackerbackend.locations.model.Location;
import com.mcsturtletrackerbackend.locations.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("app/locations")
public class AppLocationsController {

    private final LocationService locationService;
    @Autowired
    public AppLocationsController(LocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping("/last")
    @ResponseStatus(HttpStatus.OK)
    public Location GetLastLocation(@RequestParam(value = "computerId")int computerId) throws LocationNotFoundException {
        return locationService.GetLocation(computerId);
    }

}
