package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.detailFragments.SesionDetailFragment;
import dan.android.quirogest.listFragments.SesionesListFragment;
import dan.android.quirogest.listFragments.TecnicasListFragment;
import dan.android.quirogest.tecnicas.Tecnica;


public class SesionesListActivity extends Activity implements ListFragmentBase.CallbackItemClicked {
    private static final String TAG = "SesionesListActivity";

    public static final String SESION_ID = "sesion_id";
    public static final String MOTIVO_ID = "motivo_id";
    private long mMotivoId, mSesionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3frames_cabecera);

        //añadir back en el action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //añadimos el fragment principal
        mMotivoId = getIntent().getLongExtra(MOTIVO_ID,-1);
        mSesionId = getIntent().getLongExtra(SESION_ID, -1);
        SesionDetailFragment sdf = SesionDetailFragment.newInstance(mSesionId);
        TecnicasListFragment tlf = TecnicasListFragment.newInstance(mSesionId);

        SesionesListFragment f = SesionesListFragment.newInstance(mMotivoId, mSesionId);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_container, f)
                .replace(R.id.detail_container, tlf)
                .replace(R.id.secondary_list_container, sdf)
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
        SesionDetailFragment sdf;
        TecnicasListFragment tlf;

        switch (lfb.getListViewType()){
            case LIST_VIEW_SESIONES:
                mSesionId = sesionId;
                sdf = (SesionDetailFragment) getFragmentManager().findFragmentById(R.id.secondary_list_container);

                if (sdf == null || sdf.getItemId()!= sesionId){
                    sdf = SesionDetailFragment.newInstance(sesionId);
                    tlf = TecnicasListFragment.newInstance(mSesionId);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detail_container, tlf)
                            .replace(R.id.secondary_list_container, sdf)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                break;
        }
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
                startActionMode(mEditActionBarMode);        //crea los nuevos iconos de añadir, borrar y guardar
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private ActionMode.Callback mEditActionBarMode = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add:       //TODO: añadir sesión y también añadir sesión a partir de otra existente
                    return true;
                case R.id.action_delete:
                    return true;
                case R.id.action_save:
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };
}