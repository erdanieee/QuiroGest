package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dan on 17/11/13.
 */
public class Num extends TextView implements TecnicasListFragment.itemTecnicable{

    public Num(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Num(Context context) {
        super(context);
    }


    @Override
    public void setValue(int[] value) {
        setText(String.valueOf(value[0]));
    }

    @Override
    public void setMin(int min) {}

    @Override
    public void setMax(int max) {}

    @Override
    public void setParentId(int parentId) {}

    @Override
    public void setLabels(String[] labels) { }

    @Override
    public void setObserv(String observ) { }
}
