package dan.android.quirogest.listFragmentBase;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class DetailFragmentBase extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String ITEM_ID = "item_id";
    private static final int LOADER_ID = 6813215;

    private View mRootView;

    public abstract int getLayout();
    public abstract Uri getUri();
    public abstract Long getItemId();


    protected View getRootView(){ return mRootView; }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getItemId()!=null && getItemId()>=0){
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);
        return mRootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), ContentUris.withAppendedId(getUri(), getItemId()), null, null, null, null);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        //no es necesario hacer nada
    }
}
