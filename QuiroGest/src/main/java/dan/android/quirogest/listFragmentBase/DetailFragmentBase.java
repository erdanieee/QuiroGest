package dan.android.quirogest.listFragmentBase;

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

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;


public class DetailFragmentBase extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ITEM_ID = "item_id";
    private static final int LOADER_ID = 218912397;

    private long mItemId;
    private View rootView;


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static DetailFragmentBase newInstance(long itemId) {
        DetailFragmentBase f = new DetailFragmentBase();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(ITEM_ID, itemId);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItemId = getArguments().getLong(ITEM_ID, -1);

        if (mItemId >= 0) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }


    public long getItemId(){
        return mItemId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cliente_detail, container, false);
        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), ContentUris.withAppendedId(QuiroGestProvider.CONTENT_URI_CONTACTOS, mItemId), null, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.i(TAG, "Loader 'loaded'");

        String nombre, apellido1, apellido2, movil, fijo, email, direccion, cp, localidad, provincia, fechaNac, profesion, enfermedades, alergias, observaciones;
        switch (cursorLoader.getId()) {
            case LOADER_ID:
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
