package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.User;
import service.UserService;

/*
 * Test unitario para servicio de persistencia de usuarios
 * si el usuario existe retorna el objeto usuario con sus datos
 * si el usuario no existe retorna null
 */
class UserServiceTest {

	UserService userService;
	List<User> users;
	User user;
	
	
	@BeforeEach
	void setUp() {
		userService = new UserService(); // servicio para usuarios
		users = new ArrayList<>(); // crea lista de usuarios
	}
	
	@Test
	void testBuscarUsuarioPorId() {
		user = new User(); // crea nuevo usuario
		user.setUser_id(1);
		user.setUser_state(true); // habilita usuario
		users.add(user); // agrega a la lista de usuarios 
		// espera que devuelva el usuario con las credenciales entregadas
		assertEquals(user,userService.findById(1, users)); // consulta usuario por su id
	}
	
	@Test
	void testBuscarUsuarioPorIdFake() {
		user = new User(); // crea nuevo usuario
		user.setUser_id(1);
		user.setUser_state(true); // habilita usuario
		users.add(user); // agrega a la lista de usuarios 
		// espera que devuelva el usuario con las credenciales entregadas
		assertEquals(null,userService.findById(10, users)); // consulta usuario por su id
	}

}
