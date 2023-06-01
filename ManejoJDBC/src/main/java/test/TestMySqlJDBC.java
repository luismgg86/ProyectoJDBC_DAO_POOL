package test;

import java.sql.*;

public class TestMySqlJDBC {
    public static void main(String[] args) {
        //cadena de coneccion para conectarse a la base de datos mysql, especificamos ip, puerto, base de datos
        //useSSL =  false es para decirle al compilador que no vamos a utilizar la coneccion segura
        //useTimezone =  true para especificar la zona horaria
        //allowPublicRetrieval = true para 
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicRetrieval=true";
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //establecemos la coneccion con la BD
            Connection conexion = DriverManager.getConnection(url,"root","admin");
            //la clase createStatement es una implementacion de la interfaz Statement
            //Es para ejecutar una instruccion sobre nuestra tabla en la BD
            Statement instruccion = conexion.createStatement();
            //variable para guardar la consulta
            var sql = "select id_persona, nombre, apellido, email, telefono from persona";
            //en una variable de tipo ResultSet guardamos el resultado de la ejecucion de la consulta
            ResultSet resultado = instruccion.executeQuery(sql);
            //debido a que la consulta puede regresar varios registros necesitamos un ciclo while para recorrer el contenido de la variable
            //el metodo next nos indica si tenemos otro elemento a iterar, si es así regresa true
            while(resultado.next()){
                //getInt() puede recibir el indice de la columna de la tabla o el nombre de la columna
                System.out.print(" Id persona: " + resultado.getInt("id_persona"));
                System.out.print(" Nombre: " + resultado.getString("nombre"));
                System.out.print(" Apellido: " + resultado.getString("apellido"));
                System.out.print(" Email: " + resultado.getString("email"));
                System.out.print(" Telefono: " + resultado.getString("telefono"));
                System.out.println("");
            }
            resultado.close();
            instruccion.close();
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
    }
}
