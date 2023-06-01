package datos;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
//Clase para abrir y cerrar la conexion con la base de datos
public class Conexion {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicRetrieval=true";
    private static final String JDBC_USER =  "root";
    private static final String JDBC_PASSWORD =  "admin";
    
    //Inicializa el objeto de pool de conexiones
    public static DataSource getDataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(JDBC_URL);
        ds.setUsername(JDBC_USER);
        ds.setPassword(JDBC_PASSWORD);
        //definimos el tamaño inicial del pool de conexiones
        ds.setInitialSize(5);
        return ds;
    }
    
    //Devuelve un objeto de tipo Connection
    //ahora este metodo hace uso del método getDataSource definido para inicializar el pool de conexiones
    public static Connection getConnection() throws SQLException{
        //return DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
        //sigue devolviendo un objeto de tipo Connection pero ahora hace uso del pool de conexiones para ahorrar recursos y no estar abriendo y cerrando conexiones a la base de datos
        return getDataSource().getConnection();
    }
    //aplicamos sobrecarga de metodos en el metodo close ya que reciben diferentes argumentas
    //metodo para cerrar el objeto de tipo ResultSet (el que contiene el resultado de la consulta)
    public static void close(ResultSet rs) throws SQLException{
        rs.close();
    }
    //metodo para cerrar el objeto de tipo Statement (el que contiene la consulta qeu se ejecuta sobre la BD)
    public static void close(Statement smtm) throws SQLException{
        smtm.close();
    }
    //Se utiliza mas PreparedStatement porque tiene un mejor performance
    public static void close(PreparedStatement smtm) throws SQLException{
        smtm.close();
    }
    //metodo para cerrar el objeto de tipo Connection (el que establece la conexion con la BD)
    public static void close(Connection conn) throws SQLException{
        conn.close();
    }
}
