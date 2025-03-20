package com.example.turistguidedel3.dto;

import com.example.turistguidedel3.Model.Attraction;
import com.example.turistguidedel3.Model.AttractionTag;
import com.example.turistguidedel3.Model.City;
import com.example.turistguidedel3.Model.Tag;

import java.util.List;

public class TuristAttraction {
    private List<Attraction> attractions;
    private City city;
    private AttractionTag attractionTag;
    private Tag tag;

    public TuristAttraction(List<Attraction> attractions, City city, AttractionTag attractionTag, Tag tag) {
        this.attractions = attractions;
        this.city = city;
        this.attractionTag = attractionTag;
        this.tag = tag;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public City getCity() {
        return city;
    }

    public AttractionTag getAttractionTag() {
        return attractionTag;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "TuristAttraction{" +
                "attractions=" + attractions +
                ", city=" + city +
                ", attractionTag=" + attractionTag +
                ", tag=" + tag +
                '}';
    }
}