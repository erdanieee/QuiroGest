package dan.android.quirogest.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;

/**
 * Created by dan on 8/06/14.
 */
public class LabelView extends TextView implements ModificableView, View.OnClickListener, View.OnKeyListener{
    public static final String DATE_FORMAT  = "dd/MM/yyyy";
    public static final int TYPE_TEXT       = 0;
    public static final int TYPE_NUM        = 1;
    public static final int TYPE_DATE       = 2;
    private boolean mEditable               = false;
    private String mColumn                  = null;
    private Uri mUri                        = null;
    private int mType;
    private ContentValues cv;
    private LabelModificationListener mLabelModificationListener = null;
    private String mText, mAlternativeText=null;


    public LabelView(Context context) { super(context); init(); }
    public LabelView(Context context, AttributeSet attrs) { super(context, attrs); init(); }
    public LabelView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); init(); }


    public void setAltText(String altText){
        mAlternativeText = altText;
        updateText();
    }


    public void setText(String t){
        mText = "";

        if (t!=null && !t.equals("") && !t.equals("-1")){
            switch (mType){
                case TYPE_DATE:
                    mText = DatabaseHelper.parseSQLiteToDateformat(t, new SimpleDateFormat(DATE_FORMAT));
                    break;
                case TYPE_NUM:
                case TYPE_TEXT:
                    mText = t;
                    break;
            }
        }

        updateText();

        if (mLabelModificationListener!=null){
            mLabelModificationListener.onLabelModification(mText);
        }
    }


    private void updateText(){
        if (!isEditable()){
            super.setText(mAlternativeText!=null?mAlternativeText:mText);

        } else {
            if (mText==null || mText.equals("")){
                switch (mType){
                    case TYPE_TEXT:
                        super.setText("XXX");
                        break;
                    case TYPE_NUM:
                        super.setText("0");
                        break;
                    case TYPE_DATE:
                        super.setText(new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime()));
                        break;
                }
            } else {
                super.setText(mText);
            }
        }
    }


    private void init(){
        setOnClickListener(this);
        setBackgroundResource(android.R.drawable.list_selector_background);
    }


    @Override
    public void setModificationParams(Uri uri, String tableColumn, int type) {
        mUri        = uri;
        mColumn     = tableColumn;
        mType       = type;
    }


    public void setWritable(boolean b) {
        mEditable = b;

        setClickable(b);
        setFocusable(b);

        if (b){
            /*ObjectAnimator o;
            o = ObjectAnimator.ofObject(this, "backgroundColor", new ArgbEvaluator(), getResources().getColor(R.color.elementIsEditable), 0x00000000);
            //setBackgroundColor(getResources().getColor(R.color.elementIsEditable));
            o.setDuration(450);
            o.start();*/
            setTextColor(getResources().getColor(R.color.elementIsEditable));

        }else{
            setTextColor(Color.BLACK);
        }

        updateText();
    }


    @Override
    public boolean isEditable() {
        return mEditable;
    }


    @Override
    public void setModificationCallback(LabelModificationListener c) {
        mLabelModificationListener = c;
    }


    @Override
    public void onClick(final View view) {
        AlertDialog.Builder d;
        final EditText t;

        if (mEditable){
            switch (mType) {
                case TYPE_NUM:
                case TYPE_TEXT:
                    t = new EditText(getContext());
                    t.setInputType(getInputTypeForEditText(mType));
                    t.setText(getText());
                    t.setSelection(0,getText().length());

                    d = new AlertDialog.Builder(getContext());
                    d.setTitle("Introduce el nuevo valor");
                    d.setView(t);

                    d.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    d.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateDb(t.getText().toString());
                        }
                    });
                    d.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            switch (keyCode){
                                case KeyEvent.KEYCODE_ENTER:
                                case KeyEvent.KEYCODE_TAB:
                                    updateDb(t.getText().toString());
                                    dialog.dismiss();
                                    break;
                            }
                            return false;
                        }
                    });
                    d.show();
                    break;

                case TYPE_DATE:
                    DatePickerDialog dp;
                    Calendar calendar;
                    SimpleDateFormat sdf;

                    sdf         = new SimpleDateFormat(DATE_FORMAT);
                    calendar    = Calendar.getInstance();

                    try {
                        calendar.setTime(sdf.parse(mText));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dp = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            updateDb(DatabaseHelper.parseToSQLite(year, month, day));
                        }
                    },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    dp.show();
                    break;
            }
            requestFocusFromTouch();
        }
    }


    private int getInputTypeForEditText(int mType) {
        int t = InputType.TYPE_CLASS_TEXT;

        switch (mType){
            case TYPE_NUM:
                t = InputType.TYPE_CLASS_NUMBER;
            case TYPE_DATE:
                t = InputType.TYPE_CLASS_DATETIME;
        }
        return t;
    }


    private void updateDb(String newValue){
        Log.d(LabelView.class.getSimpleName(), "nuevo valor: " + newValue);
        cv  = new ContentValues();

        cv.put(mColumn, newValue);
        getContext().getContentResolver().update(mUri, cv, null, null);

        setText(newValue);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //if(isFocused()){
            callOnClick();
        //}
        return true;
    }
}
