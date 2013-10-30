package dan.android.quirogest.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by dlopez on 23/10/13.
 */
public class QuiroGestProvider extends ContentProvider {
    private static final String TAG = "QuiroGestProvider";
    public static final String PROVIDER_NAME        = "dan.android.quirogest.provider";
    public static final Uri CONTENT_URI_CONTACTOS   = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaClientes.TABLA_CLIENTES);
    public static final Uri CONTENT_URI_MOTIVOS     = Uri.parse("content://" + PROVIDER_NAME + "/" + TablaMotivos.TABLA_MOTIVOS);

    //UriMatcher
    public static final int CONTACTOS       = 1;
    public static final int CONTACTOS_ID    = 2;
    public static final int MOTIVOS         = 3;
    public static final int MOTIVOS_ID      = 4;

    //inicializamos el UriMatcher
    public static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TablaClientes.TABLA_CLIENTES,        CONTACTOS);
        uriMatcher.addURI(PROVIDER_NAME, TablaClientes.TABLA_CLIENTES + "/#", CONTACTOS_ID);
        uriMatcher.addURI(PROVIDER_NAME, TablaMotivos.TABLA_MOTIVOS,            MOTIVOS);
        uriMatcher.addURI(PROVIDER_NAME, TablaMotivos.TABLA_MOTIVOS + "/#",     MOTIVOS_ID);

    }

    private DatabaseHelper dbHelper;


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "Query " + uri.toString());
        Cursor c     = null;
        String where = selection;

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case CONTACTOS:
                c = dbHelper.getReadableDatabase().query(TablaClientes.TABLA_CLIENTES, projection, where, selectionArgs, null, null, sortOrder);
                break;
        }
        return c;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "Delete " + uri.toString());
        int count    = -1;
        String where = selection;

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case CONTACTOS:
                count = dbHelper.getWritableDatabase().delete(TablaClientes.TABLA_CLIENTES, where, selectionArgs);
                break;
        }
        return count;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "Update " + uri.toString() + " " + values.toString());
        int count    = -1;
        String where = selection;

        //si es una consulta a un ID concreto construimos el WHERE
        switch (uriMatcher.match(uri)){
            case CONTACTOS_ID:
                where = BaseColumns._ID + "=" + uri.getLastPathSegment();
            case CONTACTOS:
                count = dbHelper.getWritableDatabase().update(TablaClientes.TABLA_CLIENTES, values, where, selectionArgs);
                break;
        }
        return count;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(TAG, "Insert " + uri.toString() + " " + values.toString());
        Uri contentUri  = null;
        Uri _uri        = null;
        long id         = -1;

        switch (uriMatcher.match(uri)){
            case CONTACTOS:
                id          = dbHelper.getWritableDatabase().insert(TablaClientes.TABLA_CLIENTES, null, values);
                contentUri  = CONTENT_URI_CONTACTOS;
                break;
            case MOTIVOS:
                id          = dbHelper.getWritableDatabase().insert(TablaMotivos.TABLA_MOTIVOS, null, values);
                contentUri  = CONTENT_URI_MOTIVOS;
                break;
        }

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
                type = "vnd.android.cursor.item/vnd.quirogest.contacto";
                break;
            case CONTACTOS:
                type = "vnd.android.cursor.dir/vnd.quirogest.contacto";
                break;
        }

        return type;
    }
}
