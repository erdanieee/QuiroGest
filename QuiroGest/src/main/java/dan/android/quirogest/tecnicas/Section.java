package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dan.android.quirogest.R;

/**
 * Created by dan on 19/07/14.
 */
public class Section extends RelativeLayout{
    private TextView mTittle;
    private String mId;


    public Section(Context context) {
        super(context);

        ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tecnica_seccion, this);

         mTittle  = (TextView) findViewById(R.id.Section);
    }


    public void setText(String text){
        mTittle.setText(text);
    }


    public void setId(long id) {
        mId = String.valueOf(id);
    }
}
