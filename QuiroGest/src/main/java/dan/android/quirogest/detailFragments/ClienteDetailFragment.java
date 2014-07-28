package dan.android.quirogest.detailFragments;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dan.android.quirogest.FragmentBase.DetailFragmentBase;
import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaContactos;
import dan.android.quirogest.views.LabelModificationListener;
import dan.android.quirogest.views.LabelView;


public class ClienteDetailFragment extends DetailFragmentBase{
    private static final int    CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE     = 100;
    private static final int    LAYOUT      = R.layout.fragment_cliente_detail;
    private final Uri           QUERY_URI   = QuiroGestProvider.CONTENT_URI_CONTACTOS;

    private LabelView mNombre, mApellido1, mApellido2, mMovil, mFijo, mEmail, mDireccion, mCP, mLocalidad, mProvincia, mFechaNac, mProfesion, mEnfermedades, mAlergias, mObservaciones;
    private TextView mEdad;
    private ImageView mFoto;


    @Override
    public Uri      getUri()        { return QUERY_URI; }
    public String[] getProjection() { return null; }            // nos interesan todas las columnas


    /** El constructor tiene que estar vacío, por eso se crea esta función estática */
    public static ClienteDetailFragment newInstance(long contactoId) {
        ClienteDetailFragment f = new ClienteDetailFragment();

        // Supply id input as an argument.
        Bundle args = new Bundle();
        args.putLong(ITEM_ID, contactoId);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            Bundle extras = data.getExtras();

            if(extras!=null){
                Bitmap foto;
                ContentValues cv;
                Uri uri;
                ByteArrayOutputStream baos;

                uri     = ContentUris.withAppendedId(getUri(), getItemId());
                cv      = new ContentValues();
                foto    = (Bitmap)extras.get("data");
                baos    = new ByteArrayOutputStream();

                foto.compress(Bitmap.CompressFormat.PNG, 90, baos);

                //cv.put(TablaContactos.COL_FOTO, foto.getNinePatchChunk());
                cv.put(TablaContactos.COL_FOTO, baos.toByteArray());
                getActivity().getContentResolver().update(uri, cv, null, null);


                mFoto.setImageBitmap(foto);
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mRootView;

        mRootView       = inflater.inflate(LAYOUT, container, false);
        mNombre         = (LabelView) mRootView.findViewById(R.id.textViewNombre);
        mApellido1      = (LabelView) mRootView.findViewById(R.id.textViewApellido1);
        mApellido2      = (LabelView) mRootView.findViewById(R.id.textViewApellido2);
        mMovil          = (LabelView) mRootView.findViewById(R.id.EditTextMovil);
        mFijo           = (LabelView) mRootView.findViewById(R.id.EditTextFijo);
        mEmail          = (LabelView) mRootView.findViewById(R.id.EditTextEmail);
        mDireccion      = (LabelView) mRootView.findViewById(R.id.EditTextDireccion);
        mCP             = (LabelView) mRootView.findViewById(R.id.EditTextCP);
        mLocalidad      = (LabelView) mRootView.findViewById(R.id.EditTextLocalidad);
        mProvincia      = (LabelView) mRootView.findViewById(R.id.EditTextProvincia);
        mFechaNac       = (LabelView) mRootView.findViewById(R.id.EditTextFechaNacimiento);
        mProfesion      = (LabelView) mRootView.findViewById(R.id.EditTextProfesion);
        mEnfermedades   = (LabelView) mRootView.findViewById(R.id.EditTextEnfermedades);
        mAlergias       = (LabelView) mRootView.findViewById(R.id.EditTextAlergias);
        mObservaciones  = (LabelView) mRootView.findViewById(R.id.EditTextObservaciones);
        mFoto           = (ImageView) mRootView.findViewById(R.id.imageViewFoto);
        mEdad           = (TextView) mRootView.findViewById(R.id.TextViewEdad);

        mFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;

                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        });

        mFechaNac.setModificationCallback(new LabelModificationListener() {
            @Override
            public void onLabelModification(String t) {
                mEdad.setText(MotivoDetailFragment.getElapsedTime(mFechaNac.getText().toString(), new SimpleDateFormat(LabelView.DATE_FORMAT).format(Calendar.getInstance().getTime())));
            }
        });

