package dan.android.quirogest.tecnicas;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.views.LabelView;

import static dan.android.quirogest.listFragments.TecnicasListFragment.itemTecnicable;

/**
 * Created by dan on 17/11/13.
 */
public class Tecnica extends RelativeLayout{
    private TextView mTitle;
    private LabelView mObserv;
    private TableLayout mTable;
    private EtiquetasView mEtiquetasContainer;   //TODO: crear clase que gestione añadir/borrar etiquetas con el listener mChangeValueListener
    private ArrayList<TextView> mLabelsCols;
    private ArrayList<TextView> mLabelsRows;
    private ArrayList<itemTecnicable> mViews;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mCols, mRows;
    private Class type;
    private String etiquetas;
    private ChangeValueListener mChangeValueListener;
    private ContentValues cv;
    private StringBuffer sb;
    private long tecnicaID;
    //private Uri URI = ContentUris.withAppendedId(QuiroGestProvider.CONTENT_URI_TECNICAS, tecnicaID);


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Tecnica(Context context, Class<itemTecnicable> T, int cols, int rows) throws IllegalAccessException, InstantiationException {
        super(context);

        mContext    = context;
        mInflater   = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCols       = cols;
        mRows       = rows;
        mLabelsCols = new ArrayList<TextView>(cols);
        mLabelsRows = new ArrayList<TextView>(rows);
        mViews      = new ArrayList<itemTecnicable>(cols * rows);
        type        = T;

        //save changes to database
        mChangeValueListener = new ChangeValueListener() {
            @Override
            public void valueChanged() {    //TODO: unificar la función para CheckBox/EditText y también observaciones y etiquetas. Para ello hacer que esta función tenga parámetro cv
                cv = new ContentValues();
                sb = new StringBuffer();

                for (itemTecnicable i : mViews){
                    sb.append(i.getStringValue());
                    sb.append(",");
                }

                sb.deleteCharAt(sb.length()-1);

                cv.put(TablaTecnicas.COL_VALOR, sb.toString());
                Log.d("Tecnica.class", "updating tecnicaId: " + tecnicaID + " value: " + cv.toString());

                mContext.getContentResolver().update(ContentUris.withAppendedId(QuiroGestProvider.CONTENT_URI_TECNICAS, tecnicaID), cv, null, null);
            }
        };

        //create common views
        mInflater.inflate(R.layout.tecnica_viewtype_grid, this);
        mTitle  = (TextView) findViewById(R.id.tecnicaDescripcion);
        mObserv = (LabelView) findViewById(R.id.tecnicaObservaciones);
        mTable  = (TableLayout) findViewById(R.id.tecnicaTable);
        mEtiquetasContainer = (EtiquetasView) findViewById(R.id.etiquetasContainer);
        this.setFocusable(false);

        //create columns labels
        TextView cell;
        TableRow tr = new TableRow(context);
        tr.addView(new TextView(context));
        for (int c=0; c<cols; c++){
            cell = new TextView(context);
            cell.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            //cell.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
                    v.setChangeValueListener(mChangeValueListener);

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


    public void setWritable(boolean b){
        for (itemTecnicable i : mViews){
            i.setWritable(b);
        }

        mObserv.setWritable(b);
        mEtiquetasContainer.setWritable(b);
    }


    public void setValues(String values) {
        if (values!=null) {
            String[] v = values.split(",");

            for (int i = 0; i < mViews.size(); i++) {
                mViews.get(i).setValue((Integer.valueOf(v[i])));
            }

        } else {
            for (itemTecnicable mView : mViews) {
                mView.setDefaultValue();
            }
        }
    }


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


    public void setId(long id) {
        tecnicaID = id;

        mObserv.setModificationParams(ContentUris.withAppendedId(
                        QuiroGestProvider.CONTENT_URI_TECNICAS, tecnicaID),
                TablaTecnicas.COL_OBSERVACIONES,
                LabelView.TYPE_TEXT);
    }


    public void setmObserv(String o) {
        mObserv.setText(o);
    }


    private void updateTextView(TextView t, String s){
        if(s!=null && !s.isEmpty()){
            t.setVisibility(View.VISIBLE);
            t.setText(s);

        }else{
            t.setVisibility(View.GONE);
        }
    }

    public void setEtiquetas(long idTecnica) {
        mEtiquetasContainer.loadEtiquetas(idTecnica);
    }

    public void setMin(int min) {
        for (itemTecnicable mView : mViews) {
            mView.setMin(min);
        }
    }

    public void setMax(int max) {
        for (int i = 0; i < mViews.size(); i++) {
            mViews.get(i).setMin(max);
        }
    }


    public interface ChangeValueListener{
        public void valueChanged();
    }
}
