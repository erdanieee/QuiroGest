package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import dan.android.quirogest.R;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;

/**
 * Created by dan on 16/11/13.
 */
public class TecnicasAdapter extends CursorAdapter{
    public static final int VIEWTYPE_CHECKBOX  = 1;
    public static final int VIEWTYPE_NUMBER    = 2;
    public static final int VIEWTYPE_GRID_6x3 = 3;
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
        int viewType;
        View view;
        ViewHolder vh;
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view        = null;
        vh          = new ViewHolder();
        mInflater   = LayoutInflater.from(parent.getContext());
        viewType    = getItemViewType(cursor.getPosition());

        switch(viewType) {
            case VIEWTYPE_CHECKBOX:
                view        = mInflater.inflate(R.layout.tecnica_viewtype_checkbox, parent,false);
                vh.mView    = new Tecnica<Cb>((Cb)view);
                break;

            case VIEWTYPE_NUMBER:
                view        = mInflater.inflate(R.layout.tecnica_viewtype_numero, parent,false);
                vh.mView    = new Tecnica<Num> ((Num) view);
                break;

            case VIEWTYPE_GRID_6x3:
                view        = mInflater.inflate(R.layout.tecnica_viewtype_grid, parent,false);
                vh.mView    = new Tecnica<Grid_6x3> ((Grid_6x3)view);
                break;
            
            default:
                throw new IllegalStateException ("Should never happend!!") ;
        }

        view.setTag(vh);
        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int min, max, idPadre;
        String label, observ, value;
        ViewHolder mHolder = (ViewHolder) view.getTag();

        idPadre = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_ID_PARENT));
        min     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MIN));
        max     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MAX));
        value   = cursor.getString(cursor.getColumnIndex(TablaTecnicas.COL_VALOR));                    //TODO: sugerencia. Cambiar a String para poder hacer concatenaciones (group_concat. ej: http://stackoverflow.com/questions/16269363/joining-multiple-records-in-a-cursoradapter) o usa binarios
        label   = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_ETIQUETA));    //TODO: ajustar para que las matrices puedan coger todos los labels en una sola casilla
        observ  = cursor.getString(cursor.getColumnIndex(TablaTecnicas.COL_OBSERVACIONES));

        mHolder.mView.setValue(idPadre,min,max,value,label,observ);
    }


    public static class ViewHolder{
        Tecnica mView;
    }
}