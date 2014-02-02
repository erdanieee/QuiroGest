package dan.android.quirogest.detailFragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dan.android.quirogest.FragmentBase.DetailFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.database.TablaSesiones;


public class SesionDetailFragment extends DetailFragmentBase{
    private static final int    LAYOUT      = R.layout.fragment_sesion_detail;
    private final Uri           QUERY_URI   = QuiroGestProvider.CONTENT_URI_SESIONES;
    private TextView mDiagnostico, mFecha, mIngresos, mCuantifDolor;
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
        mDiagnostico    = (TextView) mRootView.findViewById(R.id.textViewDiagnostico);
        mFecha          = (TextView) mRootView.findViewById(R.id.textViewFecha);
        mIngresos       = (TextView) mRootView.findViewById(R.id.textViewPrecio);
        mCuantifDolor   = (TextView) mRootView.findViewById(R.id.textViewCuantificacionDolor);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null!=getCursor() && getCursor().moveToFirst()){
            String diagnostico, fecha, postrat;
            Integer cuantifDolor, ingresos, numSesion;

            diagnostico = getCursor().getString(getCursor().getColumnIndex(TablaSesiones.COL_DIAGNOSTICO));
            fecha       = getCursor().getString(getCursor().getColumnIndex(TablaSesiones.COL_FECHA));
            postrat     = getCursor().getString(getCursor().getColumnIndex(TablaSesiones.COL_POSTRATAMIENTO));
            cuantifDolor= getCursor().getInt(getCursor().getColumnIndex(TablaSesiones.COL_DOLOR));
            ingresos    = getCursor().getInt(getCursor().getColumnIndex(TablaSesiones.COL_INGRESOS));
            numSesion   = getCursor().getInt(getCursor().getColumnIndex(TablaSesiones.COL_NUM_SESION));


            mFecha.setText(DatabaseHelper.parseSQLiteDate(fecha, new SimpleDateFormat("dd/MM/yyyy")));
            mCuantifDolor.setText(cuantifDolor.toString());
            mDiagnostico.setText(diagnostico);
            mIngresos.setText(ingresos.toString());
        }
    }
}
