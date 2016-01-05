package com.victor.perseus.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.victor.perseus.Domain.Ingredient;
import com.victor.perseus.Domain.Recipe;
import com.victor.perseus.Domain.RecipeIngredient;
import com.victor.perseus.Domain.RecipeTipe;
import com.victor.perseus.R;
import com.victor.perseus.Utils.Imatge;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents de DataBase
 * Created by victor on 26/12/2015.
 */
public class DB extends SQLiteOpenHelper {
    private Resources resources;
    //DB NAME
    final static String DB_NAME = "db_recepta";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    //TABLE NAMES
    private static final String TABLE_RECEPTA = "recepta";
    private static final String TABLE_TIPUS_RECEPTA = "tipus_recepta";
    private static final String TABLE_INGREDIENT_RECEPTA = "ingredient_recepta";
    private static final String TABLE_INGREDIENT = "ingredient";
    private static final String TABLE_INGREDIENT_SUBSTITUT = "ingredient_substitut";
    //COMMON COLUMN NAMES
    private static final String KEY_ID = "id";
    //RECEPTA COLUMN NAMES
    private static final String NOM_RECEPTA = "nom_recepta";
    private static final String RECEPTA = "recepta";
    private static final String IMATGE = "imatge";
    private static final String ID_TIPUS = "id_tipus";
    //TIPUS RECEPTA COLUMN NAMES
    private static final String NOM_TIPUS = "nom_tipus";
    //INGREDIENT DE RECEPTA COLUMN NAMES
    private static final String ID_INGREDIENT = "id_ingredient";
    private static final String ID_RECEPTA = "id_recepta";
    private static final String QUANTITAT = "quantitat";
    //INGREDIENT
    private static final String NOM_INGREDIENT = "nom_ingredient";
    //INGREDIENT SUBSTITUT
    private static final String ID_INGREDIENT_RECEPTA = "id_ingredient_recepta";
    //CREATE TABLE RECEPTA
    private static final String CREATE_TABLE_RECEPTA = "CREATE TABLE "
            + TABLE_RECEPTA + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + NOM_RECEPTA
            + " TEXT, " + RECEPTA + " TEXT, " + IMATGE + " BLOB, " + ID_TIPUS
            + " INTEGER" + ")";
    //CREATE TABLE TIPUS RECEPTA
    private static final String CREATE_TABLE_TIPUS_RECEPTA = "CREATE TABLE "
            + TABLE_TIPUS_RECEPTA + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + NOM_TIPUS
            + " TEXT" +  ")";
    //CREATE TABLE INGREDIENT DE RECEPTA
    private static final String CREATE_TABLE_INGREDIENT_RECEPTA = "CREATE TABLE "
            + TABLE_INGREDIENT_RECEPTA + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + ID_INGREDIENT
            + " INTEGER, " + ID_RECEPTA + " INTEGER, " + QUANTITAT + " TEXT" + ")";
    //CREATE TABLE TIPUS INGREDIENT
    private static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE "
            + TABLE_INGREDIENT + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + NOM_INGREDIENT
            + " TEXT" +  ")";
    //CREATE TABLE INGREDIENT SUBSTITUT
    private static final String CREATE_TABLE_INGREDIENT_SUBSTITUT = "CREATE TABLE "
            + TABLE_INGREDIENT_SUBSTITUT + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + ID_INGREDIENT_RECEPTA
            + " INTEGER, " + ID_INGREDIENT + " INTEGER" + ")";


