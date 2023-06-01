package datos;

import domain.UsuarioDTO;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDao {
    
    public List<UsuarioDTO> seleccionar () throws SQLException;
    public int insertar (UsuarioDTO usuarioDto)throws SQLException;
    public int actualizar (UsuarioDTO usuarioDto)throws SQLException;
    public int eliminar (UsuarioDTO usuarioDto)throws SQLException;
    
}
