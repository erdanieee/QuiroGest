package dan.android.quirogest.listFragmentBase;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import dan.android.quirogest.listFragments.ClienteListFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;


public abstract class ListFragmentBase extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static enum ListTypes {
        LIST_VIEW_CLIENTES,
        LIST_VIEW_MOTIVOS,
        TYPE_SESIONES_LIST;
    }

    private static final int LOADER_ID  = 234237823;
    private SimpleCursorAdapter mAdapter;

    // The serialization (saved instance state) Bundle key representing the activated item position.
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition                       = ListView.INVALID_POSITION;
    private static final String ITEM_ID = "item_id";

    // The fragment's current callback object, which is notified of list item clicks.
    private CallbackItemClicked mCallbacks = null;

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



    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static ListFragmentBase newInstance(ListTypes type, long itemId) {        //TODO: Mover esta función al método Base
        ListFragmentBase f = null;

        switch (type){
            case LIST_VIEW_CLIENTES:
                f = new ClienteListFragment();
                break;
            case LIST_VIEW_MOTIVOS:
                f = new MotivosListFragment();
                break;

        }

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(ITEM_ID, itemId);          //se pone la variable como argumento para el loader...
        f.setArguments(args);

        return f;
    }


    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);

        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
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
        setListAdapter(mAdapter);

        //inicializamos el Loader con id LOADER_ID
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
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
    // L I S T   F R A G M E N T
    //********************************************************************************************//
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        setActivatedPosition(position);
        mCallbacks.onListItemSelected(this, id);
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
        }

        if (getArguments().containsKey(ITEM_ID)){
            long id = getArguments().getLong(ITEM_ID);

            for (int p=0; p<mAdapter.getCount(); p++){
                if (mAdapter.getItemId(p) == id){
                    mActivatedPosition = p;
                    getArguments().remove(ITEM_ID);
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
}


