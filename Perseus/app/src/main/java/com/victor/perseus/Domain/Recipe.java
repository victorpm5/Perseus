package com.victor.perseus.Domain;



import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.victor.perseus.R;
import com.victor.perseus.Utils.Imatge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 25/11/2015.
 */
public class Recipe {
    private int id;
    private String nom;
    private String recepta;
    private RecipeTipe type;
    private Bitmap imatge;
    private List<RecipeIngredient> ingedients;

    //CONSTRUCTORS
    public Recipe(Resources resources){
        this.nom = "nom";
        this.recepta = "Aquí va la recepta";
        ingedients = new ArrayList<RecipeIngredient>();
        imatge = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);
        type = new RecipeTipe("Altres");
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getRecepta() {
        return recepta;
    }

    public RecipeTipe getType() {
        return type;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public List<RecipeIngredient> getIngedients() {
        return ingedients;
    }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRecepta(String recepta) {
        this.recepta = recepta;
    }

    public void setType(RecipeTipe type) {
        this.type = type;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }

    public void setIngedients(List<RecipeIngredient> ingedients) {
        this.ingedients = ingedients;
    }

    public void addRecipeIngredient(RecipeIngredient ri){
        ingedients.add(ri);
    }
}
