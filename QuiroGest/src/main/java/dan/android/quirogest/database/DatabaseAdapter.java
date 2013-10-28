package dan.android.quirogest.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by dlopez on 23/10/13.
 */
public class DatabaseAdapter {
    //TODO: eliminar esta clase porque ha sido sustituida por un ContentProvider
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context contexto;


    public DatabaseAdapter (Context c){
        contexto = c;
    }


    public SQLiteDatabase openToRead(){
        dbHelper = new DatabaseHelper(contexto);
        db       = dbHelper.getReadableDatabase();
        return db;
    }


    public SQLiteDatabase openToWrite(){
        dbHelper = new DatabaseHelper(contexto);
        db       = dbHelper.getWritableDatabase();
        return db;
    }


    public void close(){
        dbHelper.close();
    }


    public long insertContacto (Contacto c){
        Log.i(this.getClass().getSimpleName(), "Insertando contacto " + c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2());
        return TablaContactos.insert(c, db);
    }


    public Cursor getAllContactsCursor (){
        Log.i(this.getClass().getSimpleName(), "Obteniendo lista de contactos...");
        return TablaContactos.getCursorAll(db);
    }


    public Cursor getContactsCursor (long id){
        Log.i(this.getClass().getSimpleName(), "Obteniendo contacto con ID " + id);
        return TablaContactos.getCursorAll(db);
    }
}
