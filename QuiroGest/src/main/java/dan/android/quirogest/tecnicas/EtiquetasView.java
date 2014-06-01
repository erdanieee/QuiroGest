package dan.android.quirogest.tecnicas;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaEtiquetas;
import dan.android.quirogest.database.TablaTiposDeEtiquetas;

/**
 * Created by dan on 25/05/14.
 */
public class EtiquetasView extends LinearLayout {
    String SELECTION = TablaEtiquetas.COL_ID_TECNICA + "=?";
    private ArrayList<Etiqueta> listaEtiquetas;
    private Context mContext;
    private long idTecnica;
    private EtiquetaCallback mCallback;


    public EtiquetasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mCallback = new EtiquetaCallback() {
            @Override
            public void deleteEtiqueta(int etiquetaId) {
                mContext.getContentResolver().delete(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_ETIQUETAS, String.valueOf(etiquetaId)),null,null);

                for (int i=0; i<getChildCount(); i++){
                    if (((Etiqueta)getChildAt(i)).getIdEtiqueta() == etiquetaId){
                        removeViewAt(i);
                        break;
                    }
                }
            }
        };
    }


    public void loadEtiquetas(long idTecnica) {
        Cursor c;
        String color, descript;
        int id;
        Etiqueta e;
        String[] proyection = {
            TablaEtiquetas._ID,
            TablaTiposDeEtiquetas.COL_DESCRIPCION,
            TablaTiposDeEtiquetas.COL_COLOR
        };
        String [] selectionArgs = { String.valueOf(idTecnica) };

        removeAllViews();

        listaEtiquetas          = new ArrayList<Etiqueta>();
        this.idTecnica          = idTecnica;
        c                       = mContext.getContentResolver().query(QuiroGestProvider.CONTENT_URI_ETIQUETAS, proyection, SELECTION, selectionArgs, null, null);

        while(c.moveToNext()){
            id          = c.getInt(c.getColumnIndex(TablaEtiquetas._ID));
            color       = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_COLOR));
            descript    = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_DESCRIPCION));

            e = new Etiqueta(mContext, id, color, descript);
            e.setCallback(mCallback);

            addView(e);
        }
        c.close();
    }


    public void setWritable(boolean w){
        for (int i=0; i<getChildCount(); i++){
            ((Etiqueta)getChildAt(i)).setEditable(w);
        }
    }






    protected interface EtiquetaCallback {
        void deleteEtiqueta(int id);
    }








    class Etiqueta extends LinearLayout{
        public int id;
        private TextView texto;
        private Button deleteButton;
        boolean editable = false;
        private EtiquetaCallback mCallback;


        public Etiqueta(Context context, final int id, String color, String descript) {
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
                    //TODO: deleteEtiqueta a Tecnica.java para gestionar el borrado, o hacer que la clase etiqueta sea consciente de la técnica a la que pertenece y hacer ella misma el borrado.
                    if (mCallback != null){
                        mCallback.deleteEtiqueta(getIdEtiqueta());
                    }
                }
            });

            addView(deleteButton);
            addView(texto);

            setEditable(editable);
        }

        public int getIdEtiqueta(){ return id;}

        protected void setEditable(boolean editable){
            if (editable){
                deleteButton.setVisibility(VISIBLE);

            }else{
                deleteButton.setVisibility(GONE);
            }
        }

        public void setCallback(EtiquetaCallback ecb){
            mCallback = ecb;
        }
    }
}
