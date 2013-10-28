package dan.android.quirogest.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlopez on 22/10/13.
 */
public class TablaContactos implements BaseColumns{
    /** nombre de la tabla */
    public static final String TABLA_CONTACTOS = "contactos";

    //MySQLlite FIELDS
    public static final String COL_NOMBRE           = "nombre";
    public static final String COL_APELLIDO1        = "apellido1";
    public static final String COL_APELLIDO2        = "apellido2";
    public static final String COL_MOVIL            = "movil";
    public static final String COL_FIJO             = "fijo";
    public static final String COL_DIRECCION        = "direccion";
    public static final String COL_CP               = "cp";
    public static final String COL_LOCALIDAD        = "localidad";
    public static final String COL_PROVINCIA        = "provincia";
    public static final String COL_FECHA_NAC        = "fechaNacimiento";
    public static final String COL_PROFESION        = "profesion";
    public static final String COL_ENFERMEDADES     = "enfermedades";
    public static final String COL_ALERGIAS         = "alergias";
    public static final String COL_OBSERVACIONES    = "observaciones";


    public static final String sqlCreateTable = "CREATE TABLE " + TABLA_CONTACTOS + " (" +
            _ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOMBRE          + " TEXT, " +
            COL_APELLIDO1       + " TEXT, " +
            COL_APELLIDO2       + " TEXT, " +
            COL_MOVIL           + " TEXT, " +
            COL_FIJO            + " TEXT, " +
            COL_DIRECCION       + " TEXT, " +
            COL_CP              + " TEXT, " +
            COL_LOCALIDAD       + " TEXT, " +
            COL_PROVINCIA       + " TEXT, " +
            COL_FECHA_NAC       + " TEXT, " +
            COL_PROFESION       + " TEXT, " +
            COL_ENFERMEDADES    + " TEXT, " +
            COL_ALERGIAS        + " TEXT, " +
            COL_OBSERVACIONES   + " TEXT)";


    public static long insert (Contacto c, SQLiteDatabase db){
        ContentValues cv;

        cv = new ContentValues();
        cv.put(COL_NOMBRE,        c.getNombre());
        cv.put(COL_APELLIDO1,     c.getApellido1());
        cv.put(COL_APELLIDO2,     c.getApellido2());
        cv.put(COL_MOVIL,         c.getMovil());
        cv.put(COL_FIJO,          c.getFijo());
        cv.put(COL_DIRECCION,     c.getDireccion());
        cv.put(COL_CP,            c.getCp());
        cv.put(COL_LOCALIDAD,     c.getLocalidad());
        cv.put(COL_PROVINCIA,     c.getProvincia());
        cv.put(COL_FECHA_NAC,     c.getFechaNac());
        cv.put(COL_PROFESION,     c.getProfesion());
        cv.put(COL_ENFERMEDADES,  c.getEnfermedades());
        cv.put(COL_ALERGIAS,      c.getAlergias());
        cv.put(COL_OBSERVACIONES, c.getObservaciones());

        return db.insert(TABLA_CONTACTOS,null,cv);
    }


    public static Contacto get(Long id, SQLiteDatabase db){
        Contacto contacto = null;
        Cursor c;

        c = db.query(TABLA_CONTACTOS, null, "rowid=?", new String[] {id.toString()}, null, null, null);

        if (c.moveToFirst()){
            contacto = new Contacto(id,
                    c.getString(c.getColumnIndex(COL_NOMBRE)),
                    c.getString(c.getColumnIndex(COL_APELLIDO1)),
                    c.getString(c.getColumnIndex(COL_APELLIDO2)),
                    c.getString(c.getColumnIndex(COL_MOVIL)),
                    c.getString(c.getColumnIndex(COL_FIJO)),
                    c.getString(c.getColumnIndex(COL_DIRECCION)),
                    c.getString(c.getColumnIndex(COL_CP)),
                    c.getString(c.getColumnIndex(COL_LOCALIDAD)),
                    c.getString(c.getColumnIndex(COL_PROVINCIA)),
                    c.getString(c.getColumnIndex(COL_FECHA_NAC)),
                    c.getString(c.getColumnIndex(COL_PROFESION)),
                    c.getString(c.getColumnIndex(COL_ENFERMEDADES)),
                    c.getString(c.getColumnIndex(COL_ALERGIAS)),
                    c.getString(c.getColumnIndex(COL_OBSERVACIONES))
                    );
        }

        return contacto;
    }


    public static List<Contacto> getAll (SQLiteDatabase db){
        ArrayList<Contacto> listaContactos;
        Cursor c;

        listaContactos  = new ArrayList<Contacto>();
        c               = db.query(TABLA_CONTACTOS, null, null, null, null, null, null);

        if (c.moveToFirst()){
            do {
                listaContactos.add(new Contacto(
                        c.getLong(0),
                        c.getString(c.getColumnIndex(COL_NOMBRE)),
                        c.getString(c.getColumnIndex(COL_APELLIDO1)),
                        c.getString(c.getColumnIndex(COL_APELLIDO2)),
                        c.getString(c.getColumnIndex(COL_MOVIL)),
                        c.getString(c.getColumnIndex(COL_FIJO)),
                        c.getString(c.getColumnIndex(COL_DIRECCION)),
                        c.getString(c.getColumnIndex(COL_CP)),
                        c.getString(c.getColumnIndex(COL_LOCALIDAD)),
                        c.getString(c.getColumnIndex(COL_PROVINCIA)),
                        c.getString(c.getColumnIndex(COL_FECHA_NAC)),
                        c.getString(c.getColumnIndex(COL_PROFESION)),
                        c.getString(c.getColumnIndex(COL_ENFERMEDADES)),
                        c.getString(c.getColumnIndex(COL_ALERGIAS)),
                        c.getString(c.getColumnIndex(COL_OBSERVACIONES))
                ));

            } while (c.moveToNext());
        }

        return listaContactos;
    }


    public static Cursor getCursorAll (SQLiteDatabase db){
        return db.query(TABLA_CONTACTOS, null, null, null, null, null, null);
    }
}
