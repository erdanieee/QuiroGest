package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.widget.CheckBox;

/**
 * Created by dan on 17/11/13.
 */
public class TecnicasViewTypeCheckbox extends CheckBox implements TecnicasListFragment.itemTecnicable{

    public TecnicasViewTypeCheckbox(Context context) {
        super(context);
    }

    @Override
    public void setValue(int min, int max, int value) {
        setChecked(value != 0);
    }
}
