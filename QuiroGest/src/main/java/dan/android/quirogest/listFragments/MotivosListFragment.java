package dan.android.quirogest.listFragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import dan.android.quirogest.listFragmentBase.ListFragmentBase;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;


public class MotivosListFragment extends ListFragmentBase {
    private final static String ARG_CONTACTO_ID = "contacto_id";

    private final Uri QUERY_URI                 = QuiroGestProvider.CONTENT_URI_MOTIVOS;
    private final String[] QUERY_PROJECTION     = {BaseColumns._ID, TablaMotivos.COL_DIAGNOSTICO, TablaMotivos.COL_FECHA};
    private final String[] LAYOUT_DATA_COLUMNS  = {TablaMotivos.COL_FECHA, TablaMotivos.COL_DIAGNOSTICO};
    private final int[] LAYOUT_VIEW_IDS         = {android.R.id.text1, android.R.id.text2};
    private final int LAYOUT                    = android.R.layout.simple_list_item_2;

    private String mContactoId = null;



    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static MotivosListFragment newInstance(long contactoId) { return newInstance(contactoId, null); }
    public static MotivosListFragment newInstance(long contactoId, Long selectedMotivoId) {
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


    private String getContactoId(){return mContactoId==null ? String.valueOf(new Bundle().getLong(ARG_CONTACTO_ID)) : mContactoId;}



    @Override
    public ListTypes getListViewType() { return ListTypes.LIST_VIEW_MOTIVOS; }

    @Override
    public String[] getProjection() { return QUERY_PROJECTION; }

    @Override
    public String getSelection() { return TablaMotivos.COL_ID_CONTACTO + "=?"; }

    @Override
    public String[] getSelectionArgs() { return new String[] {getContactoId()};}

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
