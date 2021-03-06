package dan.android.quirogest.listFragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;


public class MotivosListFragment extends ListFragmentBase {
    private static final String TAG             = "MotivosListFragment";
    private final static String ARG_CONTACTO_ID = "contacto_id";

    private final Uri       QUERY_URI            = QuiroGestProvider.CONTENT_URI_MOTIVOS;
    private final String[]  QUERY_PROJECTION     = {BaseColumns._ID, TablaMotivos.COL_FECHA};
    private final String[]  LAYOUT_DATA_COLUMNS  = {TablaMotivos.COL_FECHA};
    private final int[]     LAYOUT_VIEW_IDS      = {R.id.textViewFecha};
    private final int       LAYOUT               = R.layout.listitem_motivos;

    private Long mContactoId;



    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static MotivosListFragment newInstance(long contactoId) { return newInstance(contactoId, null); }
    public static MotivosListFragment newInstance(long contactoId, Long selectedMotivoId) {
    Log.d(TAG, "Nueva instancia");
    MotivosListFragment f;
    Bundle args;

    f    = new MotivosListFragment();
    args = new Bundle();
    args.putLong(ARG_CONTACTO_ID, contactoId);
    if (selectedMotivoId!=null){
        args.putLong(ARG_SELECTED_ITEM_ID, selectedMotivoId);
    }
    f.setArguments(args);

    return f;
}


    //Necesitamos el Id del contacto. Se puede obtener desde el intent o desde los argumentos
    public void init() {
        //mContactoId = getActivity().getIntent().getLongExtra(MotivosListActivity.MOTIVO_ID, -1);

        if (null!=getArguments() && getArguments().containsKey(ARG_CONTACTO_ID) ){
            mContactoId = getArguments().getLong(ARG_CONTACTO_ID,-1);
        }

        if (mContactoId==null || mContactoId<0){
            throw new IllegalStateException("Se ha instanciado la clase sin añadir el argumento contactoID!!!");
        }
    }



    private Long getContactoId(){ return mContactoId; }


    @Override
    public ListTypes getListViewType() { return ListTypes.LIST_VIEW_MOTIVOS; }

    @Override
    public String[] getProjection() { return QUERY_PROJECTION; }

    @Override
    public String getSelection() { return TablaMotivos.COL_ID_CONTACTO + "=?"; }

    @Override
    public String[] getSelectionArgs() { return new String[] {getContactoId().toString()};}

    @Override
    public String getOrder() { return "DATE(" + TablaMotivos.COL_FECHA + ") DESC"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return LAYOUT_DATA_COLUMNS; }

    @Override
    public int[] getViews() { return LAYOUT_VIEW_IDS; }

    @Override
    public Uri getUri() { return QUERY_URI; }
}
