package dan.android.quirogest.listFragments;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaContactos;


public class ClienteListFragment extends ListFragmentBase {
    private static final String TAG             = "MotivosListFragment";
    private final Uri       QUERY_URI           = QuiroGestProvider.CONTENT_URI_CONTACTOS;
    private final String[]  QUERY_PROJECTION    = {BaseColumns._ID, TablaContactos.COL_NOMBRE, TablaContactos.COL_APELLIDO1};
    private final String[]  LAYOUT_DATA_COLUMNS = {TablaContactos.COL_NOMBRE, TablaContactos.COL_APELLIDO1};
    private final int[]     LAYOUT_VIEW_IDS     = {R.id.text1, R.id.text2};
    private final int       LAYOUT              = R.layout.listitem_cliente;


    @Override
    public void init() {}   //no es necesario hacer nada

    public static ClienteListFragment newIntance() { return new ClienteListFragment(); }

    @Override
    public ListTypes getListViewType() { return ListTypes.LIST_VIEW_CLIENTES; }

    @Override
    public String[] getProjection() { return QUERY_PROJECTION; }

    @Override
    public String getSelection() { return null; }

    @Override
    public String[] getSelectionArgs() { return null; }

    @Override
    public String getOrder() { return TablaContactos.COL_NOMBRE + " COLLATE NOCASE"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return LAYOUT_DATA_COLUMNS; }

    @Override
    public int[] getViews() { return LAYOUT_VIEW_IDS; }

    @Override
    public Uri getUri() { return QUERY_URI; }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //necesario para que aparezca el menú
        setHasOptionsMenu(true);
    }

    //********************************************************************************************//
    // M A I N      M E N U
    //********************************************************************************************//
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_contactos, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    //Primero se llama a la activity, y llega aquí solo si la activity no consume el evento (return false)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenuAddContacto:
                ContentValues cv;

                cv = new ContentValues();

                cv.put(TablaContactos.COL_NOMBRE, "Nuevo Contacto");
                getActivity().getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


