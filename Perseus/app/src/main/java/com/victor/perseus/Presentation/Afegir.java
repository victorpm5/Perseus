package com.victor.perseus.Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.victor.perseus.Domain.Recipe;
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
    Spinner spinner;
    Recipe recipe;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir);
        recipe = new Recipe(getResources());
        imageButton = (ImageButton) findViewById(R.id.seleccioImatge);
        spinner = (Spinner) findViewById(R.id.seleccioTipus);
        editText = (EditText) findViewById(R.id.editText);
        ompleSpinner();
    }

    private void ompleSpinner(){
        List<RecipeTipe> tipus = Presentation_controller.getTipus();
        ArrayAdapter<RecipeTipe> adapter = new ArrayAdapter<RecipeTipe>(this,
                android.R.layout.simple_spinner_item, tipus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new TipusSpinner());
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
        if(!editText.getText().toString().equals("")){
            recipe.setNom(editText.getText().toString());
            recipe.setType((RecipeTipe)spinner.getSelectedItem());
            Presentation_controller.createRecepta(recipe);
            finish();
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
        //this.onCreate(null);
    }
}
