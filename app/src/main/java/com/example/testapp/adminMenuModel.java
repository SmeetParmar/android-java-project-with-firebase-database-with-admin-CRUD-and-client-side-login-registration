package com.example.testapp;

public class adminMenuModel {

    String details,image,name,price;

    adminMenuModel(){

    }
    public adminMenuModel(String details, String image, String name, String price) {
        this.details = details;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
