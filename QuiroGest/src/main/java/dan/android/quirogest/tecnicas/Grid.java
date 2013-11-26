package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Grid extends GridLayout implements TecnicasListFragment.itemTecnicable{
    private static final int MAX_ROW = 6;
    private static final int MAX_COL = 3;
    ArrayList<TextView> rowLabels = new ArrayList<TextView>(MAX_ROW);
    ArrayList<TextView> colLabels = new ArrayList<TextView>(MAX_COL);
    ArrayList<CheckBox> values    = new ArrayList<CheckBox>(MAX_ROW * MAX_COL);
    private boolean init =false;
    private Context context;
    int row=0, col=0, index=0;


    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public Grid(Context context) {
        super(context);
        this.context = context;
    }

    private void setLabelFila(int fila, String label){}
    private void setLabelCol (int col, String label){}
    public void init(int parentId){
        //obtenemos las descripciones para las columnas
       //TODO!!!!!!!!!!!!!!!
        init = true;
    }

    @Override
    public void setValue(int idPadre, int min, int max, int value) {
        if(!init){
            init(idPadre);
        }
        if (col++==0){
            addView(new TableRow(context));
        }

        CheckBox cb = new CheckBox(context);
        cb.setChecked(value!=0);

        ((TableRow)getChildAt(getRowCount()-1)).addView(cb);
    }
}
