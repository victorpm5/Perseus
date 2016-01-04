package com.victor.perseus.Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.perseus.Domain.Ingredient;
import com.victor.perseus.Domain.Recipe;
import com.victor.perseus.Domain.RecipeIngredient;
import com.victor.perseus.Domain.RecipeTipe;
import com.victor.perseus.Domain.TipusSpinner;
import com.victor.perseus.R;
import com.victor.perseus.Utils.Imatge;

import java.util.List;

/**
 * Created by victor on 26/12/2015.
 */
public class Afegir extends Activity {

    ImageButton imageButton;
    Spinner spinner,spinnerIngredients,spinnerSubstituts;
    Recipe recipe;
    EditText editText,quantitat,passos;
    CheckBox checkBox;
    TextView llistaIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir);
        recipe = new Recipe(getResources());
        imageButton = (ImageButton) findViewById(R.id.seleccioImatge);
        spinner = (Spinner) findViewById(R.id.seleccioTipus);
        editText = (EditText) findViewById(R.id.editText);
        quantitat = (EditText) findViewById(R.id.quantitat);
        spinnerIngredients = (Spinner) findViewById(R.id.seleccioIngredient);
        spinnerSubstituts = (Spinner) findViewById(R.id.seleccioSubstitut);
        spinnerSubstituts.setVisibility(View.INVISIBLE);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        llistaIngredients = (TextView) findViewById(R.id.llistaIngredients);
        passos = (EditText) findViewById(R.id.EditText02);
        ompleSpinner();
        ompleIngredients();
    }

    private void ompleSpinner(){
        List<RecipeTipe> tipus = Presentation_controller.getTipus();
        ArrayAdapter<RecipeTipe> adapter = new ArrayAdapter<RecipeTipe>(this,
                android.R.layout.simple_spinner_item, tipus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new TipusSpinner());
    }

    private void ompleIngredients(){
        List<Ingredient> ingredients = Presentation_controller.getIngredients();
        ArrayAdapter<Ingredient> adapter = new ArrayAdapter<Ingredient>(this,
                android.R.layout.simple_spinner_item, ingredients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIngredients.setAdapter(adapter);
        spinnerIngredients.setOnItemSelectedListener(new TipusSpinner());
        spinnerSubstituts.setAdapter(adapter);
        spinnerSubstituts.setOnItemSelectedListener(new TipusSpinner());

    }

    public void selectImage(View view){
        Image_Picker_Dialog();
    }

    public void Image_Picker_Dialog()
    {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Selecciona origen");

        final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        final Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        myAlertDialog.setPositiveButton("Galeria", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                galleryIntent.setType("image/*");
                galleryIntent.putExtra("return-data", true);
                startActivityForResult(galleryIntent,0);
            }
        });

        myAlertDialog.setNegativeButton("Camara", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                startActivityForResult(cameraIntent, 1);
            }
        });
        myAlertDialog.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bitmap bitmap = Imatge.getImageFromResult(this, requestCode, data);
            bitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);
            imageButton.setBackground(null);
            imageButton.setImageBitmap(bitmap);
            recipe.setImatge(bitmap);
        } else {
            Toast toast = Toast.makeText(this, "No has seleccionat cap imatge", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void guardaRecepta(View view){
        if(!editText.getText().toString().isEmpty()){
            recipe.setNom(editText.getText().toString());
            recipe.setType((RecipeTipe)spinner.getSelectedItem());
            String recepta = passos.getText().toString();
            if (recepta.isEmpty()){
                Toast toast = Toast.makeText(this, "No has introduit els passos de la recepta", Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                recipe.setRecepta(recepta);
                Presentation_controller.createRecepta(recipe);
                finish();
            }
        }
        else{
            Toast toast = Toast.makeText(this, "No has introduit el nom de la recepta", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private RecipeIngredient getRi(){
        Ingredient i = (Ingredient)spinnerIngredients.getSelectedItem();
        String quant = quantitat.getText().toString();
        RecipeIngredient recipeIngredient = null;
        if (quant.isEmpty()) {
            Toast toast = Toast.makeText(this, "No has introduit la quantitat de l'ingredient", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            if(checkBox.isChecked()){
                Ingredient substitut = (Ingredient)spinnerSubstituts.getSelectedItem();
                if(substitut.getName().equals(i.getName())){
                    Toast toast = Toast.makeText(this, "No pots posar com a substitut el mateix ingredient", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    recipeIngredient = new RecipeIngredient(i,quant);
                    recipeIngredient.addSubstitut(substitut);
                }
            }
            else recipeIngredient = new RecipeIngredient(i,quant);

        }
        return recipeIngredient;
    }

    private String generateString(RecipeIngredient ri){
        String s = "\n" + ri.getPrincipal().getName() + ": " + ri.getQuantitat() + ".";
        if(ri.getSubstitutes().size() > 0) s = s + " Es pot substituir per: "
                + ri.getSubstitutes().get(0).getName() + ".";
        return s;
    }

    public void afegeixIngredient(View view){
        RecipeIngredient ri = getRi();
        if(ri != null){
            Boolean trobat = false;
            List<RecipeIngredient> ingredients = recipe.getIngedients();
            for(int i = 0; i < ingredients.size();++i) {
                RecipeIngredient j = ingredients.get(i);
                if(j.getPrincipal().getName().equals(ri.getPrincipal().getName())){
                    trobat = true;
                    Toast toast = Toast.makeText(this, "Aquest ingredient ja està afegit", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            if(!trobat){
                String s = generateString(ri);
                String result = llistaIngredients.getText().toString() + s;
                llistaIngredients.setText(result);
                recipe.addRecipeIngredient(ri);
            }
        }
    }

    public void eliminaIngredient(View view){
        RecipeIngredient ri = new RecipeIngredient((Ingredient)spinnerIngredients.getSelectedItem(),"");
        if(ri != null){
            Boolean trobat = false;
            List<RecipeIngredient> ingredients = recipe.getIngedients();
            for(int i = 0; i < ingredients.size();++i) {
                RecipeIngredient j = ingredients.get(i);
                if(j.getPrincipal().getName().equals(ri.getPrincipal().getName())){
                    trobat = true;
                    recipe.delRecipeIngredient(i);
                    String s = llistaIngredients.getText().toString();
                    String trossos[] = s.split("\n");
                    s = "";
                    Boolean primer = true;
                    for(int k = 0; k < trossos.length;++k){
                        if(!trossos[k].contains(ri.getPrincipal().getName())){
                           if(primer){
                               s = trossos[k];
                               primer = false;
                           }
                           else s = s + "\n" + trossos[k];
                        }
                    }
                    llistaIngredients.setText(s);
                }
            }
            if(!trobat){
                Toast toast = Toast.makeText(this, "Aquest ingredient no està afegit", Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }

    public void mostraSubstitut(View view){
        if(checkBox.isChecked()) spinnerSubstituts.setVisibility(View.VISIBLE);
        else spinnerSubstituts.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Presentation_controller.actualitzaTipus();
        ompleSpinner();
        Presentation_controller.actualitzaIngredients();
        ompleIngredients();
        //this.onCreate(null);
    }
}
