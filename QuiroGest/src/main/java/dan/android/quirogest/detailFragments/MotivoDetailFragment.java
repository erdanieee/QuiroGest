package dan.android.quirogest.detailFragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import dan.android.quirogest.views.LabelView;


public class MotivoDetailFragment extends DetailFragmentBase{
    private static final int    LAYOUT      = R.layout.fragment_motivo_detail;
    private final Uri           QUERY_URI   = QuiroGestProvider.CONTENT_URI_MOTIVOS;
    private LabelView mFecha, mDescripcion, mComienzoDolor, mEstadoSalud, mActivFisica, mDiagnostico, mObservaciones;

    @Override
    public Uri      getUri()        { return QUERY_URI; }
    public String[] getProjection() { return null; }            //nos interesan casi todas las columnas


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //necesario para que aparezca el menú
        setHasOptionsMenu(true);
    }


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
        mFecha          = (LabelView) mRootView.findViewById(R.id.textViewFecha);
        mDescripcion    = (LabelView) mRootView.findViewById(R.id.textViewDescripcion);
        mComienzoDolor  = (LabelView) mRootView.findViewById(R.id.textViewComienzoDolor);
        mEstadoSalud    = (LabelView) mRootView.findViewById(R.id.textViewEstadoSalud);
        mActivFisica    = (LabelView) mRootView.findViewById(R.id.textViewActividadFisica);
        mDiagnostico    = (LabelView) mRootView.findViewById(R.id.textViewDiagnostico);
        mObservaciones  = (LabelView) mRootView.findViewById(R.id.textViewObservaciones);

        mFecha.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_MOTIVOS, String.valueOf(getItemId())),
                TablaMotivos.COL_FECHA,
                LabelView.TYPE_DATE);
        mDescripcion.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_MOTIVOS, String.valueOf(getItemId())),
                TablaMotivos.COL_DESCRIPCION,
                LabelView.TYPE_TEXT);
        mComienzoDolor.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_MOTIVOS, String.valueOf(getItemId())),
                TablaMotivos.COL_COMIENZO,
                LabelView.TYPE_DATE);
        mEstadoSalud.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_MOTIVOS, String.valueOf(getItemId())),
                TablaMotivos.COL_ESTADO_SALUD,
                LabelView.TYPE_NUM);
        mActivFisica.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_MOTIVOS, String.valueOf(getItemId())),
                TablaMotivos.COL_ACTIV_FISICA,
                LabelView.TYPE_TEXT);
        mDiagnostico.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_MOTIVOS, String.valueOf(getItemId())),
                TablaMotivos.COL_DIAGNOSTICO,
                LabelView.TYPE_TEXT);
        mObservaciones.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_MOTIVOS, String.valueOf(getItemId())),
                TablaMotivos.COL_OBSERVACIONES,
                LabelView.TYPE_TEXT);

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

            mFecha.setText(fecha);
            mDescripcion.setText(descripcion);
            mComienzoDolor.setText(comienzo,getElapsedTime(comienzo, fecha));           //TODO: añadir callback al comienzo del dolor para actualizar altText
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


    //********************************************************************************************//
    // M A I N      M E N U
    //********************************************************************************************//
    @Override
    //Primero se llama a la activity, y llega aquí solo si la activity no consume el evento (return false)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenuEditItem:
                mFecha.setEditable(!mFecha.isEditable());
                mDescripcion.setEditable(!mDescripcion.isEditable());
                mComienzoDolor.setEditable(!mComienzoDolor.isEditable());
                mEstadoSalud.setEditable(!mEstadoSalud.isEditable());
                mActivFisica.setEditable(!mActivFisica.isEditable());
                mDiagnostico.setEditable(!mDiagnostico.isEditable());
                mObservaciones.setEditable(!mObservaciones.isEditable());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
