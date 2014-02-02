package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;
import dan.android.quirogest.listFragments.TecnicasListFragment;

/**
 * Created by dan on 16/11/13.
 */
public class TecnicasAdapter extends CursorAdapter{
    public static final int VIEWTYPE_CHECKBOX  = 1;
    public static final int VIEWTYPE_NUMBER    = 2;
    public static final int VIEWTYPE_TEXT      = 3;
    public static final int VIEWTYPE_SECTION   = 4;     //TODO: ver opción automática http://kmansoft.com/2010/11/16/adding-group-headers-to-listview/
    public static final int VIEWTYPE_COUNT     = 4;    //número de VIEWTYPEs


    public TecnicasAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public int getItemViewType(int position) {
        Cursor cursor = (Cursor) getItem(position);
        return cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_VIEWTYPE));
    }


    @Override
    public int getViewTypeCount() {
        return VIEWTYPE_COUNT;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType, numCols, numRows;
        View view;
        //ViewHolder vh;
        Class type;

        view        = null;
        //vh          = new ViewHolder();
        viewType    = getItemViewType(cursor.getPosition());
        numCols     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_COLS));
        numRows     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_ROWS));

        switch(viewType) {
            case VIEWTYPE_CHECKBOX:
                type = TypeCb.class;
                break;

            case VIEWTYPE_NUMBER:
                type = TypeNum.class;
                break;

            default:
                throw new IllegalStateException ("Should never happend!!") ;
        }

        try {
            //vh.mView = new Tecnica(context, numCols, numRows, type);
            view = new Tecnica(context, numCols, numRows, type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        //view = vh.mView;
        //view.setTag(vh);
        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int min, max, idPadre;
        String colLabel, rowLabel, observ, title, values, etiquetas;
        //ViewHolder mHolder = (ViewHolder) view.getTag();

        idPadre     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_ID_PARENT));
        min         = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MIN));
        max         = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MAX));
        values      = cursor.getString(cursor.getColumnIndex(TablaTecnicas.COL_VALOR));
        title       = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_TITLE));
        colLabel    = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_LABELS_COLS));
        rowLabel    = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_LABELS_ROWS));
        observ      = cursor.getString(cursor.getColumnIndex(TablaTecnicas.COL_OBSERVACIONES));
        etiquetas   = cursor.getString(cursor.getColumnIndex(TecnicasListFragment.PROY_COMB));

        Tecnica t = (Tecnica) view;

        t.setParentId(idPadre);
        t.setMin(min);
        t.setMax(max);
        t.setValues(values);
        t.setTitle(title);
        t.setColsLabels(colLabel);
        t.setRowsLabels(rowLabel);
        t.setmObserv(observ);
        t.setEtiquetas(etiquetas);
    }


    public static class ViewHolder{
        Tecnica mView;
    }




}