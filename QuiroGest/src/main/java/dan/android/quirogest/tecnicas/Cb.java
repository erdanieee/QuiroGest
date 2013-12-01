package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Cb extends RelativeLayout implements TecnicasListFragment.itemTecnicable{
    private TextView title, observ;
    private CheckBox checkBox;
    private LayoutInflater mInflater;


    public Cb(Context context) {
        super(context);

        mInflater   = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.tecnica_viewtype_checkbox,this);

        title       = (TextView) findViewById(R.id.tecnicaDescripcion);
        observ      = (TextView) findViewById(R.id.tecnicaObservaciones);
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
