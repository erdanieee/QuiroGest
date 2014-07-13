package dan.android.quirogest.database;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        sqLiteDatabase.execSQL(TablaContactos.sqlCreateTableClientes);
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


    //TODO: Añadir función para transformar a formato SQL a partir de SimpleDateFormat!!!

    /** transforma fechas de SQLite al formato especificado */
    public static String parseSQLiteToDateformat(String date, SimpleDateFormat format) {
        String d = null;

        if (date!=null && format!=null) {
            d = format.format(parseSQLiteDate(date));
        }

        return d;
    }


    public static String parseToSQLite(Date d) {
        return new SimpleDateFormat(SQLITE_DATE_FORMAT).format(d);
    }

    public static String parseToSQLite(int year, int month, int day) {
        Calendar c;

        c   = Calendar.getInstance();
        c.set(year, month, day);

        return parseToSQLite(c.getTime());
    }


    public static Date parseSQLiteDate(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat(SQLITE_DATE_FORMAT).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }


    public long count(String table, String selection, String[] selectionArgs){
        long c;
        c = DatabaseUtils.queryNumEntries(getReadableDatabase(), table, selection, selectionArgs);
        return c;
    }
}
