package com.victor.perseus.Presentation;

import android.content.Context;
import android.content.res.Resources;

import com.victor.perseus.Domain.Ingredient;
import com.victor.perseus.Domain.MyAdapter;
import com.victor.perseus.Domain.Recipe;
import com.victor.perseus.Domain.RecipeTipe;
import com.victor.perseus.Persistence.DB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 28/12/2015.
 */
public class Presentation_controller {
    private static Context context;
    Resources resources;
    private static DB db;
    private static List<Recipe> llista;
    private static MyAdapter adapter;
    private static List<RecipeTipe> tipus;
    private static List<Ingredient> ingredients;

    public Presentation_controller(Context context,Resources resources){
        this.context = context;
        this.resources = resources;
        db = new DB(context,resources);
        llista = db.getVista();
        adapter = new MyAdapter(context,llista);
        tipus = db.getAllTipusRecepta();
        tipus.add(new RecipeTipe("Afegeix Tipus"));
        ingredients = db.getAllIngredients();
        ingredients.add(new Ingredient("Afegeix Ingredient"));
    }

    public static List<RecipeTipe> getTipus(){return tipus; }

    public static Recipe getRecepta(int posicio) {
        return db.getRecipeByName(llista.get(posicio).getNom());
    }
    public static void AfegeixTipus(String name){
        tipus.add(0,new RecipeTipe(name));
    }

    public static void AfegeixIngredient(String name){
        ingredients.add(0,new Ingredient(name));
    }

    public static Long createRecepta(Recipe recipe){
        Long id = db.createRecepta(recipe);
        llista.add(recipe);
        adapter.setReceptas(llista);
        return id;
    }

    public static List<Ingredient> getIngredients(){
        return ingredients;
    }

    public static MyAdapter getAdapter(){
        return adapter;
    }

    public static void actualitzaTipus(){
        tipus = db.getAllTipusRecepta();
        tipus.add(new RecipeTipe("Afegeix Tipus"));
    }

    public static void actualitzaIngredients(){
        ingredients = db.getAllIngredients();
        ingredients.add(new Ingredient("Afegeix Ingredient"));
    }

}
