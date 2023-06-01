package test;

import datos.*;
import domain.PersonaDTO;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class TestManejoPersonas {
    public static void main(String[] args) {
        
        //se declara la conexion aquí para poder manejar el rollback en la excepcion en el bloque try - catch
        Connection conexion = null;
        try {
            //creamos un objeto de tipo conexion, ya que ahora se va a enviar como atributo a la clase PersonaDAO
            conexion = Conexion.getConnection();
            //preguntamos si la conexion es auto commit, si es así le asignamos el valor de false para que no haga autocommit de la trasacción
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }
            
            //PersonaDaoJDBC personaJdbc = new PersonaDaoJDBC(conexion);
            //Utilizamos la interface PersonaDao en vez de la clase de PersonaDaoJDBC   
            PersonaDao personaDao = new PersonaDaoJDBC(conexion);
            
            List<PersonaDTO> personas = personaDao.seleccionar();
                
            for(PersonaDTO persona: personas){
                System.out.println("persona DTO= " + persona);
            }
            
            conexion.commit();
            System.out.println("Se ha hecho commit de la transacción");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }
}
