package com.victor.perseus.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 25/11/2015.
 */
public class RecipeIngredient {
    int id;
    private Ingredient principal;
    private String quantitat;
    private List<Ingredient> substitutes;

    //CONSTRUCTORS
    public RecipeIngredient(Ingredient principal, String quantitat) {
        this.principal = principal;
        this.quantitat = quantitat;
        this.id = -1;
        substitutes = new ArrayList<Ingredient>();
    }

    public RecipeIngredient(int id, Ingredient principal, String quantitat) {
        this.id = id;
        this.principal = principal;
        this.quantitat = quantitat;
        substitutes = new ArrayList<Ingredient>();
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public Ingredient getPrincipal() {
        return principal;
    }

    public String getQuantitat() {
        return quantitat;
    }

    public List<Ingredient> getSubstitutes() {
        return substitutes;
    }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setSubstitutes(List<Ingredient> substitutes) {
        this.substitutes = substitutes;
    }

    public void addSubstitut(Ingredient substitut){
        substitutes.add(substitut);
    }

    public void setPrincipal(Ingredient principal) {
        this.principal = principal;
    }
}
