package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Toast;

import java.util.HashMap;

import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;

/**
 * Created by dan on 16/11/13.
 */
public class TecnicasAdapter extends CursorAdapter{
    private static final String TAG = "TecnicasAdapter";

    public static final int VIEWTYPE_CHECKBOX   = 1;
    public static final int VIEWTYPE_NUMBER     = 2;
    public static final int VIEWTYPE_TEXT       = 3;
    public static final int VIEWTYPE_SECTION    = 4;     //TODO: ver opción automática http://kmansoft.com/2010/11/16/adding-group-headers-to-listview/
    public static final int VIEWTYPE_EMPTY      = 5;
    public static final int VIEWTYPE_SUBSECCION = 6;
    public static final int VIEWTYPE_COUNT      = 6;    //número de VIEWTYPEs

    private Context mContext;
    private static HashMap<Integer, Integer> mapTecnicasViewType = new HashMap<Integer, Integer>();
    public static boolean readWriteState = false;


    public TecnicasAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        mContext = context;
    }




    private int getTypeId(int nRows, int nCols, int viewType){
        return nRows + (nCols*100) + (viewType*10000);
    }


    @Override
    public int getItemViewType(int position) {
        int viewType, nCols, nRows, num;
        Cursor cursor = (Cursor) getItem(position);
        viewType    = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_VIEWTYPE));
        nRows       = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_ROWS));
        nCols       = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_COLS));
        num         = mapTecnicasViewType.get(getTypeId(nRows,nCols,viewType));

        Log.d(TAG, "Viewtype: " + num);

        return num;
    }


    @Override
    public int getViewTypeCount() {
        Cursor c;
        int viewType, nCols, nRows, num;

        num = 0;
        c   = mContext.getContentResolver().query(QuiroGestProvider.CONTENT_URI_NUM_VIEWS_TECNICAS,null,null,null,null);

        while (c.moveToNext()){
            viewType    = c.getInt(c.getColumnIndex(TablaTiposDeTecnicas.COL_VIEWTYPE));
            nRows       = c.getInt(c.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_ROWS));
            nCols       = c.getInt(c.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_COLS));

            mapTecnicasViewType.put(getTypeId(nRows,nCols,viewType), num++);
        }
        c.close();
        Log.d(TAG, "Número de vistas: " + num);

        return num;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType, numCols, numRows;
        String tittle;
        View view;
        //ViewHolder vh;
        Class type;

        view        = null;
        //vh          = new ViewHolder();
        viewType    = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_VIEWTYPE));
        numRows     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_ROWS));
        numCols     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_NUM_COLS));
        tittle      = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_TITLE));

        Log.d(TAG, "Nueva tecnica tipo " + viewType + " " + numCols + "x" + numRows);

        switch(viewType) {
            case VIEWTYPE_CHECKBOX:
                type = TypeCb.class;
                break;

            case VIEWTYPE_NUMBER:
                type = TypeNum.class;
                break;

            case VIEWTYPE_SECTION:
                return new Section(context);

            case VIEWTYPE_SUBSECCION:
                return new SubSection(context);

            case VIEWTYPE_TEXT:
            case VIEWTYPE_EMPTY:
                type = TypeCb.class; //TODO: implementar!!!
                break;

            default:
                Toast.makeText(mContext, "Error en la tabla, no existe tipo de vista para " + tittle + "!!", Toast.LENGTH_LONG).show();
                type = TypeCb.class;
                //throw new IllegalStateException ("Should never happend!!") ;
        }

        try {
            view = new Tecnica(context, type, numCols, numRows);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        //vh.mView = (Tecnica) view;
        //view.setTag(vh);
        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Integer min, max, idPadre;
        long id;
        String colLabel, rowLabel, observ, title, values;
        //ViewHolder mHolder = (ViewHolder) view.getTag();

        Log.d(TAG, "Bind tecnica");

        idPadre     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_ID_PARENT));
        min         = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MIN));
        max         = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MAX));
        values      = cursor.getString(cursor.getColumnIndex(TablaTecnicas.COL_VALOR));
        title       = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_TITLE));
        colLabel    = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_LABELS_COLS));
        rowLabel    = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_LABELS_ROWS));
        observ      = cursor.getString(cursor.getColumnIndex(TablaTecnicas.COL_OBSERVACIONES));
        id          = cursor.getLong(cursor.getColumnIndex(TablaTecnicas._ID));

        if (view instanceof Tecnica) {
            Tecnica t = (Tecnica) view;

            t.setParentId(idPadre);
            t.setMin(min);
            t.setMax(max);
            t.setValues(values);
            t.setTitle(title);
            t.setColsLabels(colLabel);
            t.setRowsLabels(rowLabel);
            t.setmObserv(observ);
            t.setEtiquetas(id);
            t.setWritable(readWriteState);
            t.setId(id);

        } else if(view instanceof Section){
            Section s = (Section) view;

            s.setText(title);
            s.setId(id);

        } else if(view instanceof SubSection){
            SubSection s = (SubSection) view;

            s.setText(title);
            s.setId(id);
        }
    }
}