package com.victor.perseus.Domain;

/**
 * Created by victor on 25/11/2015.
 */
public class Ingredient {
    private int id;
    private String name;

    //CONSTRUCTOR

    public Ingredient() {
        this.id = -1;
    }

    public Ingredient(String name){
        this.name = name;
        this.id = -1;
    }

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return name;
    }


    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
