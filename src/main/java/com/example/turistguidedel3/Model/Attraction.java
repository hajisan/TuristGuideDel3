package com.example.turistguidedel3.Model;

public class Attraction {
    private final int id;
    private final String name;
    private final City city;
    private final String description;

    // Konstruktør til at oprette en attraktion med variabler
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

    public void setId(int id) {
        this.id = id;
    }

    // toString-metode til at returenere attraktionens oplysninger
    @Override
    public String toString() {
        return  "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "City: " + city.getName() + "\n" +
                "Description: " + description;
    }

}
