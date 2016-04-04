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
        this.id = -1;
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

    public void setPrincipal(int posicio, int id){
        RecipeIngredient ri = ingedients.get(posicio);
        Ingredient i = ri.getPrincipal();
        i.setId(id);
        ri.setPrincipal(i);
        ingedients.set(posicio,ri);
    }

    //
    public void addRecipeIngredient(RecipeIngredient ri){
        ingedients.add(ri);
    }

    public void delRecipeIngredient(int pos){
        ingedients.remove(pos);
    }
}
