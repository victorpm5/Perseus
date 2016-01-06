package com.victor.perseus.Presentation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

import com.victor.perseus.R;

/**
 * Created by victor on 06/01/2016.
 */
public class Ajuda extends Activity {

    WebView afegir,consultar,modificar,esborrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        afegir = (WebView) findViewById(R.id.textView7);
        consultar = (WebView) findViewById(R.id.textView9);
        modificar = (WebView) findViewById(R.id.textView11);
        esborrar = (WebView) findViewById(R.id.textView13);

        String text;

        //Afegim text ajuda afegr
        text = "<html><body><font color='white'><p align=\"justify\">";
        text+= "Per afegir una recepta a l'aplicació s'ha d'anar a la pantalla \"Afegir Recepta\". Aquesta" +
                " opció es pot trobar en la pantalla inicial o en el menú d'opcions de la pantalla \"Veure Receptes\". <br/><br/>" +
                "Un cop allà, es pot afegir una foto prement sobre la imatge d'un plat situada a la part superior esquerra." +
                " Aquesta imatge es pot obtenir al moment des de la camara o carregar-la des del carret. En aquesta pantalla," +
                " també es pot afegir un nom a la recepta i afegir-hi un tipus des d'una llista. Si es vol afegir un tipus nou, " +
                "cal selecciónar l'opció \"Afegir tipus\" en el desplegable.<br/><br/> Per afegir ingredients a la recepta cal seleccionar" +
                " l'ingredient des del menú deplegable, on es pot afegir un nou ingredient seleccionant l'opció \"Afegir ingredient\"." +
                " A continuació cal introduïr la quantitat. En cas de voler afegir un ingredient substitut cal marcar l'opció " +
                "\"Es pot substituir per un altre ingredient?\" i seleccionar un ingredient del deplegable que apareixerà. Per afegir" +
                " l'ingredient introduït cal presionar el botó verd identificat amb el simbol \"+\". En cas de voler esborrar un ingredient" +
                " cal seleccionar l'ingredient al desplegable d'ingredients i presionar el botó vermell identificat amb el simbol \"-\". <br/><br/>" +
                "Finalment cal introduïr els passos de la recepta. Un cop introduïdes totes les dades desitjades, nomès cal presionar el botó \"guardar\"" +
                " situat a la part inferior dreta.";
        text+= "</p></body></html>";
        afegir.setBackgroundColor(Color.TRANSPARENT);
        afegir.loadData(text, "text/html; charset=utf-8", "utf-8");

        //Afegim text ajuda consultar
        text = "<html><body><font color='white'><p align=\"justify\">";
        text+= "Per consultar una recepta cal anar a la pantalla  \"Veure Receptes\" accesible des de la pantalla inicial." +
                " En aquesta pantalla es mostra una llista de totes les receptes registrades al sistema ordenades per ordre alfabètic.<br/><br/>" +
                "Des d'aquesta pantalla es podrà filtrar la llista per nom,tipus de recepte o receptes que contenen un cert ingredient. " +
                "Per fer el filtratge cal seleccionar a la part superior el tipus de filtratge i introduir el nom de la recepta (o una part d'aquest)," +
                " el nom del tipus o el nom de l'ingredient. Per retornar a la llista amb totes les receptes cal fer una cerca buida. <br/><br/>" +
                "Un cop trobada la recepta desitjada, prement sobre ella s'obté una vista detallada d'aquesta";
        text+= "</p></body></html>";
        consultar.setBackgroundColor(Color.TRANSPARENT);
        consultar.loadData(text, "text/html; charset=utf-8", "utf-8");

        //Afegim text ajuda modificar
        text = "<html><body><font color='white'><p align=\"justify\">";
        text+= "Per modificar una recepta cal anar a la pantalla  \"Veure Receptes\" accesible des de la pantalla inicial." +
                " Des d'allà cal presionar la recepta a modificar fins que aparegui un menú emergent. En aquest menú cal " +
                "seleccionar l'opció \"Modificar\".<br/><br/> En pressionar aquesta opció la pantalla canviarà a una pantalla" +
                " igual que la d'afegir recepta omplerta amb les dades de la recepta (Veure pregunta \"Com puc afegir una recepta?\" " +
                " per a més informació). Canviant els camps desitjats i pressionant sobre el botó guardar es modifica la recepta";
        text += "</p></body></html>";
        modificar.setBackgroundColor(Color.TRANSPARENT);
        modificar.loadData(text, "text/html; charset=utf-8", "utf-8");

        //Afegim text ajuda esborrar
        text = "<html><body><font color='white'><p align=\"justify\">";
        text+= "Per esborrar una recepta cal anar a la pantalla  \"Veure Receptes\" accesible des de la pantalla inicial." +
                " Des d'allà cal presionar la recepta a esborrar fins que aparegui un menú emergent. En aquest menú cal " +
                "seleccionar l'opció \"esborrar\". En pressionar aquesta opció apareixerà una finestra per confirmar l'acció.";
        text += "</p></body></html>";
        esborrar.setBackgroundColor(Color.TRANSPARENT);
        esborrar.loadData(text, "text/html; charset=utf-8", "utf-8");
    }
}
