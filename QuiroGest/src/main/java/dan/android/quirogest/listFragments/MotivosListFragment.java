package dan.android.quirogest.listFragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import dan.android.quirogest.listFragmentBase.ListFragmentBase;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;


public class MotivosListFragment extends ListFragmentBase {
    private final String TAG = "MotivosListFragment";
    private final String[] PROJECTION = {BaseColumns._ID, TablaMotivos.COL_DIAGNOSTICO, TablaMotivos.COL_FECHA};
    private final String[] COLUMNS = {TablaMotivos.COL_FECHA, TablaMotivos.COL_DIAGNOSTICO};
    private final int[] VISTAS = {android.R.id.text1, android.R.id.text2};
    private final Uri URI = QuiroGestProvider.CONTENT_URI_MOTIVOS;
    private final int LAYOUT = android.R.layout.simple_list_item_2;


    @Override
    public ListTypes getListViewType() { return ListTypes.LIST_VIEW_MOTIVOS; }

    @Override
    public String[] getProjection() { return PROJECTION; }

    @Override
    public String getSelection() { return TablaMotivos.COL_ID_CONTACTO + "=?"; }

    @Override
    public String[] getSelectionArgs() { return new String[] {String.valueOf(mContactoId)};}

    @Override
    public String getOrder() { return "DATE(" + TablaMotivos.COL_FECHA + ") DESC"; }

    @Override
    public int getListLayout() { return LAYOUT; }

    @Override
    public String[] getColumns() { return COLUMNS; }

    @Override
    public int[] getViews() { return VISTAS; }

    @Override
    public Uri getUri() { return URI; }
}
