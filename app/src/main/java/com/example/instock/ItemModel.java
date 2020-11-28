package com.example.instock;

public class ItemModel {

    private String count,title;

    public ItemModel(){ }

    public ItemModel(String count, String title) {
        this.count = count;
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
