package wallet;

import java.util.List;
import java.util.Date;
import java.util.Scanner;

import entities.Role;
import entities.User;
import entities.UserAuth;
import service.AuthService;
import service.UserService;
import service.ValidadorRut;
import service.ValidadorNumerico;
import vistas.Mostrar;

public class UsuariosAdm {
	/**
	 * Menu mantenedor de Usuarios
	 *
	 * @param usuarios, scanner, usuario autorizado
	 * @return void
	 */
	public static void mantenedorUsuarios(List<User> usuarios, Scanner leeteclado, UserAuth userAuth,
			List<UserAuth> auths) {
		String opcion;
		int id;
		String mensaje = "";
		UserService userService = new UserService(); // instancia servicios
		AuthService authService = new AuthService();
		do { // menu mantenedor
			Mostrar.mantenedor("USUARIOS");
			opcion = leeteclado.next();
			leeteclado.nextLine();
			switch (opcion) {
			case "1":
				Mostrar.tituloUsuarios();
				// usu.listAllUsersDataBase(); // acceso a base de datos mysql funcional
				userService.findAll(usuarios);
				break;
			case "2":
				Mostrar.tituloUsuario();
				mensaje = "\nIngrese ID Usuario (0=termina) ? ";
				System.out.print(mensaje);
				id = ValidadorNumerico.validaInt(leeteclado, mensaje);
				if (id > 0) {
					User user = userService.findById(id, usuarios);
					if (user != null) {
						Mostrar.imprimirDatosUsuario(user);
					} else {
						System.out.println("\n" + " ".repeat(10) + "¡ Error !, usuario solictado no existe\n");
					}
				}
				break;
			case "3":
				Mostrar.tituloEdicionUsuario();
				mensaje = "\nIngrese ID Usuario (0=termina) ? ";
				System.out.print(mensaje);
				id = ValidadorNumerico.validaInt(leeteclado, mensaje);
				if (id > 0) {
					User userEdit = userService.findById(id, usuarios);
					if (userEdit != null) {
						User userEdited = edicionDatosDelUsuario(leeteclado, userService, usuarios, userEdit); // solicita
						// ingreso
						// de
						// cambios
						if (userEdited != null) { // proceso de edición exitoso
							userService.edit(id, userEdited, usuarios); //
							System.out.println(" ".repeat(10) + "\nUsuario modificado existosamente !!\n");
						}
					} else {
						System.out.println(" ".repeat(10) + "\n¡ Error !, usuario solictado no existe\n");
					}
				}
				break;
			case "4":
				Mostrar.tituloCreacionUsuario(); // simula recepcion de datos del clientes
				User usuario = ingresoDeDatosCliente(leeteclado, userService, usuarios, userAuth);
				if (usuario != null) { // nuevo usuario creado exitosamente
					authService.register(auths, usuario, Role.USER); // asigna y registra tipo de usuario
					usuarios = userService.save(usuario, usuarios);
					System.out.println(" ".repeat(10) + "\nNuevo usuario creado existosamente !!\n");
					System.out.println(" ".repeat(10) + "IMPORTANTE: Debe crear cuenta y asociar al Nuevo Cliente");
				}
				break;
			case "5":
				mensaje = "\nid Usuario a desactivar (0=termina): ";
				System.out.print(mensaje);
				id = ValidadorNumerico.validaInt(leeteclado, mensaje);
				if (id > 0) {
					if (id != userAuth.getUser().getUser_id()) {
						usuarios = userService.delete(id, usuarios);
					} else {
						System.out.println(" ".repeat(10) + "No puede desactivar al usuario ADMIN");
					}
				}
				break;
			}
		} while (!opcion.equals("0"));
	}

