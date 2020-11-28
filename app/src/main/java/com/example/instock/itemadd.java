package com.example.instock;

public class itemadd {

    public itemadd(){

    }

    String item, quantity;

    public itemadd(String item, String quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
