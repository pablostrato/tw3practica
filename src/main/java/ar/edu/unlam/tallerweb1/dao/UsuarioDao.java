package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del DAO de Usuarios.
public interface UsuarioDao {
	
	Usuario consultarUsuario (Usuario usuario);

	void guardar(Usuario usuario);

	Usuario buscarPor(Long id);

	List<Usuario> buscarPorChar(String string);
}
