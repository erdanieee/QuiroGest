package dan.android.quirogest.database;

import android.provider.BaseColumns;

/**
 * Created by dlopez on 23/10/13.
 */
public class TablaMotivos implements BaseColumns{
    public static final String TABLA_MOTIVOS = "motivosDeConsulta";

    public static final String COL_ID_CONTACTO      = "idContacto"      + "_" + TABLA_MOTIVOS;
    public static final String COL_FECHA            = "fecha"           + "_" + TABLA_MOTIVOS;                                       // FORMATO SQLITE_DATE_FORMAT
    public static final String COL_QUE_PASA         = "queTePasa"       + "_" + TABLA_MOTIVOS;
    public static final String COL_DONDE_DUELE      = "dondeDuele"      + "_" + TABLA_MOTIVOS;
    public static final String COL_CUANDO_DUELE     = "cuandoDuele"     + "_" + TABLA_MOTIVOS;
    public static final String COL_COMO_DUELE       = "comoDuele"       + "_" + TABLA_MOTIVOS;
    public static final String COL_DESDE_CUANDO     = "desdeCuando"     + "_" + TABLA_MOTIVOS;
    public static final String COL_FORMA_APARICION  = "formaAparicion"  + "_" + TABLA_MOTIVOS;
    public static final String COL_CONCOMITANCIA    = "concomitancia"   + "_" + TABLA_MOTIVOS;
    public static final String COL_ANTECEDENTES     = "antecedentes"    + "_" + TABLA_MOTIVOS;
    public static final String COL_ACTIV_FISICA     = "actividadFisica" + "_" + TABLA_MOTIVOS;
    public static final String COL_OBSERVACIONES    = "observaciones"   + "_" + TABLA_MOTIVOS;




    public static final String sqlCreateTableMotivos = "CREATE TABLE " + TABLA_MOTIVOS + " (" +
            _ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ID_CONTACTO     + " INTEGER, " +
            COL_FECHA           + " TEXT DEFAULT CURRENT_DATE, " +
            COL_QUE_PASA        + " TEXT, " +
            COL_DONDE_DUELE     + " TEXT, " +
            COL_CUANDO_DUELE    + " TEXT, " +
            COL_COMO_DUELE      + " TEXT, " +
            COL_DESDE_CUANDO    + " TEXT, " +
            COL_FORMA_APARICION + " TEXT, " +
            COL_CONCOMITANCIA   + " TEXT, " +
            COL_ANTECEDENTES    + " TEXT, " +
            COL_ACTIV_FISICA    + " TEXT, " +
            COL_OBSERVACIONES   + " TEXT)";
}
