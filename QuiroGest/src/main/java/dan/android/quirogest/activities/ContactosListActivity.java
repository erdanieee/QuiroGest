package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaContactos;
import dan.android.quirogest.database.TablaEtiquetas;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.database.TablaSesiones;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeEtiquetas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;
import dan.android.quirogest.detailFragments.ClienteDetailFragment;
import dan.android.quirogest.listFragments.ClienteListFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;
import dan.android.quirogest.tecnicas.TecnicasAdapter;


public class ContactosListActivity extends Activity implements ListFragmentBase.CallbackItemClicked{
    private static final String TAG = "ContactosListActivity";
    private long mContactoId, mMotivoId;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "creating activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3frames);

        //añadimos el fragment principal
        ClienteListFragment f = ClienteListFragment.newIntance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_container, f)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Callback method from {@link dan.android.quirogest.FragmentBase.ListFragmentBase}
     * indicating that the item with the given ID was selected.
     * id - El id representa un contacto o un motivo, según la lista que se haya pulsado!!!
     */
    @Override
    public void onListItemSelected(ListFragmentBase lfb, long id) {     //Podría pasarme mejor la Uri!!!
        Log.i(TAG, "Item selected " + id + " en ");
        ClienteDetailFragment cdf;
        MotivosListFragment mlf;
        FragmentTransaction ft;

        switch (lfb.getListViewType()){
            case LIST_VIEW_CLIENTES:
                mContactoId = id;
                cdf = (ClienteDetailFragment) getFragmentManager().findFragmentById(R.id.detail_container);

                if (cdf == null || cdf.getItemId()!= mContactoId){
                    cdf = ClienteDetailFragment.newInstance(mContactoId);
                    mlf  = MotivosListFragment.newInstance(mContactoId);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.secondary_list_container, mlf)
                            .replace(R.id.detail_container, cdf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;

            case LIST_VIEW_MOTIVOS:
                mMotivoId = id;
                Intent myIntent = new Intent(getApplicationContext(), MotivosListActivity.class);
                myIntent.putExtra(MotivosListActivity.CONTACTO_ID, mContactoId);
                myIntent.putExtra(MotivosListActivity.MOTIVO_ID, mMotivoId);           //usado para seleccionar posición por defecto
                startActivity(myIntent);
                break;
        }
    }


    //********************************************************************************************//
    // M A I N      M E N U
    //********************************************************************************************//
    @Override
    //Primero se llama a la activity, y llega aquí solo si la activity no consume el evento (return false)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenuAddItem:      //TODO: en lugar de crear un dialogo, crear un motivo vacio y hacer la transicion para que se empiece a editar... Hacer lo mismo con las sesiones!!
                ContentValues cv;
                Uri newMotivoUri;
                Cursor c;

                cv  = new ContentValues();

                cv.put(TablaMotivos.COL_ID_CONTACTO, mContactoId);
                cv.put(TablaMotivos.COL_DIAGNOSTICO, "Nuevo Motivo");
                newMotivoUri = getContentResolver().insert(QuiroGestProvider.CONTENT_URI_MOTIVOS, cv);

                c = getContentResolver().query(newMotivoUri, new String[]{BaseColumns._ID},null,null,null);

                if (c.moveToFirst()){
                    mMotivoId = c.getLong(c.getColumnIndex(BaseColumns._ID));
                    Intent myIntent = new Intent(getApplicationContext(), MotivosListActivity.class);
                    myIntent.putExtra(MotivosListActivity.CONTACTO_ID, mContactoId);
                    myIntent.putExtra(MotivosListActivity.MOTIVO_ID, mMotivoId);           //usado para seleccionar posición por defecto
                    startActivity(myIntent);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment f1, f2;

        f1 = getFragmentManager().findFragmentById(R.id.detail_container);
        f2 = getFragmentManager().findFragmentById(R.id.secondary_list_container);

        if (f1!=null && f2!=null) {
            getFragmentManager()
                    .beginTransaction()
                    .remove(f1)
                    .remove(f2)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        } else {
            super.onBackPressed();
        }
    }
}
