package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaMotivos implements BaseColumns{
    public static final String TABLA_MOTIVOS = "motivosDeConsulta";

    public static final String COL_ID_CONTACTO     = "idContacto"                   + "_" + TABLA_MOTIVOS;
    public static final String COL_FECHA           = "fecha"                        + "_" + TABLA_MOTIVOS;                                       // FORMATO SQLITE_DATE_FORMAT
    public static final String COL_DESCRIPCION     = "descripcionMotivoConsulta"    + "_" + TABLA_MOTIVOS;
    public static final String COL_COMIENZO        = "comienzoDolor"                + "_" + TABLA_MOTIVOS;    // FORMATO SQLITE_DATE_FORMAT
    public static final String COL_ESTADO_SALUD    = "estadoSaludGeneral"           + "_" + TABLA_MOTIVOS;    // FORMATO: [enum EstadoSalud.toSQLite()]
    public static final String COL_ACTIV_FISICA    = "actividadFisica"              + "_" + TABLA_MOTIVOS;
    public static final String COL_DIAGNOSTICO     = "diagnosticoGeneral"           + "_" + TABLA_MOTIVOS;
    public static final String COL_OBSERVACIONES   = "observaciones"                + "_" + TABLA_MOTIVOS;


    public enum EstadoSalud {
        MALO,
        ACEPTABLE,
        BUENO,
        EXCELENTE;

        public int toSQLite(){return this.ordinal();}
        public EstadoSalud fromSQLite(int o){
            switch (o){
                case 0:
                    return MALO;
                case 1:
                    return ACEPTABLE;
                case 2:
                    return BUENO;
                case 3:
                    return EXCELENTE;
            }
            return null;
        }
    }


    public static final String sqlCreateTableMotivos = "CREATE TABLE " + TABLA_MOTIVOS + " (" +
            _ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_CONTACTO     + " INTEGER, " +
            COL_FECHA           + " TEXT DEFAULT CURRENT_DATE, " +
            COL_DESCRIPCION     + " TEXT, " +
            COL_COMIENZO        + " TEXT, " +
            COL_ESTADO_SALUD    + " INTEGER DEFAULT -1, " +
            COL_ACTIV_FISICA    + " TEXT, " +
            COL_DIAGNOSTICO     + " TEXT, " +
            COL_OBSERVACIONES   + " TEXT)";
}
