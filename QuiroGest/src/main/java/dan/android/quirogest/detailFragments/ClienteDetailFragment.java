package dan.android.quirogest.detailFragments;

import android.app.Activity;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
import dan.android.quirogest.FragmentBase.DetailFragmentBase;


public class ClienteDetailFragment extends DetailFragmentBase{
    private static final int    LAYOUT      = R.layout.fragment_cliente_detail;
    private final Uri           QUERY_URI   = QuiroGestProvider.CONTENT_URI_CONTACTOS;

    private TextView mNombreCompleto, mMovil, mFijo, mEmail, mDireccion, mCP, mLocalidad, mProvincia, mFechaNac, mProfesion, mEnfermedades, mAlergias, mObservaciones;


    @Override
    public Uri      getUri()        { return QUERY_URI; }
    public String[] getProjection() { return null; }            // nos interesan todas las columnas


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static ClienteDetailFragment newInstance(long contactoId) {
        ClienteDetailFragment f = new ClienteDetailFragment();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(ITEM_ID, contactoId);
        f.setArguments(args);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mRootView;

        mRootView       = inflater.inflate(LAYOUT, container, false);
        mNombreCompleto = (TextView) mRootView.findViewById(R.id.textViewNombreCompleto);
        mMovil          = (TextView) mRootView.findViewById(R.id.EditTextMovil);
        mFijo           = (TextView) mRootView.findViewById(R.id.EditTextFijo);
        mEmail          = (TextView) mRootView.findViewById(R.id.EditTextEmail);
        mDireccion      = (TextView) mRootView.findViewById(R.id.EditTextDireccion);
        mCP             = (TextView) mRootView.findViewById(R.id.EditTextCP);
        mLocalidad      = (TextView) mRootView.findViewById(R.id.EditTextLocalidad);
        mProvincia      = (TextView) mRootView.findViewById(R.id.EditTextProvincia);
        mFechaNac       = (TextView) mRootView.findViewById(R.id.EditTextFechaNacimiento);
        mProfesion      = (TextView) mRootView.findViewById(R.id.EditTextProfesion);
        mEnfermedades   = (TextView) mRootView.findViewById(R.id.EditTextEnfermedades);
        mAlergias       = (TextView) mRootView.findViewById(R.id.EditTextAlergias);
        mObservaciones  = (TextView) mRootView.findViewById(R.id.EditTextObservaciones);

        return mRootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null!=getCursor() && getCursor().moveToFirst()){
            String nombre, apellido1, apellido2, movil, fijo, email, direccion, cp, localidad, provincia, fechaNac, profesion, enfermedades, alergias, observaciones;

            nombre          = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_NOMBRE));
            apellido1       = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_APELLIDO1));
            apellido2       = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_APELLIDO2));
            movil           = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_MOVIL));
            fijo            = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_FIJO));
            email           = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_EMAIL));
            direccion       = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_DIRECCION));
            cp              = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_CP));
            localidad       = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_LOCALIDAD));
            provincia       = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_PROVINCIA));
            fechaNac        = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_FECHA_NAC));
            profesion       = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_PROFESION));
            enfermedades    = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_ENFERMEDADES));
            alergias        = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_ALERGIAS));
            observaciones   = getCursor().getString(getCursor().getColumnIndex(TablaClientes.COL_OBSERVACIONES));

            mNombreCompleto.setText(nombre + " " + apellido1 + " " + apellido2);
            mMovil.setText(movil);
            mFijo.setText(fijo);
            mEmail.setText(email);
            mDireccion.setText(direccion);
            mCP.setText(cp);
            mLocalidad.setText(localidad);
            mProvincia.setText(provincia);
            mFechaNac.setText(fechaNac);
            mProfesion.setText(profesion);
            mEnfermedades.setText(enfermedades);
            mAlergias.setText(alergias);
            mObservaciones.setText(observaciones);
        }
    }
}