        mNombre.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_NOMBRE,
                LabelView.TYPE_TEXT);
        mApellido1.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_APELLIDO1,
                LabelView.TYPE_TEXT);
        mApellido2.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_APELLIDO2,
                LabelView.TYPE_TEXT);
        mMovil.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_MOVIL,
                LabelView.TYPE_NUM);        //TODO: Add TYPE_PHONE in LabelView
        mFijo.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_FIJO,
                LabelView.TYPE_NUM);
        mEmail.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_EMAIL,
                LabelView.TYPE_TEXT);
        mDireccion.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_DIRECCION,
                LabelView.TYPE_TEXT);
        mCP.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_CP,
                LabelView.TYPE_NUM);
        mLocalidad.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_LOCALIDAD,
                LabelView.TYPE_TEXT);
        mProvincia.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_PROVINCIA,
                LabelView.TYPE_TEXT);
        mFechaNac.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_FECHA_NAC,
                LabelView.TYPE_DATE);
        mProfesion.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_PROFESION,
                LabelView.TYPE_TEXT);
        mEnfermedades.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_ENFERMEDADES,
                LabelView.TYPE_TEXT);
        mAlergias.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_ALERGIAS,
                LabelView.TYPE_TEXT);
        mObservaciones.setModificationParams(Uri.withAppendedPath(QuiroGestProvider.CONTENT_URI_CONTACTOS, String.valueOf(getItemId())),
                TablaContactos.COL_OBSERVACIONES,
                LabelView.TYPE_TEXT);

        return mRootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null!=getCursor() && getCursor().moveToFirst()){
            String nombre, apellido1, apellido2, movil, fijo, email, direccion, cp, localidad, provincia, fechaNac, profesion, enfermedades, alergias, observaciones;
            byte[] fotoByteArray;

            nombre          = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_NOMBRE));
            apellido1       = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_APELLIDO1));
            apellido2       = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_APELLIDO2));
            movil           = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_MOVIL));
            fijo            = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_FIJO));
            email           = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_EMAIL));
            direccion       = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_DIRECCION));
            cp              = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_CP));
            localidad       = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_LOCALIDAD));
            provincia       = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_PROVINCIA));
            fechaNac        = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_FECHA_NAC));
            profesion       = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_PROFESION));
            enfermedades    = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_ENFERMEDADES));
            alergias        = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_ALERGIAS));
            observaciones   = getCursor().getString(getCursor().getColumnIndex(TablaContactos.COL_OBSERVACIONES));
            fotoByteArray   = getCursor().getBlob((getCursor().getColumnIndex(TablaContactos.COL_FOTO)));

            mNombre.setText(nombre);
            mApellido1.setText(apellido1);
            mApellido2.setText(apellido2);
            mMovil.setText(movil);
            mFijo.setText(fijo);
            mEmail.setText(email);
            mDireccion.setText(direccion);
            mCP.setText(cp);
            mLocalidad.setText(localidad);
            mProvincia.setText(provincia);
            mFechaNac.setText(fechaNac);
            mProfesion.setText(profesion);
            mEnfermedades.setText(enfermedades);
            mAlergias.setText(alergias);
            mObservaciones.setText(observaciones);

            if(fotoByteArray!=null) {
                mFoto.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(fotoByteArray)));
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //necesario para que aparezca el menú
        setHasOptionsMenu(true);
    }



    //********************************************************************************************//
    // M A I N      M E N U
    //********************************************************************************************//

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_tecnicas, menu);
    }


    @Override
    //Primero se llama a la activity, y llega aquí solo si la activity no consume el evento (return false)
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenuEditItem:
                mNombre.setWritable(!mNombre.isEditable());
                mApellido1.setWritable(!mApellido1.isEditable());
                mApellido2.setWritable(!mApellido2.isEditable());
                mMovil.setWritable(!mMovil.isEditable());
                mFijo.setWritable(!mFijo.isEditable());
                mEmail.setWritable(!mEmail.isEditable());
                mDireccion.setWritable(!mDireccion.isEditable());
                mCP.setWritable(!mCP.isEditable());
                mLocalidad.setWritable(!mLocalidad.isEditable());
                mProvincia.setWritable(!mProvincia.isEditable());
                mFechaNac.setWritable(!mFechaNac.isEditable());
                mProfesion.setWritable(!mProfesion.isEditable());
                mEnfermedades.setWritable(!mEnfermedades.isEditable());
                mAlergias.setWritable(!mAlergias.isEditable());
                mObservaciones.setWritable(!mObservaciones.isEditable());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
