package dan.android.quirogest.listFragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaEtiquetas;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeEtiquetas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;
import dan.android.quirogest.tecnicas.Tecnica;
import dan.android.quirogest.tecnicas.TecnicasAdapter;

import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dan on 17/11/13.
 */
public class TecnicasListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>,  AbsListView.MultiChoiceModeListener{ //TODO: unificar tecnicasListFragment con los demás fragmentos!!
    //public  static final String PROY_COMB    = "ProyeccionCombinacionDeEtiquetas";
    private static final String TAG          = "TecnicasListFragment";
    private static final String SESION_ID    = "sesion_id";
    private static final int    LOADER_ID    = 79838383;
    private static final Uri    URI          = QuiroGestProvider.CONTENT_URI_TECNICAS;
    private static final String SELECTION    = TablaTecnicas.COL_ID_SESION + "=?";
    private static final String ORDER        = "DATE(" + TablaTecnicas.COL_FECHA + ") DESC";
    private static final String[] PROYECTION = {            //TODO: eliminar group_concat cuando la clase etiquetas se encargue de coger ella las etiquetas necesarias ;)
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
            TablaTiposDeTecnicas.COL_VIEWTYPE
            /*"(SELECT GROUP_CONCAT(" +
                    TablaEtiquetas.COL_ID_TIPO_ETIQUETA +
                    ") FROM " +
                    TablaEtiquetas.TABLA_ETIQUETAS +
                    " WHERE " +
f                    TablaTecnicas.TABLA_TECNICAS+"."+TablaTecnicas._ID + "=" + TablaEtiquetas.COL_ID_TECNICA +
                    ") AS " + PROY_COMB*/
    };

    private TecnicasAdapter mAdapter    = null;
    private long mSesionId;
    public Context mContext;


    public interface itemTecnicable{
        public void setChangeValueListener(Tecnica.ChangeValueListener l);
        public void setValue(int value);
        public String getValue();
        public void setWritable(boolean b);

        void setDefaultValue();
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

        mContext = getActivity();

        //inicializamos el adapter con un cursor nulo hasta que se inicialice el Loader
        mAdapter = new TecnicasAdapter(getActivity(), null, false);
        setListAdapter(mAdapter);

        //necesario para que aparezca el menú
        setHasOptionsMenu(true);

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





    /**
     * Añade una tecnica de forma recursiva hasta que encuentra una tecnica sin hijos
     */
    private void addTecnica(Integer parentId){
        AlertDialog.Builder ad;
        ArrayAdapter<String> a;
        final ArrayList<Integer> tecnicaIdList;
        Cursor c;
        String[] proyection, selectionArg;
        String selection;

        tecnicaIdList   = new ArrayList<Integer>();
        ad              = new AlertDialog.Builder(mContext);
        a               = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1);
        proyection      = new String[] {
                TablaTiposDeTecnicas.COL_TITLE,
                TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA };
        selection       = TablaTiposDeTecnicas.COL_ID_PARENT + "=?";
        selectionArg    = new String[] { parentId==null ? "-1" : String.valueOf(parentId) };

        c = mContext.getContentResolver().query(QuiroGestProvider.CONTENT_URI_TIPOS_TECNICAS, proyection, selection, selectionArg, null);

        while (c.moveToNext()){
            a.add(c.getString(c.getColumnIndex(TablaTiposDeTecnicas.COL_TITLE)));
            tecnicaIdList.add(c.getInt(c.getColumnIndex(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA)));
        }
        c.close();

        if (a.getCount() > 0) {    //si no es una hoja
            ad.setAdapter(a, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //dialogInterface.cancel();
                    addTecnica(tecnicaIdList.get(i));
                }
            });
            ad.show();

        } else {
            ContentValues cv = new ContentValues();

            cv.put(TablaTecnicas.COL_ID_SESION, mSesionId);
            cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, parentId);
            //cv.put(TablaTecnicas.COL_OBSERVACIONES, "KK");
            //cv.put(TablaTecnicas.COL_VALOR, 1);
            mContext.getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);
            mAdapter.notifyDataSetInvalidated();
        }
    }


    /**
     * Añade a las técnicas seleccionadas las etiquetas que se elijan
     */
    private void addEtiquetas(final long[] selectedItems){
        final ArrayList<Integer> etiquetaIdList;
        AlertDialog.Builder ad;
        ArrayAdapter<String> a;
        String[] proyection;
        String descript;
        int idEtiqueta;
        Cursor c;

        etiquetaIdList  = new ArrayList<Integer>();
        ad              = new AlertDialog.Builder(mContext);
        a               = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1);
        proyection      = new String[] {
                TablaTiposDeEtiquetas.COL_DESCRIPCION,
                TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA};

        c = mContext.getContentResolver().query(QuiroGestProvider.CONTENT_URI_TIPO_ETIQUETAS, proyection, null, null, null);

        while (c.moveToNext()){
            descript    = c.getString(c.getColumnIndex(TablaTiposDeEtiquetas.COL_DESCRIPCION));
            idEtiqueta  = c.getInt(c.getColumnIndex(TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA));

            a.add(descript);
            etiquetaIdList.add(idEtiqueta);
        }
        c.close();

        ad.setAdapter(a, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContentValues cv = new ContentValues();

                dialogInterface.cancel();

                for (long id : selectedItems) {
                    cv.put(TablaEtiquetas.COL_ID_TECNICA, id);
                    cv.put(TablaEtiquetas.COL_ID_TIPO_ETIQUETA, etiquetaIdList.get(i));
                    mContext.getContentResolver().insert(QuiroGestProvider.CONTENT_URI_ETIQUETAS, cv);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }).show();

/*        ad  .setMultiChoiceItems(c, TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA, TablaTiposDeEtiquetas.COL_DESCRIPCION, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean selected) {
                if (selected) {
                    selectedLabels.add(which);

                } else {
                    selectedLabels.remove(which);
                }
            }
        })*/
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
                mAdapter.readWriteState = !mAdapter.readWriteState;
                mAdapter.notifyDataSetInvalidated();
                Log.d("TecnicasListFragent", "READ/WRITE " + String.valueOf(mAdapter.readWriteState));
                return false;
            case R.id.mainMenuAddItem:
                addTecnica(null);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    //********************************************************************************************//
    // C O N T E X T U A L   A C T I O N   B A R 
    //********************************************************************************************//
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Here you can perform updates to the CAB due to
        // an invalidate() request
        return false;
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate the menu for the CAB
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.contextual_tecnicas, menu);
        mode.setTitle("Select Items");
        return true;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        final int checkedCount = getListView().getCheckedItemCount();
        switch (checkedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + checkedCount + " items selected");
                break;
        }
    }


    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {
            case R.id.contextualMenuDeleteItem:
                Uri u;

                for(long id : getListView().getCheckedItemIds()){
                    u = Uri.withAppendedPath(URI, String.valueOf(id));
                    getActivity().getContentResolver().delete(u,null,null);
                    mAdapter.notifyDataSetChanged();
                }
                Toast.makeText(mContext,"borrando elementos", Toast.LENGTH_SHORT).show();
                //mode.finish(); // No es necesario, porque al borrar elementos seleccionados no queda ninguno seleccionado y se cierra solo el CAB
                return false;

            case R.id.contextualMenuAddLabel:
                addEtiquetas(getListView().getCheckedItemIds());
                mode.finish(); // Action picked, so close the CAB
                return false;


            default:
                return false;
        }
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Here you can make any necessary updates to the activity when
        // the CAB is removed. By default, selected items are deselected/unchecked.
    }



}