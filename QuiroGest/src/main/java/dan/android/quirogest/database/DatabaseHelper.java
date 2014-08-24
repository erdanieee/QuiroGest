package dan.android.quirogest.database;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dan on 25/08/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME       = "QuiroGest.db";
    public static final String SQLITE_DATE_FORMAT   = "yyyy-MM-dd";  // Usado por SimpleDateFormat. <br>Debe tener el mismo formato que CURRENT_DATE de SQLite
    private static final int DATABASE_VERSION       = 4;
    private Context mContext;

    //Necesario por las actualizaciones de base de datos
    private static String TABLA_TECNICAS_V1_OLD =   "TablaTecnicas_V1_old";
    private static String TABLA_TIPO_TECNICAS_V1_OLD =   "TablaTecnicas_V2_old";


    public DatabaseHelper(Context contexto){
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);

        mContext = contexto;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaContactos.TABLA_CONTACTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaMotivos.TABLA_MOTIVOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaSesiones.TABLA_SESIONES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaTecnicas.TABLA_TECNICAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaEtiquetas.TABLA_ETIQUETAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS);


        Log.i(this.getClass().getSimpleName(), "Creando nueva base de datos");
        sqLiteDatabase.execSQL(TablaContactos.sqlCreateTableContactos);
        sqLiteDatabase.execSQL(TablaMotivos.sqlCreateTableMotivos);
        sqLiteDatabase.execSQL(TablaSesiones.sqlCreateTableSesiones);
        sqLiteDatabase.execSQL(TablaTecnicas.sqlCreateTableTecnicas);
        sqLiteDatabase.execSQL(TablaTiposDeTecnicas.sqlCreateTableTiposTecnicas);
        sqLiteDatabase.execSQL(TablaTiposDeEtiquetas.sqlCreateTableTiposEtiquetas);
        sqLiteDatabase.execSQL(TablaEtiquetas.sqlCreateTableEtiquetas);

        //insertTiposEtiquetas(sqLiteDatabase);
        //insertTiposTecnicas(sqLiteDatabase);

        //insertContactos(sqLiteDatabase);        //FIXME: PROVISIONAL!!!!

