package dan.android.quirogest.views;


import android.net.Uri;

/**
 * Created by dan on 8/06/14.
 */
public interface ModificableView {
    public void setModificationParams(Uri uri, String tableColumn, int type);
    public void setEditable (boolean e);
    public boolean isEditable();
    public void setModificationCallback(LabelModificationListener c);
}
