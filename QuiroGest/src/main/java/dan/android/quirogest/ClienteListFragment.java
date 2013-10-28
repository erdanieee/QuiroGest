package dan.android.quirogest;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaContactos;


public class ClienteListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "ClienteListFragment";
    private static final String[] PROJECTION = {TablaContactos._ID, TablaContactos.COL_NOMBRE};     //TODO: añadir otros campos para mostrar en la lista
    private static final int LOADER_ID       = 1;
    private SimpleCursorAdapter mAdapter;

    // The serialization (saved instance state) Bundle key representing the activated item position.
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition                       = ListView.INVALID_POSITION;


    // The fragment's current callback object, which is notified of list item clicks.
    private Callbacks mCallbacks = null;

    // A callback interface that all activities containing this fragment must implement. This mechanism allows activities to be notified of item selections.
    public interface Callbacks {
        public void onItemSelected(long id);
    }


    // Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes).
    public ClienteListFragment() {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "Activity created");
        super.onActivityCreated(savedInstanceState);

        //inicializamos el adapter con un cursor nulo hasta que se inicialice el Loader
        int layout          = android.R.layout.simple_list_item_activated_1;
        String[] columnas   = new String[] {TablaContactos.COL_NOMBRE};
        int[] vistas        = new int[] {android.R.id.text1};
        mAdapter            = new SimpleCursorAdapter(getActivity(), layout, null, columnas, vistas, 0);
        setListAdapter(mAdapter);

        //inicializamos el Loader con id LOADER_ID
        getLoaderManager().initLoader(LOADER_ID, null, this);

        //configuramos el ListView
        //TODO: comprobar si se puede incluir en el XML
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Si se guardó la posición la recuperamos
        if (savedInstanceState != null) {
            Log.i(TAG, "recuperamos posición guardada");
            mActivatedPosition = savedInstanceState.getInt(STATE_ACTIVATED_POSITION, 0);

        } else {
            mActivatedPosition = 0;
        }
    }


    private void setActivatedPosition(int position) {
        Log.i(TAG, "Nueva posición " + position);
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);

        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
    }



    //********************************************************************************************//
    // F R A G M E N T
    //********************************************************************************************//
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

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
        Log.i(TAG, "Item clicked posicion: " + position + " id: " + id);
        super.onListItemClick(listView, view, position, id);
        setActivatedPosition(position);
        mCallbacks.onItemSelected(getListView().getItemIdAtPosition(position));
    }



    //********************************************************************************************//
    // L O A D E R
    //********************************************************************************************//
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), QuiroGestProvider.CONTENT_URI_CONTACTOS, PROJECTION, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.i(TAG, "Loader 'loaded' ");
        switch (cursorLoader.getId()) {
            case LOADER_ID:
                mAdapter.swapCursor(cursor);
                break;
        }
        setActivatedPosition(mActivatedPosition);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}
