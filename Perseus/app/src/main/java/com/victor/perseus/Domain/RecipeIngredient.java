package com.victor.perseus.Domain;

import java.util.Set;

/**
 * Created by victor on 25/11/2015.
 */
public class RecipeIngredient {
    private Ingredient principal;
    private Set <Ingredient> substitutes;

    public RecipeIngredient(){
    }
}
