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
    public void setValue(int idPadre, int min, int max, int value) {
        if (value >= min && value <= max){
            setText(String.valueOf(value));

        } else {
            setText("ERROR!!!!");
        }
    }
}
