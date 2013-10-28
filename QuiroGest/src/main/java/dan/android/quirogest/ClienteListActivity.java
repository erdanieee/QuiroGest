package dan.android.quirogest;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;

import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaContactos;


public class ClienteListActivity extends Activity implements ClienteListFragment.Callbacks {
    private static final String TAG = "ClienteListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "creating activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente_twopane);

        //DatabaseHelper dbHelper = new DatabaseHelper(this);
        //dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TablaContactos.TABLA_CONTACTOS);
        //dbHelper.getWritableDatabase().execSQL(TablaContactos.sqlCreateTable);
        //insertamos algunos valores de ejemplo
        //ContentValues cv = new ContentValues();
        //cv.put(TablaContactos.COL_NOMBRE, "abejencio");
        //cv.put(TablaContactos.COL_APELLIDO1, "romua");
        //getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);
        //cv = new ContentValues();
        //cv.put(TablaContactos.COL_NOMBRE, "mostefat");
        //cv.put(TablaContactos.COL_MOVIL, "620587895");
        //getContentResolver().insert(QuiroGestProvider.CONTENT_URI_CONTACTOS, cv);
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
