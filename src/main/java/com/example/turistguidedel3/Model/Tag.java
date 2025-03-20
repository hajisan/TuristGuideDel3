package com.example.turistguidedel3.Model;

public class Tag {
    private int id;
    private String name;

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  "ID: " + id + "\n" +
                "Name: " + name;
    }
}
