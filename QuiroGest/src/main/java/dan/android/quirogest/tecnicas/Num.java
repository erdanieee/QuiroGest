package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public class Num extends RelativeLayout implements TecnicasListFragment.itemTecnicable{
    private TextView title, observ, num;
    private LayoutInflater mInflater;

    public Num(Context context) {
        super(context);

        mInflater   = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.tecnica_viewtype_numero,this);

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
    public void setmLabels(String[] labels) {
        title.setText(labels[0]);
    }

    @Override
    public void setmObserv(String o) {
        observ.setText(o);
    }
}
