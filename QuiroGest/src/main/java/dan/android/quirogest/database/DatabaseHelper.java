package dan.android.quirogest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dan.android.quirogest.tecnicas.TecnicasAdapter;

/**
 * Created by dan on 25/08/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME       = "QuiroGestDB";
    public static final String SQLITE_DATE_FORMAT   = "yyyy-MM-dd";  // Usado por SimpleDateFormat. <br>Debe tener el mismo formato que CURRENT_DATE de SQLite
    private static final int DATABASE_VERSION       = 1;


    public DatabaseHelper(Context contexto){
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(this.getClass().getSimpleName(), "Creando nueva base de datos");
        Log.i(this.getClass().getSimpleName(), TablaContactos.sqlCreateTableClientes);
        Log.i(this.getClass().getSimpleName(), TablaMotivos.sqlCreateTableMotivos);
        Log.i(this.getClass().getSimpleName(), TablaSesiones.sqlCreateTableSesiones);
        Log.i(this.getClass().getSimpleName(), TablaTecnicas.sqlCreateTableTecnicas);
        Log.i(this.getClass().getSimpleName(), TablaTiposDeTecnicas.sqlCreateTableTiposTecnicas);
        Log.i(this.getClass().getSimpleName(), TablaEtiquetas.sqlCreateTableEtiquetas);
        Log.i(this.getClass().getSimpleName(), TablaTiposDeEtiquetas.sqlCreateTableEtiquetas);


/*        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaContactos.TABLA_CONTACTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaMotivos.TABLA_MOTIVOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaSesiones.TABLA_SESIONES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaTecnicas.TABLA_TECNICAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaTiposDeTecnicas.TABLA_TIPOS_TECNICAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaEtiquetas.TABLA_ETIQUETAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaTiposDeEtiquetas.TABLA_TIPOS_ETIQUETAS);
*/

        sqLiteDatabase.beginTransaction();

        sqLiteDatabase.execSQL(TablaContactos.sqlCreateTableClientes);
        sqLiteDatabase.execSQL(TablaMotivos.sqlCreateTableMotivos);
        sqLiteDatabase.execSQL(TablaSesiones.sqlCreateTableSesiones);
        sqLiteDatabase.execSQL(TablaTecnicas.sqlCreateTableTecnicas);
        sqLiteDatabase.execSQL(TablaTiposDeTecnicas.sqlCreateTableTiposTecnicas);
        sqLiteDatabase.execSQL(TablaEtiquetas.sqlCreateTableEtiquetas);
        sqLiteDatabase.execSQL(TablaTiposDeEtiquetas.sqlCreateTableEtiquetas);

