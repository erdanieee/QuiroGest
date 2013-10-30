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
}
