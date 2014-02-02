package dan.android.quirogest.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by dlopez on 23/10/13.
 */
public class QuiroGestProvider extends ContentProvider {
    private static final String TAG = "QuiroGestProvider";
    public static final String PROVIDER_NAME            = "dan.android.quirogest.provider";
    public static final Uri CONTENT_URI_CONTACTOS       = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaClientes.TABLA_PACIENTES);
    public static final Uri CONTENT_URI_MOTIVOS         = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaMotivos.TABLA_MOTIVOS);
    public static final Uri CONTENT_URI_SESIONES        = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaSesiones.TABLA_SESIONES);
    public static final Uri CONTENT_URI_TECNICAS        = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaTecnicas.TABLA_TECNICAS);
    public static final Uri CONTENT_URI_TIPOS_TECNICAS  = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS);
    public static final Uri CONTENT_URI_ETIQUETAS       = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaEtiquetas.TABLA_ETIQUETAS);
    public static final Uri CONTENT_URI_TIPO_ETIQUETAS  = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS);


    //UriMatcher
    public static final int CONTACTOS           = 1;
    public static final int CONTACTOS_ID        = 2;
    public static final int MOTIVOS             = 3;
    public static final int MOTIVOS_ID          = 4;
    public static final int SESIONES            = 5;
    public static final int SESIONES_ID         = 6;
    public static final int TECNICAS            = 7;
    public static final int TECNICAS_ID         = 8;
    public static final int TIPOS_TECNICAS      = 9;
    public static final int TIPOS_TECNICAS_ID   = 10;
    public static final int ETIQUETAS           = 11;
    public static final int ETIQUETAS_ID        = 12;
    public static final int TIPOS_ETIQUETAS     = 13;
    public static final int TIPOS_ETIQUETAS_ID  = 14;

    //inicializamos el UriMatcher
    public static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TablaClientes.TABLA_PACIENTES,          CONTACTOS);
        uriMatcher.addURI(PROVIDER_NAME, TablaClientes.TABLA_PACIENTES + "/#",   CONTACTOS_ID);
        uriMatcher.addURI(PROVIDER_NAME, TablaMotivos.TABLA_MOTIVOS,            MOTIVOS);
        uriMatcher.addURI(PROVIDER_NAME, TablaMotivos.TABLA_MOTIVOS + "/#",     MOTIVOS_ID);
        uriMatcher.addURI(PROVIDER_NAME, TablaSesiones.TABLA_SESIONES,          SESIONES);
        uriMatcher.addURI(PROVIDER_NAME, TablaSesiones.TABLA_SESIONES + "/#",   SESIONES_ID);
        uriMatcher.addURI(PROVIDER_NAME, TablaTecnicas.TABLA_TECNICAS,          TECNICAS);
        uriMatcher.addURI(PROVIDER_NAME, TablaTecnicas.TABLA_TECNICAS + "/#",   TECNICAS_ID);
        uriMatcher.addURI(PROVIDER_NAME, TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS,          TIPOS_TECNICAS);
        uriMatcher.addURI(PROVIDER_NAME, TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS + "/#",   TIPOS_TECNICAS_ID);
        uriMatcher.addURI(PROVIDER_NAME, TablaEtiquetas.TABLA_ETIQUETAS,          ETIQUETAS);
        uriMatcher.addURI(PROVIDER_NAME, TablaEtiquetas.TABLA_ETIQUETAS + "/#",   ETIQUETAS_ID);
        uriMatcher.addURI(PROVIDER_NAME, TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS,          TIPOS_ETIQUETAS);
        uriMatcher.addURI(PROVIDER_NAME, TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS + "/#",   TIPOS_ETIQUETAS_ID);

    }

    private DatabaseHelper dbHelper;


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "Query " + uri.toString());
        Cursor c;
        String tabla=null;
        String where = selection;
        SQLiteQueryBuilder sqlb = new SQLiteQueryBuilder();

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                sqlb.appendWhere(TablaClientes._ID + "=" + uri.getLastPathSegment());
            case CONTACTOS:
                sqlb.setTables(TablaClientes.TABLA_PACIENTES);
                break;
            case MOTIVOS_ID:
                sqlb.appendWhere(TablaMotivos._ID + "=" + uri.getLastPathSegment());
            case MOTIVOS:
                sqlb.setTables(TablaMotivos.TABLA_MOTIVOS);
                break;
            case SESIONES_ID:
                sqlb.appendWhere(TablaSesiones._ID + "=" + uri.getLastPathSegment());
            case SESIONES:
                sqlb.setTables(TablaSesiones.TABLA_SESIONES);
                break;
            case TECNICAS_ID:
                sqlb.appendWhere(TablaTecnicas._ID + "=" + uri.getLastPathSegment());
            case TECNICAS:// LEFT JOIN      //TODO: usar group_concat para sacar también las etiquetas. Ej:  SELECT t.*, tt.*, (SELECT GROUP_CONCAT(e.tipoEtiqueta_etiquetas) FROM etiquetas e WHERE t._id=e.idTecnica_etiquetas) AS combinedsolutions FROM tecnicas t left join tiposDeTecnicas tt on tt.idTipoTecnica_tiposDeTecnicas=t.idTipoTecnica_tecnicas
                sqlb.setTables(TablaTecnicas.TABLA_TECNICAS +
                        " LEFT JOIN " +
                        TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS +
                        " ON (" +
                        TablaTecnicas.COL_ID_TIPO_TECNICA + "=" + TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA +
                        ")");
                break;
            case TIPOS_ETIQUETAS_ID:
                sqlb.appendWhere(TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA + "=" + uri.getLastPathSegment());
            case TIPOS_ETIQUETAS:
                sqlb.setTables(TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS);
                break;
            /*case TIPOS_TECNICAS_ID:
                sqlb.appendWhere(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA + "=" + uri.getLastPathSegment());
            case TIPOS_TECNICAS:
                sqlb.setTables(TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS);
                break;
            case ETIQUETAS_ID:
                sqlb.appendWhere(TablaEtiquetas.COL_ID_TIPO_ETIQUETA + "=" + uri.getLastPathSegment());
            case ETIQUETAS:// LEFT JOIN
                sqlb.setTables(TablaEtiquetas.TABLA_ETIQUETAS +
                        " LEFT JOIN " +
                        TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS +
                        " ON (" +
                        TablaEtiquetas.COL_ID_TIPO_ETIQUETA + "=" + TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA +
                        ")");
                break;*/
            default:
                throw new IllegalStateException ("Query no válida!!") ;
        }
        c= sqlb.query(dbHelper.getReadableDatabase(), projection, where, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "Delete " + uri.toString());
        String tabla=null;
        String where = selection;
        int rowDeleted;

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case CONTACTOS:
                tabla = TablaClientes.TABLA_PACIENTES;
                break;
            case MOTIVOS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case MOTIVOS:
                tabla = TablaMotivos.TABLA_MOTIVOS;
                break;
            case SESIONES_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case SESIONES:
                tabla = TablaSesiones.TABLA_SESIONES;
                break;
            case TECNICAS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case TECNICAS:
                tabla = TablaTecnicas.TABLA_TECNICAS;
                break;
          /*case TIPOS_TECNICAS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case TIPOS_TECNICAS:
                tabla = TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS;
                break;*/
            default:
                throw new IllegalStateException ("Delete no válido!!") ;
        }
        rowDeleted = dbHelper.getWritableDatabase().delete(tabla, where, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "Update " + uri.toString() + " " + values.toString());
        String tabla=null;
        String where = selection;
        int rowUpdated;

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case CONTACTOS:
                tabla = TablaClientes.TABLA_PACIENTES;
                break;
            case MOTIVOS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case MOTIVOS:
                tabla = TablaMotivos.TABLA_MOTIVOS;
                break;
            case SESIONES_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case SESIONES:
                tabla = TablaSesiones.TABLA_SESIONES;
                break;
            case TECNICAS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case TECNICAS:
                tabla = TablaTecnicas.TABLA_TECNICAS;
                break;
            /*case TIPOS_TECNICAS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case TIPOS_TECNICAS:
                tabla = TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS;
                break;*/
            default:
                throw new IllegalStateException ("Update no válido!!") ;
        }
        rowUpdated = dbHelper.getWritableDatabase().update(tabla, values, where, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "Insert " + uri.toString() + " " + values.toString());
        Uri contentUri  = null;
        Uri _uri        = null;
        long id         = -1;
        String tabla=null;

        switch (uriMatcher.match(uri)){
            case CONTACTOS:
                tabla = TablaClientes.TABLA_PACIENTES;
                contentUri  = CONTENT_URI_CONTACTOS;
                break;
            case MOTIVOS:
                tabla = TablaMotivos.TABLA_MOTIVOS;
                contentUri  = CONTENT_URI_MOTIVOS;
                break;
            case SESIONES:
                tabla = TablaSesiones.TABLA_SESIONES;
                contentUri  = CONTENT_URI_SESIONES;
                break;
            case TECNICAS:
                tabla = TablaTecnicas.TABLA_TECNICAS;
                contentUri  = CONTENT_URI_TECNICAS;
                break;
            case TIPOS_TECNICAS:
                tabla = TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS;
                contentUri  = CONTENT_URI_TIPOS_TECNICAS;
                break;
            case ETIQUETAS:
                tabla = TablaEtiquetas.TABLA_ETIQUETAS;
                contentUri  = CONTENT_URI_ETIQUETAS;
                break;
            case TIPOS_ETIQUETAS:
                tabla = TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS;
                contentUri  = CONTENT_URI_TIPO_ETIQUETAS;
                break;
            default:
                throw new IllegalStateException ("Insert no válido!!") ;
        }

        id = dbHelper.getWritableDatabase().insert(tabla, null, values);
        if (id > 0){
            _uri = ContentUris.withAppendedId(contentUri, id);
            getContext().getContentResolver().notifyChange(_uri, null);
        }

        return _uri;
    }


    @Override
    public String getType(Uri uri) {
        String type = null;

        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                type = "vnd.android.cursor.item/vnd.quirogest.contactos";
                break;
            case CONTACTOS:
                type = "vnd.android.cursor.dir/vnd.quirogest.contactos";
                break;
            case MOTIVOS_ID:
                type = "vnd.android.cursor.item/vnd.quirogest.motivos";
                break;
            case MOTIVOS:
                type = "vnd.android.cursor.dir/vnd.quirogest.motivos";
                break;
            case SESIONES_ID:
                type = "vnd.android.cursor.item/vnd.quirogest.sesiones";
                break;
            case SESIONES:
                type = "vnd.android.cursor.dir/vnd.quirogest.sesiones";
                break;
            case TECNICAS_ID:
                type = "vnd.android.cursor.item/vnd.quirogest.tecnicas";
                break;
            case TECNICAS:
                type = "vnd.android.cursor.dir/vnd.quirogest.tecnicas";
                break;
            default:
                throw new IllegalStateException ("Tipo no válido!!") ;
        }

        return type;
    }
}
