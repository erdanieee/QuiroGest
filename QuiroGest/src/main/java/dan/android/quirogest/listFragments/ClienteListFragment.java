package dan.android.quirogest.listFragments;

import android.net.Uri;
import android.provider.BaseColumns;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;


public class ClienteListFragment extends ListFragmentBase {
    private static final String TAG             = "MotivosListFragment";
    private final Uri       QUERY_URI           = QuiroGestProvider.CONTENT_URI_CONTACTOS;
    private final String[]  QUERY_PROJECTION    = {BaseColumns._ID, TablaClientes.COL_NOMBRE};     //TODO: añadir otros campos para mostrar en la lista
    private final String[]  LAYOUT_DATA_COLUMNS = {TablaClientes.COL_NOMBRE};
    private final int[]     LAYOUT_VIEW_IDS     = {android.R.id.text1};
    private final int       LAYOUT              = android.R.layout.simple_list_item_activated_1;


    @Override
    public ListTypes getListViewType() { return ListTypes.LIST_VIEW_CLIENTES; }

    @Override
    public String[] getProjection() { return QUERY_PROJECTION; }

    @Override
    public String getSelection() { return null; }

    @Override
    public String[] getSelectionArgs() { return null; }

    @Override
    public String getOrder() { return TablaClientes.COL_NOMBRE + " COLLATE NOCASE"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return LAYOUT_DATA_COLUMNS; }

    @Override
    public int[] getViews() { return LAYOUT_VIEW_IDS; }

    @Override
    public Uri getUri() { return QUERY_URI; }
}


