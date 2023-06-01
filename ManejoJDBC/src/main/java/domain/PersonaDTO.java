package domain;
//a este tipo de clase se le conoce como clases de entidad
//ya que cada objeto instanciado se relaciona con un registro de la tabla de la BD
public class PersonaDTO {

    private int idPersona;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    //se empieza a aplicar mas el concepto de sobrecarga de constructores debido a que
    //puede que ejecutemos una sentencia SQL sobre la BD que solo requiera ciertos atributos de la tabla
    //si ese es el caso solo utilizariamos el constructor con los atributos que utilizamos en la sentencia
    public PersonaDTO() {
    }
    //este constructor puede no utilizarse ya que en la tabla de la BD esta columna es autoincrementable
    public PersonaDTO(int idPersona){
        this.idPersona = idPersona;
    }

    public PersonaDTO(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public PersonaDTO(int idPersona, String nombre, String apellido, String email, String telefono) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" + "idPersona=" + idPersona + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", telefono=" + telefono + '}';
    }
    
}
