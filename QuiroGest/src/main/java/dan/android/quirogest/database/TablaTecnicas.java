package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaTecnicas implements BaseColumns {
    public static final String TABLA_TECNICAS = "tecnicas";

    public static final String COL_ID_SESION        = "idSesion";
    public static final String COL_ID_TECNICA       = "idTipoTecnica";
    public static final String COL_FECHA            = "fecha";
    public static final String COL_RESULTADO        = "resultadoTecnica";
    public static final String COL_OBSERVACIONES    = "observaciones";

    public static final String sqlCreateTableTecnicas = "CREATE TABLE " + TABLA_TECNICAS + " (" +
            _ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_SESION           + " INTEGER, " +
            COL_ID_TECNICA          + " INTEGER, " +
            COL_FECHA               + " TEXT DEFAULT CURRENT_DATE, " +
            COL_RESULTADO           + " TEXT, " +
            COL_OBSERVACIONES       + " TEXT)";
}