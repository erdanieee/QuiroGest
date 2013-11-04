package dan.android.quirogest;

import android.net.Uri;
import android.provider.BaseColumns;

import dan.android.quirogest.ItemListFragmentBase.ItemListFragmentBase;
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
    public String getOrder() { return TablaClientes.COL_NOMBRE + " COLLATE NOCASE"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return COLUMNS; }

    @Override
    public int[] getViews() { return VISTAS; }

    @Override
    public Uri getUri() { return URI; }
}


