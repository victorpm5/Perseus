package com.victor.perseus.Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.victor.perseus.Domain.Ingredient;
import com.victor.perseus.Domain.MyAdapter;
import com.victor.perseus.Domain.Recipe;
import com.victor.perseus.Domain.RecipeIngredient;
import com.victor.perseus.Domain.RecipeTipe;
import com.victor.perseus.Persistence.DB;
import com.victor.perseus.R;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by victor on 25/12/2015.
 */
public class Receptas extends Activity {

    protected ListView lv;
    EditText et;
    RadioButton nom;
    RadioButton tipus;
    RadioButton ingredient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptas);
        lv = (ListView) findViewById(R.id.lv);
        et = (EditText) findViewById(R.id.et);
        nom = (RadioButton) findViewById(R.id.nom);
        tipus = (RadioButton) findViewById(R.id.tipus);
        ingredient = (RadioButton) findViewById(R.id.Ingredient);
        lv.setAdapter(Presentation_controller.getAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent( Receptas.this, Detall.class);
                i.putExtra("posicio",position);
                startActivity(i);
            }
        });
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {

                final CharSequence[] items = {"Modificar", "Esborrar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(arg0.getContext());
                builder.setTitle("Selecciona una acció");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(item ==0){
                            Toast toast = Toast.makeText(getApplicationContext(), "modifiquem la recepta", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else{
                            dialog.dismiss();
                            builder.setTitle("Atenció");
                            builder.setMessage("Estàs apunt d'esborrar la recepta "
                                    + Presentation_controller.getRecepta(pos).getNom() + ". Estàs segur d'això?");
                            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    Presentation_controller.esborrarRecepta(pos);
                                    lv.setAdapter(Presentation_controller.getAdapter());
                                }
                            });
                            builder.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receptas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_afegir) {
            Intent i = new Intent( Receptas.this, Afegir.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }

    public void cercaPerNom(View view){
        if(nom.isChecked()) {
            Presentation_controller.filtraPerNom(et.getText().toString());
            lv.setAdapter(Presentation_controller.getAdapter());
        }
        else if (tipus.isChecked()){
            Boolean trobat = Presentation_controller.filtraPerTipus(et.getText().toString());
            if(trobat)lv.setAdapter(Presentation_controller.getAdapter());
            else{
                Toast toast = Toast.makeText(this, "No hi ha cap recepta amb aquest tipus", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else{
            Boolean trobat = Presentation_controller.filtraPerIngredient(et.getText().toString());
            if(trobat)lv.setAdapter(Presentation_controller.getAdapter());
            else{
                Toast toast = Toast.makeText(this, "No hi ha cap recepta amb aquest ingredient", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void actualitzaEt(View view){
        if(nom.isChecked()) {
            et.setHint("Introdueix el nom de la recepta");
        }
        else if (tipus.isChecked()){
            et.setHint("Introdueix el tipus de la recepta");
        }
        else{
            et.setHint("Introdueix el nom d'un ingredient");
        }
    }
}


