package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import dan.android.quirogest.listFragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.detailFragments.MotivoDetailFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;
import dan.android.quirogest.listFragments.SesionesListFragment;


public class MotivosListActivity extends Activity implements ListFragmentBase.CallbackItemClicked {
    private static final String TAG = "MotivosListActivity";

    //TODO: variables usadas para seleccionar por defecto un elemento de la lista. AÑADIR código!!!
    public static final String MOTIVO_ID = "motivo_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_motivo);

        //TODO: Añadir código para seleccionar valor por defecto para el MotivoListFragment (necesario instanciar manualmente y no en el XML)
        //new Bundle().putLong(ListFragmentBase.ARG_SELECTED_ITEM_ID, getIntent().getLongExtra(MOTIVO_ID, 0));

        //Configuramos el ListView principal
        ((MotivosListFragment)getFragmentManager().findFragmentById(R.id.motivos_list)).getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    /**
     * Callback method from {@link dan.android.quirogest.listFragmentBase.ListFragmentBase}
     * indicating that the item with the given ID was selected.c
     * id - representa un motivoId o sesionId según la lista pulsada
     */
    @Override
    public void onListItemSelected(ListFragmentBase lfb, long id) {
        Log.i(TAG, "Item selected " + id);
        MotivoDetailFragment mdf;
        SesionesListFragment slf;
        FragmentTransaction ft;

        switch (lfb.getListViewType()){
            case LIST_VIEW_MOTIVOS:
                mdf = (MotivoDetailFragment) getFragmentManager().findFragmentById(R.id.motivo_detail_container);

                if (mdf == null || mdf.getMotivoId()!= id){
                    mdf = MotivoDetailFragment.newInstance(id);
                    slf = SesionesListFragment.newInstance(id);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.motivo_detail_container, mdf)
                            .replace(R.id.sesiones_list_container, slf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;

            case LIST_VIEW_SESIONES:
                Intent myIntent = new Intent(this, SesionesListActivity.class);
                myIntent.putExtra(SesionesListActivity.SESION_ID, id);  //usado para seleccionar posición por defecto
                startActivity(myIntent);
                break;
        }
    }
}