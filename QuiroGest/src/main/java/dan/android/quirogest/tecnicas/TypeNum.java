package dan.android.quirogest.tecnicas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ResourceBundle;

import dan.android.quirogest.R;
import dan.android.quirogest.activities.MotivosListActivity;
import dan.android.quirogest.listFragments.TecnicasListFragment;

/**
 * Created by dan on 18/12/13.
 */
public class TypeNum extends TextView implements TecnicasListFragment.itemTecnicable {
    private Tecnica.ChangeValueListener mChangeValueListener = null;

    public TypeNum(final Context context) {
        super(context);

        //setWritable(false);

        setPadding(8, 8, 8, 8);
        setSelectAllOnFocus(true);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText t;
                AlertDialog.Builder d;

                t = new EditText(context);
                t.setInputType(InputType.TYPE_CLASS_NUMBER);

                d = new AlertDialog.Builder(context);
                d.setTitle("Introduce el nuevo valor");
                d.setView(t);

                d.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setText(t.getText().toString());
                        onValueChanged();
                    }
                });
                d.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                d.show();
            }
        });
    }


    @Override
    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }


    @Override
    public String getValue() { return getText().toString(); }


    @Override
    public void setWritable(boolean b) {
        setEnabled(b);
        setFocusable(b);
        setFocusableInTouchMode(b);
    }


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return false;
    }*/

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
