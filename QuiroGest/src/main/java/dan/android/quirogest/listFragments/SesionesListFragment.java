package dan.android.quirogest.listFragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaSesiones;
import dan.android.quirogest.database.TablaTecnicas;

/**
 * Created by dan on 4/11/13.
 */
public class SesionesListFragment extends ListFragmentBase{//} implements AbsListView.MultiChoiceModeListener{
    private final static String ARG_MOTIVO_ID   = "motivo_id";

    private final Uri QUERY_URI                 = QuiroGestProvider.CONTENT_URI_SESIONES;
    private final String[] QUERY_PROJECTION     = {BaseColumns._ID, TablaSesiones.COL_NUM_SESION, TablaSesiones.COL_FECHA};
    private final String[] LAYOUT_DATA_COLUMNS  = {TablaSesiones.COL_NUM_SESION, TablaSesiones.COL_FECHA};
    private final int[] LAYOUT_VIEW_IDS         = {R.id.textViewSesion, R.id.textViewFecha};
    private final int LAYOUT                    = R.layout.listitem_sesion;

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
    public ListTypes getListViewType() { return ListTypes.LIST_VIEW_SESIONES; }

    @Override
    public String[] getProjection() { return QUERY_PROJECTION; }

    @Override
    public String getSelection() { return TablaSesiones.COL_ID_MOTIVO + "=?"; }

    @Override
    public String[] getSelectionArgs() { return new String[] {getMotivoId().toString()};}

    @Override
    public String getOrder() { return "DATE(" + TablaSesiones.COL_NUM_SESION + ") DESC"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return LAYOUT_DATA_COLUMNS; }

    @Override
    public int[] getViews() { return LAYOUT_VIEW_IDS; }

    @Override
    public Uri getUri() { return QUERY_URI; }




    //********************************************************************************************//
    // C O N T E X T U A L   A C T I O N   B A R
    //********************************************************************************************//
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        super.onCreateActionMode(mode, menu);

        // Inflate the menu for the CAB
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.contextual_sesiones, menu);
        mode.setTitle("Select Items");

        return true;
    }


    @Override
    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
        super.onActionItemClicked(mode, item);

        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {
            case R.id.contextualMenuDuplicateSession:
                for(long id : getListView().getCheckedItemIds()){
                    copySesion(getActivity(), id, mMotivoId);
                }
                mode.finish(); // Action picked, so close the CAB

                return false;
            default:
                return false;
        }
    }


    public static void copySesion(Context context, long idSesion, long idMotivo) {
        Cursor c;
        ContentValues cv;
        Uri uriNuevaSesion;

        cv = new ContentValues();

        c = context.getContentResolver().query(
                QuiroGestProvider.CONTENT_URI_SESIONES,
                null,
                TablaSesiones._ID + "=?",
                new String[] {String.valueOf(idSesion)},
                null
        );
        while (c.moveToNext()) {
            cv.put(TablaSesiones.COL_DIAGNOSTICO, c.getString(c.getColumnIndex(TablaSesiones.COL_DIAGNOSTICO)));
            cv.put(TablaSesiones.COL_DOLOR, c.getString(c.getColumnIndex(TablaSesiones.COL_DOLOR)));
            cv.put(TablaSesiones.COL_ID_MOTIVO, idMotivo);
            cv.put(TablaSesiones.COL_INGRESOS, c.getString(c.getColumnIndex(TablaSesiones.COL_INGRESOS)));
            cv.put(TablaSesiones.COL_OBSERVACIONES, c.getString(c.getColumnIndex(TablaSesiones.COL_OBSERVACIONES)));
            cv.put(TablaSesiones.COL_POSTRATAMIENTO, c.getString(c.getColumnIndex(TablaSesiones.COL_POSTRATAMIENTO)));
        }
        c.close();

        uriNuevaSesion = context.getContentResolver().insert(QuiroGestProvider.CONTENT_URI_SESIONES,cv);


        c = context.getContentResolver().query(
                QuiroGestProvider.CONTENT_URI_TECNICAS,
                null,
                TablaTecnicas.COL_ID_SESION + "=?",
                new String[] {String.valueOf(idSesion)},
                null
        );
        while(c.moveToNext()) {
            cv.clear();
            cv.put(TablaTecnicas.COL_ID_SESION, uriNuevaSesion.getLastPathSegment());
            cv.put(TablaTecnicas.COL_FECHA, c.getString(c.getColumnIndex(TablaTecnicas.COL_FECHA)));
            cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, c.getString(c.getColumnIndex(TablaTecnicas.COL_ID_TIPO_TECNICA)));
            cv.put(TablaTecnicas.COL_OBSERVACIONES, c.getString(c.getColumnIndex(TablaTecnicas.COL_OBSERVACIONES)));
            cv.put(TablaTecnicas.COL_VALOR, c.getString(c.getColumnIndex(TablaTecnicas.COL_VALOR)));
            cv.put(TablaTecnicas.COL_ORDER, c.getString(c.getColumnIndex(TablaTecnicas.COL_ORDER)));
            context.getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS,cv);
        }
        c.close();
    }
}
