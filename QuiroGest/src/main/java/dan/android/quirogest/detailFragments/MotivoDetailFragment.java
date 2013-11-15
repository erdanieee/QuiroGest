package dan.android.quirogest.detailFragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dan.android.quirogest.FragmentBase.DetailFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;


public class MotivoDetailFragment extends DetailFragmentBase{
    private static final int    LAYOUT      = R.layout.fragment_motivo_detail;
    private final Uri           QUERY_URI   = QuiroGestProvider.CONTENT_URI_MOTIVOS;
    private TextView mFecha, mDescripcion, mComienzoDolor, mEstadoSalud, mActivFisica, mDiagnostico, mObservaciones;

    @Override
    public Uri      getUri()        { return QUERY_URI; }
    public String[] getProjection() { return null; }            //nos interesan casi todas las columnas


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static MotivoDetailFragment newInstance(long motivoId) {
        MotivoDetailFragment f = new MotivoDetailFragment();

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
        mFecha          = (TextView) mRootView.findViewById(R.id.textViewFecha);
        mDescripcion    = (TextView) mRootView.findViewById(R.id.textViewDescripcion);
        mComienzoDolor  = (TextView) mRootView.findViewById(R.id.textViewComienzoDolor);
        mEstadoSalud    = (TextView) mRootView.findViewById(R.id.textViewEstadoSalud);
        mActivFisica    = (TextView) mRootView.findViewById(R.id.textViewActividadFisica);
        mDiagnostico    = (TextView) mRootView.findViewById(R.id.textViewDiagnostico);
        mObservaciones  = (TextView) mRootView.findViewById(R.id.textViewObservaciones);

        return mRootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null!=getCursor() && getCursor().moveToFirst()){
            String fecha, descripcion, comienzo, activFisica, diagnostico, observaciones;
            int estadoSalud;

            fecha           = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_FECHA));
            descripcion     = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_DESCRIPCION));
            comienzo        = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_COMIENZO));
            activFisica     = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_ACTIV_FISICA));
            diagnostico     = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_DIAGNOSTICO));
            observaciones   = getCursor().getString(getCursor().getColumnIndex(TablaMotivos.COL_OBSERVACIONES));
            estadoSalud     = getCursor().getInt(getCursor().getColumnIndex(TablaMotivos.COL_ESTADO_SALUD));

            mFecha.setText(DatabaseHelper.parseSQLiteDate(fecha, new SimpleDateFormat("dd/MM/yyyy")));
            mDescripcion.setText(descripcion);
            mComienzoDolor.setText(getElapsedTime(comienzo, fecha));
            mActivFisica.setText(activFisica);
            mDiagnostico.setText(diagnostico);
            mObservaciones.setText(observaciones);
            mEstadoSalud.setText(String.valueOf(estadoSalud));
        }
    }


    private String getElapsedTime(String fechaComienzo, String fechaMotivoConsulta){
        Date dateComienzo, dateMotivo;
        long diff=0;
        long dias, semanas, meses, anios, resto;
        String tiempoTranscurrido="";
        boolean temp=false;

        try {
            dateComienzo    = new SimpleDateFormat(DatabaseHelper.SQLITE_DATE_FORMAT).parse(fechaComienzo);
            dateMotivo      = new SimpleDateFormat(DatabaseHelper.SQLITE_DATE_FORMAT).parse(fechaMotivoConsulta);
            diff            = dateMotivo.getTime()-dateComienzo.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dias = TimeUnit.MILLISECONDS.toDays(diff);

        anios = (long)Math.ceil(dias/365);
        resto = dias%365;

        meses = (long)Math.ceil(resto/30);
        resto = resto%30;

        semanas = (long)Math.ceil(resto/7);
        resto = resto%7;

        dias = resto;

        if (anios > 0){
            tiempoTranscurrido = anios + " año" + (anios>1?"s":"");
            temp=true;
        }

        if (meses>0){
            tiempoTranscurrido += (temp?" y ":"") + meses + " mes" + (meses>1?"es":"");
            if (temp){
                return tiempoTranscurrido;
            } else {
                temp = true;
            }
        }

        if (semanas>0){
            tiempoTranscurrido += (temp?" y ":"") + semanas + " semana" + (semanas>1?"s":"");
            if (temp){
                return tiempoTranscurrido;
            } else {
                temp = true;
            }
        }

        if (dias>0){
            tiempoTranscurrido += (temp?" y ":"") + dias + " día" + (dias>1?"s":"");
        }

        return tiempoTranscurrido;
    }
}
