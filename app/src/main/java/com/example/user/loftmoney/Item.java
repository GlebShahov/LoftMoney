package com.example.user.loftmoney;

public class Item {
    private String name;
    private String price;

    public String getName() {
        return name;
    }



    public String getPrice() {
        return price;
    }


    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
