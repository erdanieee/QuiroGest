package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaEtiquetas implements BaseColumns {
    public static final String TABLA_ETIQUETAS = "etiquetas";

    public static final String COL_ID_TECNICA       = "idTecnica"   + "_" + TABLA_ETIQUETAS;
    public static final String COL_ID_ETIQUETA      = "fecha"       + "_" + TABLA_ETIQUETAS;

    public static final String sqlCreateTableEtiquetas = "CREATE TABLE " + TABLA_ETIQUETAS + " (" +
            _ID             + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_TECNICA  + " TEXT, " +
            COL_ID_ETIQUETA + " TEXT)";
}