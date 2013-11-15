package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.database.TablaSesiones;
import dan.android.quirogest.detailFragments.ClienteDetailFragment;
import dan.android.quirogest.listFragments.ClienteListFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;


public class ClienteListActivity extends Activity implements ListFragmentBase.CallbackItemClicked{
    private static final String TAG = "ClienteListActivity";
    private long mContactoId, mMotivoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "creating activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.two_lists_details);

        //añadimos el fragment principal
        ClienteListFragment f = ClienteListFragment.newIntance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_container, f)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();


        //////////////// TEMPORAL /////////////////
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaClientes.TABLA_CLIENTES);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaMotivos.TABLA_MOTIVOS);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaSesiones.TABLA_SESIONES);

        dbHelper.getWritableDatabase().execSQL(TablaClientes.sqlCreateTableClientes);
        dbHelper.getWritableDatabase().execSQL(TablaMotivos.sqlCreateTableMotivos);
        dbHelper.getWritableDatabase().execSQL(TablaSesiones.sqlCreateTableSesiones);

        //insertamos algunos valores de ejemplo
        ContentValues cv = new ContentValues();
        cv.put(TablaClientes._ID, 1);
        cv.put(TablaClientes.COL_NOMBRE, "abejencio");
        cv.put(TablaClientes.COL_APELLIDO1, "romua");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);

        cv = new ContentValues();
        cv.put(TablaClientes._ID, 2);
        cv.put(TablaClientes.COL_NOMBRE, "mostefat");
        cv.put(TablaClientes.COL_MOVIL, "620587895");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);

        cv = new ContentValues();
        cv.put(TablaClientes._ID, 3);
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
        cv.put(TablaClientes._ID, 1);
        cv.put(TablaMotivos.COL_ID_CONTACTO, 3);
        cv.put(TablaMotivos.COL_DIAGNOSTICO, "Lumbalgia");
        cv.put(TablaMotivos.COL_OBSERVACIONES, "Aparece cada mes o mes y medio");
        cv.put(TablaMotivos.COL_ACTIV_FISICA, "balanceo anteroposterior de tronco");
        cv.put(TablaMotivos.COL_DESCRIPCION, "presenta dolor al estar varias horas sentado");
        cv.put(TablaMotivos.COL_COMIENZO, "2000-01-01");
        cv.put(TablaMotivos.COL_FECHA, "2000-03-03");
        cv.put(TablaMotivos.COL_ESTADO_SALUD, TablaMotivos.EstadoSalud.BUENO.toSQLite());
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_MOTIVOS, cv);

        cv = new ContentValues();
        cv.put(TablaClientes._ID, 2);
        cv.put(TablaMotivos.COL_ID_CONTACTO, 3);
        cv.put(TablaMotivos.COL_DIAGNOSTICO, "Cervicalgia");
        cv.put(TablaMotivos.COL_OBSERVACIONES, "Aparece cada mes o mes y medio");
        cv.put(TablaMotivos.COL_ACTIV_FISICA, "balanceo anteroposterior de tronco");
        cv.put(TablaMotivos.COL_DESCRIPCION, "presenta dolor al estar varias horas sentado");
        cv.put(TablaMotivos.COL_COMIENZO, "2013-08-01");
        cv.put(TablaMotivos.COL_FECHA, "2013-08-21");
        cv.put(TablaMotivos.COL_ESTADO_SALUD, TablaMotivos.EstadoSalud.MALO.toSQLite());
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_MOTIVOS, cv);

        cv = new ContentValues();
        cv.put(TablaClientes._ID, 1);
        cv.put(TablaSesiones.COL_ID_MOTIVO, 2);
        cv.put(TablaSesiones.COL_DIAGNOSTICO, "diagnóstico de la osteópata");
        cv.put(TablaSesiones.COL_DOLOR, TablaSesiones.CuantificacionDolor.DOLOR_4.toSQLite());
        cv.put(TablaSesiones.COL_FECHA,  "2001-12-14");
        cv.put(TablaSesiones.COL_INGRESOS, 35);
        cv.put(TablaSesiones.COL_OBSERVACIONES, "lo he hecho fenomenal!");
        cv.put(TablaSesiones.COL_POSTRATAMIENTO, "nada");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_SESIONES, cv);

        cv = new ContentValues();
        cv.put(TablaClientes._ID, 2);
        cv.put(TablaSesiones.COL_ID_MOTIVO, 2);
        cv.put(TablaSesiones.COL_DIAGNOSTICO, "diagnóstico de la osteópata 2");
        cv.put(TablaSesiones.COL_DOLOR, TablaSesiones.CuantificacionDolor.DOLOR_4.toSQLite());
        cv.put(TablaSesiones.COL_FECHA, "2013-01-14");
        cv.put(TablaSesiones.COL_INGRESOS, 95);
        cv.put(TablaSesiones.COL_OBSERVACIONES, "lo he hecho fenomenal!");
        cv.put(TablaSesiones.COL_POSTRATAMIENTO, "nada");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_SESIONES, cv);
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
}
