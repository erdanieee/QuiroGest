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


public class MotivosListActivity extends Activity implements ListFragmentBase.CallbackItemClicked {
    private static final String TAG = "MotivosListActivity";

    public static final String MOTIVO_ID = "motivo_id";
    private long mMotivoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_motivo);

        //obtenemos el motivo seleccionado en cliente_activity
        Intent myIntent = getIntent();
        mMotivoId       = myIntent.getLongExtra(MOTIVO_ID, 0);

        //Configuramos el ListView principal
        ((MotivosListFragment)getFragmentManager().findFragmentById(R.id.motivos_list)).getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    /**
     * Callback method from {@link dan.android.quirogest.listFragmentBase.ListFragmentBase}
     * indicating that the item with the given ID was selected.c
     */
    @Override
    public void onListItemSelected(ListFragmentBase lfb, long id) {
        Log.i(TAG, "Item selected " + id);
        MotivoDetailFragment mdf;
        FragmentTransaction ft;

        switch (lfb.getListViewType()){
            case  MotivosListFragment.TAG_LIST_VIEW:
                mdf = (MotivoDetailFragment) getFragmentManager().findFragmentById(R.id.motivo_detail_container);

                if (mdf == null || mdf.getMotivoId()!= id){
                    mdf = MotivoDetailFragment.newInstance(id);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.motivo_detail_container, mdf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;
        }
    }
}
