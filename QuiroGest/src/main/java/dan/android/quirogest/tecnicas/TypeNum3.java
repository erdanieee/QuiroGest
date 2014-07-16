package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import dan.android.quirogest.R;
import dan.android.quirogest.listFragments.TecnicasListFragment;

/**
 * Created by dan on 18/12/13.
 */
public class TypeNum3 extends SeekBar implements TecnicasListFragment.itemTecnicable {
    protected int minimumValue = 0;
    protected int maximumValue = 0;
    protected OnSeekBarChangeListener listener;
    private Tecnica.ChangeValueListener mChangeValueListener = null;


    public TypeNum3(final Context context) {
        super(context);

        //setWritable(false);

        setPadding(8, 8, 8, 8);

        //textProgress = (TextView)findViewById(android.R.id.progress);

        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d(this.getClass().toString(), "seekbar progress changed");
                 //textProgress.setText("Valor: " + minimumValue+1);
                seekBar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(this.getClass().toString(), "seekbar start");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(this.getClass().toString(), "seekbar stop");
                if (mChangeValueListener != null){
                    mChangeValueListener.valueChanged();
                    Log.d("TecnicaNum", "value changed");
                }
                seekBar.setSecondaryProgress(seekBar.getProgress());
            }
        });
    }


    public void setMin(Integer min){
        this.minimumValue = min;
    }


    public void setMax(Integer max){
        this.maximumValue = max;
        super.setMax(maximumValue - minimumValue);
    }



    public String getStringValue(){
        return String.valueOf(minimumValue + super.getProgress());
    }


    @Override
    public void setWritable(boolean b) {
        setClickable(b);
        setFocusable(b);
        setEnabled(b);

        if (b){
            setBackgroundColor(getResources().getColor(R.color.elementIsEditable));

        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setDefaultValue() {
        setValue((maximumValue-minimumValue)/2);
    }


    public void setChangeValueListener(Tecnica.ChangeValueListener l){
        mChangeValueListener = l;
    }

    @Override
    public void setValue(int value) {
        this.setProgress(value - minimumValue);
    }
}