/*        //sqLiteDatabase.execSQL("amos algunos valores de ejemplo
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1,null,null,null,4,null,null,'','','Diagnóstico')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2,null,null,null,4,null,null,'','','TGO')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (3,null,null,null,4,null,null,'','','Manipulación')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4,null,null,null,4,null,null,'','','Quiromasaje')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5,null,null,null,null,null,null,'','','Sotai')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (6,null,null,null,4,null,null,'','','Acupuntura')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (7,null,null,null,null,null,null,'','','Posiciones')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (8,null,null,null,null,null,null,'','','Respiraciones')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (11,1,null,null,null,null,null,'','','Pruebas osteopáticas')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (12,1,null,null,null,null,null,'','','Pruebas clínicas')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (21,2,null,null,null,null,null,'','','PG')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (22,2,null,null,null,null,null,'','','Estiramientos')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (23,2,null,null,null,null,null,'','','Movilizaciones')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (24,2,null,null,null,null,null,'','','Masaje Miofascial')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (31,3,null,null,null,null,null,'','','Manipulaciones MMS')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (32,3,null,null,null,null,null,'','','Manipulaciones MMI')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (33,3,null,null,null,null,null,'','','Manipulaciones Cervical')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (34,3,null,null,null,null,null,'','','Manipulaciones Dorsal')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (35,3,null,null,null,null,null,'','','Manipulaciones Lumbar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (36,3,null,null,null,null,null,'','','Manipulaciones Torácico')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (41,4,null,null,null,null,null,'','','Tratamientos ')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (42,4,null,null,null,null,null,'','','Maniobras')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (51,5,null,null,null,null,null,'','','Rotaciones')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (52,5,null,null,null,null,null,'','','Ejercicios')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (61,6,null,null,null,null,null,'','','V40')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (62,6,null,null,null,null,null,'','','VB20')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (63,6,null,null,null,null,null,'','','IG11')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (64,6,null,null,null,null,null,'','','IG4')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (65,6,null,null,null,null,null,'','','Ojos del deltoides')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (66,6,null,null,null,null,null,'','','E34')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (67,6,null,null,null,null,null,'','','C7')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (68,6,null,null,null,null,null,'','','Alegría de vivir')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (71,7,null,null,4,null,null,'','','BP')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (72,7,null,null,4,null,null,'','','SD')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (73,7,null,null,4,null,null,'','','DS')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (74,7,null,null,4,null,null,'','','DP')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (75,7,null,null,4,null,null,'','','DL')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (81,8,null,null,null,null,null,'','','Resp. Diafragmáticas 1:1')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (82,8,null,null,null,null,null,'','','Resp. Diafragmáticas 1:2')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (111,11,1,5,2,-3,3,'Valor (+ inclinados a la derecha; - inclinados a la izquierda)','A. Mastoides, Acrómion, Escápulas, EI, Trocánter','Simetría')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (112,11,null,null,null,null,null,'','','Movilidad Activa')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (113,11,null,null,null,null,null,'','','Movilidad Pasiva')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (114,11,null,null,null,null,null,'','','Presiones axiales')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (115,11,2,6,1,0,1,'Agudo, Crónico','Eritema, Edema, Temperatura, Kibler (skin rolling), Densidad, Dolor','Palpación')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (116,11,3,6,1,0,1,'E, N, F','D12-L1, L1-L2, L2-L3, L3-L4,L4-L5, L5-S1','T. Mitchell')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (121,12,null,null,3,null,null,'','','P. Adams')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (122,12,2,1,1,0,1,'Sí, No','Positivo','Extensión Unipodal')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (123,12,2,1,1,0,1,'Sí, No','Positivo','P. Cuadrantes')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (124,12,2,1,1,0,1,'Sí, No','Positivo','P. Valsalva')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (125,12,2,1,1,0,1,'Sí, No','Positivo','Signo del Psoas')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (126,12,2,1,1,0,1,'Sí, No','Positivo','P. Lasegue')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (127,12,2,1,1,0,1,'Sí, No','Positivo','P. Bragard')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (128,12,2,1,1,0,1,'Sí, No','Positivo','T. Hoover')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (129,12,2,1,1,0,1,'Sí, No','Positivo','P. Cram')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (221,22,null,null,null,null,null,'','','Estiramientos MMS')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (222,22,null,null,null,null,null,'','','Estiramientos MMI')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (223,22,null,null,null,null,null,'','','Estiramientos Cervical')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (224,22,null,null,null,null,null,'','','Estiramientos Tórax')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (225,22,null,null,null,null,null,'','','Estiramientos Pélvis')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (231,23,null,null,null,null,null,'','','Movilizaciones Dorsal')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (232,23,null,null,null,null,null,'','','Movilizaciones Lumbar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (311,31,null,null,null,null,null,'','','Manipulación Muñeca')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (321,32,null,null,null,null,null,'','','Manipulación Tibioastragalina')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (341,34,null,null,null,null,null,'','','Manipulación en DOG')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (351,35,null,null,null,null,null,'','','Roll lumbar en rotación')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (352,35,null,null,null,null,null,'','','Roll lumbar en apertura')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (361,36,null,null,null,null,null,'','','Manipulación de 1ª costilla')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4101,41,null,null,null,null,null,'','','Tratamiento Espinal')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4102,41,null,null,null,null,null,'','','Tratamiento Cervical')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4103,41,null,null,null,null,null,'','','Tratamiento Dorsal')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4104,41,null,null,null,null,null,'','','Tratamiento Lumbar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4105,41,null,null,null,null,null,'','','Tratamiento Hombro')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4106,41,null,null,null,null,null,'','','Tratamiento Brazo')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4107,41,null,null,null,null,null,'','','Tratamiento Mano')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4108,41,null,null,null,null,null,'','','Tratamiento Torácico')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4109,41,null,null,null,null,null,'','','Tratamiento Abdominal')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4110,41,null,null,null,null,null,'','','Tratamiento Pelvis')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4111,41,null,null,null,null,null,'','','Tratamiento Piernas')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4112,41,null,null,null,null,null,'','','Tratamiento Ciática')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4113,41,null,null,null,null,null,'','','Tratamiento Pie')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4114,41,null,null,null,null,null,'','','Tratamiento Facial')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4115,41,null,null,null,null,null,'','','Tratamiento Espinal-Facial')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4116,41,null,null,null,null,null,'','','Tratamiento General')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (421,42,null,null,null,null,null,'','','Preparatorias')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (422,42,null,null,null,null,null,'','','Descontracturantes')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (423,42,null,null,null,null,null,'','','Estimulantes')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (424,42,null,null,null,null,null,'','','Relajantes')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (511,51,null,null,null,null,null,'','','Rotaciones Tobillo')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (512,51,null,null,null,null,null,'','','Rotaciones Dedos Pie')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (513,51,null,null,null,null,null,'','','Rotaciones Muñeca')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (514,51,null,null,null,null,null,'','','Rotaciones Dedos Mano')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5201,52,null,null,null,null,null,'','','Básico 1')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5202,52,null,null,null,null,null,'','','Básico 1.1')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5203,52,null,null,null,null,null,'','','Básico 2')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5204,52,null,null,null,null,null,'','','Básico 2.2')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5205,52,null,null,null,null,null,'','','Básico 3')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5206,52,null,null,null,null,null,'','','Básico 4')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5207,52,null,null,null,null,null,'','','Básico 5')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5208,52,null,null,null,null,null,'','','Básico 6')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5209,52,null,null,null,null,null,'','','Básico 7')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5210,52,null,null,null,null,null,'','','Básico 8')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5211,52,null,null,null,null,null,'','','Básico 9')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5212,52,null,null,null,null,null,'','','Básico 10A')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5213,52,null,null,null,null,null,'','','Básico 10B')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5214,52,null,null,null,null,null,'','','Básico 10C')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5215,52,null,null,null,null,null,'','','Básico 11')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (5216,52,1,1,2,0,10,'','Distancia a la pared (NºDedos)','Prueba cifosis (sotai)')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1121,112,2,1,1,0,1,'F, E','Limitado','Movilidad Activa F/E')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1122,112,2,1,1,0,1,'Ld, Li','Limitado','Movilidad Activa Ld/Li')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1123,112,2,1,1,0,1,'Rd/Ri','Limitado','Movilidad Activa Rotación')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1131,113,2,1,1,0,1,'F, E','Limitado','Movilidad Pasiva F/E')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1132,113,2,1,1,0,1,'Ld, Li','Limitado','Movilidad Pasiva Ld/Li')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1133,113,2,1,1,0,1,'Rd/Ri','Limitado','Movilidad Pasiva Rotación')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1141,114,3,7,1,0,1,'I, E, D','C1, C2, C3, C4, C5, C6, C7','Presiones Axiales Cervicales')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1142,114,5,12,1,0,1,'TI, CI, E, CD, TD','D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12','Presiones Axiales Dorsales')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (1143,114,5,6,1,0,1,'TI, CI, E, CD, TD','D12, L1. L2, L3, L4, L5','Presiones Axiales Lumbares')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2211,221,null,null,null,null,null,'','','Estiramiento Triceps')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2212,221,null,null,null,null,null,'','','Estiramiento Biceps')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2213,221,null,null,null,null,null,'','','Estiramiento Flexor Antebrazo')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2214,221,null,null,null,null,null,'','','Estiramiento Extensor Antebrazo')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2215,221,null,null,null,null,null,'','','Estiramiento Supinador')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2216,221,null,null,null,null,null,'','','Estiramiento Pronador')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2217,221,1,6,1,0,1,'','Biceps, Triceps, Flexor Anteb, Extensor Anteb, Supinador, Pronado ','Estiramientos MMS')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2221,222,null,null,null,null,null,'','','Estiramiento Gemelos')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2222,222,null,null,null,null,null,'','','Estiramiento Isquiotibiales')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2223,222,null,null,null,null,null,'','','Estiramiento Cuadriceps')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2224,222,null,null,null,null,null,'','','Estiramiento Add')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2225,222,null,null,null,null,null,'','','Estiramiento Add')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2226,222,1,7,1,0,1,'','Gemelos, Isquiotibiales, Cuádriceps, Gluteo, Piramidal, Add, Abd','Estiramientos MMI')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2231,223,null,null,null,null,null,'','','Estiramiento Occipital')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2232,223,null,null,null,null,null,'','','Estiramiento Trapecio')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2233,223,null,null,null,null,null,'','','Estiramiento Angular Omóplato')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2234,223,null,null,null,null,null,'','','Estiramiento Escalenos')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2235,223,null,null,null,null,null,'','','Estiramiento ECM')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2236,223,null,null,null,null,null,'','','Estiramientos Especificos Cervicales')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2241,224,null,null,null,null,null,'','','Estiramiento Dorsal Alto')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2242,224,null,null,null,null,null,'','','Estiramiento Dorsal Bajo')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2243,224,null,null,null,null,null,'','','Estiramiento Romboides')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2244,224,null,null,null,null,null,'','','Estiramiento Pectoral-Esternón')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2245,224,null,null,null,null,null,'','','Estiramiento Pectoral-Clavícula')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2246,224,null,null,null,null,null,'','','Estiramiento Intercostales-Serratos')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2251,225,null,null,null,null,null,'','','Estiramiento Psoas')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2252,225,null,null,null,null,null,'','','Estiramiento Oblícuos')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2253,225,null,null,null,null,null,'','','Estiramiento Glúteo')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2254,225,null,null,null,null,null,'','','Estiramiento Piramidal')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2255,225,null,null,null,null,null,'','','Estiramiento C. Lumbar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2256,225,null,null,null,null,null,'','','Estiramiento Isquiotibiales')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2257,225,1,6,1,0,1,'','C. Lumbar, Psoas, Isquiotibiales, Gluteo, Piramidal, Oblicuos','Estiramientos Zona Lumbar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2311,231,null,null,null,null,null,'','','Movilización Escapular')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2321,232,null,null,null,null,null,'','','Movilización Carillas Articulares')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2322,232,null,null,null,null,null,'','','Tracción Axial')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2323,232,null,null,null,null,null,'','','Circunducción Coxofemoral')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2324,232,null,null,null,null,null,'','','T. Surcos/Sulcos Sacroiliacos')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2325,232,null,null,null,null,null,'','','Presiones Contrariadas Lumbar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2326,232,null,null,null,null,null,'','','Movilización Segmentaria F/E')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2327,232,null,null,null,null,null,'','','Movilización Segmentaria Ld/Li')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2328,232,null,null,null,null,null,'','','Rotaciones Contrariadas Lumbar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (2329,232,null,null,null,null,null,'','','Técnica de Rolling')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (41112,4111,null,null,null,null,null,'','','Tratamiento Piernas General')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (41113,4111,null,null,null,null,null,'','','Tratamiento Piernas Específico')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (41131,4113,null,null,null,null,null,'','','Tratamiento Pie Terapéutico')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (41132,4113,null,null,null,null,null,'','','Tratamiento Pie Relajante')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4211,421,null,null,null,null,null,'','','Pms')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4212,421,null,null,null,null,null,'','','VV')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4213,421,null,null,null,null,null,'','','AD')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4221,422,null,null,null,null,null,'','','ADP')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4222,422,null,null,null,null,null,'','','ANS')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4223,422,null,null,null,null,null,'','','ANC')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4224,422,null,null,null,null,null,'','','APP')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4231,423,null,null,null,null,null,'','','Cachete Cubital')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4232,423,null,null,null,null,null,'','','Cachete Dorso-palmar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4233,423,null,null,null,null,null,'','','Cachete Compresivo Giratorio')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4234,423,null,null,null,null,null,'','','Palmada Cóncava')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4235,423,null,null,null,null,null,'','','Palmada Digital')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4241,424,null,null,null,null,null,'','','Palmada Digital Fricción')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4242,424,null,null,null,null,null,'','','Roces Digitales')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4243,424,null,null,null,null,null,'','','Roces Digitales Circunflejos')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4244,424,null,null,null,null,null,'','','Rodamiento Palmar')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4245,424,null,null,null,null,null,'','','Vibraciones')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4246,424,null,null,null,null,null,'','','Pellizcos ')");
        sqLiteDatabase.execSQL("INSERT INTO tiposDeTecnicas VALUES (4247,424,null,null,null,null,null,'','','Fricciones')");

        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();

*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //TODO!!
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
}
