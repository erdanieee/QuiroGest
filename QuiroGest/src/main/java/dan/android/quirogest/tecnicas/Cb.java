package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Cb extends LinearLayout implements TecnicasListFragment.itemTecnicable{
    private TextView title, observ;
    private CheckBox checkBox;


    public Cb(Context context) { this(context,null); }
    public Cb(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        title   = (TextView) findViewById(R.id.tecnicaDescripcion);
        observ  = (TextView) findViewById(R.id.tecnicaObservaciones);
        checkBox    = (CheckBox) findViewById(R.id.tecnicaCheckBox);
    }

    @Override
    public void setValue(int[] value) {
        checkBox.setChecked(value[0] != 0);
    }

    @Override
    public void setMin(int min) {}

    @Override
    public void setMax(int max) {}

    @Override
    public void setParentId(int parentId) {}

    @Override
    public void setmLabels(String[] labels) {
        title.setText(labels[0]);
    }

    @Override
    public void setmObserv(String o) {
        observ.setText(o);
    }
}
