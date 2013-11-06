package dan.android.quirogest.listFragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import dan.android.quirogest.ItemListFragmentBase.ItemListFragmentBase;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;


public class MotivosListFragment extends ItemListFragmentBase {
    public static final int TAG_LIST_VIEW = 929823978;
    private static final String CONTACTO_ID = "contacto_id";
    private final String TAG = "MotivosListFragment";
    private final String[] PROJECTION = {BaseColumns._ID, TablaMotivos.COL_DIAGNOSTICO, TablaMotivos.COL_FECHA};
    private final String[] COLUMNS = {TablaMotivos.COL_FECHA, TablaMotivos.COL_DIAGNOSTICO};
    private final int[] VISTAS = {android.R.id.text1, android.R.id.text2};
    private final Uri URI = QuiroGestProvider.CONTENT_URI_MOTIVOS;
    private final int LAYOUT = android.R.layout.simple_list_item_2;
    private long mContactoId;


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static MotivosListFragment newInstance(long contactoId) {
        MotivosListFragment f = new MotivosListFragment();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(CONTACTO_ID, contactoId);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactoId = getArguments().getLong(CONTACTO_ID, -1);
    }


    @Override
    public int getListviewTag() { return TAG_LIST_VIEW; }

    @Override
    public String[] getProjection() { return PROJECTION; }

    @Override
    public String getSelection() { return TablaMotivos.COL_ID_CONTACTO + "=?"; }

    @Override
    public String[] getSelectionArgs() { return new String[] {String.valueOf(mContactoId)};}

    @Override
    public String getOrder() { return "DATE(" + TablaMotivos.COL_FECHA + ") DESC"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return COLUMNS; }

    @Override
    public int[] getViews() { return VISTAS; }

    @Override
    public Uri getUri() { return URI; }
}
