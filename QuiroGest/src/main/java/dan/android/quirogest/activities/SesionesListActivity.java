package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.detailFragments.MotivoDetailFragment;
import dan.android.quirogest.detailFragments.SesionDetailFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;
import dan.android.quirogest.listFragments.SesionesListFragment;
import dan.android.quirogest.tecnicas.TecnicasListFragment;


public class SesionesListActivity extends Activity implements ListFragmentBase.CallbackItemClicked {
    private static final String TAG = "SesionesListActivity";

    public static final String SESION_ID = "sesion_id";
    public static final String MOTIVO_ID = "motivo_id";
    private long mMotivoId, mSesionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.one_list_details_cabecera);

        //añadimos el fragment principal
        mMotivoId = getIntent().getLongExtra(MOTIVO_ID,-1);
        mSesionId = getIntent().getLongExtra(SESION_ID, -1);
        SesionDetailFragment sdf = SesionDetailFragment.newInstance(mSesionId);
        TecnicasListFragment tlf = TecnicasListFragment.newInstance(mSesionId);

        SesionesListFragment f = SesionesListFragment.newInstance(mMotivoId, mSesionId);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_container, f)
                .replace(R.id.detail_container, sdf)
                .replace(R.id.list_container, tlf)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    /**
     * Callback method from {@link dan.android.quirogest.FragmentBase.ListFragmentBase}
     * indicating that the item with the given ID was selected.c
     * id - representa un motivoId o sesionId según la lista pulsada
     */
    @Override
    public void onListItemSelected(ListFragmentBase lfb, long sesionId) {
        Log.i(TAG, "Item selected " + sesionId);
        SesionesListFragment slf;
        SesionDetailFragment sdf;
        FragmentTransaction ft;

        switch (lfb.getListViewType()){
            case LIST_VIEW_SESIONES:
                mSesionId = sesionId;
                sdf = (SesionDetailFragment) getFragmentManager().findFragmentById(R.id.detail_container);
                TecnicasListFragment tlf = TecnicasListFragment.newInstance(mSesionId);

                if (sdf == null || sdf.getItemId()!= sesionId){
                    sdf = SesionDetailFragment.newInstance(sesionId);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detail_container, sdf)
                            .replace(R.id.list_container, tlf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;
        }
    }
}