/*        try {
            input  = mContext.getAssets().open(DATABASE_NAME);
            output = new FileOutputStream(mContext.getDatabasePath(DATABASE_NAME));
            buffer = new byte[1024];

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext,"Ha habido un error al copiar los datos iniciales",Toast.LENGTH_LONG);
        }*/
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String tableTemp;

        switch (oldVersion){
            case 1:
                tableTemp = TABLA_TECNICAS_V1_OLD;
                db.execSQL("DROP TABLE IF EXISTS " + tableTemp);
                db.execSQL("ALTER TABLE " + TablaTecnicas.TABLA_TECNICAS + " RENAME TO " + tableTemp);
                db.execSQL(TablaTecnicas.sqlCreateTableTecnicas);
                db.execSQL("INSERT INTO "
                        + TablaTecnicas.TABLA_TECNICAS
                        + " ("
                            + TablaTecnicas._ID + ","
                            + TablaTecnicas.COL_ID_SESION + ","
                            + TablaTecnicas.COL_ID_TIPO_TECNICA + ","
                            + TablaTecnicas.COL_OBSERVACIONES + ","
                            + TablaTecnicas.COL_VALOR
                        + ") "
                        + " SELECT "
                            + TablaTecnicas._ID + ","
                            + TablaTecnicas.COL_ID_SESION + ","
                            + TablaTecnicas.COL_ID_TIPO_TECNICA + ","
                            + TablaTecnicas.COL_OBSERVACIONES + ","
                            + TablaTecnicas.COL_VALOR
                        + " FROM " + tableTemp
                        + " ORDER BY "
                                + TablaTecnicas.COL_ID_SESION + ","
                                + TablaTecnicas.COL_FECHA + " DESC"
                );
                break;

            case 2:
            case 3:
                tableTemp = TABLA_TIPO_TECNICAS_V1_OLD;
                db.execSQL("DROP TABLE IF EXISTS " + tableTemp);
                db.execSQL("ALTER TABLE " + TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS + " RENAME TO " + tableTemp);
                db.execSQL(TablaTiposDeTecnicas.sqlCreateTableTiposTecnicas);
                db.execSQL("INSERT INTO "
                                + TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS
                                + " ("
                                    + TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA + ","
                                    + TablaTiposDeTecnicas.COL_ID_PARENT + ","
                                    + TablaTiposDeTecnicas.COL_LABELS_COLS + ","
                                    + TablaTiposDeTecnicas.COL_LABELS_ROWS + ","
                                    + TablaTiposDeTecnicas.COL_MAX + ","
                                    + TablaTiposDeTecnicas.COL_MIN + ","
                                    + TablaTiposDeTecnicas.COL_NUM_COLS + ","
                                    + TablaTiposDeTecnicas.COL_NUM_ROWS + ","
                                    + TablaTiposDeTecnicas.COL_TITLE + ","
                                    + TablaTiposDeTecnicas.COL_VIEWTYPE
                                + ")"
                                + " SELECT "
                                    + TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA + ","
                                    + TablaTiposDeTecnicas.COL_ID_PARENT + ","
                                    + TablaTiposDeTecnicas.COL_LABELS_COLS + ","
                                    + TablaTiposDeTecnicas.COL_LABELS_ROWS + ","
                                    + TablaTiposDeTecnicas.COL_MAX + ","
                                    + TablaTiposDeTecnicas.COL_MIN + ","
                                    + TablaTiposDeTecnicas.COL_NUM_COLS + ","
                                    + TablaTiposDeTecnicas.COL_NUM_ROWS + ","
                                    + TablaTiposDeTecnicas.COL_TITLE + ","
                                    + TablaTiposDeTecnicas.COL_VIEWTYPE
                                + " FROM " + tableTemp
                                + " ORDER BY "
                                + TablaTiposDeTecnicas.COL_ID_TIPO_TECNICA + " ASC"
                );
                break;
        }
    }


    public static boolean importDb(String fileIn, String databasePath){       //TODO: hacer comprobaciones!!
        byte[] buffer;
        int length;
        InputStream input;
        OutputStream output;
        File fileOut;

        try {
            input   = new FileInputStream(fileIn);
            output  = new FileOutputStream(databasePath);
            buffer  = new byte[1024];

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();

            Log.d(DatabaseHelper.class.getSimpleName(), "Base de datos importada correctamente");

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean exportDb(String fileOut){
        byte[] buffer;
        int length;
        InputStream input;
        OutputStream output;

        try {
            input   = new FileInputStream("/data/data/dan.android.quirogest/databases/" + DATABASE_NAME);   //TODO: ver por qué no funciona getDatabasePath()
            output  = new FileOutputStream(fileOut);
            buffer  = new byte[1024];

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();

            Log.d(DatabaseHelper.class.getSimpleName(), "Base de datos exportada correctamente");

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    /** transforma fechas de SQLite al formato especificado */
    public static String parseSQLiteToDateformat(String date, SimpleDateFormat format) {
        String d = null;

        if (date!=null && format!=null) {
            d = format.format(parseSQLiteDate(date));
        }

        return d;
    }


    public static String parseToSQLite(Date d) {
        return new SimpleDateFormat(SQLITE_DATE_FORMAT).format(d);
    }

    public static String parseToSQLite(int year, int month, int day) {
        Calendar c;

        c   = Calendar.getInstance();
        c.set(year, month, day);

        return parseToSQLite(c.getTime());
    }


    public static Date parseSQLiteDate(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat(SQLITE_DATE_FORMAT).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }


    public long count(String table, String selection, String[] selectionArgs){
        long c;
        c = DatabaseUtils.queryNumEntries(getReadableDatabase(), table, selection, selectionArgs);
        return c;
    }



    private void insertTiposEtiquetas(SQLiteDatabase db) {
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (1,'I','FFFF00')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (2,'D','FFFF00')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (3,'UL','FFFF00')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (4,'BL','FFFF00')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (5,'Thrust','004C99')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (6,'Ballesteo Armónico','004C99')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (7,'Miotensivo','004C99')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (8,'Body Drop','004C99')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (9,'Tug Leg','004C99')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (10,'Pasivo','009900')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (11,'Concéntrico','009900')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (12,'Excéntrico','009900')");
        db.execSQL("INSERT INTO tiposDeEtiquetas VALUES (13,'Postisométrico','009900')");
    }

    private void insertTiposTecnicas(SQLiteDatabase db) {
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1, -1, null, null, null, null, null, '', '', 'Diganóstico')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2, -1, null, null, null, null, null, '', '', 'TGO')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3, -1, null, null, null, null, null, '', '', 'Manipulación')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4, -1, null, null, null, null, null, '', '', 'Quiromasaje')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5, -1, null, null, null, null, null, '', '', 'Sotai')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (6, -1, null, null, null, null, null, '', '', 'Acupuntura')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (7, -1, null, null, null, null, null, '', '', 'Posiciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (8, -1, null, null, null, null, null, '', '', 'Respiraciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (9, -1, null, null, null, null, null, '', '', 'Secciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11, 1, null, null, null, null, null, '', '', 'Pruebas osteopáticas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (12, 1, null, null, null, null, null, '', '', 'Pruebas clínicas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (21, 2, null, null, null, null, null, '', '', 'PG')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (22, 2, null, null, null, null, null, '', '', 'Estiramientos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (23, 2, null, null, null, null, null, '', '', 'Movilizaciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (24, 2, null, null, null, null, null, '', '', 'Masaje Miofascial')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (31, 3, null, null, null, null, null, '', '', 'Corrección articular MMS')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (32, 3, null, null, null, null, null, '', '', 'Corrección articular MMI')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (33, 3, null, null, null, null, null, '', '', 'Corrección articular Cervical')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (34, 3, null, null, null, null, null, '', '', 'Corrección articular Dorsal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (35, 3, null, null, null, null, null, '', '', 'Corrección articular Tórax')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (36, 3, null, null, null, null, null, '', '', 'Corrección articular Lumbar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (37, 3, null, null, null, null, null, '', '', 'Corrección articular Pelvis')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (41, 4, null, null, null, null, null, '', '', 'Tratamientos ')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (42, 4, null, null, null, null, null, '', '', 'Maniobras')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (51, 5, null, null, null, null, null, '', '', 'Rotaciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (52, 5, null, null, null, null, null, '', '', 'Ejercicios')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (61, 6, null, null, null, null, null, '', '', 'V40')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (62, 6, null, null, null, null, null, '', '', 'VB20')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (63, 6, null, null, null, null, null, '', '', 'IG11')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (64, 6, null, null, null, null, null, '', '', 'IG4')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (65, 6, null, null, null, null, null, '', '', 'Ojos del deltoides')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (66, 6, null, null, null, null, null, '', '', 'E34')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (67, 6, null, null, null, null, null, '', '', 'C7')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (68, 6, null, null, null, null, null, '', '', 'Alegría de vivir')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (81, 8, null, null, null, null, null, '', '', 'Resp. Diafragmáticas 1:1')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (82, 8, null, null, null, null, null, '', '', 'Resp. Diafragmáticas 1:2')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (91, 9, null, null, 4, null, null, '', '', 'Diganóstico')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (92, 9, null, null, 4, null, null, '', '', 'TGO')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (93, 9, null, null, 4, null, null, '', '', 'Manipulación')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (94, 9, null, null, 4, null, null, '', '', 'Técnicas específicas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (95, 9, null, null, null, null, null, '', '', 'Subsecciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (221, 22, null, null, null, null, null, '', '', 'Estiramientos MMS')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (222, 22, null, null, null, null, null, '', '', 'Estiramientos MMI')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (223, 22, null, null, null, null, null, '', '', 'Estiramientos Cervical')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (224, 22, null, null, null, null, null, '', '', 'Estiramientos Tórax')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (225, 22, null, null, null, null, null, '', '', 'Estiramientos Pélvis')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (231, 23, null, null, null, null, null, '', '', 'Movilizaciones Dorsal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (232, 23, null, null, null, null, null, '', '', 'Movilizaciones Lumbo-pélvicas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (233, 23, null, null, null, null, null, '', '', 'Movilizaciónes Lumbar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (234, 23, null, null, null, null, null, '', '', 'Movilizaciónes Iliaco')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (235, 23, null, null, null, null, null, '', '', 'Movilizaciónes CF')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (241, 24, null, null, 5, null, null, '', '', 'M. Miofascial general (espalda)')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (242, 24, null, null, 5, null, null, '', '', 'M. Miofascial glúteo')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (311, 31, null, null, 5, null, null, '', '', 'Manipulación Muñeca')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (321, 32, null, null, 5, null, null, '', '', 'Manipulación Tibioastragalina')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (341, 34, null, null, 5, null, null, '', '', 'Manipulación en DOG')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (351, 35, null, null, 5, null, null, '', '', 'Manipulación de 1ª costilla')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (361, 36, null, null, 5, null, null, '', '', 'Roll lumbar en rotación')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (362, 36, null, null, 5, null, null, '', '', 'Roll lumbar en apertura')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (421, 42, null, null, null, null, null, '', '', 'Preparatorias')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (422, 42, null, null, null, null, null, '', '', 'Descontracturantes')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (423, 42, null, null, null, null, null, '', '', 'Estimulantes')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (424, 42, null, null, null, null, null, '', '', 'Relajantes')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (511, 51, null, null, 5, null, null, '', '', 'Rotaciones Tobillo')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (512, 51, null, null, 5, null, null, '', '', 'Rotaciones Dedos Pie')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (513, 51, null, null, 5, null, null, '', '', 'Rotaciones Muñeca')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (514, 51, null, null, 5, null, null, '', '', 'Rotaciones Dedos Mano')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (951, 95, null, null, 4, null, null, '', '', 'BP')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (952, 95, null, null, 4, null, null, '', '', 'SD')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (953, 95, null, null, 4, null, null, '', '', 'DS')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (954, 95, null, null, 4, null, null, '', '', 'DP')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (955, 95, null, null, 4, null, null, '', '', 'DL')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1101, 11, 1, 5, 2, -3, 3, 'Valor (+ inclinado a la derecha; - inclinado a la izquierda)', 'A. Mastoides,Acrómion,Escápulas,C. Iliaca,Trocánter', 'Simetría')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1102, 11, null, null, null, null, null, '', '', 'Movilidad Activa')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1103, 11, null, null, null, null, null, '', '', 'Movilidad Pasiva')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1104, 11, null, null, null, null, null, '', '', 'Presiones axiales')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1105, 11, 2, 6, 1, 0, 1, 'Agudo,Crónico', 'Eritema,Edema,Temperatura,Kibler (skin rolling),Densidad,Dolor', 'Palpación')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1106, 11, 3, 6, 1, 0, 1, 'E,N,F', 'D12-L1,L1-L2,L2-L3,L3-L4,L4-L5,L5-S1', 'T. Mitchell')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1107, 11, 3, 6, 1, 0, 1, 'E,N,F', 'D12-L1,L1-L2,L2-L3,L3-L4,L4-L5,L5-S1', 'T. F/E segmentaria')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1108, 11, 6, 1, 2, 0, 10, 'F,E,Rd,Ri,Ld,Li', 'Limitación movimiento', 'Estrella de Maigne')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1109, 11, 1, 5, 2, -3, 3, 'Valor (+ inclinado a la derecha; - inclinado a la izquierda)', 'C. Iliaca,EIAS,Pubis,Isquión,EIPS', 'Simetría Pélvica')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1110, 11, 2, 1, 1, 0, 1, 'I,D', 'Arrastra', 'T. Arrastre EIPS')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1111, 11, 2, 1, 1, 0, 1, 'I,D', 'Arrastra', 'T. Arrastre Ápex')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1112, 11, 2, 1, 1, 0, 1, 'I,D', 'Arrastra', 'T. Cigüeña')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1113, 11, 2, 2, 1, 0, 1, 'I,D', 'Ascendida,Descendida', 'Rama púbica')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1114, 11, 2, 2, 1, 0, 1, 'I,D', 'No acorta,No alarga', 'T. Downing')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1115, 11, 2, 2, 1, 0, 1, 'I,D', 'Posteriorizado,Anteriorizado', 'Movilidad Iliaca')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1116, 11, 2, 2, 1, 0, 1, 'I,D', 'Nutado,Contranutado', 'T. N/CN activa HB')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1117, 11, 2, 2, 1, 0, 1, 'I,D', 'Nutado,Contranutado', 'T. N/CN activa Ápex')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1118, 11, 2, 2, 1, 0, 1, 'I,D', 'Nutado,Contranutado', 'T. Respiración sacra')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1119, 11, null, null, 3, null, null, '', '', 'Pruebas funcionales de la musculatura')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1201, 12, 1, 1, 1, 0, 1, '', 'Estructurada', 'P. Adams')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1202, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'Extensión Unipodal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1203, 12, 4, 1, 1, 0, 1, 'FLd,FLi,ELd,ELi', 'Positivo', 'P. Cuadrantes')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1204, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'P. Valsalva')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1205, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'Signo del Psoas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1206, 12, 3, 1, 1, 0, 1, '<35,35-70,>70', 'Positivo', 'P. Lasegue')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1207, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'P. Bragard')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1208, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'T. Hoover')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1209, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'P. Cram')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1210, 12, 2, 3, 1, 0, 1, 'I,D', 'Arrastra,MMI Largo,MMI Corto', 'T. Derbolowsky')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1211, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'T. Gaenslen')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1212, 12, 2, 1, 1, 0, 1, 'Compresión,Dispersión', 'Dolor', 'T. Compresión/ Dispersión')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1213, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'T. Provocación cóccix')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1214, 12, 3, 1, 1, 0, 1, 'Alteración,Inestabilidad,Dolor', 'Positivo', 'Exploración de la marcha')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1215, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'T. Patrick')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1216, 12, 2, 1, 1, 0, 1, 'CF,ASI', 'Dolor', 'T. Laguerre')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1217, 12, 2, 1, 1, 0, 1, 'TFL,ASI', 'Dolor', 'T. Nobble')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1218, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'T. Noble')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1219, 12, 2, 2, 1, 0, 1, 'I,D', 'Cuádriceps,Psoas', 'P. Thomas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1220, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'P. Ober')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1221, 12, 1, 1, 1, 0, 1, '', 'Positivo', 'P. Anvil')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (1222, 12, 2, 2, 1, 0, 1, 'I,D', 'Bascula,Lateraliza', 'P. Trendelenburg-Duchenne')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2211, 221, null, null, 5, null, null, '', '', 'Estiramiento Triceps')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2212, 221, null, null, 5, null, null, '', '', 'Estiramiento Biceps')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2213, 221, null, null, 5, null, null, '', '', 'Estiramiento Flexor Antebrazo')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2214, 221, null, null, 5, null, null, '', '', 'Estiramiento Extensor Antebrazo')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2215, 221, null, null, 5, null, null, '', '', 'Estiramiento Supinador')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2216, 221, null, null, 5, null, null, '', '', 'Estiramiento Pronador')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2217, 221, 1, 6, 1, 0, 1, '', 'Biceps,Triceps,Flexor Anteb,Extensor Anteb,Supinador,Pronado ', 'Estiramientos MMS')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2221, 222, null, null, 5, null, null, '', '', 'Estiramiento Gemelos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2222, 222, null, null, 5, null, null, '', '', 'Estiramiento Isquiotibiales')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2223, 222, null, null, 5, null, null, '', '', 'Estiramiento Cuadriceps')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2224, 222, null, null, 5, null, null, '', '', 'Estiramiento Abd')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2225, 222, null, null, 5, null, null, '', '', 'Estiramiento Add')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2226, 222, 1, 7, 1, 0, 1, '', 'Gemelos,Isquiotibiales,Cuádriceps,Gluteo,Piramidal,Add,Abd', 'Estiramientos MMI')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2231, 223, null, null, 5, null, null, '', '', 'Estiramiento Occipital')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2232, 223, null, null, 5, null, null, '', '', 'Estiramiento Trapecio')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2233, 223, null, null, 5, null, null, '', '', 'Estiramiento Angular Omóplato')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2234, 223, null, null, 5, null, null, '', '', 'Estiramiento Escalenos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2235, 223, null, null, 5, null, null, '', '', 'Estiramiento ECM')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2236, 223, null, null, 5, null, null, '', '', 'Estiramientos Especificos Cervicales')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2241, 224, null, null, 5, null, null, '', '', 'Estiramiento Dorsal Alto')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2242, 224, null, null, 5, null, null, '', '', 'Estiramiento Dorsal Bajo')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2243, 224, null, null, 5, null, null, '', '', 'Estiramiento Romboides')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2244, 224, null, null, 5, null, null, '', '', 'Estiramiento Pectoral-Esternón')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2245, 224, null, null, 5, null, null, '', '', 'Estiramiento Pectoral-Clavícula')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2246, 224, null, null, 5, null, null, '', '', 'Estiramiento Intercostales-Serratos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2251, 225, null, null, 5, null, null, '', '', 'Estiramiento Psoas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2252, 225, null, null, 5, null, null, '', '', 'Estiramiento Oblícuos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2253, 225, null, null, 5, null, null, '', '', 'Estiramiento Glúteo')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2254, 225, null, null, 5, null, null, '', '', 'Estiramiento Piramidal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2255, 225, null, null, 5, null, null, '', '', 'Estiramiento C. Lumbar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2256, 225, null, null, 5, null, null, '', '', 'Estiramiento Isquiotibiales')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2257, 225, null, null, 5, null, null, '', '', 'Estiramiento Rotadores Internos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2258, 225, null, null, 5, null, null, '', '', 'Estiramiento Rotadores Externos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2259, 225, null, null, 5, null, null, '', '', 'Estiramiento Recto Abdominal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2260, 225, 1, 6, 1, 0, 1, '', 'C. Lumbar,Psoas,Isquiotibiales,Gluteo,Piramidal,Oblicuos', 'Estiramientos Zona Lumbar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2261, 225, null, null, 5, null, null, '', '', 'Estiramiento Pilares Diafrágma')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2311, 231, null, null, 5, null, null, '', '', 'Movilización Escapular')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2321, 232, null, null, 5, null, null, '', '', 'Movilización Carillas Articulares')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2322, 232, null, null, 5, null, null, '', '', 'Tracción Axial')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2323, 232, null, null, 5, null, null, '', '', 'Circunducción Coxofemoral')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2324, 232, null, null, 5, null, null, '', '', 'T. Surcos/Sulcos Sacroiliacos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2325, 233, null, null, 5, null, null, '', '', 'Presiones Contrariadas Lumbar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2326, 233, null, null, 5, null, null, '', '', 'Movilización Segmentaria F/E')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2327, 233, null, null, 5, null, null, '', '', 'Movilización Segmentaria Ld/Li')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2328, 233, null, null, 5, null, null, '', '', 'Rotaciones Contrariadas Lumbar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2329, 233, null, null, 5, null, null, '', '', 'Técnica de Rolling')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2330, 234, null, null, 5, null, null, '', '', 'T. Volante')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2331, 234, null, null, 5, null, null, '', '', 'Movilización Iliaco')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2332, 234, null, null, 5, null, null, '', '', 'Anteriorización Iliaco')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2333, 235, null, null, 5, null, null, '', '', 'F/E')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2334, 235, null, null, 5, null, null, '', '', 'Ri/Re')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (2335, 235, null, null, 5, null, null, '', '', 'Abd/Add')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3701, 37, null, null, 5, null, null, '', '', 'Corrección IA')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3702, 37, null, null, 5, null, null, '', '', 'Corrección IP')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3703, 37, null, null, 5, null, null, '', '', 'Corrección Up-Slip')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3704, 37, null, null, 5, null, null, '', '', 'Corrección Down-Slip')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3705, 37, null, null, 5, null, null, '', '', 'Correción SP (estiramiento psoas-add)')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3706, 37, null, null, 5, null, null, '', '', 'Escopetazo púbico')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3707, 37, null, null, 5, null, null, '', '', 'Corrección sacro rebote')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3708, 37, null, null, 5, null, null, '', '', 'T. Llave')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3709, 37, null, null, 5, null, null, '', '', 'Corrección sacro cizallamiento')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3710, 37, null, null, 5, null, null, '', '', 'Corrección sacro depresión')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3711, 37, null, null, 5, null, null, '', '', 'Corrección cóccix anterior (v. interna)')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3712, 37, null, null, 5, null, null, '', '', 'Contrapalanca CF F/E')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3713, 37, null, null, 5, null, null, '', '', 'Contrapalanca CF Add/Abd')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (3715, 37, null, null, 5, null, null, '', '', 'Corrección CF rodillo alta densidad')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4101, 41, null, null, 5, null, null, '', '', 'Tratamiento Espinal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4102, 41, null, null, 5, null, null, '', '', 'Tratamiento Cervical')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4103, 41, null, null, 5, null, null, '', '', 'Tratamiento Dorsal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4104, 41, null, null, 5, null, null, '', '', 'Tratamiento Lumbar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4105, 41, null, null, 5, null, null, '', '', 'Tratamiento Hombro')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4106, 41, null, null, 5, null, null, '', '', 'Tratamiento Brazo')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4107, 41, null, null, 5, null, null, '', '', 'Tratamiento Mano')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4108, 41, null, null, 5, null, null, '', '', 'Tratamiento Torácico')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4109, 41, null, null, 5, null, null, '', '', 'Tratamiento Abdominal')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4110, 41, null, null, 5, null, null, '', '', 'Tratamiento Pelvis')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4111, 41, null, null, 5, null, null, '', '', 'Tratamiento Piernas')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4112, 41, null, null, 5, null, null, '', '', 'Tratamiento Ciática')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4113, 41, null, null, 5, null, null, '', '', 'Tratamiento Pie')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4114, 41, null, null, 5, null, null, '', '', 'Tratamiento Facial')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4115, 41, null, null, 5, null, null, '', '', 'Tratamiento Espinal-Facial')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4116, 41, null, null, 5, null, null, '', '', 'Tratamiento General')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4211, 421, null, null, 5, null, null, '', '', 'Pms')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4212, 421, null, null, 5, null, null, '', '', 'VV')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4213, 421, null, null, 5, null, null, '', '', 'AD')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4221, 422, null, null, 5, null, null, '', '', 'ADP')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4222, 422, null, null, 5, null, null, '', '', 'ANS')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4223, 422, null, null, 5, null, null, '', '', 'ANC')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4224, 422, null, null, 5, null, null, '', '', 'APP')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4231, 423, null, null, 5, null, null, '', '', 'Cachete Cubital')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4232, 423, null, null, 5, null, null, '', '', 'Cachete Dorso-palmar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4233, 423, null, null, 5, null, null, '', '', 'Cachete Compresivo Giratorio')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4234, 423, null, null, 5, null, null, '', '', 'Palmada Cóncava')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4235, 423, null, null, 5, null, null, '', '', 'Palmada Digital')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4241, 424, null, null, 5, null, null, '', '', 'Palmada Digital Fricción')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4242, 424, null, null, 5, null, null, '', '', 'Roces Digitales')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4243, 424, null, null, 5, null, null, '', '', 'Roces Digitales Circunflejos')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4244, 424, null, null, 5, null, null, '', '', 'Rodamiento Palmar')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4245, 424, null, null, 5, null, null, '', '', 'Vibraciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4246, 424, null, null, 5, null, null, '', '', 'Pellizcos ')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (4247, 424, null, null, 5, null, null, '', '', 'Fricciones')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5201, 52, null, null, 5, null, null, '', '', 'Básico 1')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5202, 52, null, null, 5, null, null, '', '', 'Básico 1.1')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5203, 52, null, null, 5, null, null, '', '', 'Básico 2')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5204, 52, null, null, 5, null, null, '', '', 'Básico 2.2')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5205, 52, null, null, 5, null, null, '', '', 'Básico 3')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5206, 52, null, null, 5, null, null, '', '', 'Básico 4')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5207, 52, null, null, 5, null, null, '', '', 'Básico 5')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5208, 52, null, null, 5, null, null, '', '', 'Básico 6')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5209, 52, null, null, 5, null, null, '', '', 'Básico 7')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5210, 52, null, null, 5, null, null, '', '', 'Básico 8')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5211, 52, null, null, 5, null, null, '', '', 'Básico 9')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5212, 52, null, null, 5, null, null, '', '', 'Básico 10A')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5213, 52, null, null, 5, null, null, '', '', 'Básico 10B')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5214, 52, null, null, 5, null, null, '', '', 'Básico 10C')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5215, 52, null, null, 5, null, null, '', '', 'Básico 11')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (5216, 52, 1, 1, 2, 0, 10, '', 'Distancia a la pared (NºDedos)', 'Prueba cifosis (sotai)')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11021, 1102, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'F,E', 'Movilidad Activa F/E')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11022, 1102, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Ld,Li', 'Movilidad Activa Ld/Li')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11023, 1102, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Rd,Ri', 'Movilidad Activa Rotación')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11024, 1102, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Abd,Add', 'Movilidad Activa Abd/Add')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11025, 1102, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Ri,Re', 'Movilidad Activa Ri/Re')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11031, 1103, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'F,E', 'Movilidad Pasiva F/E')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11032, 1103, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Ld,Li', 'Movilidad Pasiva Ld/Li')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11033, 1103, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Rd,Ri', 'Movilidad Pasiva Rotación')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11034, 1103, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Abd,Add', 'Movilidad Pasiva Abd/Add')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11035, 1103, 3, 2, 1, 0, 1, 'Limitado,Dolor local,Dolor referido', 'Ri,Re', 'Movilidad Pasiva Ri/Re')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11041, 1104, 3, 7, 1, 0, 1, 'I,E,D', 'C1,C2,C3,C4,C5,C6,C7', 'Presiones Axiales Cervicales')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11042, 1104, 5, 12, 1, 0, 1, 'TI,CI,E,CD,TD', 'D1,D2,D3,D4,D5,D6,D7,D8,D9,D10,D11,D12', 'Presiones Axiales Dorsales')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (11043, 1104, 5, 6, 1, 0, 1, 'TI,CI,E,CD,TD', 'D12,L1,L2,L3,L4,L5', 'Presiones Axiales Lumbares')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (41112, 4111, null, null, 5, null, null, '', '', 'Tratamiento Piernas General')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (41113, 4111, null, null, 5, null, null, '', '', 'Tratamiento Piernas Específico')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (41131, 4113, null, null, 5, null, null, '', '', 'Tratamiento Pie Terapéutico')");
        db.execSQL("INSERT INTO tiposDeTecnicas VALUES (41132, 4113, null, null, 5, null, null, '', '', 'Tratamiento Pie Relajante')");
    }


    private void insertContactos(SQLiteDatabase db) {
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (1,'Alejandro','Arce','','609 14 19 39','','','','','Alemania','1985-01-01','Investigador Científico','Fusión L5-S1','','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (2,'Diana','Barroso','Guardia','660 27 72 98','','','','San Sebastian de los Reyes','Madrid','1980-05-12','Técnico de Laboratorio','Supraespinoso D roto parcialemente en accidente de tráfico','','Constitución: Pícnico')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (3,'Ana','Carbajo','Amigo','669 60 36 37','91 734 39 06','','28034','Madrid','Madrid','1957-01-01','Psicologa','','','Mira a su paciente de frente; pasa muchas horas sentada.')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (4,'Mª Jesús','de la Rosa','Prieto','655 14 71 15','91 734 62 28','C/ Costa Brava 7, Portal 1-1ºB','28034','Madrid','Madrid','1944-01-01','Ama de Casa','Varices y CV mal (estenosis de canal)','','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (5,'Chus','Jaén','Ortega','649 91 86 46','','C/ Sabadell','28034','Madrid','Madrid','1985-10-13','Bailarina y profesora de bailes y pilates','Luxación frecuente de astrágalo D','Alergia al esparadrapo','Tolera bien el VNM')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (6,'Daniel','López','López','620 57 99 69','','C/ Islas Antípodas 37, Bajo A','28034','Madrid','Madrid','1981-8-21','Bioinformático','','','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (7,'Julia','Ortega','','650 20 12 07','','C/ Sabadell','28034','Madrid','Madrid','1954-02-11','Camarera y Dependienta del hogar','Dos hernias discales y artrosis en lumbares; artrosis en cervicales (diagnosticado por el médico); artrosis de rodilla; y síndrome del túnel carpiano.','','En los ratos libres cuida de su nieto de 2 años.')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (8,'Noelia','Peña','del Barrio','627 08 03 96','','','','Móstoles','Madrid','1985-10-17','Informática','Fractura de tibia hace 18 años. Hipotiroidismo bajo medicación.','','Constitución: Pícnico')");
            db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (9,'Violeta',' Martín ','Gutierrez','656 42 17 85','','','','Las Gabias','Granada','1980-01-01','Camarera/Directora de discoteca-salón de baile','Prolactinemia con galactorrea y crecimiento anómalo de hipófisis desde hace años. Actualmente sin medicación.','','Postura extremadamente cifótica adquiridad a edad temprana. Familia paterna con la misma postura. Refiere que es debida a rasgos de personalidad (inseguridad). Ha sufrido agorafobia, actualmente superada.')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (10,'Isabel','Espinosa','Salinas','651 38 22 03','','C/Villava 12D, 2ºD','28050','Las Tablas','Madrid','1982-01-01','Investigadora científica','Se le ha salido los otolitos hace 5 o 6 años.','Nada','Postura ligeramente cifótica')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (11,'Cyrielle','Le Marre','','625 21 00 65','','C/Isabel Rosillo, 12, 1ºA','28100','Alcobendas','Madrid','1987-02-11','Docente infantil-primaria','Tiene asma a causa de la alergia.','Animales, sol, polen, perfumes','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (12,'Yolanda','Leo','del Puerto','657 26 33 77','','','28100','Alcobendas','Madrid','1978-01-01','Técnico de Laboratorio',' Protrusión L5-S1 y principios de otras un poco mas arriba. Artrosis de ATM; le han recetado férula de descarga que recoge dentro de dos días. ','Polen','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (13,'Juan José ','Quereda','Torres','686 46 98 66','','C/ Sonrisa 12, Blq 8, 2B','28100','Alcobendas','Madrid','1983-01-01','Investigador científico','Dermografismo. Asma. Colón irritable.','Acaros','Postura cifótica.')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (14,'Ignacio ','Foche','Pérez','650 78 40 45','','','','Madrid','Madrid','1984-01-01','Investigador científico; bioinformático','Comunicación intrauricular detectada a los 14 años y operada y ahora está todo bien.','Polen pero no diagnosticado','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (15,'Jose Luis','Nieto','Torres','620 05 04 31','','C/ Fermín Caballero 57, 6ªA','28034','Madrid','Madrid','1984-12-19','Investigador científico','Sin amigdalas. ','Nada','Postura ligeramente cifótica')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (16,'Estefanía','Peralta','Vergara','654 69 88 16','','C/ San Restituto 51, 3K','28039*','Madrid','Madrid','1983-9-27','Informática','','','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (17,'Manolo ','Arellano ','López','677 72 12 61','','C/Enrique Granados','','Móstoles','Madrid','1986-7-17','Auxiliar de veterinaria','Nada','Nada','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (18,'Arno','Pichon','','665 87 00 37','','Av, de la Vega, 6','28100','Alcobendas','Madrid','1972-6-23','Profesor','Nada','Nada','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (19,'Victoria','Puertas','Dueñas','   659 80 94 66','','Fantasía','28100','Alcobendas','Madrid','1957-7-26','Peluquera','Nada','Nada','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (20,'Jose','Dopazo','','665 20 47 38','','C/Isabel Rosillo, 12, 1ºA','28100','Alcobendas','Madrid','1982-6-24','Técnico de Laboratorio','Nada','Nada','Costitución atlética endomorfo.')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (21,'Beatríz','Bodega','Calle','650 30 06 42','','Plaza del Roble, 2, 4º4','28100','Alcobendas','Madrid','1981-11-3','Enfermera matrona','Hipotiroidismo en tratamiento','Polen y acaros','Parto hace 13 meses')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (22,'Anne','Archambault','','665 87 00 37','','Av, de la Vega, 6','28100','Alcobendas','Madrid','1976-3-9','Profesora','Nada','Nada','CUIDADO: NO PONER VENDAJE!!! produce dermatitis. Constitución atlética- endomorfo. Ha ido a otros terapeutas en Francia (quiropráctico u osteópata).')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (23,'Ludmila','Frangu','','609 29 03 52','','Antonio de Nebrija, 5, Bloque 2 3ºH','28100','Alcobendas','Madrid','1981-6-9','Asesor fiscal','Nada','Nada','')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (24,'Cecilia','Schwank','','','','','','','Ginebra','1986-12-22','Contable','Nada','Polen y acaros','Hombro dislocado varios eventos. Para contactar preguntar a Vir.')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (25,'Ana','Díaz','Blázquez','636 59 81 70','','','28922','Alcorcón','Madrid','1972-7-18','Técnico laboratorio, Biologa','Cancer mama, mastectomía y dos nódulos; Bajo tratamiento antihormonal. Hace tres años','Nada','Escoliosis dorsoventral, dice que tiene un poco de fusión en dorsal alta. Dolores articulares en manos y pies. Espolón calcaneo. Tras quimio. ')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (26,'Gadea','Rico','Pérez','699 60 90 69','','','28760','Tres cantos ','Madrid','1985-12-10','Investigadora científica','Nada','Polen','Posible hipercifosis dorsal')");
        db.execSQL("INSERT INTO pacientes (_id,nombre_pacientes,apellido1_pacientes,apellido2_pacientes,movil_pacientes,fijo_pacientes,direccion_pacientes,cp_pacientes,localidad_pacientes,provincia_pacientes,fechaNacimiento_pacientes,profesion_pacientes,enfermedades_pacientes,alergias_pacientes,observaciones_pacientes) VALUES (27,'Pedro','Jaén','Campanario','619 52 18 04','','C/Sabadell','28034','Madrid',' Madrid','1953-8-4','Administrativo','Protesis de cadera BL. Recambio cadera D. Coxartrosis. Pequeña hernia discal entre L4-L5','Nada','Ectomorfo, obesidad. Posible insuficiencia respiratoria. Fumador')");

    }
}
