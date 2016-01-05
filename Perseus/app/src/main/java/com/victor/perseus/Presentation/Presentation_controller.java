package com.victor.perseus.Presentation;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Adapter;
import android.widget.Toast;

import com.victor.perseus.Domain.Ingredient;
import com.victor.perseus.Domain.MyAdapter;
import com.victor.perseus.Domain.Recipe;
import com.victor.perseus.Domain.RecipeIngredient;
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
    private static List<Recipe> filtratge;
    private static MyAdapter adapter;
    private static List<RecipeTipe> tipus;
    private static List<Ingredient> ingredients;

    public Presentation_controller(Context context,Resources resources){
        this.context = context;
        this.resources = resources;
        db = new DB(context,resources);
        llista = db.getVista();
        filtratge = llista;
        adapter = new MyAdapter(context,llista);
        tipus = db.getAllTipusRecepta();
        tipus.add(new RecipeTipe("Afegeix Tipus"));
        ingredients = db.getAllIngredients();
        ingredients.add(new Ingredient("Afegeix Ingredient"));
    }

    public static List<RecipeTipe> getTipus(){return tipus; }

    public static Recipe getRecepta(int posicio) {
        return db.getRecipeByName(filtratge.get(posicio).getNom());
    }

    public static void ompleAdapter(){
        adapter = new MyAdapter(context,llista);
    }

    private static Boolean comprobaExistencia(String nom){
        Recipe r = db.getRecipeByName(nom);
        if(r.getId() > 0) return true;
        return false;
    }

    public static void esborrarRecepta(int posicio) {
        int id_recepta = filtratge.get(posicio).getId();
        int id_tipus = filtratge.get(posicio).getType().getId();
        db.removeRecipeById(id_recepta,id_tipus);
        filtratge.remove(posicio);
        ingredients = db.getAllIngredients();
        tipus = db.getAllTipusRecepta();
        adapter = new MyAdapter(context,filtratge);
    }

    public static Boolean createRecepta(Recipe recipe, Boolean comproba){
        Boolean existeix = false;
        if(comproba)existeix = comprobaExistencia(recipe.getNom());
        if(existeix) return false;

        db.createRecepta(recipe);
        llista = db.getVista();
        filtratge = llista;
        adapter = new MyAdapter(context, llista);

        return true;
    }

    public static Boolean modificaRecepta(int posició,Recipe recipe){
        Boolean existeix = comprobaExistencia(recipe.getNom());
        //si existeix una recepta al sistema amb el mateix nom (i q no sigui el nom original) no fem res
        if(existeix && !recipe.getNom().equals(filtratge.get(posició).getNom())) return false;

        int countTipus = db.countTipusById(filtratge.get(posició).getType().getId());
        if (countTipus == 1) recipe.getType().setId(-1);
        List<RecipeIngredient> ingredients = recipe.getIngedients();
        for(int i = 0; i < ingredients.size();++i){
            int countIngredient = db.countIngredientById(ingredients.get(i).getPrincipal().getId());
            if(countIngredient == 1)recipe.setPrincipal(i,-1);
        }

        esborrarRecepta(posició);
        createRecepta(recipe, false);

        return true;
    }

    public static void AfegeixTipus(String name){
        Boolean trobat = false;
        for(int i = 0; i < tipus.size();++i){
            if(tipus.get(i).getName().equals(name))trobat = true;
        }
        if(trobat){
            Toast toast = Toast.makeText(context, "Ja existeix un tipus amb aquest nom", Toast.LENGTH_LONG);
            toast.show();
        }
        else tipus.add(0,new RecipeTipe(name));
    }

    public static void AfegeixIngredient(String name){
        Boolean trobat = false;
        for(int i = 0; i < ingredients.size();++i){
            if(ingredients.get(i).getName().equals(name))trobat = true;
        }
        if(trobat){
            Toast toast = Toast.makeText(context, "Ja existeix un ingredient amb aquest nom", Toast.LENGTH_LONG);
            toast.show();
        }
        else ingredients.add(0,new Ingredient(name));
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

    public static void filtraPerNom(String s){
        filtratge = db.filtraPerNom(s);
        adapter = new MyAdapter(context,filtratge);
    }

    public static Boolean filtraPerTipus(String s){
        Boolean trobat = false;
        if(s.isEmpty()){
            adapter = new MyAdapter(context,llista);
            filtratge = llista;
            trobat=true;
        }
        else{
            RecipeTipe tipus = db.getTipusReceptaByName(s);
            if(tipus.getId() > 0){
                filtratge = db.filtraPerTipus(tipus.getId());
                adapter = new MyAdapter(context,filtratge);
                trobat = true;
            }
        }
        return trobat;

    }

    public static Boolean filtraPerIngredient(String s){
        Boolean trobat = false;
        if(s.isEmpty()){
            adapter = new MyAdapter(context,llista);
            filtratge = llista;
            trobat=true;
        }
        else{
            Ingredient ingredient = db.getIngredientByName(s);
            if(ingredient.getId() > 0){
                filtratge = db.filtraPerIngredient(ingredient.getId());
                adapter = new MyAdapter(context,filtratge);
                trobat = true;
            }
        }
        return trobat;

    }
}
