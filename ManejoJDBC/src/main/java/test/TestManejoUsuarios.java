
package test;

import datos.*;
import domain.PersonaDTO;
import domain.UsuarioDTO;
import java.sql.*;
import java.util.List;

public class TestManejoUsuarios {
    
    public static void main(String[] args) {
      
        Connection conexion =  null;
        
        try {
            conexion = Conexion.getConnection();
            
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }
            UsuarioDao usuarioDao = new UsuarioDaoJDBC(conexion);
            
            List<UsuarioDTO> usuarios = usuarioDao.seleccionar();
                
            for(UsuarioDTO usuario: usuarios){
                System.out.println("Usuario DTO= " + usuario);
            }
            
            conexion.commit();
            System.out.println("Se realizó el commit de la transacción");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entra al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
      
    }
   
}
