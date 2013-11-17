package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import dan.android.quirogest.R;

/**
 * Created by dan on 17/11/13.
 */
public abstract class TecnicasViewTypeBase extends LinearLayout{
    LayoutInflater mInflater;

    public TecnicasViewTypeBase(Context context) {
        super(context);
    }

    public abstract void setValues(int min, int max, int value);
}