	/**
	 * Creación de Nuevos Usuarios
	 *
	 * @param scanner, servicio usuarios, usuarios
	 * @return nuevo usuario
	 */
	public static User ingresoDeDatosCliente(Scanner leeteclado, UserService userService, List<User> usuarios,
			UserAuth userAuth) {
		User usuario = new User();
		System.out.println(" ".repeat(10) + "Ingreso de Datos del Cliente");
		System.out.println("-".repeat(140));
		System.out.print(" ".repeat(10) + "Nombre           : ");
		String user_name = leeteclado.next();
		System.out.print(" ".repeat(10) + "Apellido         : ");
		String user_lastname = leeteclado.next();
		boolean rutValido = false;
		String user_identity_number;
		do {
			System.out.print(" ".repeat(10) + "DNI/RUT (0=fin)  : ");
			user_identity_number = leeteclado.next();
			if (user_identity_number.equals("0")) { // cancela proceso
				System.out.println(" ".repeat(10) + "proceso cancelado por el usuario");
				return null;
			}
			rutValido = ValidadorRut.validarRut(user_identity_number);
			if (!rutValido) {
				System.out.println("DNI/Rut no válido\n");
			} else {
				if (userService.findByRut(user_identity_number, usuarios) != null) {
					rutValido = false; // invalida el rut para solicitar otro
					System.out.println("El DNI/Rut ingresado ya existe en un Cliente");
				}
			}
		} while (!rutValido);
		System.out.print(" ".repeat(10) + "Correo           : ");
		String user_email = leeteclado.next();
		if (userAuth.getRole().equals(Role.ADMIN)) { // el admin puede llenar la siguiente información
			System.out.print(" ".repeat(10) + "Username         : ");
			String user_username = leeteclado.next();
			System.out.print(" ".repeat(10) + "Password         : ");
			String user_password_hash = leeteclado.next();
			System.out.print(" ".repeat(10) + "E.Civil c=casado : ");
			String ecivil = leeteclado.next();
			int user_age = 0;
			while (user_age < 18) {
				String mensaje = " ".repeat(10) + "Edad             : ";
				System.out.print(mensaje);
				user_age = ValidadorNumerico.validaInt(leeteclado, mensaje);
				if (user_age < 18 || user_age > 120) {
					System.out.println("debe ingresar una edad válida");
				}
			}
			System.out.print(" ".repeat(10) + "Sexo    h=hombre : ");
			String sexo = leeteclado.next();
			boolean user_married = ecivil.equals("c") ? true : false;
			boolean user_sex = sexo.equals("h") ? true : false;
			usuario.setUser_username(user_username);
			usuario.setUser_password_hash(user_password_hash);
			usuario.setUser_age((short) user_age);
			usuario.setUser_sex(user_sex);
			usuario.setUser_married(user_married);
		} else {
			usuario.setUser_username(""); // datos por defecto
			usuario.setUser_password_hash("");
			usuario.setUser_age((short) 18);
			usuario.setUser_sex(true);
			usuario.setUser_married(false);
		}
		usuario.setUser_created_at(new Date());
		usuario.setUser_firstname(user_name);
		usuario.setUser_lastname(user_lastname);
		usuario.setUser_identity_number(user_identity_number);
		usuario.setUser_email(user_email);
		System.out.println("_".repeat(140));
		return usuario;
	}

