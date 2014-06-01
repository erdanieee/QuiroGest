package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
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



    class Etiqueta extends LinearLayout{
        private String id;
        private TextView texto;
        private Button deleteButton;
        boolean editable = false;

        public Etiqueta(Context context, String id, String color, String descript) {
            super(context);

            setOrientation(HORIZONTAL);

            this.id         = id;
            texto           = new TextView(context);
            deleteButton    = new Button(context);

            texto.setText(descript);
            texto.setTextColor(Color.BLACK);
            texto.setBackgroundColor(Color.parseColor(color));
            texto.setMinWidth(40);
            //texto.setGravity(1);
            deleteButton.setBackgroundResource(android.R.drawable.ic_menu_delete);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: callback a Tecnica.java para gestionar el borrado, o hacer que la clase etiqueta sea consciente de la técnica a la que pertenece y hacer ella misma el borrado.
                }
            });


        }
    }
}
