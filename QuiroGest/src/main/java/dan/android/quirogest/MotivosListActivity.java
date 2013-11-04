package dan.android.quirogest;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import dan.android.quirogest.ItemListFragmentBase.CallbackItemClicked;
import dan.android.quirogest.ItemListFragmentBase.ListViewItemClickeable;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaClientes;
import dan.android.quirogest.database.TablaMotivos;


public class MotivosListActivity extends Activity implements CallbackItemClicked{
    private static final String TAG = "MotivosListActivity";

    public static final String MOTIVO_ID = "contacto_id";
    private long mMotivoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_motivos);

        //obtenemos el motivo seleccionado en cliente_activity
        Intent myIntent = getIntent();
        mMotivoId       = myIntent.getLongExtra(MOTIVO_ID, 0);

        //Configuramos el ListView principal
        ((MotivosListFragment)getFragmentManager().findFragmentById(R.id.motivos_list)).getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    /**
     * Callback method from {@link dan.android.quirogest.ItemListFragmentBase.ListViewItemClickeable}
     * indicating that the item with the given ID was selected.c
     */
    @Override
    public void onListItemSelected(ListViewItemClickeable listView, long id) {
        Log.i(TAG, "Item selected " + id);
        MotivosDetailFragment motivosDetailFragment;
        FragmentTransaction ft;

        switch (listView.getListviewTag()){
            case MotivosListFragment.TAG_LIST_VIEW:
                motivosDetailFragment = (MotivosDetailFragment) getFragmentManager().findFragmentById(R.id.motivos_detail_container);

                if (motivosDetailFragment == null || motivosDetailFragment.getContactoId()!= id){
                    motivosDetailFragment = MotivosDetailFragment.newInstance(id);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.cliente_detail_container, clienteDetaiFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;
        }
    }
}
