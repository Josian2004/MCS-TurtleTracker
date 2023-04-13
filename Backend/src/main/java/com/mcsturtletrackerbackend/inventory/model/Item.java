package com.mcsturtletrackerbackend.inventory.model;

import com.fasterxml.jackson.annotation.*;



public class Item {

    private String name;

    @JsonProperty("display_name")
    private String displayName;

    private int count;

    @JsonProperty("max_count")
    private int maxCount;
    private int slot;


    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCount() {
        return count;
    }

    public int getMaxCount() {
        return maxCount;
    }


    public int getSlot() {
        return slot;
    }

}
