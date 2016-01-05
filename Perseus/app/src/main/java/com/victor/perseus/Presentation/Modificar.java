package com.victor.perseus.Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
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
 * Created by victor on 04/01/2016.
 */
public class Modificar extends Activity {

    ImageButton imageButton;
    Spinner spinner,spinnerIngredients,spinnerSubstituts;
    Recipe recipe;
    EditText editText,quantitat,passos;
    CheckBox checkBox;
    TextView llistaIngredients;
    int posicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        Intent intent = getIntent();
        posicio = intent.getIntExtra("posicio", 0);
        recipe = Presentation_controller.getRecepta(posicio);
        imageButton = (ImageButton) findViewById(R.id.seleccioImatgeM);
        spinner = (Spinner) findViewById(R.id.seleccioTipusM);
        editText = (EditText) findViewById(R.id.editTextM);
        quantitat = (EditText) findViewById(R.id.quantitatM);
        spinnerIngredients = (Spinner) findViewById(R.id.seleccioIngredientM);
        spinnerSubstituts = (Spinner) findViewById(R.id.seleccioSubstitutM);
        spinnerSubstituts.setVisibility(View.INVISIBLE);
        checkBox = (CheckBox) findViewById(R.id.checkBoxM);
        llistaIngredients = (TextView) findViewById(R.id.llistaIngredientsM);
        passos = (EditText) findViewById(R.id.EditText02M);
        ompleSpinner();
        ompleIngredients();
        omplePlantilla();
    }

    private String generateString(RecipeIngredient ri){
        String s = "\n" + ri.getPrincipal().getName() + ": " + ri.getQuantitat() + ".";
        if(ri.getSubstitutes().size() > 0) s = s + " Es pot substituir per: "
                + ri.getSubstitutes().get(0).getName() + ".";
        return s;
    }

    private void omplePlantilla(){
        Bitmap bitmap = Bitmap.createScaledBitmap(recipe.getImatge(), 300, 300, true);
        imageButton.setImageBitmap(bitmap);
        editText.setText(recipe.getNom());
        passos.setText(recipe.getRecepta());
        String s = "";
        List<RecipeIngredient> recipeIngredientList = recipe.getIngedients();
        for(int i = 0; i < recipeIngredientList.size();++i){
            s = s + generateString(recipeIngredientList.get(i));
        }
        llistaIngredients.setText(s);
    }

    private void ompleSpinner(){
        List<RecipeTipe> tipus = Presentation_controller.getTipus();
        ArrayAdapter<RecipeTipe> adapter = new ArrayAdapter<RecipeTipe>(this,
                android.R.layout.simple_spinner_item, tipus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new TipusSpinner());
        for(int i = 0; i < tipus.size();++i){
            if(tipus.get(i).getId() == recipe.getType().getId())spinner.setSelection(i);
        }
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

    public void afegeixIngredientM(View view){
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

    public void eliminaIngredientM(View view){
        RecipeIngredient ri = new RecipeIngredient((Ingredient)spinnerIngredients.getSelectedItem(),"");
        if(ri != null){
            Boolean trobat = false;
            List<RecipeIngredient> ingredients = recipe.getIngedients();
            String s = "";
            for(int i = 0; i < ingredients.size();++i) {
                RecipeIngredient j = ingredients.get(i);
                if(j.getPrincipal().getName().equals(ri.getPrincipal().getName())){
                    trobat = true;
                    recipe.delRecipeIngredient(i);
                    --i;
                }
                else s = s + generateString(j);
            }
            llistaIngredients.setText(s);
            if(!trobat){
                Toast toast = Toast.makeText(this, "Aquest ingredient no està afegit", Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }

    public void mostraSubstitutM(View view){
        if(checkBox.isChecked()) spinnerSubstituts.setVisibility(View.VISIBLE);
        else spinnerSubstituts.setVisibility(View.INVISIBLE);
    }

    //----------------------------------Tractament imatge ---------------------------------------
    //-------------------------------------------------------------------------------------------


    public void selectImageM(View view){
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


    //-------------------------------------------------------------------------------------------

    public void guardaReceptaM(View view){
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
                Boolean b = Presentation_controller.modificaRecepta(posicio,recipe);
                if(b) finish();
                else{
                    Toast toast = Toast.makeText(this, "Ja existeix una recepta amb aquest nom", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
        else{
            Toast toast = Toast.makeText(this, "No has introduit el nom de la recepta", Toast.LENGTH_LONG);
            toast.show();
        }
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
