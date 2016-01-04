package com.victor.perseus.Domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.perseus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 31/12/2015.
 */
public class MyAdapter extends ArrayAdapter {
    Context context;
    private List<Recipe> receptas;

    public MyAdapter(Context context,List<Recipe> recipe){
        super(context, -1, recipe);
        this.context = context;
        this.receptas = recipe;
    }

    public void setReceptas(List<Recipe> receptas) {
        this.receptas = receptas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fila_receptas, parent, false);
        TextView nom = (TextView) rowView.findViewById(R.id.firstLine);
        TextView tipus = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imatge = (ImageView) rowView.findViewById(R.id.icon);
        nom.setText(receptas.get(position).getNom());
        tipus.setText("Tipus: " + receptas.get(position).getType().getName());
        imatge.setImageBitmap(receptas.get(position).getImatge());
        return rowView;
    }
}
