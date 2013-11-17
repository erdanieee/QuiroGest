package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaTiposDeTecnicas implements BaseColumns {
    public static final String TABLA_TIPOS_TECNICAS = "tiposDeTecnicas";

    public static final String COL_ID_PARENT        = "idParent_tablaTipoTecnica";
    public static final String COL_MIN              = "minimoValor_tablaTipoTecnica";
    public static final String COL_MAX              = "maximoValor_tablaTipoTecnica";
    public static final String COL_VIEWTYPE         = "tipoDeVista_tablaTipoTecnica";
    public static final String COL_DESCRIPCION      = "observaciones_tablaTipoTecnica";

    public static final String sqlCreateTableTiposTecnicas = "CREATE TABLE " + TABLA_TIPOS_TECNICAS + " (" +
            _ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_PARENT           + " INTEGER, " +
            COL_MIN                 + " INTEGER, " +
            COL_MAX                 + " INTEGER, " +
            COL_VIEWTYPE            + " INTEGER, " +    //How to draw the wirdget
            COL_DESCRIPCION         + " TEXT)";
}
