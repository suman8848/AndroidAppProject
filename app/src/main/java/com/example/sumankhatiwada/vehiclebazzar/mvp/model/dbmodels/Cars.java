package com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels;

/**
 * Created by Niwesh on 4/19/2018.
 */

public class Cars {

    String name;
    String image;

    public Cars(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() { return name; }

    public String getImage() {
        return image;
    }
}
