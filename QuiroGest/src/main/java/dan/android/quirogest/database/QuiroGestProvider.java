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
    private static final String PROVIDER_NAME                = "dan.android.quirogest.provider";
    private static final String URI_TIPOS_VISTAS_TECNICAS    = "tiposDeVistasParaLasTenicas";
    private static final String URI_CONTACTOS                = "tablaContactos";
    private static final String URI_MOTIVOS                  = "tablaMotivos";
    private static final String URI_SESIONES                 = "tablaSesiones";
    private static final String URI_TECNICAS                 = "tablaTecnicas";
    private static final String URI_TIPOS_TECNICAS           = "tablaTiposDeTecnicas";
    private static final String URI_ETIQUETAS                = "tablaEtiquetas";
    private static final String URI_TIPOS_ETIQUETAS          = "tablaTiposDeEtiquetas";

    public static final Uri CONTENT_URI_CONTACTOS           = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_CONTACTOS);
    public static final Uri CONTENT_URI_MOTIVOS             = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_MOTIVOS);
    public static final Uri CONTENT_URI_SESIONES            = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_SESIONES);
    public static final Uri CONTENT_URI_TECNICAS            = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_TECNICAS);
    public static final Uri CONTENT_URI_TIPOS_TECNICAS      = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_TIPOS_TECNICAS);
    public static final Uri CONTENT_URI_ETIQUETAS           = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_ETIQUETAS);
    public static final Uri CONTENT_URI_TIPO_ETIQUETAS      = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_TIPOS_ETIQUETAS);
    public static final Uri CONTENT_URI_NUM_VIEWS_TECNICAS  = Uri.parse("content://" + PROVIDER_NAME + "/" + URI_TIPOS_VISTAS_TECNICAS);

    //UriMatcher
    private static final int CONTACTOS           = 1;
    private static final int CONTACTOS_ID        = 2;
    private static final int MOTIVOS             = 3;
    private static final int MOTIVOS_ID          = 4;
    private static final int SESIONES            = 5;
    private static final int SESIONES_ID         = 6;
    private static final int TECNICAS            = 7;
    private static final int TECNICAS_ID         = 8;
    private static final int TIPOS_TECNICAS      = 9;
    private static final int TIPOS_TECNICAS_ID   = 10;
    private static final int ETIQUETAS           = 11;
    private static final int ETIQUETAS_ID        = 12;
    private static final int TIPOS_ETIQUETAS     = 13;
    private static final int TIPOS_ETIQUETAS_ID  = 14;
    private static final int NUM_VIEWS_TECNICAS  = 15;

    //inicializamos el UriMatcher
    public static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, URI_CONTACTOS,                 CONTACTOS);
        uriMatcher.addURI(PROVIDER_NAME, URI_CONTACTOS + "/#",          CONTACTOS_ID);
        uriMatcher.addURI(PROVIDER_NAME, URI_MOTIVOS,                   MOTIVOS);
        uriMatcher.addURI(PROVIDER_NAME, URI_MOTIVOS + "/#",            MOTIVOS_ID);
        uriMatcher.addURI(PROVIDER_NAME, URI_SESIONES,                  SESIONES);
        uriMatcher.addURI(PROVIDER_NAME, URI_SESIONES + "/#",           SESIONES_ID);
        uriMatcher.addURI(PROVIDER_NAME, URI_TECNICAS,                  TECNICAS);
        uriMatcher.addURI(PROVIDER_NAME, URI_TECNICAS + "/#",           TECNICAS_ID);
        uriMatcher.addURI(PROVIDER_NAME, URI_TIPOS_TECNICAS,            TIPOS_TECNICAS);
        uriMatcher.addURI(PROVIDER_NAME, URI_TIPOS_TECNICAS + "/#",     TIPOS_TECNICAS_ID);
        uriMatcher.addURI(PROVIDER_NAME, URI_ETIQUETAS,                 ETIQUETAS);
        uriMatcher.addURI(PROVIDER_NAME, URI_ETIQUETAS + "/#",          ETIQUETAS_ID);
        uriMatcher.addURI(PROVIDER_NAME, URI_TIPOS_ETIQUETAS,           TIPOS_ETIQUETAS);
        uriMatcher.addURI(PROVIDER_NAME, URI_TIPOS_ETIQUETAS + "/#",    TIPOS_ETIQUETAS_ID);
        uriMatcher.addURI(PROVIDER_NAME, URI_TIPOS_VISTAS_TECNICAS,     NUM_VIEWS_TECNICAS);
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
        SQLiteQueryBuilder sqlb = new SQLiteQueryBuilder();

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                sqlb.appendWhere(TablaContactos._ID + "=" + uri.getLastPathSegment());
            case CONTACTOS:
                sqlb.setTables(TablaContactos.TABLA_CONTACTOS);
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

        //    case ETIQUETAS_ID:
        //        sqlb.appendWhere(TablaEtiquetas.COL_ID_TIPO_ETIQUETA + "=" + uri.getLastPathSegment());
            case ETIQUETAS:// LEFT JOIN
                sqlb.setTables(TablaEtiquetas.TABLA_ETIQUETAS +
                        " LEFT JOIN " +
                        TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS +
                        " ON (" +
                        TablaEtiquetas.COL_ID_TIPO_ETIQUETA + "=" + TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA +
                        ")");
                break;

            case TIPOS_TECNICAS_ID:
                sqlb.appendWhere(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA + "=" + uri.getLastPathSegment());
            case TIPOS_TECNICAS:
                sqlb.setTables(TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS);
                break;

            case NUM_VIEWS_TECNICAS:
                sqlb.setTables(TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS);
                projection      = new String[] {TablaTiposDeTecnicas.COL_VIEWTYPE,
                                                TablaTiposDeTecnicas.COL_NUM_ROWS,
                                                TablaTiposDeTecnicas.COL_NUM_COLS};
                selection       = null;
                selectionArgs   = null;
                sqlb.setDistinct(true);
                break;

            default:
                throw new IllegalStateException ("Query no válida!!") ;
        }
        c= sqlb.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);   //TODO: comprobar si es necesario esto (!!!???)
        return c;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tabla=null;
        String where = selection;
        int rowDeleted;

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                where = TablaContactos._ID + "=" + uri.getLastPathSegment();
            case CONTACTOS:
                tabla = TablaContactos.TABLA_CONTACTOS;
                deleteLeaves(CONTENT_URI_CONTACTOS, TablaContactos._ID, where, selectionArgs, CONTENT_URI_MOTIVOS, TablaMotivos.COL_ID_CONTACTO);
                break;

            case MOTIVOS_ID:
                where = TablaMotivos._ID + "=" + uri.getLastPathSegment();
            case MOTIVOS:
                tabla = TablaMotivos.TABLA_MOTIVOS;
                deleteLeaves(CONTENT_URI_MOTIVOS, TablaMotivos._ID, where, selectionArgs, CONTENT_URI_SESIONES, TablaSesiones.COL_ID_MOTIVO);
                break;

            case SESIONES_ID:
                where = TablaSesiones._ID + "=" + uri.getLastPathSegment();
            case SESIONES:
                tabla = TablaSesiones.TABLA_SESIONES;
                deleteLeaves(CONTENT_URI_SESIONES, TablaSesiones._ID, where, selectionArgs, CONTENT_URI_TECNICAS, TablaTecnicas.COL_ID_SESION);
                break;

            case TECNICAS_ID:
                where = TablaTecnicas._ID + "=" + uri.getLastPathSegment();
            case TECNICAS:
                tabla = TablaTecnicas.TABLA_TECNICAS;
                deleteLeaves(CONTENT_URI_TECNICAS, TablaTecnicas._ID, where, selectionArgs, CONTENT_URI_ETIQUETAS, TablaEtiquetas.COL_ID_TECNICA);
                break;

            case ETIQUETAS_ID:
                where = TablaEtiquetas._ID + "=" + uri.getLastPathSegment();
            case ETIQUETAS:
                tabla = TablaEtiquetas.TABLA_ETIQUETAS;
                break;

            default:
                throw new IllegalStateException ("Delete no válido!!") ;
        }
        rowDeleted = dbHelper.getWritableDatabase().delete(tabla, where, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        Log.d(TAG, "Delete " + rowDeleted + " items on " +  uri.toString());
        return rowDeleted;
    }


    private void deleteLeaves(Uri uriOrigen, String colIdOrigen, String where, String[] selectionArgs, Uri uriDestino, String colIdDestino) {
        Cursor c;
        long id;

        c = query(
                uriOrigen,
                new String[]{colIdOrigen},
                where,
                selectionArgs,
                null);

        while (c.moveToNext()){
            id = c.getLong(0);
            delete(
                    uriDestino,
                    colIdDestino + "=" + String.valueOf(id),
                    null);
        }
        c.close();
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
                tabla = TablaContactos.TABLA_CONTACTOS;
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
        Uri contentUri  = null;
        Uri _uri        = null;
        long id         = -1;
        String tabla=null;

        switch (uriMatcher.match(uri)){
            case CONTACTOS:
                tabla = TablaContactos.TABLA_CONTACTOS;
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
        Log.d(TAG, "Inserted id: " + id + " on " + uri.toString() + " " + values.toString());

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
