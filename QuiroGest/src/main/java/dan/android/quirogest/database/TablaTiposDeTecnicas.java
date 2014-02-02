package dan.android.quirogest.database;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaTiposDeTecnicas{
    public static final String TABLA_TIPOS_TECNICAS = "tiposDeTecnicas";

    public static final String COL_ID_TIPO_TECNICA  = "idTipoTecnica"   + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_ID_PARENT        = "idParent"        + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_NUM_COLS         = "numColumns"      + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_NUM_ROWS         = "numRows"         + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_VIEWTYPE         = "tipoDeVista"     + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_MIN              = "minimoValor"     + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_MAX              = "maximoValor"     + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_TITLE            = "tituloGeneral"   + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_LABELS_COLS      = "etiquetasCols"   + "_" + TABLA_TIPOS_TECNICAS;
    public static final String COL_LABELS_ROWS      = "etiquetasRows"   + "_" + TABLA_TIPOS_TECNICAS;

    public static final String sqlCreateTableTiposTecnicas = "CREATE TABLE " + TABLA_TIPOS_TECNICAS + " (" +
            COL_ID_TIPO_TECNICA     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_PARENT           + " INTEGER, " +
            COL_NUM_COLS            + " INTEGER, " +
            COL_NUM_ROWS            + " INTEGER, " +
            COL_VIEWTYPE            + " INTEGER, " +    //How to draw the widget
            COL_MIN                 + " INTEGER, " +
            COL_MAX                 + " INTEGER, " +
            COL_LABELS_COLS         + " TEXT, " +
            COL_LABELS_ROWS         + " TEXT, " +
            COL_TITLE + " TEXT)";
}
