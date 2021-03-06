package dan.android.quirogest.detailFragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import dan.android.quirogest.FragmentBase.DetailFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaSesiones;
import dan.android.quirogest.views.LabelView;


public class SesionDetailFragment extends DetailFragmentBase{
    private static final int    LAYOUT      = R.layout.fragment_sesion_detail;
    private final Uri           QUERY_URI   = QuiroGestProvider.CONTENT_URI_SESIONES;
    private LabelView mDiagnostico, mFecha, mIngresos, mCuantifDolor, mObservaciones, mNumeroSesion;
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

        //necesario para que aparezca el menú
        setHasOptionsMenu(true);

        mRootView       = inflater.inflate(LAYOUT, container, false);
        mFecha          = (LabelView) mRootView.findViewById(R.id.textViewFecha);
        mDiagnostico    = (LabelView) mRootView.findViewById(R.id.textViewDiagnostico);
        mIngresos       = (LabelView) mRootView.findViewById(R.id.textViewPrecio);
        mCuantifDolor   = (LabelView) mRootView.findViewById(R.id.textViewCuantificacionDolor);
        mObservaciones  = (LabelView) mRootView.findViewById(R.id.textViewObservaciones);
        mNumeroSesion   = (LabelView) mRootView.findViewById(R.id.textViewNumeroSesion);

        mDiagnostico.setModificationParams(
                Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_SESIONES, String.valueOf(getItemId())),
                TablaSesiones.COL_DIAGNOSTICO,
                LabelView.TYPE_TEXT);
        mFecha.setModificationParams(
                Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_SESIONES, String.valueOf(getItemId())),
                TablaSesiones.COL_FECHA,
                LabelView.TYPE_DATE);
        mIngresos.setModificationParams(
                Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_SESIONES, String.valueOf(getItemId())),
                TablaSesiones.COL_INGRESOS,
                LabelView.TYPE_NUM);
        mCuantifDolor.setModificationParams(
                Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_SESIONES, String.valueOf(getItemId())),
                TablaSesiones.COL_DOLOR,
                LabelView.TYPE_NUM);
        mObservaciones.setModificationParams(
                Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_SESIONES, String.valueOf(getItemId())),
                TablaSesiones.COL_OBSERVACIONES,
                LabelView.TYPE_TEXT);
        mNumeroSesion.setModificationParams(
                Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_SESIONES, String.valueOf(getItemId())),
                TablaSesiones.COL_NUM_SESION,
                LabelView.TYPE_NUM);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null!=getCursor() && getCursor().moveToFirst()){
            String diagnostico, fecha, postrat, observ;
            Integer cuantifDolor, ingresos, numSesion;

            diagnostico = getCursor().getString(getCursor().getColumnIndex(TablaSesiones.COL_DIAGNOSTICO));
            fecha       = getCursor().getString(getCursor().getColumnIndex(TablaSesiones.COL_FECHA));
            observ      = getCursor().getString(getCursor().getColumnIndex(TablaSesiones.COL_OBSERVACIONES));
            postrat     = getCursor().getString(getCursor().getColumnIndex(TablaSesiones.COL_POSTRATAMIENTO));
            cuantifDolor= getCursor().getInt(getCursor().getColumnIndex(TablaSesiones.COL_DOLOR));
            ingresos    = getCursor().getInt(getCursor().getColumnIndex(TablaSesiones.COL_INGRESOS));
            numSesion   = getCursor().getInt(getCursor().getColumnIndex(TablaSesiones.COL_NUM_SESION));

            mFecha.setText(fecha);
            mObservaciones.setText(observ);
            mCuantifDolor.setText(cuantifDolor.toString());
            mDiagnostico.setText(diagnostico);
            mIngresos.setText(ingresos.toString());
            mNumeroSesion.setText(numSesion.toString());
        }
    }




    //********************************************************************************************//
    // M A I N      M E N U
    //********************************************************************************************//
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_tecnicas, menu);
    }


    @Override
    //Primero se llama a la activity, y llega aquí solo si la activity no consume el evento (return false)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenuEditItem:
                mFecha.setWritable(!mFecha.isEditable());
                mCuantifDolor.setWritable(!mCuantifDolor.isEditable());
                mDiagnostico.setWritable(!mDiagnostico.isEditable());
                mIngresos.setWritable(!mIngresos.isEditable());
                mObservaciones.setWritable(!mObservaciones.isEditable());
                mNumeroSesion.setWritable(!mNumeroSesion.isEditable());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
