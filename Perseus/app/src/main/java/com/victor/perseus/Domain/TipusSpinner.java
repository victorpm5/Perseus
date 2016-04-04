package com.victor.perseus.Domain;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.victor.perseus.Presentation.Presentation_controller;
import com.victor.perseus.R;

/**
 * Created by victor on 02/01/2016.
 */
public class TipusSpinner extends Activity implements AdapterView.OnItemSelectedListener {


    public void onItemSelected(final AdapterView<?> parent, View view,
                               int pos, long id) {
        if(parent.getItemAtPosition(pos).toString().equals("Afegeix Tipus")){
            AlertDialog.Builder alert = new AlertDialog.Builder(parent.getContext());
            final EditText edittext = new EditText(parent.getContext());
            edittext.setHint("Nom del tipus");
            edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            alert.setTitle("Nou tipus");
            alert.setMessage("Introdueix el nom del tipus a afegir");

            alert.setView(edittext);

            alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String tipus = edittext.getText().toString();
                    if(!tipus.isEmpty())Presentation_controller.AfegeixTipus(tipus);
                    parent.setSelection(0);
                }
            });

            alert.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    parent.setSelection(0);
                }
            });
            alert.show();
        }
        else if(parent.getItemAtPosition(pos).toString().equals("Afegeix Ingredient")){
            AlertDialog.Builder alert = new AlertDialog.Builder(parent.getContext());
            final EditText edittext = new EditText(parent.getContext());
            edittext.setHint("Nom de l'ingredient");
            edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            alert.setTitle("Nou ingredient");
            alert.setMessage("Introdueix el nom de l'ingredient a afegir");

            alert.setView(edittext);

            alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String ingredient = edittext.getText().toString();
                    if(!ingredient.isEmpty())Presentation_controller.AfegeixIngredient(ingredient);
                    parent.setSelection(0);
                }
            });

            alert.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    parent.setSelection(0);
                }
            });
            alert.show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
