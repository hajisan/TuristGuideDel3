package com.example.turistguidedel3.Model;

public class AttractionTag {
    private final int tagId;
    private final int attractionId;

    public AttractionTag(int tagId, int attractionId) {
        this.tagId = tagId;
        this.attractionId = attractionId;
    }

    public int getTagId() {
        return tagId;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public String toString() {
        return  "Tag_ID: " + tagId + "\n" +
                "Attraction_ID: " + attractionId;
    }


}
