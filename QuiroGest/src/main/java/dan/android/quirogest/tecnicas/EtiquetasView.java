package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaTiposDeEtiquetas;

/**
 * Created by dan on 25/05/14.
 */
public class EtiquetasView extends LinearLayout {
    private ArrayList<Etiqueta> listaEtiquetas;
    private static HashMap<String, Etiqueta> mapIdEtiquetaDetails;
    private Context mContext;


    public EtiquetasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    private Etiqueta getEtiquetaDetails(String idEtiqueta){
        if (mapIdEtiquetaDetails == null){
            Cursor c;
            String id, color, descript;

            mapIdEtiquetaDetails = new HashMap<String, Etiqueta>();
            c = mContext.getContentResolver().query(QuiroGestProvider.CONTENT_URI_TIPO_ETIQUETAS, null, null, null, null);

            while(c.moveToNext()){
                id          = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA));
                color       = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_COLOR));
                descript    = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_DESCRIPCION));

                mapIdEtiquetaDetails.put(id, new Etiqueta(mContext, id, color, descript));
            }
            c.close();
        }
        return mapIdEtiquetaDetails.get(idEtiqueta);
    }


    public void loadEtiquetas(String etiquetas) {
        listaEtiquetas = new ArrayList<Etiqueta>();
        removeAllViews();

        if (etiquetas != null) {
            for (String s : etiquetas.split(",")) {
                listaEtiquetas.add(getEtiquetaDetails(s));
                addView(getEtiquetaDetails(s));
            }
        }
    }



    class Etiqueta extends TextView{
        String id, color, descript;

        public Etiqueta(Context context, String id, String color, String descript) {
            super(context);
            this.id         = id;
            this.color      = color;
            this.descript   = descript;

            setText(descript);
            setBackgroundColor(Color.parseColor(color));
            setTextColor(Color.BLACK);
            setMinWidth(40);
            setGravity(1);
        }
    }
}
