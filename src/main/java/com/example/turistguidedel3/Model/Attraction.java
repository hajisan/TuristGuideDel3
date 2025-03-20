package com.example.turistguidedel3.Model;

import java.util.List;

public class Attraction {
    private int id;
    private final String name;
    private final City city;
    private final String description;
    private List<Tag> tags;

    public Attraction(int id, String name, City city, String description) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Attraction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }
}