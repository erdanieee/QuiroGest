package dan.android.quirogest.listFragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaEtiquetas;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;
import dan.android.quirogest.tecnicas.TecnicasAdapter;
import android.app.ActionBar;
import android.widget.Toast;

/**
 * Created by dan on 17/11/13.
 */
public class TecnicasListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>,  AbsListView.MultiChoiceModeListener{
    public  static final String PROY_COMB    = "ProyeccionCombinacionDeEtiquetas";
    private static final String TAG          = "TecnicasListFragment";
    private static final String SESION_ID    = "sesion_id";
    private static final int    LOADER_ID    = 79838383;
    private static final Uri    URI          = QuiroGestProvider.CONTENT_URI_TECNICAS;
    private static final String SELECTION    = TablaTecnicas.COL_ID_SESION + "=?";
    private static final String ORDER        = "DATE(" + TablaTecnicas.COL_FECHA + ") DESC";
    private static final String[] PROYECTION = {
            TablaTecnicas._ID,
            TablaTecnicas.COL_ID_TIPO_TECNICA,
            TablaTecnicas.COL_OBSERVACIONES,
            TablaTecnicas.COL_VALOR,
            TablaTiposDeTecnicas.COL_ID_PARENT,
            TablaTiposDeTecnicas.COL_NUM_COLS,
            TablaTiposDeTecnicas.COL_NUM_ROWS,
            TablaTiposDeTecnicas.COL_TITLE,
            TablaTiposDeTecnicas.COL_LABELS_COLS,
            TablaTiposDeTecnicas.COL_LABELS_ROWS,
            TablaTiposDeTecnicas.COL_MIN,
            TablaTiposDeTecnicas.COL_MAX,
            TablaTiposDeTecnicas.COL_VIEWTYPE,
            "(SELECT GROUP_CONCAT(" +
                    TablaEtiquetas.COL_ID_TIPO_ETIQUETA +
                    ") FROM " +
                    TablaEtiquetas.TABLA_ETIQUETAS +
                    " WHERE " +
                    TablaTecnicas.TABLA_TECNICAS+"."+TablaTecnicas._ID + "=" + TablaEtiquetas.COL_ID_TECNICA +
                    ") AS " + PROY_COMB      //TODO: usar group_concat para sacar también las etiquetas. Ej:  SELECT t.*, tt.*, (SELECT GROUP_CONCAT(e.tipoEtiqueta_etiquetas) FROM etiquetas e WHERE t._id=e.idTecnica_etiquetas) AS combinedsolutions FROM tecnicas t left join tiposDeTecnicas tt on tt.idTipoTecnica_tiposDeTecnicas=t.idTipoTecnica_tecnicas
    };

    private TecnicasAdapter mAdapter    = null;
    private long mSesionId;


    public interface itemTecnicable{
        public void setValue(int value);
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
        //getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);

        //borrar elementos de la lista
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

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



    //********************************************************************************************//
    // C O N T E X T U A L   A C T I O N   B A R 
    //********************************************************************************************//
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        // Here you can do something when items are selected/de-selected,
        // such as update the title in the CAB
    }


    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {
            case R.id.action_delete:
                Toast.makeText(getActivity(),"borrando elementos", Toast.LENGTH_SHORT).show();
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate the menu for the CAB
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.deleteicon, menu);
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Here you can make any necessary updates to the activity when
        // the CAB is removed. By default, selected items are deselected/unchecked.
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Here you can perform updates to the CAB due to
        // an invalidate() request
        return false;
    }
}