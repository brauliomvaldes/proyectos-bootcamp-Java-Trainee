package models;

import java.util.List;

public class AuthUsuario {
	
	private int id;
	private List<Usuario> usuario;
	private String username;
	private String password;
	
	public AuthUsuario() {}

	public AuthUsuario(int id, List<Usuario> usuario, String username, String password) {
		this.id = id;
		this.usuario = usuario;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
}
