package com.example.neeraj.buddyhub;

/**
 * Created by ankitgarg on 04/10/17.
 */

public class property {

    public property(String house_image, String house_rent, String house_bhk, String house_colony, String house_bed) {
        this.house_image = house_image;
        this.house_rent = house_rent;
        this.house_bhk = house_bhk;
        this.house_colony = house_colony;
        this.house_bed = house_bed;
    }

    public property() {

    }

    public String getHouse_image() {
        return house_image;
    }

    public void setHouse_image(String house_image) {
        this.house_image = house_image;
    }

    public String getHouse_rent() {
        return house_rent;
    }

    public void setHouse_rent(String house_rent) {
        this.house_rent = house_rent;
    }

    public String getHouse_bhk() {
        return house_bhk;
    }

    public void setHouse_bhk(String house_bhk) {
        this.house_bhk = house_bhk;
    }

    public String getHouse_colony() {
        return house_colony;
    }

    public void setHouse_colony(String house_colony) {
        this.house_colony = house_colony;
    }

    public String getHouse_bed() {
        return house_bed;
    }

    public void setHouse_bed(String house_bed) {
        this.house_bed = house_bed;
    }

    public String house_image;
    public String house_rent;
    public String house_bhk;
    public String house_colony;
    public String house_bed;
}
