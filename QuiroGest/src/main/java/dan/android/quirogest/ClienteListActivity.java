package dan.android.quirogest;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
import dan.android.quirogest.database.TablaMotivos;


public class ClienteListActivity extends Activity implements ClienteListFragment.Callbacks {
    private static final String TAG = "ClienteListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "creating activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente);

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
        cv.put(TablaMotivos.COL_DIAGNOSTICO, "Síndrome de la p#### constantemente tiesa");
        cv.put(TablaMotivos.COL_OBSERVACIONES, "Efectívamente está muy dura");
        cv.put(TablaMotivos.COL_ACTIV_FISICA, "balanceo anteroposterior de tronco");
        cv.put(TablaMotivos.COL_DESCRIPCION, "unos 19cm, gorda como un zollo");
        cv.put(TablaMotivos.COL_COMIENZO, new SimpleDateFormat(TablaMotivos.SQLITE_DATE_FORMAT).format(new Date(0)));
        cv.put(TablaMotivos.COL_ESTADO_SALUD, TablaMotivos.EstadoSalud.BUENO.toSQLite());
    }


    /**
     * Callback method from {@link ClienteListFragment.Callbacks}
     * indicating that the item with the given ID was selected.c
     */
    @Override
    public void onItemSelected(long contactoId) {
        Log.i(TAG, "Item selected " + contactoId);
        Bundle arguments;
        ClienteDetailFragment fragment;
        FragmentTransaction ft;

        fragment = (ClienteDetailFragment) getFragmentManager().findFragmentById(R.id.cliente_detail_container);

        if (fragment == null || fragment.getContactoId()!= contactoId){
            fragment = ClienteDetailFragment.newInstance(contactoId);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.cliente_detail_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}
