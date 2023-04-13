package com.mcsturtletrackerbackend.computers.api;

import com.mcsturtletrackerbackend.ResponseObject;
import com.mcsturtletrackerbackend.computers.exceptions.ComputerNotFoundException;
import com.mcsturtletrackerbackend.computers.model.Computer;
import com.mcsturtletrackerbackend.computers.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("app/computers")
public class AppInfoController {

    private InfoService infoService;
    @Autowired
    public AppInfoController(InfoService infoService){
        this.infoService = infoService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Computer> getAllComputers(){
        return infoService.getAllComputers();
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Computer getComputerById(@RequestParam(value = "id")long id) throws ComputerNotFoundException {
        return infoService.getComputerById(id);
    }
}