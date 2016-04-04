package com.victor.perseus.Domain;

/**
 * Created by victor on 25/11/2015.
 */
public class RecipeTipe {
    int id;
    private String name;

    //CONSTRUCTORS
    public RecipeTipe(){
        this.id = -1;
    }

    public RecipeTipe(String name){
        this.name = name;
        this.id = -1;
    }

    public RecipeTipe(int id, String name) {
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

    public String toString(){ return  name; }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
