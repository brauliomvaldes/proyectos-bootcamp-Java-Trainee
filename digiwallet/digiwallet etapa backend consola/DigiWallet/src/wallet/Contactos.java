package wallet;

import java.util.List;
import java.util.Scanner;

import entities.Account;
import entities.Bank;
import entities.Currencyy;
import entities.Role;
import entities.TypeOfAccount;
import entities.User;
import entities.UserAuth;
import service.AccountService;
import service.BankService;
import service.CurrencyService;
import service.ToAService;
import service.UserService;
import service.ValidadorNumerico;
import vistas.Mostrar;

public class Contactos {

	/**
	 * Menu procesamiento de Contactos del Usuario autorizado
	 *
	 * @param scanner, usuarios, cuentas, bancos, tipos de cuentas, monedas, usuario
	 *                 autorizado
	 * @return void
	 */
	public static void procesarContactos(Scanner leeteclado, List<User> usuarios, List<Account> cuentas,
			List<Bank> bancos, List<TypeOfAccount> toas, List<Currencyy> monedas, UserAuth userAuth) {

		// crear instancias servicios
		UserService userService = new UserService(); // instancia servicios
		AccountService ctasService = new AccountService(); // instancias servicios

		String opcion = "";
		do {
			Mostrar.menuContactos(userAuth.getUsername());
			opcion = leeteclado.next();
			switch (opcion) {
			case "1":
				Mostrar.tituloContactosUsuarios();
				userService.findAllContact(userAuth.getUser());
				break;
			case "2":
				Mostrar.tituloAgregaContactosUsuarios();
				User usuario = UsuariosAdm.ingresoDeDatosCliente(leeteclado, userService, usuarios, userAuth);
				if (usuario != null) { // usuario creado exitosamente
					Account cuenta = generaCuentaContacto(cuentas, usuarios, bancos, toas, monedas, leeteclado,
							userService, ctasService, userAuth, usuario);
					if (cuenta != null) {
						cuentas = ctasService.save(cuenta, cuentas); // registra nueva cuenta del nuevo usuario
						usuarios = userService.save(usuario, usuarios); // registra nuevo usuario
						usuarios = userService.saveUserContact(userAuth.getUser().getUser_id(), usuarios, usuario); // registra
																													// el
																													// contacto
																													// al
																													// usuario
						System.out.println("\nNuevo usuario creado existosamente !!\n");
					} else { // si no existe alguna de los objetos necesarios
						System.out.println(" ".repeat(10) + "\nNo fue posible generar la nueva cuenta\n"
								+ " ".repeat(10) + "proceso cancelado");
					}
				}
				break;
			case "3":
				if (userAuth.getRole().equals(Role.ADMIN)) {
					Mostrar.tituloEdicionUsuario();
					String mensaje = "\nid del Contacto (0=termina) ? ";
					System.out.print(mensaje);
					int id = ValidadorNumerico.validaInt(leeteclado, mensaje);
					if (id > 0) {
						User userEdit = userService.findById(id, usuarios);
						if (userEdit != null) {
							User userEdited = UsuariosAdm.edicionDatosDelUsuario(leeteclado, userService, usuarios,
									userEdit); // solicita ingreso de cambios
							if (userEdited != null) { // proceso de edición exitoso
								userService.edit(id, userEdited, usuarios); //
								System.out.println("\n" + " ".repeat(10) + "Contacto modificado existosamente !!\n");
							}
						} else {
							System.out.println("\n" + " ".repeat(10) + "¡ Error !, Contacto solictado no existe\n");
						}
					} else {
						opcion="0"; // termina el proceso
						break;
					}
				} else {
					System.out.println("\n" + " ".repeat(10) + "No esta autorizado para realizar este proceso");
				}
				break;
			}
		} while (!opcion.equals("0"));
	}

	/**
	 * Creación de Cuenta del Nuevo Contacto
	 *
	 * @param cuentas, usuarios, bancos, tipos de cuentas, monedas, scanner,
	 *                 servicio usuarios, servicios cuentas, usuario autorizado,
	 *                 nuevo contacto
	 * @return nueva cuenta del nuevo contacto
	 */
	public static Account generaCuentaContacto(List<Account> cuentas, List<User> usuarios, List<Bank> bancos,
			List<TypeOfAccount> toas, List<Currencyy> monedas, Scanner leeteclado, UserService userService,
			AccountService ctasService, UserAuth userAuth, User nvousuario) {

		BankService bankService = new BankService(); // instancias servicios
		CurrencyService currencyService = new CurrencyService();
		ToAService toaService = new ToAService();

		// creacion cuenta nueva
		return CuentasAdm.ingresoDeDatosCuentaNueva(leeteclado, usuarios, cuentas, bancos, toas, monedas, userService,
				bankService, currencyService, toaService, ctasService, userAuth, nvousuario);
	}

}
