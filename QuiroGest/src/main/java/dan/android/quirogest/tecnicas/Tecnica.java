package dan.android.quirogest.tecnicas;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeEtiquetas;

import static dan.android.quirogest.listFragments.TecnicasListFragment.itemTecnicable;

/**
 * Created by dan on 17/11/13.
 */
public class Tecnica extends RelativeLayout{
    private TextView mTitle;
    private TextView mObserv;           //TODO: crear nueva clase que incluya el onClickListener, setWritable y dialogo de editar
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
                    sb.append(i.getValue());
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
        mObserv = (TextView) findViewById(R.id.tecnicaObservaciones);
        mTable  = (TableLayout) findViewById(R.id.tecnicaTable);
        mEtiquetasContainer = (EtiquetasView) findViewById(R.id.etiquetasContainer);
        this.setFocusable(false);

        //catch changes in observations
        mObserv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText t;
                AlertDialog.Builder d;

                t = new EditText(mContext);
                t.setInputType(InputType.TYPE_CLASS_TEXT);

                d = new AlertDialog.Builder(mContext);
                d.setTitle("Introduce el nuevo valor");
                d.setView(t);

                d.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mObserv.setText(t.getText().toString());

                        cv = new ContentValues();

                        cv.put(TablaTecnicas.COL_OBSERVACIONES, mObserv.getText().toString());
                        Log.d("Tecnica.class", "updating tecnicaId: " + tecnicaID + " value: " + cv.toString());

                        mContext.getContentResolver().update(ContentUris.withAppendedId(QuiroGestProvider.CONTENT_URI_TECNICAS, tecnicaID), cv, null, null);
                    }
                });
                d.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                d.show();
            }
        });

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

        mObserv.setEnabled(b);
        mObserv.setFocusable(b);
        mObserv.setFocusableInTouchMode(b);
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


    public void setId(long id) { tecnicaID = id;}


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
        mEtiquetasContainer.loadEtiquetas(etiquetas);
    }


    public interface ChangeValueListener{
        public void valueChanged();
    }
}
