package com.mcsturtletrackerbackend.computers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.computers.event.InfoPublisher;
import com.mcsturtletrackerbackend.computers.event.NewComputerEvent;
import com.mcsturtletrackerbackend.computers.exceptions.ComputerNotFoundException;
import com.mcsturtletrackerbackend.computers.model.Computer;
import com.mcsturtletrackerbackend.computers.model.Farm;
import com.mcsturtletrackerbackend.computers.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InfoService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final ComputerRepository computerRepository;
    private final InfoPublisher infoPublisher;
    private List<Computer> computers;

    @Autowired
    public InfoService(ComputerRepository computerRepository, InfoPublisher infoPublisher) {
        this.computerRepository = computerRepository;
        this.infoPublisher = infoPublisher;
        computers = computerRepository.findAll();
    }

    public List<Computer> getAllComputers() {
        return Collections.unmodifiableList(computerRepository.findAll());
    }
    public Computer getComputerById(long id) throws ComputerNotFoundException {
        Optional<Computer> computer = computerRepository.findById(id);
        if (computer.isPresent()){
            return computer.get();
        } else {
            throw new ComputerNotFoundException();
        }
    }

    public String convertToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }



    public void newComputer(Computer computer) throws JsonProcessingException {
        addNewComputer(computer);
        infoPublisher.publishNewComputerEvent(new NewComputerEvent(convertToJson(computer)));

    }

    private Farm getMcsSystem(int mcsSystemId){
        ResponseEntity<String> response;
        try {
            String url = "https://systems.mcsynergy.nl/api/get-system?SystemID={mcsSystemId}";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity request = new HttpEntity(headers);
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class,
                    mcsSystemId
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                //System.out.println("Request 2 Successful.");
            } else {
                System.out.println("Systems API not operational!");
                System.out.println(response.getStatusCode());
            }
        } catch (Exception e){
            System.out.println("Systems API Exception");
            return new Farm("");
        }


        try {
            return objectMapper.readValue(response.getBody(), Farm.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void addNewComputer(Computer computer){
        int i = 0;
        //Farm farm = getMcsSystem(computer.getMcsSystemId());
        //computer.setAssignedMcsSystem(farm.getName());
        for (Computer c : computers) {
            if (c.getComputerId() == computer.getComputerId()){
                computers.set(i, computer);
                computerRepository.save(computer);
                return;
            }
            i++;
        }
        computers.add(computer);
        computerRepository.save(computer);

    }





}
