package datos;

import static datos.Conexion.*;
import domain.UsuarioDTO;
import java.sql.*;
import java.util.*;

public class UsuarioDaoJDBC implements UsuarioDao{
    
    private Connection conexionTransaccional;
    
    private static final String SQL_SELECT = "select id_usuario, usuario, password from usuario";
    private static final String SQL_INSERT = "insert into usuario (usuario, password) values (?, ?)";
    private static final String SQL_UPDATE = "update usuario set usuario = ?, password = ? where id_usuario = ?";
    private static final String SQL_DELETE = "delete from usuario where id_usuario = ?";
    
    public UsuarioDaoJDBC(){
        
    }
    
    public UsuarioDaoJDBC(Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
    }
    
    public List<UsuarioDTO> seleccionar() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UsuarioDTO usuario = null;
        List<UsuarioDTO> usuarios = new ArrayList<>();
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()){
                int idPersona = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("usuario");
                String password = rs.getString("password");
                
                usuario = new UsuarioDTO(idPersona, nombreUsuario, password);
                
                usuarios.add(usuario);
            }
        } finally {
            try {
                close(rs);
                close(stmt);
                if (this.conexionTransaccional == null){
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return usuarios;
    }
    
    public int insertar (UsuarioDTO usuario) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = conexionTransaccional != null ? conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            registros = stmt.executeUpdate();
        }  finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null){
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
        return registros;
    }
    
    public int actualizar (UsuarioDTO usuario) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = conexionTransaccional != null ? conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1,usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3,usuario.getIdUsuario());
            registros = stmt.executeUpdate();
        }  finally{
            try {
                close(stmt);
                if (this.conexionTransaccional == null){
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }
    
    public int eliminar (UsuarioDTO usuario) throws SQLException{
        Connection conn =  null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = conexionTransaccional != null ? conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1,usuario.getIdUsuario());
            registros = stmt.executeUpdate();
        }  finally{
            try {
                close(stmt);
                if (this.conexionTransaccional == null){
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }
    
    
}
