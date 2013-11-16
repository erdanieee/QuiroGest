package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaTiposDeTecnicas implements BaseColumns {
    public static final String TABLA_TIPOS_TECNICAS = "tiposDeTecnicas";

    public static final String COL_ID_PARENT        = "idParent";
    public static final String COL_MIN              = "minimoValor";
    public static final String COL_MAX              = "maximoValor";
    public static final String COL_REPRESENTACION   = "tipoDeRepresentacion";
    public static final String COL_DESCRIPCION      = "observaciones";

    public static final String sqlCreateTableTiposTecnicas = "CREATE TABLE " + TABLA_TIPOS_TECNICAS + " (" +
            _ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_PARENT           + " INTEGER, " +
            COL_MIN                 + " INTEGER, " +
            COL_MAX                 + " INTEGER, " +
            COL_REPRESENTACION      + " INTEGER, " +    //How to draw the wirdget
            COL_DESCRIPCION         + " TEXT)";
}
