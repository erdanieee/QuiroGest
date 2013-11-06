package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import dan.android.quirogest.ItemListFragmentBase.ListViewItemClickeable;
import dan.android.quirogest.ItemListFragmentBase.CallbackItemClicked;
import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.detailFragments.ClienteDetailFragment;
import dan.android.quirogest.listFragments.ClienteListFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;


public class ClienteListActivity extends Activity implements CallbackItemClicked{
    private static final String TAG = "ClienteListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "creating activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente);

        //Configuramos el ListView principal
        ((ClienteListFragment)getFragmentManager().findFragmentById(R.id.cliente_list)).getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaClientes.TABLA_CLIENTES);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaMotivos.TABLA_MOTIVOS);

        dbHelper.getWritableDatabase().execSQL(TablaClientes.sqlCreateTableClientes);
        dbHelper.getWritableDatabase().execSQL(TablaMotivos.sqlCreateTableMotivos);

        //insertamos algunos valores de ejemplo
        ContentValues cv = new ContentValues();
        cv.put(TablaClientes.COL_NOMBRE, "abejencio");
        cv.put(TablaClientes.COL_APELLIDO1, "romua");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);

        cv = new ContentValues();
        cv.put(TablaClientes.COL_NOMBRE, "mostefat");
        cv.put(TablaClientes.COL_MOVIL, "620587895");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);

        cv = new ContentValues();
        cv.put(TablaClientes.COL_NOMBRE, "Mohamed");
        cv.put(TablaClientes.COL_APELLIDO1, "Burgarit");
        cv.put(TablaClientes.COL_APELLIDO2, "San petremari");
        cv.put(TablaClientes.COL_MOVIL, "+41 365 445 545");
        cv.put(TablaClientes.COL_FIJO, "+34 91 651 354");
        cv.put(TablaClientes.COL_DIRECCION, "C/ San mortiburio epto 43, 1ºD");
        cv.put(TablaClientes.COL_CP, "28100");
        cv.put(TablaClientes.COL_LOCALIDAD, "Alcobendas");
        cv.put(TablaClientes.COL_PROVINCIA, "Madrid");
        cv.put(TablaClientes.COL_FECHA_NAC, "21/08/1981");
        cv.put(TablaClientes.COL_PROFESION, "Vendedor de barras de mantequilla");
        cv.put(TablaClientes.COL_ENFERMEDADES, "Aracnofobia");
        cv.put(TablaClientes.COL_ALERGIAS, "Al agua bendita");
        cv.put(TablaClientes.COL_OBSERVACIONES, "Me mira de forma cachondona");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);

        cv = new ContentValues();
        cv.put(TablaMotivos.COL_ID_CONTACTO, 3);
        cv.put(TablaMotivos.COL_DIAGNOSTICO, "Lumbalgia");
        cv.put(TablaMotivos.COL_OBSERVACIONES, "Aparece cada mes o mes y medio");
        cv.put(TablaMotivos.COL_ACTIV_FISICA, "balanceo anteroposterior de tronco");
        cv.put(TablaMotivos.COL_DESCRIPCION, "presenta dolor al estar varias horas sentado");
        cv.put(TablaMotivos.COL_FECHA, new SimpleDateFormat(TablaMotivos.SQLITE_DATE_FORMAT).format(new Date(0)));
        cv.put(TablaMotivos.COL_ESTADO_SALUD, TablaMotivos.EstadoSalud.BUENO.toSQLite());
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_MOTIVOS, cv);

        cv = new ContentValues();
        cv.put(TablaMotivos.COL_ID_CONTACTO, 3);
        cv.put(TablaMotivos.COL_DIAGNOSTICO, "Cervicalgia");
        cv.put(TablaMotivos.COL_OBSERVACIONES, "Aparece cada mes o mes y medio");
        cv.put(TablaMotivos.COL_ACTIV_FISICA, "balanceo anteroposterior de tronco");
        cv.put(TablaMotivos.COL_DESCRIPCION, "presenta dolor al estar varias horas sentado");
        cv.put(TablaMotivos.COL_FECHA, "1981-08-21");
        cv.put(TablaMotivos.COL_ESTADO_SALUD, TablaMotivos.EstadoSalud.MALO.toSQLite());
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_MOTIVOS, cv);
    }


    /**
     * Callback method from {@link dan.android.quirogest.ItemListFragmentBase.ListViewItemClickeable}
     * indicating that the item with the given ID was selected.
     * id - El id representa un contacto o un motivo, según la lista que se haya pulsado!!!
     */
    @Override
    public void onListItemSelected(ListViewItemClickeable listView, long id) {
        Log.i(TAG, "Item selected " + id);
        ClienteDetailFragment clienteDetaiFragment;
        MotivosListFragment motivosListFragment;
        FragmentTransaction ft;

        switch (listView.getListviewTag()){
            case ClienteListFragment.TAG_LIST_VIEW:
                clienteDetaiFragment = (ClienteDetailFragment) getFragmentManager().findFragmentById(R.id.cliente_detail_container);

                if (clienteDetaiFragment == null || clienteDetaiFragment.getContactoId()!= id){
                    clienteDetaiFragment = ClienteDetailFragment.newInstance(id);
                    motivosListFragment  = MotivosListFragment.newInstance(id);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.cliente_detail_container, clienteDetaiFragment)
                            .replace(R.id.motivos_list_container, motivosListFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;

            case MotivosListFragment.TAG_LIST_VIEW:
                Intent myIntent = new Intent(this, MotivosListFragment.class);
                myIntent.putExtra(MotivosListActivity.MOTIVO_ID, id);
                startActivity(myIntent);
                break;
        }
    }
}