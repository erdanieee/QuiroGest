package dan.android.quirogest;

import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import dan.android.quirogest.ItemListFragmentBase.ItemListFragmentBase;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.database.TablaSesiones;

/**
 * Created by dan on 4/11/13.
 */
//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class SesionesListFragment extends ItemListFragmentBase {
    public static final int TAG_LIST_VIEW = 73648273;
    private static final String MOTIVO_ID = "motivo_id";
    private final String TAG = "SesionesListFragment";
    private final String[] PROJECTION = {BaseColumns._ID, TablaSesiones.COL_DIAGNOSTICO, TablaSesiones.COL_FECHA};
    private final String[] COLUMNS = {TablaSesiones.COL_FECHA, TablaSesiones.COL_DIAGNOSTICO};
    private final int[] VISTAS = {android.R.id.text1, android.R.id.text2};
    private final Uri URI = QuiroGestProvider.CONTENT_URI_SESIONES;
    private final int LAYOUT = android.R.layout.simple_list_item_2;
    private long mMotivoId;


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static SesionesListFragment newInstance(long motivoId) {
        SesionesListFragment f = new SesionesListFragment();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(MOTIVO_ID, motivoId);
        f.setArguments(args);

        return f;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMotivoId = getArguments().getLong(MOTIVO_ID, -1);
    }


    @Override
    public int getListviewTag() { return TAG_LIST_VIEW; }

    @Override
    public String[] getProjection() { return PROJECTION; }

    @Override
    public String getSelection() { return TablaSesiones.COL_ID_MOTIVO + "=?"; }

    @Override
    public String[] getSelectionArgs() { return new String[] {String.valueOf(mMotivoId)};}

    @Override
    public String getOrder() { return "DATE(" + TablaSesiones.COL_FECHA + ") DESC"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return COLUMNS; }

    @Override
    public int[] getViews() { return VISTAS; }

    @Override
    public Uri getUri() { return URI; }

}
