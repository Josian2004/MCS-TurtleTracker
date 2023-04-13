package com.mcsturtletrackerbackend.locations.model;

import com.mcsturtletrackerbackend.computers.model.Computer;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "locations")
@TypeAlias(value = "Location")
public class Location {

    protected Location() {
    }

    public Location(long computerId, int x, int y, int z, String dimension) {
        this.computerId = computerId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }


    @Id
    private long computerId;
    private int x;

    public long getComputerId() {
        return computerId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getDimension() {
        return dimension;
    }

    private int y;

    private int z;

    private String dimension;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location that = (Location) o;
        return Objects.equals(computerId, that.computerId) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(z, that.z) &&
                Objects.equals(dimension, that.dimension);

    }

    @Override
    public int hashCode() {
        return Objects.hash(computerId, x, y, z, dimension);
    }
}