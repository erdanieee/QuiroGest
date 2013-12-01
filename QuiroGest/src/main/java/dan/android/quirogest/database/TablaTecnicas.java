package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaTecnicas implements BaseColumns {
    public static final String TABLA_TECNICAS = "tecnicas";

    public static final String COL_ID_SESION        = "idSesion"            + "_" + TABLA_TECNICAS;
    public static final String COL_ID_TIPO_TECNICA  = "idTipoTecnica"       + "_" + TABLA_TECNICAS;
    public static final String COL_FECHA            = "fecha"               + "_" + TABLA_TECNICAS;
    public static final String COL_VALOR            = "resultadoTecnica"    + "_" + TABLA_TECNICAS;
    public static final String COL_OBSERVACIONES    = "observaciones"       + "_" + TABLA_TECNICAS;

    public static final String sqlCreateTableTecnicas = "CREATE TABLE " + TABLA_TECNICAS + " (" +
            _ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_SESION           + " INTEGER, " +
            COL_ID_TIPO_TECNICA     + " INTEGER, " +
            COL_FECHA               + " TEXT DEFAULT CURRENT_DATE, " +
            COL_VALOR               + " TEXT, " +
            COL_OBSERVACIONES       + " TEXT)";
}