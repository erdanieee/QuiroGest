package dan.android.quirogest.FragmentBase;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


public abstract class DetailFragmentBase extends Fragment{
    protected static final String ITEM_ID = "item_id";
    private Cursor mCursor;
    private long mItemId;

    public abstract Uri         getUri();
    public abstract String[]    getProjection();

    public long         getItemId(){ return mItemId;}
    protected Cursor    getCursor(){ return mCursor; }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!getArguments().containsKey(ITEM_ID)){
            throw new IllegalStateException("Se ha intentado crear el fragment sin proporcionar un ID.");
        }
        mItemId = getArguments().getLong(ITEM_ID, -1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCursor = getActivity().getContentResolver().query(ContentUris.withAppendedId(getUri(), getItemId()), getProjection(), null, null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }
}
