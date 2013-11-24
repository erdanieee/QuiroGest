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
    public void setValue(int idPadre, int min, int max, int value) {
        setChecked(value != 0);
    }
}
