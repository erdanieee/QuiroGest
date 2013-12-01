package dan.android.quirogest.tecnicas;

/**
 * Created by dan on 17/11/13.
 */
public class Tecnica<T extends TecnicasListFragment.itemTecnicable>{
    private static final String REGULAR_EXPRESSION_MATRIX = ",";

    private T obj;


    public Tecnica(T o) {
        this.obj = o;
    }

    public Object getView(){return obj;}


    public void setValue(int idPadre, int min, int max, String v, String labels, String observ) {
        int[] values;
        String[] temp;

        //parse cbItems
        temp    = v.split(REGULAR_EXPRESSION_MATRIX);
        values  = new int[temp.length];
        for (int i=0; i< values.length; i++){
            values[i] = Integer.parseInt(temp[i]);
        }

        obj.setParentId(idPadre);
        obj.setMin(min);
        obj.setMax(max);
        obj.setValue(values);
        obj.setmLabels(labels.split(REGULAR_EXPRESSION_MATRIX));
        obj.setmObserv(observ);
    }
}
