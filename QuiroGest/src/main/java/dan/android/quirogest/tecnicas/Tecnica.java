package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Tecnica<T extends TecnicasListFragment.itemTecnicable>{
    private static final String REGULAR_EXPRESSION_MATRIX = ",";

    T obj;


    public Tecnica(T o) {
        this.obj = o;
    }


    public void setValue(int idPadre, int min, int max, String v, String labels, String observ) {
        int[] values;
        String[] temp;

        //parse cbItems
        temp    = v.split(REGULAR_EXPRESSION_MATRIX);
        values  = new int[temp.length];
        for (int i=0; i< values.length; i++){
            values[i] = Integer.parseInt(temp[i]);
        }

        obj.setParentId(idPadre);
        obj.setMin(min);
        obj.setMax(max);
        obj.setValue(values);
        obj.setLabels(labels.split(REGULAR_EXPRESSION_MATRIX));
        obj.setObserv(observ);
    }
}
