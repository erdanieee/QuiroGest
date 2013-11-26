package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Grid_6x3 extends GridLayout implements TecnicasListFragment.itemTecnicable{
    private static final int NUM_ROW = 6;
    private static final int NUM_COL = 3;
    TextView title, observ;
    TableLayout table;
    ArrayList<TextView> labels  = new ArrayList<TextView>(NUM_ROW + NUM_COL);  //primero columnas, luego filas
    ArrayList<CheckBox> cbItems = new ArrayList<CheckBox>(NUM_ROW * NUM_COL);
    private Context context;


    public Grid_6x3(Context context) { this(context, null);}
    public Grid_6x3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context c){
        this.context = c;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tecnica_viewtype_grid, this, true);

        title   = (TextView) findViewById(R.id.tecnicaDescripcion);
        observ  = (TextView) findViewById(R.id.tecnicaObservaciones);
        table   = (TableLayout) findViewById(R.id.tecnicaTable);

        //get columns labels
        TableRow tr = (TableRow)table.getChildAt(0);
        for (int i=0; i<tr.getChildCount(); i++){
            labels.add((TextView)tr.getChildAt(i));
        }

        //get row labels
        for (int i=1; i<table.getChildCount(); i++){
            tr = (TableRow)table.getChildAt(i);
            labels.add((TextView)tr.getChildAt(0));
        }

        //get checkBox items
        for (int i=1; i<table.getChildCount(); i++){
            tr = (TableRow)table.getChildAt(i);
            for (int j=1; j<tr.getChildCount(); j++){
                cbItems.add((CheckBox)tr.getChildAt(j));
            }
        }
    }


    @Override
    public void setValue(int[] v) {
        for (int i=0; i< cbItems.size();i++){
            cbItems.get(i).setChecked(v[i]!=0);
        }
    }

    @Override
    public void setMin(int min) { }

    @Override
    public void setMax(int max) { }

    @Override
    public void setParentId(int parentId) { }

    @Override
    public void setLabels(String[] l) {
        //set labels
        for (int i=0; i< labels.size();i++){
            labels.get(i).setText(l[i]);
        }

        //set title
        title.setText(l[l.length-1]);
    }

    @Override
    public void setObserv(String o) {
        observ.setText(o);
    }
}
