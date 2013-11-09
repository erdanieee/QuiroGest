package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaSesiones  implements BaseColumns {
    public static final String TABLA_SESIONES = "sesiones";

    public static final String COL_ID_MOTIVO        = "idMotivo";
    public static final String COL_NUM_SESION       = "numeroDeSesion";
    public static final String COL_FECHA            = "fecha";                                       // FORMATO SQLITE_DATE_FORMAT
    public static final String COL_DOLOR            = "cuantificacionDelDolor";
    public static final String COL_POSTRATAMIENTO   = "escuelaPostratamiento";
    public static final String COL_DIAGNOSTICO      = "diagnosticoEspecifico";
    public static final String COL_OBSERVACIONES    = "observaciones";
    public static final String COL_INGRESOS         = "ingresos";


    public enum CuantificacionDolor {
        DOLOR_0,
        DOLOR_1,
        DOLOR_2,
        DOLOR_3,
        DOLOR_4,
        DOLOR_5,
        DOLOR_6,
        DOLOR_7,
        DOLOR_8,
        DOLOR_9,
        DOLOR_10;

        public int toSQLite(){return this.ordinal();}
        public CuantificacionDolor fromSQLite(int o){
            switch (o){
                case 0:
                    return DOLOR_0;
                case 1:
                    return DOLOR_1;
                case 2:
                    return DOLOR_2;
                case 3:
                    return DOLOR_3;
                case 4:
                    return DOLOR_4;
                case 5:
                    return DOLOR_5;
                case 6:
                    return DOLOR_6;
                case 7:
                    return DOLOR_7;
                case 8:
                    return DOLOR_8;
                case 9:
                    return DOLOR_9;
                case 10:
                    return DOLOR_10;
            }
            return null;
        }
    }


    public static final String sqlCreateTableSesiones = "CREATE TABLE " + TABLA_SESIONES + " (" +
            _ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_MOTIVO       + " INTEGER, " +
            COL_NUM_SESION      + " INTEGER, " +
            COL_FECHA           + " TEXT DEFAULT CURRENT_DATE, " +
            COL_DOLOR           + " INTEGER, " +
            COL_POSTRATAMIENTO  + " TEXT, " +
            COL_DIAGNOSTICO     + " TEXT, " +
            COL_INGRESOS        + " INTEGER, " +
            COL_OBSERVACIONES   + " TEXT)";
}
