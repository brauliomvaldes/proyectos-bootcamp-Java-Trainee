package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import AccesoADatos.Coneccion;
import entities.User;
import persistence.IEntitiesService;

public class UserService implements IEntitiesService<User> {

	public void findAll(List<User> users) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		users.forEach(u -> {
			System.out.println(" ".repeat(10) + "id: " + u.getUser_id() + " " + "nombre: " + u.getUser_firstname()
					+ " " + u.getUser_lastname() + " IDN/RUT: " + u.getUser_identity_number() + " creado: "
					+ dateFormat.format(u.getUser_created_at()) + " estado: " + (u.isUser_state() ? "Activo" : "Desactivado"));
		});
	}

	public User findById(int id, List<User> users) {
		return users.stream().filter(u -> u.getUser_id() == id).findFirst().orElse(null);
	}

	public User findByRut(String rut, List<User> users) {
		return users.stream().filter(u->u.getUser_identity_number().equals(rut)).findFirst().orElse(null);		
	}
	
	public void findAllContact(User user) {
		List<User> contact_user = user.getUser_contact();
		contact_user.forEach(u -> {
			System.out.println(" ".repeat(10) + "Id : " + u.getUser_id() + " " + "Nombre: " + u.getUser_firstname()
					+ " " + u.getUser_lastname() + " IDN/RUT: " + u.getUser_identity_number());
		});
	}

	public List<User> edit(int id, User userEdited, List<User> users) {
		User user = this.findById(id, users);
		if (user != null) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getUser_id() == id) {
					// ingreso de datos modificados
					users.get(i).setUser_firstname(userEdited.getUser_firstname());
					users.get(i).setUser_lastname(userEdited.getUser_lastname());
					users.get(i).setUser_identity_number(userEdited.getUser_identity_number());
					users.get(i).setUser_email(userEdited.getUser_email());
					users.get(i).setUser_age(userEdited.isUser_age());
					users.get(i).setUser_sex(userEdited.isUser_sex());
					users.get(i).setUser_married(userEdited.isUser_married());
					return users;
				}
			}
		}
		return users;
	}

	public List<User> save(User user, List<User> users) {
		// simula auto_increment
		int idUsuarioNuevo = users.size();
		idUsuarioNuevo++;
		user.setUser_id(idUsuarioNuevo);
		user.setUser_state(true);
		// se registra
		users.add(user);
		return users;
	}

	public List<User> saveUserContact(int userId, List<User> users, User new_user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUser_id() == userId) { // busca el usuario para agregar el contacto
				List<User> user_contact = users.get(i).getUser_contact(); // recupera lista de contactos
				user_contact.add(new_user); // agrega nuevo contacto a la lista
				users.get(i).setUser_contact(user_contact); // actualiza lista de contactos del usuario
				System.out.println(" ".repeat(10) + "se agregó el nuevo contacto");
				break;
			}
		}
		return users;
	}

	public List<User> delete(int id, List<User> users) {
		// busca el cliente
		boolean existe = users.stream().anyMatch(user -> user.getUser_id() == id);
		if (existe) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getUser_id() == id) {
					users.get(i).setUser_state(false);
					System.out.println("Cliente id :" + id + " fue desactivado");
				}
			}
		} else {
			System.out.println("Cliente id :" + id + " no existe");
		}
		return users;
	}

	// prueba conexion a db
	public void listAllUsersDataBase() {
		try {
			Coneccion conn = Coneccion.getInstance();// establece ua coneción a la DB
			Statement querySQL = conn.getConeccion().createStatement();// crea el objeto Statement para consultar la DB
			ResultSet resultado = querySQL.executeQuery("SELECT user_name FROM users");// consulta la DB
			while (resultado.next()) {// recorre los registros hallados
				System.out.print(resultado.getString("user_name"));
				System.out.println();
			}
		} catch (SQLException exceptionSql) {
			exceptionSql.printStackTrace();
		}
	}
}
