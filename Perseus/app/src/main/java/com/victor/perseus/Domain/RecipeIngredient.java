package com.victor.perseus.Domain;

import java.util.Set;

/**
 * Created by victor on 25/11/2015.
 */
public class RecipeIngredient {
    int id;
    private Ingredient principal;
    private Set <Ingredient> substitutes;

    public RecipeIngredient(){
    }
}
