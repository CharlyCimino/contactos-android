package caemci.aplicacioncontactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Charly on 15/10/2016.
 */

public class BaseDeDatos extends SQLiteOpenHelper {

    private static final int version=1;
    private static final String tabla_contactos="CREATE TABLE TablaContactos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, mail TEXT, telefono TEXT)";

    public BaseDeDatos(Context context) {
        super(context, "contactos", null,version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla_contactos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tabla_contactos);
        onCreate(db);
    }

    public int insertarContacto(String nom, String ape, String mail, String tel){

        long posicion = 0;
        SQLiteDatabase db=getWritableDatabase();
        if(db !=null)
        {
            ContentValues valores=new ContentValues();
            valores.put("id", obtenerProximoId());
            valores.put("nombre",nom);
            valores.put("apellido",ape);
            valores.put("mail",mail);
            valores.put("telefono",tel);
            posicion = db.insert("TablaContactos",null,valores);
            db.close();
        }
        return (int) posicion;
    }

    private int obtenerProximoId()
    {
        SQLiteDatabase db = getReadableDatabase();
        String[] campos = {"id"};
        Cursor cur = db.query("TablaContactos", campos, null, null, null, null, null, null);
        cur.moveToFirst();
        if (cur.getCount() == 0)
        {
            return 1;
        }
        else
        {
            cur.moveToLast();
            Log.i("Mensaje",  "Se devolvió " +  (1 + cur.getInt(0) ) + "\n\n");
            return 1 + cur.getInt(0);
        }

    }

    public ArrayList<Contacto> obtenerContactos ()
    {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Contacto> listaDeContactos = new ArrayList<Contacto>();
        String[] campos = {"id", "nombre", "apellido", "mail", "telefono"};
        Cursor cur = db.query("TablaContactos", campos, null, null, null, null, "nombre");
        cur.moveToFirst();
        if (cur.getCount() == 0)
        {
            return null;
        }
        else
        {
            do
            {
                Contacto contacto = new Contacto(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4));
                listaDeContactos.add(contacto);
            }while(cur.moveToNext());
        }

        db.close();
        cur.close();
        return listaDeContactos;
    }

    public Contacto obtenerElContacto(int id){
        SQLiteDatabase db = getReadableDatabase();
        String[] campos = {"id", "nombre", "apellido", "mail", "telefono"};
        Cursor cur = db.query("TablaContactos", campos, "id="+ id, null, null, null, null, null);
        cur.moveToFirst();

        if(cur!=null)
            cur.moveToFirst();
        Contacto persona = new Contacto(cur.getInt(0),cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4));

        db.close();
        cur.close();
        return persona;
    }

    public void modificarContacto(Contacto persona){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("id",persona.getId());
        valores.put("nombre",persona.getNombre());
        valores.put("apellido",persona.getApellido());
        valores.put("mail",persona.getMail());
        valores.put("telefono",persona.getTelefono());
        db.update("TablaContactos",valores,"id="+persona.getId(),null);
        db.close();
    }

    public void borrarContacto(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("TablaContactos", "id="+id, null);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("TablaContactos", null, null);
    }
}
