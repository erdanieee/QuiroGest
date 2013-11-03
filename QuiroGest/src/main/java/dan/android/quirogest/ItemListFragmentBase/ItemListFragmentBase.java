package dan.android.quirogest.ItemListFragmentBase;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public abstract class ItemListFragmentBase extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, ListViewItemClickeable {
    private static final int LOADER_ID  = 234237823;
    private SimpleCursorAdapter mAdapter;

    // The serialization (saved instance state) Bundle key representing the activated item position.
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition                       = ListView.INVALID_POSITION;

    // The fragment's current callback object, which is notified of list item clicks.
    private CallbackItemClicked mCallbacks = null;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //inicializamos el adapter con un cursor nulo hasta que se inicialice el Loader
        mAdapter = new SimpleCursorAdapter(getActivity(), getListLayout(), null, getColumns(), getViews(), 0);
        setListAdapter(mAdapter);

        //inicializamos el Loader con id LOADER_ID
        getLoaderManager().initLoader(LOADER_ID, null, this);

        //Si se guardó la posición la recuperamos
        if (savedInstanceState != null) {
            mActivatedPosition = savedInstanceState.getInt(STATE_ACTIVATED_POSITION, 0);

        } else {
            mActivatedPosition = 0;
        }
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
    // F R A G M E N T
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
        super.onListItemClick(listView, view, position, id);
        setActivatedPosition(position);
        mCallbacks.onListItemSelected(this, getListView().getItemIdAtPosition(position));
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
        setActivatedPosition(mActivatedPosition);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}


