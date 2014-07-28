package dan.android.quirogest.database;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaTiposDeEtiquetas{
    public static final String TABLA_TIPOS_ETIQUETAS = "tiposDeEtiquetas";

    public static final String COL_ID_TIPO_ETIQUETA = "idTipoEtiqueta"  + "_" + TABLA_TIPOS_ETIQUETAS;
    public static final String COL_DESCRIPCION      = "descripcion"     + "_" + TABLA_TIPOS_ETIQUETAS;
    public static final String COL_COLOR            = "color"           + "_" + TABLA_TIPOS_ETIQUETAS;

    public static final String sqlCreateTableTiposEtiquetas = "CREATE TABLE " + TABLA_TIPOS_ETIQUETAS + " (" +
            COL_ID_TIPO_ETIQUETA    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DESCRIPCION         + " TEXT, " +
            COL_COLOR               + " TEXT)";
}