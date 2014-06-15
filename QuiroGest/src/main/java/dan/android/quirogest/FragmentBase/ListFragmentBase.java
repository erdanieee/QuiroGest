package dan.android.quirogest.FragmentBase;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.views.LabelView;


public abstract class ListFragmentBase extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.MultiChoiceModeListener{
    private static final String TAG                         = "ListFragmentBase";
    private static final int    LOADER_ID                   = 234237823;
    private static final String STATE_ACTIVATED_POSITION    = "activated_position";
    public  static final String ARG_SELECTED_ITEM_ID        = "default_selected_item_id";

    public static enum ListTypes {
        LIST_VIEW_CLIENTES,
        LIST_VIEW_MOTIVOS,
        LIST_VIEW_SESIONES,
        LIST_VIEW_TECNICAS;
    }

    private CallbackItemClicked mCallbacks          = null;
    private SimpleCursorAdapter mAdapter          = null;
    private int                 mActivatedPosition  = ListView.INVALID_POSITION;


    // Métodos abstractos
    public abstract ListTypes   getListViewType();
    public abstract String[]    getProjection();
    public abstract String      getSelection();
    public abstract String[]    getSelectionArgs();
    public abstract String      getOrder();
    public abstract int         getListLayout();
    public abstract String[]    getColumns();
    public abstract int[]       getViews();
    public abstract Uri         getUri();
    public abstract void        init();


    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);

        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
        Log.d(TAG, "seleccionamos item " + position);
    }



    //********************************************************************************************//
    // L I S T F R A G M E N T
    //********************************************************************************************//
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        setActivatedPosition(position);
        mCallbacks.onListItemSelected(this, id);
    }



    //********************************************************************************************//
    // F R A G M E N T          L I F E C Y C L I N G
    //********************************************************************************************//
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof CallbackItemClicked)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (CallbackItemClicked) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inicializamos el adapter con un cursor nulo hasta que se inicialice el Loader
        mAdapter = new SimpleCursorAdapter(getActivity(), getListLayout(), null, getColumns(), getViews(), 0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view instanceof LabelView){
                    String date;

                    date = DatabaseHelper.parseSQLiteToDateformat(cursor.getString(columnIndex), new SimpleDateFormat(LabelView.DATE_FORMAT));

                    ((TextView)view).setText(date);
                    return true;
                }
                return false;
            }
        });
        setListAdapter(mAdapter);

        //inicializamos el fragment
        init();

        //empezamos la carga de datos en paralelo
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(this);
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }



    //********************************************************************************************//
    // L O A D E R
    //********************************************************************************************//
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), getUri(), getProjection(), getSelection(), getSelectionArgs(), getOrder());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        switch (cursorLoader.getId()) {
            case LOADER_ID:
                mAdapter.swapCursor(cursor);
                break;
            default:
                throw new IllegalStateException("Nunca debería pasar esto");
        }

        if (getArguments()!=null && getArguments().containsKey(ARG_SELECTED_ITEM_ID)){
            Log.d(TAG, "queremos un item preseleccionado");
            long id = getArguments().getLong(ARG_SELECTED_ITEM_ID);

            for (int p=0; p<mAdapter.getCount(); p++){
                if (mAdapter.getItemId(p) == id){
                    mActivatedPosition = p;
                    getArguments().remove(ARG_SELECTED_ITEM_ID);
                    break;
                }
            }
        }

        setActivatedPosition(mActivatedPosition);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }



    //********************************************************************************************//
    // I N T E R F A C E S
    //********************************************************************************************//
    public interface CallbackItemClicked {
        public void onListItemSelected(ListFragmentBase lfb, long id);
    }







    //********************************************************************************************//
    // C O N T E X T U A L   A C T I O N   B A R
    //********************************************************************************************//
    @Override
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
}


