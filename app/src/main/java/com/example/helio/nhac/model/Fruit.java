package com.example.helio.nhac.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Fruit implements Parcelable {
    private String id_name;
    private String name;
    private Boolean isCollected;
    private byte [] image;
    private String details;

    public Fruit(String id_name, String name, String details, Boolean isCollected, byte [] image){
        this.id_name = id_name;
        this.name = name;
        this.details = details;
        this.isCollected = isCollected;
        this.image = image;
    }

    public static final Creator<Fruit> CREATOR = new Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel in) {
            return new Fruit(in);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };

    public Fruit(Parcel in) {

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

    public byte [] getImage() {
        return image;
    }

    public void setImage(byte [] image) {
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
        dest.writeString(id_name);
        dest.writeString(name);
        dest.writeByte((byte) (isCollected == null ? 0 : isCollected ? 1 : 2));
        dest.writeByteArray(image);
        dest.writeString(details);
    }
}
