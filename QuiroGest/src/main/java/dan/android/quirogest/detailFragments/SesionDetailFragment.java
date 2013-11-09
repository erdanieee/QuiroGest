package dan.android.quirogest.detailFragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dan.android.quirogest.FragmentBase.DetailFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;


public class SesionDetailFragment extends DetailFragmentBase{
    private static final int    LAYOUT      = R.layout.fragment_sesion_detail;
    private final Uri           QUERY_URI   = QuiroGestProvider.CONTENT_URI_SESIONES;
    //private TextView ;
    //private ListView ;

    @Override
    public Uri      getUri()        { return QUERY_URI; }
    public String[] getProjection() { return null; }            //nos interesan casi todas las columnas


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static SesionDetailFragment newInstance(long motivoId) {
        SesionDetailFragment f = new SesionDetailFragment();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(ITEM_ID, motivoId);
        f.setArguments(args);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mRootView;

        mRootView       = inflater.inflate(LAYOUT, container, false);
      /*  mFecha          = (TextView) mRootView.findViewById(R.id.textViewFecha);
        mEstadoSalud    = (TextView) mRootView.findViewById(R.id.textViewEstadoSalud);
        mDatosMotivo    = (ListView) mRootView.findViewById(R.id.listViewDatosMotivos);*/

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null!=getCursor() && getCursor().moveToFirst()){
            String fecha, descripcion, comienzo, activFisica, diagnostico, observaciones;
            int estadoSalud;
            SimpleDateFormat sdfIn, sdfOut;
            Date date;

            fecha           = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_FECHA));
            descripcion     = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_DESCRIPCION));
            comienzo        = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_COMIENZO));
            activFisica     = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_ACTIV_FISICA));
            diagnostico     = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_DIAGNOSTICO));
            observaciones   = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_OBSERVACIONES));
            //contactoId      = getCursor().getInt(getCursor().getColumnIndex(TablaMotivos.COL_ID_CONTACTO));
            estadoSalud     = getCursor().getInt(getCursor().getColumnIndex(TablaMotivos.COL_ESTADO_SALUD));

            //ponemos la fecha  //TODO: hacer una clase de conversión format SQL-aplicación
            sdfIn   = new SimpleDateFormat(TablaMotivos.SQLITE_DATE_FORMAT);
            sdfOut  = new SimpleDateFormat("dd/MM/yyyy");
  /*          try {
                date = sdfIn.parse(fecha);
                mFecha.setText(sdfOut.format(date));

            } catch (ParseException e) {
                mFecha.setText("FECHA INVÁLIDA");
                e.printStackTrace();
            }

            mEstadoSalud.setText(String.valueOf(estadoSalud)    );*/

            //mDatosMotivo.divid
        }
    }
}
