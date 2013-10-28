package dan.android.quirogest;

/**
 * Created by dan on 25/08/13.
 */
public class Cte {
    //MySQLlite TABLES

    public static final String TABLA_MOTIVO         = "motivo";
    public static final String TABLA_SESION         = "sesion";
    public static final String TABLA_TECNICA        = "tecnica";
    public static final String TABLA_DESCR_TECNICAS = "descrTecnicas";

    //MySQLlite FIELDS commons
    public static final String FIELD_ID_CONTACTO    = "idContacto";
    public static final String FIELD_ID_MOTIVO      = "idMotivo";
    public static final String FIELD_ID_SESION      = "idSesion";
    public static final String FIELD_ID_TECNICA     = "idTecnica";
    public static final String FIELD_OBSERVACIONES  = "observaciones";
    public static final String FIELD_FECHA          = "fecha";
    public static final String FIELD_DESCRIPCION    = "descripcion";



    //MySQLlite FIELDS motivo
    public static final String FIELD_INICIO_DOLOR   = "comienzoDelDolor";
    public static final String FIELD_ACT_FISICA     = "actividadFisica";
    public static final String FIELD_DIAGNOSTICO    = "diagnostico";

    //MySQLlite FIELDS sesion
    public static final String FIELD_INGRESOS       = "ingresos";
    public static final String FIELD_ESTADO_DOLOR   = "cuantificacionDelDolor";

    //MySQLlite FIELDS tecnica
    public static final String FIELD_FAMILIA    = "familia";
    public static final String FIELD_CLASE      = "clase";
    public static final String FIELD_TIPO       = "tipo";
    public static final String FIELD_SUBTIPO    = "subtipo";
    public static final String FIELD_SUBSUBTIPO = "subsubtipo";
    public static final String FIELD_GRADO      = "grado";
}
