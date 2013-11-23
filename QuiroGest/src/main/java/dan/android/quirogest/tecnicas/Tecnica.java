package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Tecnica<T extends TecnicasListFragment.itemTecnicable>{
    T obj;

    public Tecnica(T o) {
        this.obj = o;
    }

    public void setValues(int min, int max, int value){
        obj.setValue(min, max, value);
    }
}
