package dan.android.quirogest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dan on 25/08/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuiroGestDB";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context contexto){
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(this.getClass().getSimpleName(), "Creando base de datos");
        sqLiteDatabase.execSQL(TablaClientes.sqlCreateTableClientes);
        //TODO!!
        //sqLiteDatabase.execSQL(sqlCreateTableMotivos);
        //sqLiteDatabase.execSQL(sqlCreateTableSesiones);
        //sqLiteDatabase.execSQL(sqlCreateTableTecnicas);
        //sqLiteDatabase.execSQL(sqlCreateTableDescrTecnicas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //TODO!!
    }




}
