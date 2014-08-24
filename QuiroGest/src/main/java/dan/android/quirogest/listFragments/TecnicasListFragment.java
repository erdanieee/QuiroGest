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
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaEtiquetas;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeEtiquetas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;
import dan.android.quirogest.tecnicas.Tecnica;
import dan.android.quirogest.tecnicas.TecnicasAdapter;

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
    private static final String ORDER        = TablaTecnicas.COL_ORDER + " ASC";
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
    private ArrayList<Integer> tecnicaIdListToInsert;


    public interface itemTecnicable{
        public void setChangeValueListener(Tecnica.ChangeValueListener l);
        public void setValue(int value);
        public void setMax(Integer max);
        public void setMin(Integer min);
        public String getStringValue();
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
    private void addTecnica(Integer parentId, CharSequence parentName){
        AlertDialog.Builder ad;
        final CharSequence[] items;
        final int[]   itemsId;
        boolean[] selected;
        Cursor c;
        String[] proyection, selectionArg;
        String selection;

        if(parentId==null){
            tecnicaIdListToInsert = new ArrayList<Integer>();
        }

        ad              = new AlertDialog.Builder(mContext);
        proyection      = new String[] { TablaTiposDeTecnicas.COL_TITLE, TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA };
        selection       = TablaTiposDeTecnicas.COL_ID_PARENT + "=?";
        selectionArg    = new String[] { parentId==null ? "-1" : String.valueOf(parentId) };

        c = mContext.getContentResolver().query(QuiroGestProvider.CONTENT_URI_TIPOS_TECNICAS, proyection, selection, selectionArg, null);

        items       = new CharSequence[c.getCount()];
        itemsId     = new int[c.getCount()];
        selected    = new boolean[c.getCount()];
        int i       = 0;

        while (c.moveToNext()){
            items[i]    = c.getString(c.getColumnIndex(TablaTiposDeTecnicas.COL_TITLE));
            itemsId[i]  = c.getInt(c.getColumnIndex(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA));
            if (tecnicaIdListToInsert.contains(itemsId[i])){
                selected[i] = true;

            }else{
                selected[i] = false;
            }
            i++;
        }
        c.close();

        if (items.length > 0) {
            ad.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    addTecnica(itemsId[which], items[which]);
                }
            });
            if(parentId==null) {
                ad.setTitle("Selecciona las técnicas");
                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Integer i : tecnicaIdListToInsert) {
                            ContentValues cv = new ContentValues();

                            cv.put(TablaTecnicas.COL_ID_SESION, mSesionId);
                            cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, i);
                            cv.put(TablaTecnicas.COL_ORDER, getListView().getCount());
                            mContext.getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);
                            getListView().setSelection(getListView().getCount());
                        }
                    }
                });

            } else{
                ad.setTitle(parentName);
            }
            ad.show();

        } else {
            if (tecnicaIdListToInsert.contains(parentId)){
                tecnicaIdListToInsert.remove(tecnicaIdListToInsert.indexOf(parentId));

            } else {
                tecnicaIdListToInsert.add(parentId);
            }
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
                addTecnica(null,null);
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
        SparseBooleanArray sba;
        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {
            case R.id.contextualMenuDeleteItem:
                Uri u;

                Toast.makeText(mContext,"borrando elementos", Toast.LENGTH_SHORT).show();
                for(long id : getListView().getCheckedItemIds()){
                    u = Uri.withAppendedPath(URI, String.valueOf(id));
                    getActivity().getContentResolver().delete(u,null,null);
                    mAdapter.notifyDataSetChanged();
                }
                mode.finish(); //FIXME: No es necesario, porque al borrar elementos seleccionados no queda ninguno seleccionado y se cierra solo el CAB
                return false;

            case R.id.contextualMenuAddLabel:
                addEtiquetas(getListView().getCheckedItemIds());
                mode.finish(); // Action picked, so close the CAB
                return false;

            case R.id.contextualMenuMoveUp:
                sba = getListView().getCheckedItemPositions();
                Log.d(TecnicasListFragment.class.getSimpleName(), sba.toString());

                for (int i=sba.size()-1; i>=0;i--){
                    if(sba.keyAt(i) > 0) {
                        long id1 = getListView().getItemIdAtPosition(sba.keyAt(i));
                        long id2 = getListView().getItemIdAtPosition(sba.keyAt(i) - 1);

                        intercambiaTecnicas(id1, id2);
                        mAdapter.notifyDataSetChanged();
                        Log.d(TecnicasListFragment.class.getSimpleName(), "Intercambiando ID:" + String.valueOf(id1) + " con ID:" + String.valueOf(id2));
                    }
                }
                getListView().setSelectionFromTop(sba.keyAt(0)-1, getListView().getHeight()/2);
                return true;

            case R.id.contextualMenuMoveDown:
                sba = getListView().getCheckedItemPositions();
                Log.d(TecnicasListFragment.class.getSimpleName(), sba.toString());

                for (int i=0; i<sba.size();i++){
                    if(sba.keyAt(i)+1 < getListView().getCount()) {
                        long id1 = getListView().getItemIdAtPosition(sba.keyAt(i));
                        long id2 = getListView().getItemIdAtPosition(sba.keyAt(i) + 1);

                        intercambiaTecnicas(id1, id2);
                        mAdapter.notifyDataSetChanged();
                        Log.d(TecnicasListFragment.class.getSimpleName(), "Intercambiando ID:" + String.valueOf(id1) + " con ID:" + String.valueOf(id2));
                    }
                }
                getListView().setSelectionFromTop(sba.keyAt(0)+1, getListView().getHeight()/2);
                return true;

            default:
                return false;
        }
    }

    private void intercambiaTecnicas(long id1, long id2) {
        int order1, order2;
        Uri uri1, uri2;
        ContentValues cv;

        uri1 = Uri.withAppendedPath(URI,String.valueOf(id1));
        uri2 = Uri.withAppendedPath(URI,String.valueOf(id2));

        order1 = getItemOrder(uri1);
        order2 = getItemOrder(uri2);

        if (order1 == order2){  //just in case
            order1 = order2 + 1;
        }

        Log.d(TecnicasListFragment.class.getSimpleName(), "Intercambiando order:" + order1 + " con order:" + order2);

        cv = new ContentValues();
        cv.put(TablaTecnicas.COL_ORDER, order2);
        mContext.getContentResolver().update(uri1, cv, null, null);

        cv.clear();
        cv.put(TablaTecnicas.COL_ORDER, order1);
        mContext.getContentResolver().update(uri2, cv, null, null);
    }


    private int getItemOrder(Uri uri){
        Cursor c;
        int order = Integer.parseInt(uri.getLastPathSegment());
        String[] proyection = {TablaTecnicas.COL_ORDER};

        c = mContext.getContentResolver().query(uri,proyection, null,null,null);

        if(c.moveToNext()){
            if (!c.isNull(0)) {
                order = c.getInt(0);
            }
        }
        c.close();

        return order;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Here you can make any necessary updates to the activity when
        // the CAB is removed. By default, selected items are deselected/unchecked.
    }
}