    public DB (Context context,Resources resources) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.resources=resources;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_TIPUS_RECEPTA);
            db.execSQL(CREATE_TABLE_RECEPTA);
            db.execSQL(CREATE_TABLE_INGREDIENT);
            db.execSQL(CREATE_TABLE_INGREDIENT_RECEPTA);
            db.execSQL(CREATE_TABLE_INGREDIENT_SUBSTITUT);
        }catch (SQLException e){
            Log.e("Data Base: ","Error en la creació de taules");
        }
        inicialitzacio(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEPTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPUS_RECEPTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_RECEPTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_SUBSTITUT);

        onCreate(db);

    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void inicialitzacio(SQLiteDatabase db){

        try {
            ContentValues values = new ContentValues();

            //INICIALITXEM ALGUNS TIPUS DE RECEPTA
            values.put(KEY_ID, "1");
            values.put(NOM_TIPUS, "Altres");
            db.insert(TABLE_TIPUS_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "2");
            values.put(NOM_TIPUS, "Catalana");
            db.insert(TABLE_TIPUS_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "3");
            values.put(NOM_TIPUS, "Tropical");
            db.insert(TABLE_TIPUS_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "4");
            values.put(NOM_TIPUS, "Mediterrània");
            db.insert(TABLE_TIPUS_RECEPTA, KEY_ID, values);
            values.clear();

            //INICIALITZEM LA BD AMB ALGUNA RECEPTA
            values.put(KEY_ID, "1");
            values.put(NOM_RECEPTA, "Ametllats");
            values.put(RECEPTA, "En un bol, s'hi barreja la farina d'ametlla amb el sucre i l'essència de vainilla.\n" +
                    "A poc a poc, s'hi afegeixen les clares tot barrejant fins obtenir una pasta fina.\n" +
                    "A continuació, s'aboca la massa dins una màniga pastissera.\n" +
                    "Al damunt d'un silpat o paper de forn, es formen cercles de massa d'uns 2 cm de diàmetre i al damunt s'hi col·loca una ametlla marcona crua.\n" +
                    "S'enfornen dins el forn prèviament escalfat a 180ºC fins que es comencin a enrossir.\n" +
                    "Es deixen refredar a temperatura ambient i es reserven dins un recipient hermètic.");
            values.put(IMATGE,Imatge.getBytes(BitmapFactory.decodeResource(resources,R.drawable.ametllats)));
            values.put(ID_TIPUS, "2");
            db.insert(TABLE_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "2");
            values.put(NOM_RECEPTA, "Panellets");
            values.put(RECEPTA, "Barregeu l'ametlla triturada, el sucre en pols, els rovells, la clara, la vainilla i la llimona i amasseu. Potser el principi, es una barreja que sembla que mai s'unirà, no desistiu, amasseu una bona estona. Quan ja sigui una massa unificada que no se us enganxa als dits, tapeu-la amb un drap de cuina i deixeu-la reposar 2 hores com a mínim.\n" +
                    "Si després del repòs, veieu que és molt dura podeu afegir-hi una mica de clara d'ou muntada, amasseu una mica i ja podreu començar a fer els panellets.\n" +
                    "Escalfeu el forn a uns 200º.\n" +
                    "Feu els panellets (normalment són en forma de boletes) i incrusteu-hi els pinyons, ametlles,... o arrebosseu-los amb coco, segons el vostre gust. També els hi podeu donar una pinzellada de rovell d'ou per què quedin d'un color més daurat.\n" +
                    "Una vegada tingueu els panellets fets, poseu-los al forn i vigileu-los durant uns 5-10 min fins que comencin a estar una mica daurats.\n" +
                    "No us preocupeu si quan els retireu del forn semblen tous, al refredar-se s'enduriran una mica.\n" +
                    "Apa a llepar-se els dits!");
            values.put(IMATGE,Imatge.getBytes(BitmapFactory.decodeResource(resources,R.drawable.panellets)));
            values.put(ID_TIPUS, "2");
            db.insert(TABLE_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "3");
            values.put(NOM_RECEPTA, "Batut de meló amb tarongina");
            values.put(RECEPTA, "Es pela el meló i es talla a trossos petits.\n" +
                    "Es netegen les fulles de tarongina amb aigua.\n" +
                    "S'introdueixen tots els ingredients dins el vas del túrmix o de la liquadora i es tritura tot fins que quedi una mescla homogènia.\n" +
                    "Es reserva a la nevera fins el moment de servir.");
            values.put(IMATGE,Imatge.getBytes(BitmapFactory.decodeResource(resources,R.drawable.batut)));
            values.put(ID_TIPUS, "3");
            db.insert(TABLE_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "4");
            values.put(NOM_RECEPTA, "Pasta fresca");
            values.put(RECEPTA, "En un bol, es barreja la farina amb els ous, l'oli i la sal. S'amassa fins aconseguir una massa homogènia i que no s'enganxi als dits.\n" +
                    "Es tapa amb film transparent i es deixa reposar durant tota la nit dins la nevera.\n" +
                    "L'endemà, es divideix la massa en 8 parts iguals.\n" +
                    "S'agafa cadascuna de les porcions de massa i s'aplana amb el corró fins aconseguir un gruix d'1 cm (i l'amplada, ha de ser menor a l'amplada de la màquina).\n" +
                    "S'enfarina bé, la massa allisada, per les dues bandes per tal que no s'enganxi a la màquina. I es passa primer per l'accessori llis, i de més gruixut a més prim. Per exemple, primer dues vegades pel número 2, després una pel 4 i finalment, una pel 5. (Comproveu les instruccions del fabricant). I per tal que no se us enganxi la massa a la màquina, cal enfarinar-la bé cada vegada!\n" +
                    "Quan la massa està estirada ben fina, es col·loca al damunt d'una superfície llisa i es van tallant els raviolis amb un tallador. (Sobretot, no deixeu assecar la pasta més d'una hora! Ja que després s'esquerda i es fa difícil poder tancar els raviolis).\n" +
                    "A continuació, es farceixen amb una culleradeta del farcit, es pinta la vora amb aigua, i es tanquen tot prement amb els dits.\n" +
                    "En aquest punt, es poden guardar dins un recipient hermètic a la nevera (ben enfarinats perquè no s'enganxin entre ells) o al congelador.");
            values.put(IMATGE,Imatge.getBytes(BitmapFactory.decodeResource(resources,R.drawable.pasta)));
            values.put(ID_TIPUS, "4");
            db.insert(TABLE_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "5");
            values.put(NOM_RECEPTA, "Iogurt casolà");
            values.put(RECEPTA, "Es bull la llet i es deixa refredar fins a temperatura ambient. Un cop freda, es cola. S'afegeix un iogurt natural que es dissol amb la llet. Tot seguit, es reparteix la mescla entre els 7 pots. Es col·loquen els pots dins la iogurtera (sense tapar) i es cobreixen amb la tapa de la iogurtera. Finalment, es posa la iogurtera en marxa i es deixa fermentar durant 8 hores. Un cop acabat, es tapen i es guarden a la nevera fins al moment de servir.\n" +
                    "\n" +
                    "Per aquells que no tinguin iogurtera, es pot fer igualment seguint el següent procediment:\n" +
                    "\n" +
                    "S'escalfa el forn a 50ºC (122ºF) i quan arriba a aquesta temperatura s'apaga. S'escalfa la llet en un cassó fins a 45ºC (113ºF), es retira del foc i s'hi barreja un iogurt natural. Es reparteix la barreja entre els pots i es tapen amb paper film. Es col·loquen a una plata de forn i es tapen amb un drap gruixut per tal que mantingui l'escalfor. Es deixen dins el forn durant 12 hores. Finalment, es guarden a la nevera.");
            values.put(IMATGE,Imatge.getBytes(BitmapFactory.decodeResource(resources,R.drawable.iogurt)));
            values.put(ID_TIPUS, "1");
            db.insert(TABLE_RECEPTA, KEY_ID, values);
            values.clear();

            //INICIALITZEM LA BD AMB ALGUN INGREDIENT
            values.put(KEY_ID, "1");
            values.put(NOM_INGREDIENT, "Farina d'ametlla");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "2");
            values.put(NOM_INGREDIENT, "Ametlla");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "3");
            values.put(NOM_INGREDIENT, "Sucre en pols");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "4");
            values.put(NOM_INGREDIENT, "Ou");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "5");
            values.put(NOM_INGREDIENT, "Vainilla");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "6");
            values.put(NOM_INGREDIENT, "Pinyons");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "7");
            values.put(NOM_INGREDIENT, "Meló");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "8");
            values.put(NOM_INGREDIENT, "Tarongina");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "9");
            values.put(NOM_INGREDIENT, "Iogurt natural");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "10");
            values.put(NOM_INGREDIENT, "Sucre");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "11");
            values.put(NOM_INGREDIENT, "Farina");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "12");
            values.put(NOM_INGREDIENT, "Sal");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "13");
            values.put(NOM_INGREDIENT, "Oli d'oliva");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "14");
            values.put(NOM_INGREDIENT, "Llet");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "15");
            values.put(NOM_INGREDIENT, "Ametlla Triturada");
            db.insert(TABLE_INGREDIENT, KEY_ID, values);
            values.clear();

            //INICIALITZEM LA BD AMB INGREDIENT DE RECEPTA
            values.put(KEY_ID, "1");
            values.put(ID_INGREDIENT, "1");
            values.put(ID_RECEPTA, "1");
            values.put(QUANTITAT, "250gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "2");
            values.put(ID_INGREDIENT, "3");
            values.put(ID_RECEPTA, "1");
            values.put(QUANTITAT, "375gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "3");
            values.put(ID_INGREDIENT, "4");
            values.put(ID_RECEPTA, "1");
            values.put(QUANTITAT, "3 clares");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "4");
            values.put(ID_INGREDIENT, "5");
            values.put(ID_RECEPTA, "1");
            values.put(QUANTITAT, "1/2 cullaradeta petita");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "6");
            values.put(ID_INGREDIENT, "2");
            values.put(ID_RECEPTA, "1");
            values.put(QUANTITAT, "350gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "7");
            values.put(ID_INGREDIENT, "15");
            values.put(ID_RECEPTA, "2");
            values.put(QUANTITAT, "500 gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "8");
            values.put(ID_INGREDIENT, "3");
            values.put(ID_RECEPTA, "2");
            values.put(QUANTITAT, "350 gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "9");
            values.put(ID_INGREDIENT, "4");
            values.put(ID_RECEPTA, "2");
            values.put(QUANTITAT, "3 rovells i 1 clara");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "10");
            values.put(ID_INGREDIENT, "5");
            values.put(ID_RECEPTA, "2");
            values.put(QUANTITAT, "1/2 cullerada petita");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "11");
            values.put(ID_INGREDIENT, "6");
            values.put(ID_RECEPTA, "2");
            values.put(QUANTITAT, "300 gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "12");
            values.put(ID_INGREDIENT, "7");
            values.put(ID_RECEPTA, "3");
            values.put(QUANTITAT, "500 gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "13");
            values.put(ID_INGREDIENT, "8");
            values.put(ID_RECEPTA, "3");
            values.put(QUANTITAT, "un parell de fulles");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "14");
            values.put(ID_INGREDIENT, "9");
            values.put(ID_RECEPTA, "3");
            values.put(QUANTITAT, "1");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "15");
            values.put(ID_INGREDIENT, "10");
            values.put(ID_RECEPTA, "3");
            values.put(QUANTITAT, "1 cullerada");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "16");
            values.put(ID_INGREDIENT, "11");
            values.put(ID_RECEPTA, "4");
            values.put(QUANTITAT, "500 gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "17");
            values.put(ID_INGREDIENT, "4");
            values.put(ID_RECEPTA, "4");
            values.put(QUANTITAT, "5");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "18");
            values.put(ID_INGREDIENT, "12");
            values.put(ID_RECEPTA, "4");
            values.put(QUANTITAT, "5 gr");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "19");
            values.put(ID_INGREDIENT, "13");
            values.put(ID_RECEPTA, "4");
            values.put(QUANTITAT, "30 ml");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "20");
            values.put(ID_INGREDIENT, "9");
            values.put(ID_RECEPTA, "5");
            values.put(QUANTITAT, "1");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            values.put(KEY_ID, "21");
            values.put(ID_INGREDIENT, "14");
            values.put(ID_RECEPTA, "5");
            values.put(QUANTITAT, "1 litre");
            db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
            values.clear();

            //INICIALITZEM LA BD AMB SUBSTITUTS
            values.put(KEY_ID, "1");
            values.put(ID_INGREDIENT_RECEPTA, "11");
            values.put(ID_INGREDIENT, "2");
            db.insert(TABLE_INGREDIENT_SUBSTITUT, KEY_ID, values);
            values.clear();

        }catch (SQLException e){
            Log.e("Data Base:","Error en inicialització");
        }
    }

    //TABLE TIPUS_RECEPTAS OPERATIONS

    /*
    * Afegir Tipus
    */
    public long createTipus(RecipeTipe tipus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOM_TIPUS, tipus.getName());

        // insert row
        long tag_tipus = db.insert(TABLE_TIPUS_RECEPTA, null, values);

        return tag_tipus;
    }

    /**
     * getting all tipus
     * */
    public List<RecipeTipe> getAllTipusRecepta() {
        List<RecipeTipe> tipus = new ArrayList<RecipeTipe>();
        String selectQuery = "SELECT  * FROM " + TABLE_TIPUS_RECEPTA
                + " ORDER BY " + NOM_TIPUS;;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                RecipeTipe rt = new RecipeTipe();
                rt.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                rt.setName(c.getString(c.getColumnIndex(NOM_TIPUS)));

                // adding to tags list
                tipus.add(rt);
            } while (c.moveToNext());
        }
        c.close();
        return tipus;
    }

    /**
     * getting Specific tipus by name
     * */
    public RecipeTipe getTipusReceptaByName(String nom) {
        RecipeTipe tipus = new RecipeTipe();
        String selectQuery = "SELECT  * FROM " + TABLE_TIPUS_RECEPTA
                +" WHERE " + NOM_TIPUS + "= '" + nom +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            tipus.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            tipus.setName(c.getString(c.getColumnIndex(NOM_TIPUS)));
        }
        c.close();
        return tipus;
    }

    /**
     * getting Specific tipus
     * */
    public RecipeTipe getTipusReceptaById(Long id) {
        RecipeTipe tipus = new RecipeTipe();
        String selectQuery = "SELECT  * FROM " + TABLE_TIPUS_RECEPTA
                +" WHERE " + KEY_ID + "=" + id ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            tipus.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            tipus.setName(c.getString(c.getColumnIndex(NOM_TIPUS)));
        }
        c.close();
        return tipus;
    }
    public int countTipusById(int id){
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_RECEPTA
                +" WHERE " + ID_TIPUS + "=" + id ;
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    /**
     * remove tipus per id
     * */
    public void removeTipusById(int id) {
        int count = countTipusById(id);
        if(count == 1) {
            //Borrem de la bd tots els recipe ingredients
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db.delete(TABLE_TIPUS_RECEPTA, KEY_ID + " = " + id, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TABLE INGREDIENT OPERATIONS

    /*
    * Afegir Ingredient
    */
    public long createIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOM_INGREDIENT, ingredient.getName());

        // insert row
        long tag_ingredient = db.insert(TABLE_INGREDIENT, null, values);

        return tag_ingredient;
    }

    /**
     * getting all Ingredients
     * */
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENT + " ORDER BY " + NOM_INGREDIENT;;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                ingredient.setName(c.getString(c.getColumnIndex(NOM_INGREDIENT)));

                // adding to tags list
                ingredients.add(ingredient);
            } while (c.moveToNext());
        }
        c.close();
        return ingredients;
    }

    /**
     * getting Specific ingredient by name
     * */
    public Ingredient getIngredientByName(String nom) {
        Ingredient ingredient = new Ingredient();
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENT
                +" WHERE " + NOM_INGREDIENT + "= '" + nom +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            ingredient.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            ingredient.setName(c.getString(c.getColumnIndex(NOM_INGREDIENT)));
        }
        c.close();
        return ingredient;
    }

    /**
     * getting Specific ingredient by id
     * */
    public Ingredient getIngredientById(Long id) {
        Ingredient ingredient = new Ingredient();
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENT
                +" WHERE " + KEY_ID + "=" + id ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            ingredient.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            ingredient.setName(c.getString(c.getColumnIndex(NOM_INGREDIENT)));
        }
        c.close();
        return ingredient;
    }

    public int countIngredientById(int id){
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_INGREDIENT_RECEPTA
                +" WHERE " + ID_INGREDIENT + "=" + id ;
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    /**
     * remove ingredient per id
     * */
    public void removeIngredientById(int id) {
        int count = countIngredientById(id);
        if(count == 1) {
            //Borrem de la bd tots els recipe ingredients
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db.delete(TABLE_INGREDIENT, KEY_ID + " = " + id, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TABLE INGREDIENT SUBSTITUT

    /*
   * Afegir Ingredient substitut
   */
    public long createSubstitut(int principal, Ingredient substitut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_INGREDIENT_RECEPTA, principal);
        if(substitut.getId() > 0) values.put(ID_INGREDIENT, substitut.getId());
        else {
            int trobat =  getIngredientByName(substitut.getName()).getId();
            if(trobat > 0)values.put(ID_INGREDIENT,trobat);
            else{
                Long id = createIngredient(substitut);
                values.put(ID_INGREDIENT,id);
            }
        }

        // insert row
        long tag_substitut = db.insert(TABLE_INGREDIENT_SUBSTITUT, null, values);

        return tag_substitut;
    }

    /**
     * get substituts by principal
     * */
    public List<Ingredient> getAllSubstituts(int principal) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String selectQuery = "SELECT DISTINCT " + ID_INGREDIENT + " FROM " + TABLE_INGREDIENT_SUBSTITUT
                                + " WHERE " + ID_INGREDIENT_RECEPTA + "=" + principal;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(c.getInt((c.getColumnIndex(ID_INGREDIENT))));
                ingredient.setName(getIngredientById((long)ingredient.getId()).getName());

                // adding to tags list
                ingredients.add(ingredient);
            } while (c.moveToNext());
        }
        c.close();
        return ingredients;
    }

    /**
     * remove substitud per id
     * */
    public void removeSubstitutById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_INGREDIENT_SUBSTITUT, ID_INGREDIENT_RECEPTA + " = " + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //TABLE INGREDIENT_RECEPTA OPERATIONS

     /*
   * Afegir Ingredient de recepta
   */
    public long createIngredientRecepta(RecipeIngredient recipeIngredient, Long id_recepta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        int principal = recipeIngredient.getPrincipal().getId();
        if (principal > 0) values.put(ID_INGREDIENT,principal);
        else{
            Ingredient cercat = getIngredientByName(recipeIngredient.getPrincipal().getName());
            if(cercat.getId() > 0)values.put(ID_INGREDIENT,cercat.getId());
            else{
                Long id_creat = createIngredient(recipeIngredient.getPrincipal());
                values.put(ID_INGREDIENT,id_creat);
            }
        }
        values.put(ID_RECEPTA,id_recepta);
        values.put(QUANTITAT,recipeIngredient.getQuantitat());

        // insert row
        long tag_ingredient_recepta = db.insert(TABLE_INGREDIENT_RECEPTA, null, values);
        recipeIngredient.setId((int)tag_ingredient_recepta);
        List<Ingredient> substituts = recipeIngredient.getSubstitutes();
        for (int i = 0; i < substituts.size(); ++i){
         createSubstitut((int)tag_ingredient_recepta,substituts.get(i));
        }
        return tag_ingredient_recepta;
    }

    /**
     * get All recipe Ingredient
     * */
    public List<RecipeIngredient> getAllRecipeIngredient(int id_recepta) {
        List<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT_RECEPTA
                + " WHERE " + ID_RECEPTA + "=" + id_recepta;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt((c.getColumnIndex(KEY_ID)));
                Ingredient principal = getIngredientById((long)c.getInt((c.getColumnIndex(ID_INGREDIENT))));
                String quantitat = c.getString(c.getColumnIndex(QUANTITAT));
                RecipeIngredient recipeIngredient = new RecipeIngredient(id,principal,quantitat);
                recipeIngredient.setSubstitutes(getAllSubstituts(id));

                // adding to tags list
                ingredients.add(recipeIngredient);
            } while (c.moveToNext());
        }
        c.close();
        return ingredients;
    }


    /**
     * remove recipeIngredient by id and ingredients associated to it
     * */
    public void removeRecipeIngredientById(int id_recepta) {
        //esborrem els ingredients i substituts associats
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT_RECEPTA
                + " WHERE " + ID_RECEPTA + "=" + id_recepta;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt((c.getColumnIndex(KEY_ID)));
                removeSubstitutById(id);
                int id_ingredient = c.getInt((c.getColumnIndex(ID_INGREDIENT)));
                removeIngredientById(id_ingredient);
            } while (c.moveToNext());
        }
        c.close();

        //Borrem de la bd tots els recipe ingredients
        db = this.getWritableDatabase();
        try
        {
            db.delete(TABLE_INGREDIENT_RECEPTA, ID_RECEPTA  + " = " + id_recepta, null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //TABLE RECEPTA OPERATIONS

    /*
   * Afegir Recepta
   */
    public long createRecepta(Recipe recepta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOM_RECEPTA, recepta.getNom());
        values.put(RECEPTA,recepta.getRecepta());
        values.put(IMATGE,Imatge.getBytes(recepta.getImatge()));
        int tipus = recepta.getType().getId();
        if(tipus > 0)values.put(ID_TIPUS,tipus);
        else {
            int trobat = getTipusReceptaByName(recepta.getType().getName()).getId();
            if (trobat > 0) values.put(ID_TIPUS, trobat);
            else {
                Long id = createTipus(recepta.getType());
                values.put(ID_TIPUS, id);
            }
        }
        // insert row
        long tag_recepta = db.insert(TABLE_RECEPTA, null, values);
        List<RecipeIngredient> ingredients = recepta.getIngedients();
        for(int i = 0; i < ingredients.size();++i){
            createIngredientRecepta(ingredients.get(i),tag_recepta);
        }
        return tag_recepta;
    }

    /**
     * getting Specific recipe by name
     * */
    public Recipe getRecipeByName(String nom) {
        Recipe recipe = new Recipe(resources);
        String selectQuery = "SELECT  * FROM " + TABLE_RECEPTA
                +" WHERE " + NOM_RECEPTA + "= '" + nom +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            recipe.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            recipe.setNom(c.getString(c.getColumnIndex(NOM_RECEPTA)));
            recipe.setRecepta(c.getString(c.getColumnIndex(RECEPTA)));
            int id_tipus = c.getInt(c.getColumnIndex(ID_TIPUS));
            recipe.setType(getTipusReceptaById((long)id_tipus));
            byte[] blob = c.getBlob(c.getColumnIndex(IMATGE));
            recipe.setImatge(Imatge.getPhoto(blob));
            recipe.setIngedients(getAllRecipeIngredient(recipe.getId()));
        }
        c.close();
        return recipe;
    }

    /**
     * getting Specific recipe by id
     * */
    public Recipe getRecipeById(int id) {
        Recipe recipe = new Recipe(resources);
        String selectQuery = "SELECT  * FROM " + TABLE_RECEPTA
                +" WHERE " + KEY_ID + "= " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            recipe.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            recipe.setNom(c.getString(c.getColumnIndex(NOM_RECEPTA)));
            recipe.setRecepta(c.getString(c.getColumnIndex(RECEPTA)));
            int id_tipus = c.getInt(c.getColumnIndex(ID_TIPUS));
            recipe.setType(getTipusReceptaById((long)id_tipus));
            byte[] blob = c.getBlob(c.getColumnIndex(IMATGE));
            recipe.setImatge(Imatge.getPhoto(blob));
            recipe.setIngedients(getAllRecipeIngredient(recipe.getId()));
        }
        c.close();
        return recipe;
    }

    public List<Recipe> getAllRecipes(){
        List<Recipe> receptas = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_RECEPTA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(resources);
                recipe.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                recipe.setNom(c.getString(c.getColumnIndex(NOM_RECEPTA)));
                recipe.setRecepta(c.getString(c.getColumnIndex(RECEPTA)));
                int id_tipus = c.getInt(c.getColumnIndex(ID_TIPUS));
                recipe.setType(getTipusReceptaById((long)id_tipus));
                recipe.setImatge(Imatge.getPhoto(c.getBlob(c.getColumnIndex(IMATGE))));
                recipe.setIngedients(getAllRecipeIngredient(recipe.getId()));

                // adding to tags list
                receptas.add(recipe);
            } while (c.moveToNext());
        }
        c.close();
        return receptas;
    }

    public List<Recipe> getVista(){
        List<Recipe> receptas = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_RECEPTA + " ORDER BY " + NOM_RECEPTA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(resources);
                recipe.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                recipe.setNom(c.getString(c.getColumnIndex(NOM_RECEPTA)));
                int id_tipus = c.getInt(c.getColumnIndex(ID_TIPUS));
                recipe.setType(getTipusReceptaById((long)id_tipus));
                recipe.setImatge(Imatge.getPhoto(c.getBlob(c.getColumnIndex(IMATGE))));
                // adding to tags list
                receptas.add(recipe);
            } while (c.moveToNext());
        }
        c.close();
        return receptas;
    }

    public List<Recipe> filtraPerNom(String s){
        List<Recipe> receptas = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_RECEPTA + " WHERE "
                + NOM_RECEPTA + " LIKE ? ORDER BY " + NOM_RECEPTA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[] { "%" + s + "%" });


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(resources);
                recipe.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                recipe.setNom(c.getString(c.getColumnIndex(NOM_RECEPTA)));
                int id_tipus = c.getInt(c.getColumnIndex(ID_TIPUS));
                recipe.setType(getTipusReceptaById((long)id_tipus));
                recipe.setImatge(Imatge.getPhoto(c.getBlob(c.getColumnIndex(IMATGE))));
                // adding to tags list
                receptas.add(recipe);
            } while (c.moveToNext());
        }
        c.close();
        return receptas;
    }

    public List<Recipe> filtraPerTipus(int i){
        List<Recipe> receptas = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_RECEPTA + " WHERE "
                + ID_TIPUS + "=" + i + " ORDER BY " + NOM_RECEPTA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(resources);
                recipe.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                recipe.setNom(c.getString(c.getColumnIndex(NOM_RECEPTA)));
                int id_tipus = c.getInt(c.getColumnIndex(ID_TIPUS));
                recipe.setType(getTipusReceptaById((long)id_tipus));
                recipe.setImatge(Imatge.getPhoto(c.getBlob(c.getColumnIndex(IMATGE))));
                // adding to tags list
                receptas.add(recipe);
            } while (c.moveToNext());
        }
        c.close();
        return receptas;
    }

    public List<Recipe> filtraPerIngredient(int i){
        List<Recipe> receptas = new ArrayList<Recipe>();
        String selectQuery = "SELECT " + ID_RECEPTA + " FROM " + TABLE_INGREDIENT_RECEPTA + " WHERE "
                + ID_INGREDIENT + "=" + i;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int idRecepta = c.getInt(c.getColumnIndex(ID_RECEPTA));
                Recipe recipe = getRecipeById(idRecepta);
                // adding to tags list
                receptas.add(recipe);
            } while (c.moveToNext());
        }
        c.close();
        return receptas;
    }

    /**
     * remove recipe by id and ingredients associated to it
     * */
    public void removeRecipeById(int id_recepta,int id_tipus) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            removeRecipeIngredientById(id_recepta);
            removeTipusById(id_tipus);
            db.delete(TABLE_RECEPTA, KEY_ID  + " = " + id_recepta, null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}