	/**
	 * Edición de Usuarios
	 *
	 * @param scanner, servicio usuarios, usuarios, usuario a editar
	 * @return usuario editado
	 */
	public static User edicionDatosDelUsuario(Scanner leeteclado, UserService userService, List<User> usuarios,
			User userEdited) {
		System.out.println(" ".repeat(10) + "( 'm' mantiene sin modificar )");
		String sexo = userEdited.isUser_sex() ? "Hombre" : "Mujer";
		String ecivil = userEdited.isUser_married() ? "casado" : "soltero";
		System.out.println("-".repeat(140));
		System.out.print(" ".repeat(10) + "Nombre           ( " + userEdited.getUser_firstname() + " ) ? ");
		String user_firstname = leeteclado.next();
		System.out.print(" ".repeat(10) + "Apellido         ( " + userEdited.getUser_lastname() + " ) ? ");
		String user_lastname = leeteclado.next();
		String user_identity_number;
		boolean rutValido = false;
		do {
			System.out.print(" ".repeat(10) + "DNI/RUT [0=fin]  ( " + userEdited.getUser_identity_number() + " ) ? ");
			user_identity_number = leeteclado.next();
			if (user_identity_number.equals("0")) {
				System.out.println(" ".repeat(10) + "Proceso cancelado por el usuario");
				return null; // cancela proceso
			}
			if (!user_identity_number.equals("m")) {
				rutValido = ValidadorRut.validarRut(user_identity_number);
				if (!rutValido) {
					System.out.println("DNI/Rut no válido\n");
				} else {
					if (userService.findByRut(user_identity_number, usuarios) != null) {
						rutValido = false; // anula rut para solicitar otro
						System.out.println("El DNI/Rut ingresado ya existe en un Cliente");
					}
				}
			} else { // mantiene rut original
				rutValido = true;
			}
		} while (!rutValido);
		System.out.print(" ".repeat(10) + "E.Civil c=casado ( " + ecivil + " ) ? ");
		ecivil = leeteclado.next();
		int user_age = -1; // valor inválido
		while (user_age < 18) {
			String mensaje = " ".repeat(10) + "Edad [0]mantiene ( " + userEdited.isUser_age() + " ) ? ";
			System.out.print(mensaje);
			user_age = ValidadorNumerico.validaInt(leeteclado, mensaje);
			if (user_age == 0) { 
				break; // mantiene edad
			} else if (user_age < 18 || user_age > 120) { // mayoría de edad
				user_age = -1;
				System.out.println("debe ingresar una edad válida");
			}
		}
		System.out.print(" ".repeat(10) + "Correo           ( " + userEdited.getUser_email() + " ) ? ");
		String user_email = leeteclado.next();
		System.out.print(" ".repeat(10) + "Sexo    h=hombre ( " + sexo + " ) ? ");
		sexo = leeteclado.next();
		System.out.println("_".repeat(140));
		System.out.print(" ".repeat(10) + "Sexo    h=hombre ( " + sexo + " ) ? ");
		sexo = leeteclado.next();
		System.out.println("_".repeat(140));
		System.out.println("*".repeat(40));
		System.out.print(" ".repeat(10) + "Habilita Username y Password provisorio (s=Si) ? ");
		String habilitado = leeteclado.next().toLowerCase();
		User usuario = new User(); // crea instancia de usuario editado
		if(habilitado.equals("s")) { // set password y username por defecto
			usuario.setUser_username(user_firstname); 
			usuario.setUser_password_hash("123");
			System.out.println(" ".repeat(10)+"se configuro username: "+user_firstname+" y password: 123");
		}
		System.out.println("_".repeat(140));
		boolean user_married = ecivil.equals("c") ? true : false;
		boolean user_sex = sexo.equals("h") ? true : false;
		if (user_firstname.equalsIgnoreCase("m")) // "m" no hay cambios conserva el valor original
			user_firstname = userEdited.getUser_firstname();
		usuario.setUser_firstname(user_firstname);
		if (user_lastname.equalsIgnoreCase("m"))
			user_lastname = userEdited.getUser_lastname();
		usuario.setUser_lastname(user_lastname);
		if (user_identity_number.equalsIgnoreCase("m"))
			user_identity_number = userEdited.getUser_identity_number();
		usuario.setUser_identity_number(user_identity_number);
		if (user_email.equalsIgnoreCase("m"))
			user_email = userEdited.getUser_email();
		usuario.setUser_email(user_email);
		if (user_age == 0)
			user_age = userEdited.isUser_age();
		usuario.setUser_age((short) user_age);
		if (sexo.equalsIgnoreCase("m"))
			user_sex = userEdited.isUser_sex();
		usuario.setUser_sex(user_sex);
		if (ecivil.equalsIgnoreCase("m"))
			user_married = userEdited.isUser_married();
		usuario.setUser_married(user_married);
		return usuario;
	}

}
