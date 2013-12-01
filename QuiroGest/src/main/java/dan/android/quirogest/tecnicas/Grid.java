package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Grid extends LinearLayout implements TecnicasListFragment.itemTecnicable{
    private TextView mTitle, mObserv;
    private TableLayout mTable;
    private ArrayList<TextView> mLabels;             //primero columnas, luego filas
    private ArrayList<CheckBox> mCheckBoxes;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mCols, mRows;


    public Grid(Context context, int cols, int rows){
        super(context);

        mContext    = context;
        mInflater   = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCols       = cols;
        mRows       = rows;
        mLabels     = new ArrayList<TextView>(cols + rows);
        mCheckBoxes = new ArrayList<CheckBox>(cols * rows);

        //create common views
        mInflater.inflate(R.layout.tecnica_viewtype_grid, this);
        mTitle  = (TextView) findViewById(R.id.tecnicaDescripcion);
        mObserv = (TextView) findViewById(R.id.tecnicaObservaciones);
        mTable  = (TableLayout) findViewById(R.id.tecnicaTable);

        //create columns labels
        TextView cell;
        TableRow tr = new TableRow(context);
        tr.addView(new TextView(context));
        for (int c=0; c<cols; c++){
            cell = new TextView(context);
            mLabels.add(cell);
            tr.addView(cell);
        }
        mTable.addView(tr);

        //add the other rows
        TextView label;
        CheckBox val;
        for (int r=0; r<rows;r++){
            tr      = new TableRow(context);
            label   = new TextView(context);
            tr.addView(label);
            mLabels.add(label);

            for (int c=0; c<cols; c++){
                val = new CheckBox(context);
                tr.addView(val);
                mCheckBoxes.add(val);
            }
            mTable.addView(tr);
        }
    }


    @Override
    public void setValue(int[] v) {
        for (int i=0; i< mCheckBoxes.size();i++){
            mCheckBoxes.get(i).setChecked(v[i]!=0);
        }
    }

    @Override
    public void setMin(int min) { }

    @Override
    public void setMax(int max) { }

    @Override
    public void setParentId(int parentId) { }

    @Override
    public void setmLabels(String[] l) {
        //set mLabels
        for (int i=0; i< mLabels.size();i++){
            mLabels.get(i).setText(l[i]);
        }

        //set mTitle
        mTitle.setText(l[l.length - 1]);
    }

    @Override
    public void setmObserv(String o) {
        mObserv.setText(o);
    }
}
