package dan.android.quirogest.listFragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaSesiones;
import dan.android.quirogest.tecnicas.TecnicasAdapter;

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
    // F R A G M E N T          L I F E C Y C L I N G
    //********************************************************************************************//
/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //necesario para que aparezca el menú
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);

        //borrar elementos de la lista
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
    }
*/




    //********************************************************************************************//
    // C O N T E X T U A L   A C T I O N   B A R
    //********************************************************************************************//
/*    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Here you can perform updates to the CAB due to
        // an invalidate() request
        return false;
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate the menu for the CAB
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.contextual_sesiones, menu);
        mode.setTitle("Select Items");
        return true;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        final int checkedCount = getListView().getCheckedItemCount();
        switch (checkedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + checkedCount + " items selected");
                break;
        }
    }


    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {
            case R.id.contextualMenuDeleteItem:
                Uri u;

                for(long id : getListView().getCheckedItemIds()){
                    u = Uri.withAppendedPath(getUri(), String.valueOf(id));
                    getActivity().getContentResolver().delete(u,null,null);
                }
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Here you can make any necessary updates to the activity when
        // the CAB is removed. By default, selected items are deselected/unchecked.
    }
*/

}
