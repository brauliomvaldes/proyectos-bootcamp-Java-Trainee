package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.UsuarioFake;

/*
 * Test unitario para probar clase User
 */
class UserTest {
	
	private UsuarioFake usuarioFake;
	private Scanner tecladoSimulado;
	
	@BeforeEach
	public void setUp() {
	    // Crear el objeto simulando teclado para el atributo de la clase Usuario
		tecladoSimulado = mock(Scanner.class);
			    
		// Crear una instancia de la clase Usuario
		usuarioFake = new UsuarioFake(tecladoSimulado);
	}

	@Test
	public void testCargarDatosValidosDelUsuario() {
	    
	    // Llamar los métodos cargar datos personales y
	    // verificar que los datos se hayan establecido correctamente

	    // nombre
	    when(tecladoSimulado.next()).thenReturn("Brisa");
	    usuarioFake.ingresarNombre();
	    assertEquals("Brisa", usuarioFake.getUser_firstname());  
	    
	    // edad
	    when(tecladoSimulado.nextInt()).thenReturn(21);
	    usuarioFake.ingresarEdad();
	    assertEquals(21, usuarioFake.isUser_age());

	    // username
	    when(tecladoSimulado.next()).thenReturn("brisa");
	    usuarioFake.ingresarUsername();
	    assertEquals("brisa", usuarioFake.getUser_username());

	    // password
	    when(tecladoSimulado.next()).thenReturn("123456");
	    usuarioFake.ingresarPassword();
	    assertEquals("123456", usuarioFake.getUser_password_hash());

	}
	
	@Test
	public void testCargarDatosInvalidosUsuario() {

	    // Llamar los métodos cargar datos personales y
	    // verificar que los datos se hayan establecido correctamente

	    // nombre
	    when(tecladoSimulado.next()).thenReturn("Brisa");
	    usuarioFake.ingresarNombre();
	    assertNotEquals("Braulio", usuarioFake.getUser_firstname());  
	    
	    // edad
	    when(tecladoSimulado.nextInt()).thenReturn(21);
	    usuarioFake.ingresarEdad();
	    assertNotEquals(55, usuarioFake.isUser_age());

	    // username
	    when(tecladoSimulado.next()).thenReturn("brisa");
	    usuarioFake.ingresarUsername();
	    assertNotEquals("braulio", usuarioFake.getUser_username());

	    // password
	    when(tecladoSimulado.next()).thenReturn("123456");
	    usuarioFake.ingresarPassword();
	    assertNotEquals("123", usuarioFake.getUser_password_hash());

	}
}
