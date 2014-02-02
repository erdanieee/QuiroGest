package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import dan.android.quirogest.listFragments.TecnicasListFragment;

/**
 * Created by dan on 18/12/13.
 */
public class TypeCb extends CheckBox implements TecnicasListFragment.itemTecnicable {
    public TypeCb(Context context) {
        super(context);
        setClickable(false);
    }


    @Override
    public void setValue(int value) {
        this.setChecked(value!=0);
    }
}
