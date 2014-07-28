package dan.android.quirogest.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import dan.android.quirogest.FragmentBase.ListFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.DatabaseHelper;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaMotivos;
import dan.android.quirogest.detailFragments.ClienteDetailFragment;
import dan.android.quirogest.listFragments.ClienteListFragment;
import dan.android.quirogest.listFragments.MotivosListFragment;


public class ContactosListActivity extends Activity implements ListFragmentBase.CallbackItemClicked{
    private static final String TAG = "ContactosListActivity";
    private static final int INTENT_MAIN_MENU_IMPORT_DB = 1;
    private static final int INTENT_MAIN_MENU_EXPORT_DB = 2;
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


    //********************************************************************************************//
    // M A I N      M E N U
    //********************************************************************************************//
    @Override
    //Primero se llama a la activity, y llega aquí solo si la activity no consume el evento (return false)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenuAddItem:      //TODO: en lugar de crear un dialogo, crear un motivo vacio y hacer la transicion para que se empiece a editar... Hacer lo mismo con las sesiones!!
                ContentValues cv;
                Uri newMotivoUri;
                Cursor c;

                cv  = new ContentValues();

                cv.put(TablaMotivos.COL_ID_CONTACTO, mContactoId);
                newMotivoUri = getContentResolver().insert(QuiroGestProvider.CONTENT_URI_MOTIVOS, cv);

                c = getContentResolver().query(newMotivoUri, new String[]{BaseColumns._ID},null,null,null);

                if (c.moveToFirst()){
                    mMotivoId = c.getLong(c.getColumnIndex(BaseColumns._ID));
                    Intent myIntent = new Intent(getApplicationContext(), MotivosListActivity.class);
                    myIntent.putExtra(MotivosListActivity.CONTACTO_ID, mContactoId);
                    myIntent.putExtra(MotivosListActivity.MOTIVO_ID, mMotivoId);           //usado para seleccionar posición por defecto
                    startActivity(myIntent);
                }

                return true;

            case R.id.mainMenuExportDb:
/*                Intent i;

                i = new Intent(Intent.ACTION_CREATE_DOCUMENT);      //FIXME: Se ha comentado porque no se puede hacer por intent si la tablet no tiene browser
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType(DocumentsContract.Document.MIME_TYPE_DIR);
                i.putExtra(Intent.EXTRA_TITLE, "Quirogest.db");

                startActivityForResult(i, INTENT_MAIN_MENU_EXPORT_DB);
*/
                String file;

                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    file = Environment.getExternalStorageDirectory() + "/" + DatabaseHelper.DATABASE_NAME;

                } else {
                    file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + DatabaseHelper.DATABASE_NAME;
                }

                if (DatabaseHelper.exportDb(file)){
                    Toast.makeText(getApplicationContext(), "Guardado correctamente en " + file, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "¡¡Error!!", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.mainMenuImportDb:
                Intent i;

                i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("file/*");

                startActivityForResult(i, INTENT_MAIN_MENU_IMPORT_DB);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==Activity.RESULT_OK){
            switch (requestCode){
                case INTENT_MAIN_MENU_IMPORT_DB:
                    if (DatabaseHelper.importDb(data.getData().getPath(), getBaseContext().getDatabasePath(DatabaseHelper.DATABASE_NAME).getAbsolutePath())){
                        Toast.makeText(getApplicationContext(), "Base de datos importada correctamente " + getApplicationContext().getDatabasePath(DatabaseHelper.DATABASE_NAME), Toast.LENGTH_LONG).show();
                    }
                    break;
                case INTENT_MAIN_MENU_EXPORT_DB:
                    if (DatabaseHelper.exportDb(data.getData().getPath())){
                        Toast.makeText(getApplicationContext(), "Guardado correctamente en " + data.getData().getPath(), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        Fragment f1, f2;

        f1 = getFragmentManager().findFragmentById(R.id.detail_container);
        f2 = getFragmentManager().findFragmentById(R.id.secondary_list_container);

        if (f1!=null && f2!=null) {
            getFragmentManager()
                    .beginTransaction()
                    .remove(f1)
                    .remove(f2)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        } else {
            super.onBackPressed();
        }
    }
}
