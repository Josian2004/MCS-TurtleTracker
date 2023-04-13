package com.mcsturtletrackerbackend.computers.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Farm {
    public Farm() {
    }
    public Farm(String name) {
        this.name = name;
    }

    public long getFarmId() {
        return farmId;
    }

    public long getConnectedToId() {
        return connectedToId;
    }

    public String getConnectedSide() {
        return connectedSide;
    }

    public String getName() {
        return name;
    }

    public String getOutput() {
        return output;
    }

    public String getDescription() {
        return description;
    }
    @JsonProperty("SystemID")
    private long farmId;
    @JsonProperty("ConnectedToID")
    private long connectedToId;
    @JsonProperty("ConnectedSide")
    private String connectedSide;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Output")
    private String output;
    @JsonProperty("Description")
    private String description;
}
