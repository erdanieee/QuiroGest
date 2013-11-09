package dan.android.quirogest.listFragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaSesiones;

/**
 * Created by dan on 4/11/13.
 */
public class SesionesListFragment extends ListFragmentBase {
    private final static String ARG_MOTIVO_ID   = "motivo_id";

    private final Uri QUERY_URI                 = QuiroGestProvider.CONTENT_URI_SESIONES;
    private final String[] QUERY_PROJECTION     = {BaseColumns._ID, TablaSesiones.COL_DIAGNOSTICO, TablaSesiones.COL_FECHA};
    private final String[] LAYOUT_DATA_COLUMNS  = {TablaSesiones.COL_FECHA, TablaSesiones.COL_DIAGNOSTICO};
    private final int[] LAYOUT_VIEW_IDS         = {android.R.id.text1, android.R.id.text2};
    private final int LAYOUT                    = android.R.layout.simple_list_item_activated_2;

    private Long mMotivoId = null;



    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static SesionesListFragment newInstance(long motivoId) { return newInstance(motivoId, null); }
    public static SesionesListFragment newInstance(long motivoId, Long selectedSesionId) {
        SesionesListFragment f;
        Bundle args;

        f    = new SesionesListFragment();
        args = new Bundle();
        args.putLong(ARG_MOTIVO_ID, motivoId);
        if (selectedSesionId!=null){
            args.putLong(ARG_SELECTED_ITEM_ID, selectedSesionId);
        }
        f.setArguments(args);

        return f;
    }


    public void init() {
        if (null== getArguments() || !getArguments().containsKey(ARG_MOTIVO_ID) ){
            throw new IllegalStateException("Se ha instanciado la clase sin añadir el argumento contactoID!!!");
        }
        mMotivoId = getArguments().getLong(ARG_MOTIVO_ID,-1);
    }


    private Long getMotivoId(){return mMotivoId;}


    @Override
    public ListTypes getListViewType() { return ListTypes.LIST_VIEW_MOTIVOS; }

    @Override
    public String[] getProjection() { return QUERY_PROJECTION; }

    @Override
    public String getSelection() { return TablaSesiones.COL_ID_MOTIVO + "=?"; }

    @Override
    public String[] getSelectionArgs() { return new String[] {getMotivoId().toString()};}

    @Override
    public String getOrder() { return "DATE(" + TablaSesiones.COL_FECHA + ") DESC"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return LAYOUT_DATA_COLUMNS; }

    @Override
    public int[] getViews() { return LAYOUT_VIEW_IDS; }

    @Override
    public Uri getUri() { return QUERY_URI; }
}
