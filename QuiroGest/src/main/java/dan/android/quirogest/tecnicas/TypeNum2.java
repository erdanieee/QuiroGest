package dan.android.quirogest.tecnicas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import dan.android.quirogest.R;
import dan.android.quirogest.listFragments.TecnicasListFragment;

/**
 * Created by dan on 18/12/13.
 */
public class TypeNum2 extends NumberPicker implements TecnicasListFragment.itemTecnicable {
    private Tecnica.ChangeValueListener mChangeValueListener = null;

    public TypeNum2(final Context context) {
        super(context);

        //setWritable(false);

        setPadding(8, 8, 8, 8);

        this.setChangeValueListener(new Tecnica.ChangeValueListener() {
            @Override
            public void valueChanged() {
                onValueChanged();
            }
        });
    }


    @Override
    public void setMax(Integer max) {
        if (max!=null){
            super.setMaxValue(max);
        }
    }

    @Override
    public void setMin(Integer min) {
        if (min!=null && min>=0) {
            super.setMinValue(min);
        }else{
            super.setMinValue(0);
        }
    }


    public String getStringValue(){
        return String.valueOf(super.getValue());
    }


    @Override
    public void setWritable(boolean b) {
        setClickable(b);
        setFocusable(b);

        if (b){
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
            Log.d("TecnicaNum", "value changed");
        }
    }


    public void setChangeValueListener(Tecnica.ChangeValueListener l){
        mChangeValueListener = l;
    }
}
