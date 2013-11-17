package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by dan on 17/11/13.
 */
public class TecnicasViewTypeNumber extends TextView implements TecnicasListFragment.itemTecnicable{


    public TecnicasViewTypeNumber(Context context) {
        super(context);
    }

    @Override
    public void setValue(int min, int max, int value) {
        if (value >= min && value <= max){
            setText(value);

        } else {
            setText("ERROR!!!!");
        }
    }
}
