package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by dan on 17/11/13.
 */
public class Cb extends CheckBox implements TecnicasListFragment.itemTecnicable{


    public Cb(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Cb(Context context) {
        super(context);
    }


    @Override
    public void setValue(int[] value) {
        setChecked(value[0] != 0);
    }

    @Override
    public void setMin(int min) {}

    @Override
    public void setMax(int max) {}

    @Override
    public void setParentId(int parentId) {}

    @Override
    public void setLabels(String[] labels) {

    }

    @Override
    public void setObserv(String observ) {

    }
}
