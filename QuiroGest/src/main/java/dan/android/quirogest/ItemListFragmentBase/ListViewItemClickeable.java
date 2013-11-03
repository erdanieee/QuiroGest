package dan.android.quirogest.ItemListFragmentBase;

import android.net.Uri;

/**
 * Created by dan on 1/11/13.
 */
public interface ListViewItemClickeable {
    public int getListviewTag();
    public String[] getProjection();
    public String getSelection();
    public String[] getSelectionArgs();
    public String getOrder();
    public int getListLayout();
    public String[] getColumns();
    public int[] getViews();
    public Uri getUri();

}
