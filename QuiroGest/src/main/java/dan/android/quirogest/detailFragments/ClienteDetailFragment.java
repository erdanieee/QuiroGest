package dan.android.quirogest.detailFragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
import dan.android.quirogest.listFragmentBase.DetailFragmentBase;


public class ClienteDetailFragment extends DetailFragmentBase{
    private static final String CONTACTO_ID = "contacto_id";
    private static final int    LAYOUT      = R.layout.fragment_cliente_detail;

    private Long mContactoId;
    private TextView mNombreCompleto, mMovil, mFijo, mEmail, mDireccion, mCP, mLocalidad, mProvincia, mFechaNac, mProfesion, mEnfermedades, mAlergias, mObservaciones;


    @Override
    public int  getLayout() { return LAYOUT; }
    public Uri  getUri()    { return QuiroGestProvider.CONTENT_URI_CONTACTOS; }
    public Long getItemId() { return mContactoId; }



    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static ClienteDetailFragment newInstance(long contactoId) {
        ClienteDetailFragment f = new ClienteDetailFragment();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(CONTACTO_ID, contactoId);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!getArguments().containsKey(CONTACTO_ID)){
            throw new IllegalStateException("Se ha intentado crear el fragment sin proporcionar un contactoID.");
        }
        mContactoId = getArguments().getLong(CONTACTO_ID, -1);
    }

    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNombreCompleto = (TextView) getRootView().findViewById(R.id.textViewNombreCompleto);
        mMovil          = (TextView) getRootView().findViewById(R.id.EditTextMovil);
        mFijo           = (TextView) getRootView().findViewById(R.id.EditTextFijo);
        mEmail          = (TextView) getRootView().findViewById(R.id.EditTextEmail);
        mDireccion      = (TextView) getRootView().findViewById(R.id.EditTextDireccion);
        mCP             = (TextView) getRootView().findViewById(R.id.EditTextCP);
        mLocalidad      = (TextView) getRootView().findViewById(R.id.EditTextLocalidad);
        mProvincia      = (TextView) getRootView().findViewById(R.id.EditTextProvincia);
        mFechaNac       = (TextView) getRootView().findViewById(R.id.EditTextFechaNacimiento);
        mProfesion      = (TextView) getRootView().findViewById(R.id.EditTextProfesion);
        mEnfermedades   = (TextView) getRootView().findViewById(R.id.EditTextEnfermedades);
        mAlergias       = (TextView) getRootView().findViewById(R.id.EditTextAlergias);
        mObservaciones  = (TextView) getRootView().findViewById(R.id.EditTextObservaciones);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        String nombre, apellido1, apellido2, movil, fijo, email, direccion, cp, localidad, provincia, fechaNac, profesion, enfermedades, alergias, observaciones;

        if (cursor != null && cursor.moveToFirst()){
            nombre          = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_NOMBRE));
            apellido1       = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_APELLIDO1));
            apellido2       = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_APELLIDO2));
            movil           = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_MOVIL));
            fijo            = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_FIJO));
            email           = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_EMAIL));
            direccion       = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_DIRECCION));
            cp              = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_CP));
            localidad       = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_LOCALIDAD));
            provincia       = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_PROVINCIA));
            fechaNac        = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_FECHA_NAC));
            profesion       = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_PROFESION));
            enfermedades    = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_ENFERMEDADES));
            alergias        = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_ALERGIAS));
            observaciones   = cursor.getString(cursor.getColumnIndex(TablaClientes.COL_OBSERVACIONES));


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
