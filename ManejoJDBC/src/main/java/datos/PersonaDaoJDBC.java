package datos;
//patron de diseño data access object

import static datos.Conexion.*;
import domain.PersonaDTO;
import java.sql.*;
import java.util.*;
//clase de la capa de datos que recupera la info de la BD y crea objetos de de tipo persona para agregarlos a una lista
public class PersonaDaoJDBC implements PersonaDao {
    //para qeue opcionalmente pueda recibir un objeto Connection desde afuera de clase
    private Connection conexionTransaccional;
    
    private static final String SQL_SELECT = "select id_persona, nombre, apellido, email, telefono from persona";
    private static final String SQL_INSERT = "insert into persona (nombre, apellido, email, telefono) values (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "update persona set nombre = ?, apellido = ?, email = ?, telefono = ? where id_persona = ?";
    private static final String SQL_DELETE = "delete from persona where id_persona = ?";
    
    public PersonaDaoJDBC (){
        
    }
    
    public PersonaDaoJDBC (Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
    }
        
    public List<PersonaDTO> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PersonaDTO persona = null;
        List<PersonaDTO> personas = new ArrayList<>();

        try {
            //objeto conexion
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            //objeto statement 
            //a diferencia de la clase de test en este caso se usó prepareStatement debido a que ya
            //se definio una costante con la sentencia SQL a ejecutar en la BD
            stmt = conn.prepareStatement(SQL_SELECT);
            //resultado de la consulta en la BD
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idPersona = rs.getInt("id_persona");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                //con los valores obtenidos de la BD construimos un objeto de tipo persona con el constructor que tiene todos los atributos
                persona = new PersonaDTO(idPersona, nombre, apellido, email, telefono);
                //insertamos el objeto tipo persona en nuestra lista
                personas.add(persona);
            }

        }
        
        finally {
            try {
                //debido a que se hizo el import static ya no se tiene que especificar el nombre de la clase
                //los metodos close de la clase conexion son estaticos
                //se cierran en orden inverso al que se fueron abriendo
                close(rs);
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn); 
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return personas;
    }
    //devuelve int debido a que indica cuantos registros se han modificado
    public int insertar (PersonaDTO persona) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            //setString debido a que el tipo del atributo nombre es String o varchar en BD
            //el metodo recibe el indice del atributo (en este caso en el indice 1 esta el nombre)
            //y con el atributo getNombre de la clase persona le pasamos el atributo nombre del objeto persona
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            //ejecuta el update en la BD y retorna un entero con los registros afectados
            //el metodo executeUpdate() puede ejecutar sentencias de insert, update o delete
            registros = stmt.executeUpdate();
            
        } 
        finally{
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn); 
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }
    
    public int actualizar (PersonaDTO persona) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1,persona.getNombre());
            stmt.setString(2,persona.getApellido());
            stmt.setString(3,persona.getEmail());
            stmt.setString(4,persona.getTelefono());
            stmt.setInt(5, persona.getIdPersona());
            registros = stmt.executeUpdate();
            
        } finally{
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn); 
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }
    
    public int eliminar (PersonaDTO persona) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, persona.getIdPersona());
            registros = stmt.executeUpdate();
        } finally{
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn); 
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

  
}
