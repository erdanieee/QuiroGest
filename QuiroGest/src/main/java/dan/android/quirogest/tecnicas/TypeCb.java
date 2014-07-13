package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import dan.android.quirogest.R;
import dan.android.quirogest.listFragments.TecnicasListFragment;

/**
 * Created by dan on 18/12/13.
 */
public class TypeCb extends CheckBox implements TecnicasListFragment.itemTecnicable {
    private Tecnica.ChangeValueListener mChangeValueListener = null;

    public TypeCb(Context context) {
        super(context);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onValueChanged();
            }
        });
    }


    @Override
    public void setValue(int value) {
        this.setChecked(value!=0);
    }

    @Override
    public String getValue() { return isChecked()? "1" : "0"; }

    @Override
    public void setWritable(boolean b) {
        setClickable(b);
        setFocusable(b);
        if (b) {
            setBackgroundColor(getResources().getColor(R.color.elementIsEditable));
        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setDefaultValue() {
        setValue(0);
    }


    public void onValueChanged(){
        if (mChangeValueListener != null){
            mChangeValueListener.valueChanged();
            Log.d("TecnicaCb", "value changed");
        }
    }


    public void setChangeValueListener(Tecnica.ChangeValueListener l){
        mChangeValueListener = l;
    }
}
