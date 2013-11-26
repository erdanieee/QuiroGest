package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.detailFragments.MotivoDetailFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;
import dan.android.quirogest.listFragments.SesionesListFragment;


public class MotivosListActivity extends Activity implements ListFragmentBase.CallbackItemClicked {
    private static final String TAG = "MotivosListActivity";

    public static final String MOTIVO_ID    = "motivo_id";
    public static final String CONTACTO_ID  = "contacto_id";
    private long mContactoId, mMotivoId, mSesionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3frames_cabecera);

        //añadimos el fragment principal
        mContactoId = getIntent().getLongExtra(CONTACTO_ID,-1);
        mMotivoId   = getIntent().getLongExtra(MOTIVO_ID, -1);
        SesionesListFragment slf = SesionesListFragment.newInstance(mMotivoId);
        MotivoDetailFragment mdf = MotivoDetailFragment.newInstance(mMotivoId);

        MotivosListFragment f = MotivosListFragment.newInstance(mContactoId, mMotivoId);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_container, f)
                .replace(R.id.detail_container, mdf)
                .replace(R.id.secondary_list_container, slf)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    /**
     * Callback method from {@link dan.android.quirogest.FragmentBase.ListFragmentBase}
     * indicating that the item with the given ID was selected.c
     * id - representa un motivoId o sesionId según la lista pulsada
     */
    @Override
    public void onListItemSelected(ListFragmentBase lfb, long id) {
        Log.i(TAG, "Item selected " + id);
        MotivoDetailFragment mdf;
        SesionesListFragment slf;

        switch (lfb.getListViewType()){
            case LIST_VIEW_MOTIVOS:
                mMotivoId = id;
                mdf = (MotivoDetailFragment) getFragmentManager().findFragmentById(R.id.detail_container);

                if (mdf == null || mdf.getItemId()!= mMotivoId){
                    mdf = MotivoDetailFragment.newInstance(mMotivoId);
                    slf = SesionesListFragment.newInstance(mMotivoId);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detail_container, mdf)
                            .replace(R.id.secondary_list_container, slf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;

            case LIST_VIEW_SESIONES:
                mSesionId = id;
                Intent myIntent = new Intent(this, SesionesListActivity.class);
                myIntent.putExtra(SesionesListActivity.MOTIVO_ID, mMotivoId);
                myIntent.putExtra(SesionesListActivity.SESION_ID, mSesionId);  //usado para seleccionar posición por defecto
                startActivity(myIntent);
                break;
        }
    }
}