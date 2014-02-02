package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import dan.android.quirogest.listFragments.TecnicasListFragment;

/**
 * Created by dan on 18/12/13.
 */
public class TypeNum extends TextView implements TecnicasListFragment.itemTecnicable {
    public TypeNum(Context context) {
        super(context);

        setPadding(8,8,8,8);
    }


    @Override
    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }
}
