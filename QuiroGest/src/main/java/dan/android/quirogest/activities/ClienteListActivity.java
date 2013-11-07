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

import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.detailFragments.ClienteDetailFragment;
import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.listFragments.ClienteListFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;


public class ClienteListActivity extends Activity implements ListFragmentBase.CallbackItemClicked{
    private static final String TAG = "ClienteListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "creating activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente);

        //Configuramos el ListView principal
        //TODO: unificar layout para vistas y meter el fragment a manija en lugar de hacerlo en el XML
        ((ClienteListFragment)getFragmentManager().findFragmentById(R.id.cliente_list)).getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);



        //////////////// TEMPORAL /////////////////
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
     * Callback method from {@link dan.android.quirogest.FragmentBase.ListFragmentBase}
     * indicating that the item with the given ID was selected.
     * id - El id representa un contacto o un motivo, según la lista que se haya pulsado!!!
     */
    @Override
    public void onListItemSelected(ListFragmentBase lfb, long id) {
        Log.i(TAG, "Item selected " + id + " en " + lfb.getClass());
        ClienteDetailFragment cdf;
        MotivosListFragment mlf;
        FragmentTransaction ft;

        switch (lfb.getListViewType()){
            case LIST_VIEW_CLIENTES:
                cdf = (ClienteDetailFragment) getFragmentManager().findFragmentById(R.id.cliente_detail_container);

                if (cdf == null || cdf.getItemId()!= id){
                    cdf  = ClienteDetailFragment.newInstance(id);
                    mlf  = MotivosListFragment.newInstance(id);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.motivos_list_container, mlf)
                            .replace(R.id.cliente_detail_container, cdf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;

            case LIST_VIEW_MOTIVOS:
                Intent myIntent = new Intent(this, MotivosListActivity.class);
                myIntent.putExtra(MotivosListActivity.MOTIVO_ID, id);           //usado para seleccionar posición por defecto
                startActivity(myIntent);
                break;
        }
    }
}
