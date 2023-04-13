package com.mcsturtletrackerbackend.computers.model;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.Objects;


@Document(collection = "computers")
@TypeAlias(value = "Computer")
public class Computer {

    protected Computer() {
    }
    public Computer(long computerId) {
        this.computerId = computerId;
    }

    public Computer(long computerId, String computerLabel, int mcsSystemId, String status, String device, int fuelLevel, int fuelLimit, String creationDateTime) {
        this.computerId = computerId;
        this.computerLabel = computerLabel;
        this.mcsSystemId = mcsSystemId;
        this.status = status;
        this.device = device;
        this.fuelLevel = fuelLevel;
        this.fuelLimit = fuelLimit;
        this.creationDateTime = creationDateTime;
    }

    public long getComputerId() {
        return computerId;
    }

    public String getComputerLabel() {
        return computerLabel;
    }

    public String getAssignedMcsSystem() {
        return assignedMcsSystem;
    }

    public String getStatus() {
        return status;
    }

    public String getDevice() {
        return device;
    }

    public Number getFuelLevel() {
        return fuelLevel;
    }

    public Number getFuelLimit() {
        return fuelLimit;
    }
    public int getMcsSystemId() {
        return mcsSystemId;
    }

    @Id
    private long computerId;
    private String computerLabel;
    private int mcsSystemId;
    @Transient
    private String assignedMcsSystem;
    private String status;
    private String device;
    private int fuelLevel;
    private int fuelLimit;
    private String creationDateTime =  LocalDateTime.now().toString();


    public String getCreationDateTime() {
        return creationDateTime;
    }
    public void setAssignedMcsSystem(String assignedMcsSystem) {
        this.assignedMcsSystem = assignedMcsSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computer that = (Computer) o;
        return Objects.equals(computerId, that.computerId) &&
                Objects.equals(computerLabel, that.computerLabel) &&
                Objects.equals(mcsSystemId, that.mcsSystemId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(device, that.device) &&
                Objects.equals(fuelLevel, that.fuelLevel) &&
                Objects.equals(fuelLimit, that.fuelLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(computerId, computerLabel, mcsSystemId, assignedMcsSystem, status, device, fuelLevel, fuelLimit, creationDateTime);
    }
}