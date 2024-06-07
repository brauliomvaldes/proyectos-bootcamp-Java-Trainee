package entities;

import java.util.Scanner;
// clase hereda de User 
// emplea atributos y métodos para poblar la clase
// tiene un atributo para simular el teclado para ingreso de datos

public class UsuarioFake extends User{

	protected Scanner teclado;
	
	public UsuarioFake(Scanner teclado) {
		super();
		this.teclado=teclado;
	}
	
	public void ingresarNombre() {
		super.setUser_firstname(teclado.next());
	}
	public void ingresarEdad() {
		super.setUser_age((short)teclado.nextInt());
	}
	public void ingresarUsername() {
		super.setUser_username(teclado.next());
	}
	public void ingresarPassword() {
		super.setUser_password_hash(teclado.next());
	}

}
