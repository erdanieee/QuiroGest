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

        SesionesListFragment f = SesionesListFragment.newInstance(mMotivoId, mSesionId);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_container, f)
                .replace(R.id.detail_container, sdf)
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
        SesionesListFragment slf;
        SesionDetailFragment sdf;
        FragmentTransaction ft;

        switch (lfb.getListViewType()){
            case LIST_VIEW_MOTIVOS:
                sdf = (SesionDetailFragment) getFragmentManager().findFragmentById(R.id.detail_container);

                if (sdf == null || sdf.getItemId()!= id){
                    sdf = SesionDetailFragment.newInstance(id);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detail_container, sdf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;

            case LIST_VIEW_SESIONES:
                Intent myIntent = new Intent(this, SesionesListActivity.class);
                myIntent.putExtra(SesionesListActivity.MOTIVO_ID, id);
                myIntent.putExtra(SesionesListActivity.SESION_ID, id);  //usado para seleccionar posición por defecto
                startActivity(myIntent);
                break;
        }
    }
}