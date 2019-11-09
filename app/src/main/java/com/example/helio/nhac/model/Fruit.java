package com.example.helio.nhac.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Fruit implements Parcelable {
    private String id_name;
    private String name;
    private Boolean isCollected;
    private int image;
    private String details;

    public Fruit(String id_name, String name, Boolean isCollected, int image){
        this.id_name = id_name;
        this.name = name;
        this.isCollected = isCollected;
        this.image = image;
        this.details = "Informações sobre " + name;
    }
    public Fruit(String id_name, String name, Boolean isCollected, int image, String details){
        this.id_name = id_name;
        this.name = name;
        this.isCollected = isCollected;
        this.image = image;
        this.details = details;
    }

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
