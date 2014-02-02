package dan.android.quirogest.tecnicas;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaTiposDeEtiquetas;
import dan.android.quirogest.listFragments.TecnicasListFragment;

import static dan.android.quirogest.listFragments.TecnicasListFragment.itemTecnicable;

/**
 * Created by dan on 17/11/13.
 */
public class Tecnica extends RelativeLayout{
    private TextView mTitle, mObserv;
    private TableLayout mTable;
    private LinearLayout mEtiquetasContainer;
    private ArrayList<TextView> mLabelsCols;
    private ArrayList<TextView> mLabelsRows;
    private ArrayList<itemTecnicable> mViews;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mCols, mRows;
    private Class type;
    private String etiquetas;
    private static HashMap<String, TipoEtiqueta> mapIdTipoEtiqueta = null;


    public Tecnica(Context context, int cols, int rows, Class<itemTecnicable> T) throws IllegalAccessException, InstantiationException {
        super(context);

        mContext    = context;
        mInflater   = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCols       = cols;
        mRows       = rows;
        mLabelsCols = new ArrayList<TextView>(cols);
        mLabelsRows = new ArrayList<TextView>(rows);
        mViews      = new ArrayList<itemTecnicable>(cols * rows);
        type        = T;

        //create common views
        mInflater.inflate(R.layout.tecnica_viewtype_grid, this);
        mTitle  = (TextView) findViewById(R.id.tecnicaDescripcion);
        mObserv = (TextView) findViewById(R.id.tecnicaObservaciones);
        mTable  = (TableLayout) findViewById(R.id.tecnicaTable);
        mEtiquetasContainer = (LinearLayout) findViewById(R.id.etiquetasContainer);

        //create columns labels
        TextView cell;
        TableRow tr = new TableRow(context);
        tr.addView(new TextView(context));
        for (int c=0; c<cols; c++){
            cell = new TextView(context);
            mLabelsCols.add(cell);
            tr.addView(cell);
        }
        mTable.addView(tr);

        //add the other rows
        TextView label;
        Constructor ctt=null;
        itemTecnicable v = null;
        try {
            ctt = type.getDeclaredConstructor(Context.class);
            ctt.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        for (int r=0; r<rows;r++){
            tr      = new TableRow(context);
            label   = new TextView(context);
            tr.addView(label);
            mLabelsRows.add(label);

            for (int c=0; c<cols; c++){

                try {
                    v = (itemTecnicable) ctt.newInstance(context);


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                tr.addView((View)v);
                mViews.add(v);

            }
            mTable.addView(tr);
        }
    }


    public void setValues(String values) {
        String[] v = values.split(",");

        for (int i=0; i< mViews.size();i++){
            ((itemTecnicable) mViews.get(i)).setValue((Integer.valueOf(v[i])));
        }
    }


    public void setMin(int min) { } //TODO


    public void setMax(int max) { } //TODO


    public void setParentId(int parentId) { } //TODO


    public void setColsLabels(String l) {
        String[] c;

        if (l!=null){
            c = l.split(",");

            for (int i=0; i< mLabelsCols.size();i++){
                updateTextView(mLabelsCols.get(i), c[i]);
            }
        }
    }


    public void setRowsLabels(String l) {
        String[] c;

        if (l!=null){
            c = l.split(",");

            for (int i=0; i< mLabelsRows.size();i++){
                updateTextView(mLabelsRows.get(i), c[i]);
            }
        }
    }


    public void setTitle(String l) {
        updateTextView(mTitle, l);
    }


    public void setmObserv(String o) {
        updateTextView(mObserv, o);
    }


    private void updateTextView(TextView t, String s){
        if(!s.isEmpty()){
            t.setVisibility(View.VISIBLE);
            t.setText(s);

        }else{
            t.setVisibility(View.GONE);
        }
    }


    public void setEtiquetas(String etiquetas) {
        if (null != etiquetas && !etiquetas.isEmpty()){
            for (String s : etiquetas.split(",")){
                TextView t;
                TipoEtiqueta tt;

                tt  = getTipoEtiqueta(s);
                t   = new TextView(mContext);

                t.setText(tt.descript);
                t.setBackgroundColor(Color.parseColor(tt.color));
                t.setTextColor(Color.BLACK);
                t.setMinWidth(40);
                t.setGravity(1);

                mEtiquetasContainer.addView(t);
            }
        }
    }


    private TipoEtiqueta getTipoEtiqueta(String idEtiqueta){
        if (mapIdTipoEtiqueta == null){
            Cursor c;
            String id, color, descript;

            mapIdTipoEtiqueta = new HashMap<String, TipoEtiqueta>();
            c = mContext.getContentResolver().query(QuiroGestProvider.CONTENT_URI_TIPO_ETIQUETAS, null, null, null, null);

            while(c.moveToNext()){
                id          = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA));
                color       = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_COLOR));
                descript    = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_DESCRIPCION));

                mapIdTipoEtiqueta.put(id, new TipoEtiqueta(id, color, descript));
            }
            c.close();
        }
        return mapIdTipoEtiqueta.get(idEtiqueta);
    }


    class TipoEtiqueta{
        String id, color, descript;

        public TipoEtiqueta(String id, String color, String descript) {
            this.id         = id;
            this.color      = color;
            this.descript   = descript;
        }
    }

}
