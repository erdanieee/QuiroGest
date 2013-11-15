package dan.android.quirogest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dan on 25/08/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME       = "QuiroGestDB";
    public static final String SQLITE_DATE_FORMAT   = "yyyy-MM-dd";  // Usado por SimpleDateFormat. <br>Debe tener el mismo formato que CURRENT_DATE de SQLite
    private static final int DATABASE_VERSION       = 1;


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


    /** transforma fechas de SQLite al formato especificado */
    public static String parseSQLiteDate(String date, SimpleDateFormat format) {
        String d = null;
        d = format.format(parseSQLiteDate(date));

        return d;
    }

    public static Date parseSQLiteDate(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat(SQLITE_DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

}
