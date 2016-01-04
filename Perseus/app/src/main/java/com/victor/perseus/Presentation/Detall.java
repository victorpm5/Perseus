package com.victor.perseus.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.perseus.Domain.Ingredient;
import com.victor.perseus.Domain.Recipe;
import com.victor.perseus.Domain.RecipeIngredient;
import com.victor.perseus.R;

import java.util.List;

/**
 * Created by victor on 31/12/2015.
 */
public class Detall extends Activity {
    ImageView imatge;
    TextView nom, tipus, ingredients, recepta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recepta);

        Intent intent = getIntent();
        int posicio = intent.getIntExtra("posicio", 0);
        Recipe recipe = Presentation_controller.getRecepta(posicio);
        imatge = (ImageView) findViewById(R.id.imatge);
        imatge.setImageBitmap(recipe.getImatge());
        nom = (TextView) findViewById(R.id.nom);
        nom.setText(recipe.getNom());
        tipus = (TextView) findViewById(R.id.tipus);
        tipus.setText(recipe.getType().getName());
        List<RecipeIngredient> ri= recipe.getIngedients();
        String ingredient = "";
        for(int i = 0; i < ri.size();i++){
            RecipeIngredient ir = ri.get(i);
            ingredient = ingredient + " " + ir.getPrincipal().getName() + ": " + ir.getQuantitat();
            List<Ingredient> ii = ir.getSubstitutes();
            for(int j = 0; j < ii.size();++j){
                if(j == 0)ingredient = ingredient + ". Es pot substituir per: " + ii.get(j).getName();
                else ingredient = ingredient + ", " + ii.get(j).getName();
            }
            ingredient = ingredient +".\n";
        }
        ingredients = (TextView) findViewById(R.id.ingredients);
        ingredients.setText(ingredient);
        recepta = (TextView) findViewById(R.id.textRecepta);
        recepta.setText(recipe.getRecepta() + "\n\n");
    }
}
