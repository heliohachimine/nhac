package com.example.helio.nhac.presentation.Model;

import com.example.helio.nhac.R;

public class Fruit {
    private String name;
    private Boolean isCollected;
    private int image;
    private String details;

    public Fruit(String name, Boolean isCollected, int image){
        this.name = name;
        this.isCollected = isCollected;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCollected() {
        return isCollected;
    }

    public void setCollected(Boolean collected) {
        isCollected = collected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
