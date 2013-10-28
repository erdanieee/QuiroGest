package dan.android.quirogest.database;

/**
 * Created by dlopez on 22/10/13.
 */
//TODO: Posiblemente al final no haga falta esta clase y se pueda eliminar...
public class Contacto {
    private Long id                 = null;
    private String nombre           = null;
    private String apellido1        = null;
    private String apellido2        = null;
    private String movil            = null;
    private String fijo             = null;
    private String direccion        = null;
    private String cp               = null;
    private String localidad        = null;
    private String provincia        = null;
    private String fechaNac         = null;
    private String profesion        = null;
    private String enfermedades     = null;
    private String alergias         = null;
    private String observaciones    = null;


    public Contacto (Long contactId,
                     String nombre,
                     String apellido1,
                     String apellido2,
                     String movil,
                     String fijo,
                     String direccion,
                     String cp,
                     String localidad,
                     String provincia,
                     String fechaNac,
                     String profesion,
                     String enfermedades,
                     String alergias,
                     String observaciones){
        this.id             = contactId;
        this.nombre         = nombre;
        this.apellido1      = apellido1;
        this.apellido2      = apellido2;
        this.movil          = movil;
        this.fijo           = fijo;
        this.direccion      = direccion;
        this.cp             = cp;
        this.localidad      = localidad;
        this.provincia      = provincia;
        this.fechaNac       = fechaNac;
        this.profesion      = profesion;
        this.enfermedades   = enfermedades;
        this.alergias       = alergias;
        this.observaciones  = observaciones;
    }


    /** use only for debug purpouses */
    public Contacto (){
        this.id             = Double.doubleToLongBits(Math.random());
        this.nombre         = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.apellido1      = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.apellido2      = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.movil          = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.fijo           = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.direccion      = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.cp             = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.localidad      = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.provincia      = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.fechaNac       = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.profesion      = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.enfermedades   = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.alergias       = Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.observaciones  = Long.toHexString(Double.doubleToLongBits(Math.random()));
    }


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre==null?"":nombre;
    }

    public String getApellido1() {
        return apellido1==null?"":apellido1;
    }

    public String getApellido2() {
        return apellido2==null?"":apellido2;
    }

    public String getMovil() {
        return movil==null?"":movil;
    }

    public String getFijo() {
        return fijo==null?"":fijo;
    }

    public String getDireccion() {
        return direccion==null?"":direccion;
    }

    public String getCp() {
        return cp==null?"":cp;
    }

    public String getLocalidad() {
        return localidad==null?"":localidad;
    }

    public String getProvincia() {
        return provincia==null?"":provincia;
    }

    public String getFechaNac() {
        return fechaNac==null?"":fechaNac;
    }

    public String getProfesion() {
        return profesion==null?"":profesion;
    }

    public String getEnfermedades() {
        return enfermedades==null?"":enfermedades;
    }

    public String getAlergias() {
        return alergias==null?"":alergias;
    }

    public String getObservaciones() {
        return observaciones==null?"":observaciones;
    }
}
