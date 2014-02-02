package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
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


public class ClienteListActivity extends Activity implements ListFragmentBase.CallbackItemClicked{
    private static final String TAG = "ClienteListActivity";
    private long mContactoId, mMotivoId;



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


        //////////////// TEMPORAL /////////////////
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaClientes.TABLA_PACIENTES);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaMotivos.TABLA_MOTIVOS);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaSesiones.TABLA_SESIONES);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaTecnicas.TABLA_TECNICAS);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaEtiquetas.TABLA_ETIQUETAS);
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS);

        dbHelper.getWritableDatabase().execSQL(TablaClientes.sqlCreateTableClientes);
        dbHelper.getWritableDatabase().execSQL(TablaMotivos.sqlCreateTableMotivos);
        dbHelper.getWritableDatabase().execSQL(TablaSesiones.sqlCreateTableSesiones);
        dbHelper.getWritableDatabase().execSQL(TablaTecnicas.sqlCreateTableTecnicas);
        dbHelper.getWritableDatabase().execSQL(TablaTiposDeTecnicas.sqlCreateTableTiposTecnicas);
        dbHelper.getWritableDatabase().execSQL(TablaEtiquetas.sqlCreateTableEtiquetas);
        dbHelper.getWritableDatabase().execSQL(TablaTiposDeEtiquetas.sqlCreateTableEtiquetas);

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
        cv.put(TablaMotivos._ID, 1);
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
        cv.put(TablaMotivos._ID, 2);
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
        cv.put(TablaSesiones._ID, 1);
        cv.put(TablaSesiones.COL_ID_MOTIVO, 2);
        cv.put(TablaSesiones.COL_DIAGNOSTICO, "diagnóstico de la osteópata");
        cv.put(TablaSesiones.COL_DOLOR, TablaSesiones.CuantificacionDolor.DOLOR_4.toSQLite());
        cv.put(TablaSesiones.COL_FECHA,  "2001-12-14");
        cv.put(TablaSesiones.COL_INGRESOS, 35);
        cv.put(TablaSesiones.COL_NUM_SESION, 1);
        cv.put(TablaSesiones.COL_OBSERVACIONES, "lo he hecho fenomenal!");
        cv.put(TablaSesiones.COL_POSTRATAMIENTO, "nada");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_SESIONES, cv);
        cv = new ContentValues();
        cv.put(TablaSesiones._ID, 2);
        cv.put(TablaSesiones.COL_ID_MOTIVO, 2);
        cv.put(TablaSesiones.COL_DIAGNOSTICO, "diagnóstico de la osteópata 2");
        cv.put(TablaSesiones.COL_DOLOR, TablaSesiones.CuantificacionDolor.DOLOR_4.toSQLite());
        cv.put(TablaSesiones.COL_FECHA, "2013-01-14");
        cv.put(TablaSesiones.COL_INGRESOS, 95);
        cv.put(TablaSesiones.COL_NUM_SESION, 2);
        cv.put(TablaSesiones.COL_OBSERVACIONES, "lo he hecho fenomenal!");
        cv.put(TablaSesiones.COL_POSTRATAMIENTO, "nada");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_SESIONES, cv);

        cv = new ContentValues();
        cv.put(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA, 1);
        cv.put(TablaTiposDeTecnicas.COL_TITLE, "estiramiento de cuello");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_COLS, "");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_ROWS, "");
        cv.put(TablaTiposDeTecnicas.COL_VIEWTYPE, TecnicasAdapter.VIEWTYPE_CHECKBOX);
        cv.put(TablaTiposDeTecnicas.COL_MIN, 0);
        cv.put(TablaTiposDeTecnicas.COL_MAX, 1);
        cv.put(TablaTiposDeTecnicas.COL_NUM_COLS, 1);
        cv.put(TablaTiposDeTecnicas.COL_NUM_ROWS, 1);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TIPOS_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA, 2);
        cv.put(TablaTiposDeTecnicas.COL_TITLE, "torsión de cuello");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_COLS, "");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_ROWS, "");
        cv.put(TablaTiposDeTecnicas.COL_VIEWTYPE, TecnicasAdapter.VIEWTYPE_NUMBER);
        cv.put(TablaTiposDeTecnicas.COL_MIN, 0);
        cv.put(TablaTiposDeTecnicas.COL_MAX, 5);
        cv.put(TablaTiposDeTecnicas.COL_NUM_COLS, 1);
        cv.put(TablaTiposDeTecnicas.COL_NUM_ROWS, 1);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TIPOS_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA, 3);
        cv.put(TablaTiposDeTecnicas.COL_TITLE, "Valoración cervical");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_COLS, "Izq,Esp,Der");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_ROWS, "C1,C2,C3,C4,C5,C6,C7");
        cv.put(TablaTiposDeTecnicas.COL_VIEWTYPE, TecnicasAdapter.VIEWTYPE_CHECKBOX);
        cv.put(TablaTiposDeTecnicas.COL_MIN, 0);
        cv.put(TablaTiposDeTecnicas.COL_MAX, 1);
        cv.put(TablaTiposDeTecnicas.COL_NUM_COLS, 3);
        cv.put(TablaTiposDeTecnicas.COL_NUM_ROWS, 7);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TIPOS_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA, 4);
        cv.put(TablaTiposDeTecnicas.COL_TITLE, "Valoración dorsal");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_COLS, "TI,CI,E,CD,TD");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_ROWS, "D1,D2,D3,D4,D5,D6,D7,D8,D9,D10,D11,D12");
        cv.put(TablaTiposDeTecnicas.COL_VIEWTYPE, TecnicasAdapter.VIEWTYPE_CHECKBOX);
        cv.put(TablaTiposDeTecnicas.COL_MIN, 0);
        cv.put(TablaTiposDeTecnicas.COL_MAX, 1);
        cv.put(TablaTiposDeTecnicas.COL_NUM_COLS, 5);
        cv.put(TablaTiposDeTecnicas.COL_NUM_ROWS, 12);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TIPOS_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA, 5);
        cv.put(TablaTiposDeTecnicas.COL_TITLE, "Valoración lumbar");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_COLS, "TI,CI,E,CD,TD");
        cv.put(TablaTiposDeTecnicas.COL_LABELS_ROWS, "L1,L2,L3,L4,L5");
        cv.put(TablaTiposDeTecnicas.COL_VIEWTYPE, TecnicasAdapter.VIEWTYPE_NUMBER);
        cv.put(TablaTiposDeTecnicas.COL_MIN, 0);
        cv.put(TablaTiposDeTecnicas.COL_MAX, 1);
        cv.put(TablaTiposDeTecnicas.COL_NUM_COLS, 5);
        cv.put(TablaTiposDeTecnicas.COL_NUM_ROWS, 5);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TIPOS_TECNICAS, cv);

        cv = new ContentValues();
        cv.put(TablaTecnicas._ID, 1);
        cv.put(TablaTecnicas.COL_ID_SESION, 2);
        cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, 1);
        cv.put(TablaTecnicas.COL_OBSERVACIONES, "bien");
        cv.put(TablaTecnicas.COL_VALOR, 1);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTecnicas._ID, 2);
        cv.put(TablaTecnicas.COL_ID_SESION, 2);
        cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, 2);
        cv.put(TablaTecnicas.COL_OBSERVACIONES, "otra observación");
        cv.put(TablaTecnicas.COL_VALOR, 3);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTecnicas._ID, 3);
        cv.put(TablaTecnicas.COL_ID_SESION, 2);
        cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, 2);
        cv.put(TablaTecnicas.COL_OBSERVACIONES, "otra observación");
        cv.put(TablaTecnicas.COL_VALOR, 5);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTecnicas._ID, 4);
        cv.put(TablaTecnicas.COL_ID_SESION, 2);
        cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, 3);
        cv.put(TablaTecnicas.COL_OBSERVACIONES, "kk de la vaca (observada)");
        cv.put(TablaTecnicas.COL_VALOR, "1,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,1");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTecnicas._ID, 5);
        cv.put(TablaTecnicas.COL_ID_SESION, 2);
        cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, 4);
        cv.put(TablaTecnicas.COL_OBSERVACIONES, "Observo lluego existo");
        cv.put(TablaTecnicas.COL_VALOR, "1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);
        cv = new ContentValues();
        cv.put(TablaTecnicas._ID, 6);
        cv.put(TablaTecnicas.COL_ID_SESION, 2);
        cv.put(TablaTecnicas.COL_ID_TIPO_TECNICA, 5);
        cv.put(TablaTecnicas.COL_OBSERVACIONES, "Observación");
        cv.put(TablaTecnicas.COL_VALOR, "1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TECNICAS, cv);

        cv = new ContentValues();
        cv.put(TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA, 1);
        cv.put(TablaTiposDeEtiquetas.COL_DESCRIPCION, "I");
        cv.put(TablaTiposDeEtiquetas.COL_COLOR, "#ffcc3820");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TIPO_ETIQUETAS, cv);
        cv = new ContentValues();
        cv.put(TablaTiposDeEtiquetas.COL_ID_TIPO_ETIQUETA, 2);
        cv.put(TablaTiposDeEtiquetas.COL_DESCRIPCION, "der");
        cv.put(TablaTiposDeEtiquetas.COL_COLOR, "#ffccca20");
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_TIPO_ETIQUETAS, cv);

        cv = new ContentValues();
        cv.put(TablaEtiquetas.COL_ID_TIPO_ETIQUETA,1);
        cv.put(TablaEtiquetas.COL_ID_TECNICA,4);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_ETIQUETAS, cv);
        cv = new ContentValues();
        cv.put(TablaEtiquetas.COL_ID_TIPO_ETIQUETA,2);
        cv.put(TablaEtiquetas.COL_ID_TECNICA,4);
        getContentResolver().insert(QuiroGestProvider.CONTENT_URI_ETIQUETAS, cv);
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
