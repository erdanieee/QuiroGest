package dan.android.quirogest;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;


public class ClienteDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = "ClienteDetailFragment";
    private static final String CONTACTO_ID = "contacto_id";
    private static final int LOADER_CONTACTO_ID = 1;

    private long mContactoId;
    private View rootView;


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static ClienteDetailFragment newInstance(long contactoId) {
        Log.i(TAG, "creando nueva instancia ID: " + contactoId);
        ClienteDetailFragment f = new ClienteDetailFragment();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(CONTACTO_ID, contactoId);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactoId = getArguments().getLong(CONTACTO_ID, -1);

        if (mContactoId >= 0) {
            getLoaderManager().initLoader(LOADER_CONTACTO_ID, null, this);
        }
    }


    public long getContactoId(){
        return mContactoId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cliente_detail, container, false);
        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), ContentUris.withAppendedId(QuiroGestProvider.CONTENT_URI_CONTACTOS, mContactoId), null, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.i(TAG, "Loader 'loaded'");

        String nombre, apellido1, apellido2, movil, fijo, email, direccion, cp, localidad, provincia, fechaNac, profesion, enfermedades, alergias, observaciones;
        switch (cursorLoader.getId()) {
            case LOADER_CONTACTO_ID:
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


                    ((TextView) rootView.findViewById(R.id.textViewNombreCompleto)).setText(nombre + " " + apellido1 + " " + apellido2);
                    ((TextView) rootView.findViewById(R.id.EditTextMovil)).setText(movil);
                    ((TextView) rootView.findViewById(R.id.EditTextFijo)).setText(fijo);
                    ((TextView) rootView.findViewById(R.id.EditTextEmail)).setText(email);
                    ((TextView) rootView.findViewById(R.id.EditTextDireccion)).setText(direccion);
                    ((TextView) rootView.findViewById(R.id.EditTextCP)).setText(cp);
                    ((TextView) rootView.findViewById(R.id.EditTextLocalidad)).setText(localidad);
                    ((TextView) rootView.findViewById(R.id.EditTextProvincia)).setText(provincia);
                    ((TextView) rootView.findViewById(R.id.EditTextFechaNacimiento)).setText(fechaNac);
                    ((TextView) rootView.findViewById(R.id.EditTextProfesion)).setText(profesion);
                    ((TextView) rootView.findViewById(R.id.EditTextEnfermedades)).setText(enfermedades);
                    ((TextView) rootView.findViewById(R.id.EditTextAlergias)).setText(alergias);
                    ((TextView) rootView.findViewById(R.id.EditTextObservaciones)).setText(observaciones);
                }
                break;
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        //no es necesario hacer nada
    }
}
