package com.victor.perseus.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.victor.perseus.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Presentation_controller(getApplicationContext(),getResources());
    }

    public void changeToReceptas(View view){
        Intent i = new Intent( MainActivity.this, Receptas.class);
        startActivity(i);
    }

    public void changeToAfegir(View view){
        Intent i = new Intent( MainActivity.this, Afegir.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ajuda) {
            Intent i = new Intent( MainActivity.this, Ajuda.class);
            startActivity(i);
        }

        if (id == R.id.action_informacio) {
            Toast.makeText(getApplicationContext(), "Aplicació creada per Víctor Pérez per l'assignatura d'IDI a la FIB, UPC.",
                    Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
