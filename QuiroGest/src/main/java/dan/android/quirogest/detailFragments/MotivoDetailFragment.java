package dan.android.quirogest.detailFragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import dan.android.quirogest.FragmentBase.DetailFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaContactos;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.database.TablaSesiones;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;
import dan.android.quirogest.listFragments.SesionesListFragment;
import dan.android.quirogest.views.LabelModificationListener;
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
            mActivFisica.setText(activFisica);
            mDiagnostico.setText(diagnostico);
            mObservaciones.setText(observaciones);
            mEstadoSalud.setText(String.valueOf(estadoSalud));

            mComienzoDolor.setModificationCallback(new LabelModificationListener() {        //FIXME: tiene que asignar el callback antes de definir el texto para que se actualice
                @Override
                public void onLabelModification(String t) {
                    mComienzoDolor.setAltText(getElapsedTime(mComienzoDolor.getText().toString(), mFecha.getText().toString()));
                }
            });
            mComienzoDolor.setText(comienzo);

        }
    }


    private String getElapsedTime(String fechaComienzo, String fechaMotivoConsulta){
        Date dateComienzo, dateMotivo;
        long diff=0;
        long dias, semanas, meses, anios, resto;
        String tiempoTranscurrido="";
        boolean temp=false;

        try {
            dateComienzo    = new SimpleDateFormat(LabelView.DATE_FORMAT).parse(fechaComienzo);
            dateMotivo      = new SimpleDateFormat(LabelView.DATE_FORMAT).parse(fechaMotivoConsulta);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detalles_motivos, menu);
    }


    @Override
    //Primero se llama a la activity, y llega aquí solo si la activity no consume el evento (return false)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenuEditItem:
                mFecha.setWritable(!mFecha.isEditable());
                mDescripcion.setWritable(!mDescripcion.isEditable());
                mComienzoDolor.setWritable(!mComienzoDolor.isEditable());
                mEstadoSalud.setWritable(!mEstadoSalud.isEditable());
                mActivFisica.setWritable(!mActivFisica.isEditable());
                mDiagnostico.setWritable(!mDiagnostico.isEditable());
                mObservaciones.setWritable(!mObservaciones.isEditable());
                return false;

            case R.id.mainMenuCopyItemFrom:
                ArrayList<Uri> uris;
                ArrayList<String> colIds, selections;
                ArrayList<String[]> tittles;

                uris            = new ArrayList<Uri>();
                colIds          = new ArrayList<String>();
                selections      = new ArrayList<String>();
                tittles         = new ArrayList<String[]>();

                uris.add(QuiroGestProvider.CONTENT_URI_CONTACTOS);
                uris.add(QuiroGestProvider.CONTENT_URI_MOTIVOS);
                uris.add(QuiroGestProvider.CONTENT_URI_SESIONES);

                colIds.add(TablaContactos._ID);
                colIds.add(TablaMotivos._ID);
                colIds.add(TablaSesiones._ID);

                selections.add(null);
                selections.add(TablaMotivos.COL_ID_CONTACTO + "=?");
                selections.add(TablaSesiones.COL_ID_MOTIVO + "=?");

                tittles.add(new String[] {TablaContactos.COL_NOMBRE, TablaContactos.COL_APELLIDO1});
                tittles.add(new String[] {TablaMotivos.COL_DIAGNOSTICO, TablaMotivos.COL_FECHA});
                tittles.add(new String[] {TablaSesiones.COL_NUM_SESION, TablaSesiones.COL_FECHA});

                getSelectedId(uris,colIds,tittles,selections,null);

                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void getSelectedId(final ArrayList<Uri> uris, final ArrayList<String> colIds, final ArrayList<String[]> tittles, final ArrayList<String> selections, Integer selectedId){
        final int[] selectedItemId = new int[1];
        final ArrayList<Integer> idList;
        AlertDialog.Builder ad;
        ArrayAdapter<String> a;
        Cursor c;
        String[] proyection;
        StringBuffer sb;
        Uri uri;
        String colId, selection;
        String[] tittle, selectionArg;


        if (uris.isEmpty() && selectedId!=null){
            SesionesListFragment.copySesion(getActivity(), selectedId, getItemId());

        } else {
            uri = uris.get(0);
            colId = colIds.get(0);
            tittle = tittles.get(0);
            selection = selections.get(0);

            uris.remove(0);
            colIds.remove(0);
            tittles.remove(0);
            selections.remove(0);

            //if (selectedId!=null){
            //    uri = Uri.withAppendedPath(uri, String.valueOf(selectedId));
            //}

            idList = new ArrayList<Integer>();
            ad = new AlertDialog.Builder(getActivity());
            a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
            proyection = new String[tittle.length + 1];

            proyection[0] = colId;
            for (int i = 0; i < tittle.length; i++) {
                proyection[i + 1] = tittle[i];
            }

            c = getActivity().getContentResolver().query(uri, proyection, selection, selectedId != null ? new String[]{selectedId.toString()} : null, null);

            while (c.moveToNext()) {
                sb = new StringBuffer();

                for (String s : tittle) {
                    sb.append(c.getString(c.getColumnIndex(s)));
                    sb.append(" ");
                }

                a.add(sb.subSequence(0, sb.length() - 1).toString());
                idList.add(c.getInt(c.getColumnIndex(colId)));
            }
            c.close();

            ad.setAdapter(a, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    getSelectedId(uris, colIds, tittles, selections, idList.get(i));
                }
            }).create();
            ad.show();
        }
    }
}
