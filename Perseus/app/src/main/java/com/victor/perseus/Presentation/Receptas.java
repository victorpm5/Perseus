package com.victor.perseus.Presentation;

import android.app.Activity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptas);
        lv = (ListView) findViewById(R.id.lv);
        et = (EditText) findViewById(R.id.et);
        lv.setAdapter(Presentation_controller.getAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent( Receptas.this, Detall.class);
                i.putExtra("posicio",position);
                startActivity(i);
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
}
