package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 22/10/13.
 */
public class TablaClientes implements BaseColumns{
    /** nombre de la tabla */
    public static final String TABLA_CLIENTES = "clientes";

    //MySQLlite FIELDS
    public static final String COL_NOMBRE           = "nombre_TablaCliente";
    public static final String COL_APELLIDO1        = "apellido1_TablaCliente";
    public static final String COL_APELLIDO2        = "apellido2_TablaCliente";
    public static final String COL_MOVIL            = "movil_TablaCliente";
    public static final String COL_FIJO             = "fijo_TablaCliente";
    public static final String COL_EMAIL            = "email_TablaCliente";
    public static final String COL_DIRECCION        = "direccion_TablaCliente";
    public static final String COL_CP               = "cp_TablaCliente";
    public static final String COL_LOCALIDAD        = "localidad_TablaCliente";
    public static final String COL_PROVINCIA        = "provincia_TablaCliente";
    public static final String COL_FECHA_NAC        = "fechaNacimiento_TablaCliente";
    public static final String COL_PROFESION        = "profesion_TablaCliente";
    public static final String COL_ENFERMEDADES     = "enfermedades_TablaCliente";
    public static final String COL_ALERGIAS         = "alergias_TablaCliente";
    public static final String COL_OBSERVACIONES    = "observaciones_TablaCliente";


    public static final String sqlCreateTableClientes = "CREATE TABLE " + TABLA_CLIENTES + " (" +
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
            COL_FECHA_NAC       + " TEXT, " +
            COL_PROFESION       + " TEXT, " +
            COL_ENFERMEDADES    + " TEXT, " +
            COL_ALERGIAS        + " TEXT, " +
            COL_OBSERVACIONES   + " TEXT)";
}
