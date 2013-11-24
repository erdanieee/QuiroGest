package dan.android.quirogest.tecnicas;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;

/**
 * Created by dan on 17/11/13.
 */
public class TecnicasListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG          = "TecnicasListFragment";
    private static final String SESION_ID    = "sesion_id";
    private static final int    LOADER_ID    = 79838383;
    private static final Uri    URI          = QuiroGestProvider.CONTENT_URI_TECNICAS;
    private static final String SELECTION    = TablaTecnicas.COL_ID_SESION + "=?";
    private static final String ORDER        = "DATE(" + TablaTecnicas.COL_FECHA + ") DESC";
    private static final String[] PROYECTION = {
            TablaTecnicas._ID,
            TablaTecnicas.COL_ID_TECNICA,
            TablaTecnicas.COL_OBSERVACIONES,
            TablaTecnicas.COL_VALOR,
            TablaTiposDeTecnicas.COL_ID_PARENT,
            TablaTiposDeTecnicas.COL_DESCRIPCION,
            TablaTiposDeTecnicas.COL_MIN,
            TablaTiposDeTecnicas.COL_MAX,
            TablaTiposDeTecnicas.COL_VIEWTYPE};


    private TecnicasAdapter mAdapter    = null;
    private long mSesionId;


    public interface itemTecnicable{
        public void setValue(int idPadre, int min, int max, int value);
    }


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static TecnicasListFragment newInstance(long sesionId) {
        TecnicasListFragment f;
        Bundle args;

        f    = new TecnicasListFragment();
        args = new Bundle();
        args.putLong(SESION_ID, sesionId);
        f.setArguments(args);

        return f;
    }



    //********************************************************************************************//
    // F R A G M E N T          L I F E C Y C L I N G
    //********************************************************************************************//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inicializamos el adapter con un cursor nulo hasta que se inicialice el Loader
        mAdapter = new TecnicasAdapter(getActivity(), null, false);
        setListAdapter(mAdapter);

        mSesionId = getArguments().getLong(SESION_ID,-1);

        //empezamos la carga de datos en paralelo
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
    }



    //********************************************************************************************//
    // L O A D E R
    //********************************************************************************************//
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] selectionArgs = {String.valueOf(mSesionId)};
        return new CursorLoader(getActivity(), URI, PROYECTION, SELECTION, selectionArgs, ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        switch (cursorLoader.getId()) {
            case LOADER_ID:
                mAdapter.swapCursor(cursor);
                break;
            default:
                throw new IllegalStateException("Nunca debería pasar esto");
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}