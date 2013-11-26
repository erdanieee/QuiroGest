package dan.android.quirogest.tecnicas;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import dan.android.quirogest.R;
import dan.android.quirogest.database.QuiroGestProvider;
import dan.android.quirogest.database.TablaTecnicas;
import dan.android.quirogest.database.TablaTiposDeTecnicas;

/**
 * Created by dan on 16/11/13.
 */
public class TecnicasAdapter extends CursorAdapter{
    public static final int VIEWTYPE_CHECKBOX  = 1;
    public static final int VIEWTYPE_NUMBER    = 2;
    public static final int VIEWTYPE_GRID_6x3  = 3;
    public static final int VIEWTYPE_COUNT     = 4;    //número de VIEWTYPEs


    public TecnicasAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public int getItemViewType(int position) {
        Cursor cursor = (Cursor) getItem(position);
        return cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_VIEWTYPE));
    }


    @Override
    public int getViewTypeCount() {
        return VIEWTYPE_COUNT;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = null;
        ViewHolder vh = new ViewHolder();
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        switch(getItemViewType(cursor.getPosition())) {
            case VIEWTYPE_CHECKBOX:
                view        = mInflater.inflate(R.layout.tecnica_viewtype_checkbox, parent,false);
                vh.mView    = new Tecnica<Cb>((Cb)view.findViewById(R.id.tecnicaView));
                break;

            case VIEWTYPE_NUMBER:
                view        = mInflater.inflate(R.layout.tecnica_viewtype_numero, parent,false);
                vh.mView    = new Tecnica<Num> ((Num) view.findViewById(R.id.tecnicaView));
                break;

            case VIEWTYPE_GRID_6x3:
                view        = mInflater.inflate(R.layout.tecnica_viewtype_grid, parent,false);
                vh.mView    = new Tecnica<Grid> ((Grid) view.findViewById(R.id.tecnicaView));
                break;
            
            default:
                throw new IllegalStateException ("Should never happend!!") ;
        }

        vh.mDescripcion     = (TextView) view.findViewById(R.id.tecnicaDescripcion);
        vh.mObservaciones   = (TextView) view.findViewById(R.id.tecnicaObservaciones);

        view.setTag(vh);
        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int min, max, value, idPadre;
        String descrip, observ;
        ViewHolder mHolder = (ViewHolder) view.getTag();

        idPadre = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_ID_PARENT));
        min     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MIN));
        max     = cursor.getInt(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_MAX));
        value   = cursor.getInt(cursor.getColumnIndex(TablaTecnicas.COL_VALOR));                    //TODO: sugerencia. Cambiar a String para poder hacer concatenaciones (group_concat. ej: http://stackoverflow.com/questions/16269363/joining-multiple-records-in-a-cursoradapter) o usa binarios
        descrip = cursor.getString(cursor.getColumnIndex(TablaTiposDeTecnicas.COL_DESCRIPCION));    //TODO: ajustar para que las matrices puedan coger todos los labels en una sola casilla
        observ  = cursor.getString(cursor.getColumnIndex(TablaTecnicas.COL_OBSERVACIONES));

        if (mHolder.mDescripcion!=null) {mHolder.mDescripcion.setText(descrip);}
        if (mHolder.mObservaciones!=null && !observ.equals("")){ mHolder.mObservaciones.setText(observ); }

        mHolder.mView.setValues(idPadre, min, max, value);
    }


    public static class ViewHolder{
        TextView mDescripcion, mObservaciones;
        Tecnica mView;
    }
}






/*
 private class MyCustomAdapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;
        private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

        private ArrayList mData = new ArrayList();
        private LayoutInflater mInflater;

        private TreeSet mSeparatorsSet = new TreeSet();

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        public void addSeparatorItem(final String item) {
            mData.add(item);
            // save separator position
            mSeparatorsSet.add(mData.size() - 1);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int type = getItemViewType(position);
            System.out.println("getView " + position + " " + convertView + " type = " + type);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (type) {
                    case TYPE_ITEM:
                        convertView = mInflater.inflate(R.layout.item1, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.text);
                        break;
                    case TYPE_SEPARATOR:
                        convertView = mInflater.inflate(R.layout.item2, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.textSeparator);
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(mData.get(position));
            return convertView;
        }

    }

    public static class ViewHolder {
        public TextView textView;
    }
}
* */







/*
*
public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public LazyAdapter(Activity a, ArrayList&lt;HashMap&lt;String, String&gt;&gt; d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        HashMap&lt;String, String&gt; song = new HashMap&lt;String, String&gt;();
        song = data.get(position);

        // Setting all values in listview
        title.setText(song.get(CustomizedListView.KEY_TITLE));
        artist.setText(song.get(CustomizedListView.KEY_ARTIST));
        duration.setText(song.get(CustomizedListView.KEY_DURATION));
        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
* */




/*
public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    List<RowItem> rowItems;

    public CustomBaseAdapter(Context context, List<RowItem> items) {
        this.context = context;
        this.rowItems = items;
    }

    /private view holder class
private class ViewHolder {
    ImageView imageView;
    TextView txtTitle;
    TextView txtDesc;
}
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getItem(position);

        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
 */




/*
e class OrderAdapter extends ArrayAdapter<Order> {

        private ArrayList<Order> items;

        public OrderAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }
                Order o = items.get(position);
                if (o != null) {
                        TextView tt = (TextView) v.findViewById(R.id.toptext);
                        TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                        if (tt != null) {
                              tt.setText("Name: "+o.getOrderName());                            }
                        if(bt != null){
                              bt.setText("Status: "+ o.getOrderStatus());
                        }
                }
                return v;
        }
}
 */







//TODO: ver http://stackoverflow.com/questions/11395425/android-cursorloaded-with-custom-baseadapter para usar un CUrsorAdapter en lugar de Baseadapter