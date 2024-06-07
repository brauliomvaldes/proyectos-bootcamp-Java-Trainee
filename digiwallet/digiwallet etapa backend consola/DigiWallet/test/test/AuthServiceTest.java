package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.Role;
import entities.User;
import entities.UserAuth;
import service.AuthService;

/*
 * Test unitario para servicio de autenticación usuario
 */
class AuthServiceTest {

	AuthService authService;
	List<UserAuth> auths;
	User user;
	
	@BeforeEach
	void setUp() {
		authService = new AuthService(); // servicio para autenticación
		auths = new ArrayList<>(); // crea lista de usuario registrados
	}
	
	@Test
	void testUsuarioAutorizadoHabilitado() {
		user = new User(); // crea usuario
		user.setUser_state(true); // habilita usuario
		UserAuth ua = new UserAuth("braulio","123",Role.USER, user); // registra usuario autorizado
		auths.add(ua); // agrega a la lista de usuarios autorizados 
		// espera que devuelva el usuario con las credenciales entregadas
		assertEquals(ua,authService.login(auths, "braulio", "123")); // consulta login
	}

	@Test
	void testUsuarioAutorizadoDehabilitado() {
		user = new User(); // crea usuario
		user.setUser_state(false); // deshabilita usuario
		UserAuth ua = new UserAuth("braulio","123",Role.USER, user); // registra usuario autorizado
		auths.add(ua); // agrega a la lista de usuarios autorizados 
		// espera que devuelva null por usuario deshabilitado
		assertEquals(null,authService.login(auths, "braulio", "123")); // consulta login
	}
	
	@Test
	void testUsuarioNoAutorizado() {
		user = new User(); // crea usuario
		user.setUser_state(false); // habilita usuario
		UserAuth ua = new UserAuth("braulio","123",Role.USER, user); // registra usuario autorizado
		auths.add(ua); // agrega a la lista de usuarios autorizados 
		// espera una respuesta null por no coincidir las credenciales entregadas
		assertEquals(null,authService.login(auths, "braulio", "123456")); // consulta login
	}
}
