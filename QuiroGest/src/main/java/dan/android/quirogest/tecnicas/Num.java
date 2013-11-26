package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Num extends LinearLayout implements TecnicasListFragment.itemTecnicable{
    private TextView title, observ, num;


    public Num(Context context) { super(context, null); }
    public Num(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        title   = (TextView) findViewById(R.id.tecnicaDescripcion);
        observ  = (TextView) findViewById(R.id.tecnicaObservaciones);
        num     = (TextView) findViewById(R.id.tecnicaNum);
    }

    @Override
    public void setValue(int[] value) {
        num.setText(String.valueOf(value[0]));
    }

    @Override
    public void setMin(int min) {}

    @Override
    public void setMax(int max) {}

    @Override
    public void setParentId(int parentId) {}

    @Override
    public void setLabels(String[] labels) {
        title.setText(labels[0]);
    }

    @Override
    public void setObserv(String o) {
        observ.setText(o);
    }
}
