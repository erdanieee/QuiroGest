package dan.android.quirogest;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ClipData;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import dan.android.quirogest.ItemListFragmentBase.CallbackItemClicked;
import dan.android.quirogest.ItemListFragmentBase.ItemListFragmentBase;
import dan.android.quirogest.ItemListFragmentBase.ListViewItemClickeable;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;


public class ClienteListFragment extends ItemListFragmentBase {
    public static final int TAG_LIST_VIEW = 55887760;
    private final String TAG = "ClienteListFragment";
    private final String[] PROJECTION = {BaseColumns._ID, TablaClientes.COL_NOMBRE};     //TODO: añadir otros campos para mostrar en la lista
    private final String[] COLUMNS = {TablaClientes.COL_NOMBRE};
    private final int[] VISTAS = {android.R.id.text1};
    private final Uri URI = QuiroGestProvider.CONTENT_URI_CONTACTOS;
    private final int LAYOUT = android.R.layout.simple_list_item_activated_1;


    @Override
    public int getListviewTag() {
        return TAG_LIST_VIEW;
    }

    @Override
    public String[] getProjection() { return PROJECTION; }

    @Override
    public String getSelection() { return null; }

    @Override
    public String[] getSelectionArgs() { return null; }

    @Override
    public String getOrder() { return null; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return COLUMNS; }

    @Override
    public int[] getViews() { return VISTAS; }

    @Override
    public Uri getUri() { return URI; }
}


