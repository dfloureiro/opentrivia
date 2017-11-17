package com.dfl.trivia.main.model;

/**
 * Created by Loureiro on 15/11/2017.
 */

public class Category {

    private int id;
    private String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
