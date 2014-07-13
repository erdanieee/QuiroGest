package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 22/10/13.
 */
public class TablaContactos implements BaseColumns{
    /** nombre de la tabla */
    public static final String TABLA_CONTACTOS = "pacientes";

    //MySQLlite FIELDS
    public static final String COL_NOMBRE           = "nombre"          + "_" + TABLA_CONTACTOS;
    public static final String COL_APELLIDO1        = "apellido1"       + "_" + TABLA_CONTACTOS;
    public static final String COL_APELLIDO2        = "apellido2"       + "_" + TABLA_CONTACTOS;
    public static final String COL_MOVIL            = "movil"           + "_" + TABLA_CONTACTOS;
    public static final String COL_FIJO             = "fijo"            + "_" + TABLA_CONTACTOS;
    public static final String COL_EMAIL            = "email"           + "_" + TABLA_CONTACTOS;
    public static final String COL_DIRECCION        = "direccion"       + "_" + TABLA_CONTACTOS;
    public static final String COL_CP               = "cp"              + "_" + TABLA_CONTACTOS;
    public static final String COL_LOCALIDAD        = "localidad"       + "_" + TABLA_CONTACTOS;
    public static final String COL_PROVINCIA        = "provincia"       + "_" + TABLA_CONTACTOS;
    public static final String COL_FECHA_NAC        = "fechaNacimiento" + "_" + TABLA_CONTACTOS;
    public static final String COL_PROFESION        = "profesion"       + "_" + TABLA_CONTACTOS;
    public static final String COL_ENFERMEDADES     = "enfermedades"    + "_" + TABLA_CONTACTOS;
    public static final String COL_ALERGIAS         = "alergias"        + "_" + TABLA_CONTACTOS;
    public static final String COL_OBSERVACIONES    = "observaciones"   + "_" + TABLA_CONTACTOS;
    public static final String COL_FOTO             = "foto"            + "_" + TABLA_CONTACTOS;


    public static final String sqlCreateTableClientes = "CREATE TABLE " + TABLA_CONTACTOS + " (" +
            _ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOMBRE          + " TEXT, " +
            COL_APELLIDO1       + " TEXT, " +
            COL_APELLIDO2       + " TEXT, " +
            COL_MOVIL           + " TEXT, " +
            COL_FIJO            + " TEXT, " +
            COL_EMAIL           + " TEXT, " +
            COL_DIRECCION       + " TEXT, " +
            COL_CP              + " TEXT, " +
            COL_LOCALIDAD       + " TEXT, " +
            COL_PROVINCIA       + " TEXT, " +
            COL_FECHA_NAC       + " TEXT DEFAULT '1981-12-14', " +
            COL_PROFESION       + " TEXT, " +
            COL_ENFERMEDADES    + " TEXT, " +
            COL_ALERGIAS        + " TEXT, " +
            COL_FOTO            + " BLOB, " +
            COL_OBSERVACIONES   + " TEXT)";
}
