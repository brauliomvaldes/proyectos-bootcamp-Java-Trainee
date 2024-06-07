package wallet;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
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

public class CuentasAdm {
	/**
	 * Menu mantenedor de Cuentas
	 *
	 * @param cuentas, usuarios, bancos, tipos de cuentas, monedas, scanner, usuario
	 *                 autorizado
	 * @return void
	 */
	public static void mantenedorCuentas(List<Account> cuentas, List<User> usuarios, List<Bank> bancos,
			List<TypeOfAccount> toas, List<Currencyy> monedas, Scanner leeteclado, UserAuth userAuth) {
		String opcion;
		UserService userService = new UserService(); // instancias servicios
		AccountService ctasService = new AccountService();
		BankService bankService = new BankService();
		CurrencyService currencyService = new CurrencyService();
		ToAService toaService = new ToAService();
		String mensaje = "";
		int id = 0;
		do { // menu cuentas
			Mostrar.mantenedor("CUENTAS");
			opcion = leeteclado.next();
			leeteclado.nextLine();

			switch (opcion) {
			case "1":
				Mostrar.tituloCuentas();
				ctasService.findAll(cuentas);
				break;
			case "2":
				Mostrar.tituloCuenta();
				mensaje = "\nid Cuenta a mostrar ? ";
				System.out.print(mensaje);
				id = ValidadorNumerico.validaInt(leeteclado, mensaje);
				Account account = ctasService.findById(id, cuentas);
				if (account != null) {
					Mostrar.imprimirDatosCuenta(account);
				}
				break;
			case "3":
				if (userAuth.getRole().equals(Role.ADMIN)) {
					Mostrar.tituloEdicionCuentas();
					mensaje = "\nIngrese ID Cuenta a modificar ? ";
					System.out.print(mensaje);
					id = ValidadorNumerico.validaInt(leeteclado, mensaje);
					Account ctaEdit = ctasService.findById(id, cuentas);
					if (ctaEdit != null) {
						Account ctaEdited = edicionDatosDeLaCuenta(leeteclado, ctaEdit, usuarios, bancos, toas, monedas,
								userService, bankService, currencyService, toaService); // solicita ingreso de cambios
						ctasService.edit(id, ctaEdited, cuentas);
						System.out.println(" ".repeat(10) + "\nUsuario modificado existosamente !!\n");
					} else {
						System.out.println("\n" + " ".repeat(10) + "¡ Error !, usuario solictado no existe\n");
					}
				} else {
					System.out.println("\n	* * * No tiene autorización para emplear esta funcionalidad * * *");
				}
				break;
			case "4":
				Account cuenta = ingresoDeDatosCuentaNueva(leeteclado, usuarios, cuentas, bancos, toas, monedas,
						userService, bankService, currencyService, toaService, ctasService, userAuth,
						userAuth.getUser()); // creacion cuenta nueva
				if (cuenta != null) {
					cuentas = ctasService.save(cuenta, cuentas);
					System.out.println("\nNueva cuenta creada existosamente !!\n");
				} else { // si no existe alguna de los objetos necesarios
					System.out.println(" ".repeat(10) + "\nNo fue posible generar la nueva cuenta\n" + " ".repeat(10)
							+ "por una inconsistencia en información entregada");
				}
				break;
			case "5":
				if (userAuth.getRole().equals(Role.ADMIN)) {
					Mostrar.tituloDesactivarCuenta();
					mensaje = "Ingrese ID Cuenta a desactivar: ";
					System.out.print(mensaje);
					id = ValidadorNumerico.validaInt(leeteclado, mensaje); // solicita id cuenta
					cuentas = ctasService.delete(id, cuentas);
					break;
				} else {
					System.out.println("\n	* * * No tiene autorización para emplear esta funcionalidad * * *");
				}
			}
		} while (!opcion.equals("0"));
	};

	/**
	 * Creación de Nuevas Cuentas
	 *
	 * @param scanner, servicio usuarios, cuentas, bancos, tipos de cuentas,
	 *                 monedas, servicio usuarios, servicio bancos, servicio
	 *                 monedas, servicio tipos de cuentas, servicio cuentas, usuario
	 *                 autorizado, usuario de la cuenta
	 * @return nueva cuenta
	 */
	public static Account ingresoDeDatosCuentaNueva(Scanner leeteclado, List<User> usuarios, List<Account> cuentas,
			List<Bank> bancos, List<TypeOfAccount> toas, List<Currencyy> monedas, UserService userService,
			BankService bankService, CurrencyService currencyService, ToAService toaService, AccountService ctasService,
			UserAuth userAuth, User nvousuario) {
		Mostrar.tituloCreacionNuevaCuenta();
		User usuario;
		String mensaje = "";
		int account_user;
		if (userAuth.getRole().equals(Role.ADMIN)) {
			userService.findAll(usuarios); // lista usuarios
			System.out.println(" ".repeat(10) + "Se vincula con Usuario");
			mensaje = " ".repeat(10) + "Id Usuario (0=sale) : ";
			System.out.print(mensaje);
			account_user = ValidadorNumerico.validaInt(leeteclado, mensaje);
			if (account_user > 0) {
				usuario = userService.findById(account_user, usuarios); // busca el usuario
				if (usuario == null) {// valida exista el usuario
					return null;
				}
			} else {
				return null;
			}
		} else {
			usuario = userAuth.getUser();
			account_user = userAuth.getUser().getUser_id();
		}
		System.out.print(" ".repeat(10) + "Número Cuenta       : ");
		String account_number = leeteclado.next(); // solicta nro de cuenta
		Account cuentaExiste = ctasService.validaNumeroCuenta(account_number, cuentas);
		if (cuentaExiste != null) { // ya existe la cuenta
			ctasService.findById(cuentaExiste.getAccount_id(), cuentas); // datos de la cuenta existente
			System.out.println(" ".repeat(10) + "La cuenta señalada ya existe .........");
			System.out.print(" ".repeat(10) + "Desea utilizar los datos del propietario de la cuenta ? ");
			String respuesta = leeteclado.next().toLowerCase();
			if (respuesta.equals("s")) {
				System.out.println(
						"".repeat(10) + "Cuenta id : " + cuentaExiste.getAccount_id() + "  asignada al nuevo usuario");
				return cuentaExiste;
			}
			return null;
		}
		BigDecimal account_balance = BigDecimal.ZERO;
		if (userAuth.getRole().equals(Role.ADMIN)) {
			
			mensaje = " ".repeat(10) + "Balance inicial     : ";
			System.out.print(mensaje);
			account_balance = ValidadorNumerico.validaBigDecimal(leeteclado, mensaje);
		}
		currencyService.findAll(monedas); // lista monedas
		mensaje = " ".repeat(10) + "Id moneda           : ";
		System.out.print(mensaje);
		int account_currency_id = ValidadorNumerico.validaInt(leeteclado, mensaje);
		Currencyy moneda = currencyService.findById(account_currency_id, monedas); // busca la moneda
		if (moneda == null) {// valida exista moneda
			return null;
		}
		toaService.findAll(toas); // lista tipos de cuentas

		mensaje = " ".repeat(10) + "Id Tipo de Cuenta   : ";
		System.out.print(mensaje);
		int account_type_id = ValidadorNumerico.validaInt(leeteclado, mensaje);
		
		TypeOfAccount toa = toaService.findById(account_type_id, toas); // busca el tipo de cuenta
		if (toa == null) {// valida exista tipo de cuenta
			return null;
		}
		bankService.findAll(bancos); // lista bancos
		mensaje = " ".repeat(10) + "Id Banco            : ";
		System.out.print(mensaje);
		int account_bank_id = ValidadorNumerico.validaInt(leeteclado, mensaje);
		Bank banco = bankService.findById(account_bank_id, bancos); // busca el banco
		if (banco == null) {// valida exista el banco
			return null;
		}
		System.out.println("-".repeat(140));
		Date account_created_at = new Date(); // fecha de creación
		Account cuenta = new Account(); // creación del objeto cuenta nueva
		cuenta.setAccount_id(account_user); // se ingresa el id como id de cuenta
		cuenta.setAccount_user(nvousuario); // asigna el dueño de la cuenta
		cuenta.setAccount_number(account_number); // numero de la cuenta
		cuenta.setAccount_balance(account_balance); // saldo inicial
		cuenta.setAccount_currency_id(moneda); // la moneda
		cuenta.setAccount_created_at(account_created_at); // fecha de creación
		cuenta.setAccount_type_id(toa); // tipo de cuenta
		cuenta.setAccount_bank_id(banco); // banco al que pertenece
		cuenta.setAccount_state(true); // cuenta activa
		return cuenta;
	}

	/**
	 * Edición de Cuentas
	 *
	 * @param scanner, cuenta a editar, usuarios, bancos, tipos de cuentas, monedas,
	 *                 servicio usuarios, servicio bancos, servicio monedas,
	 *                 servicio tipos de cuentas
	 * @return cuenta editada
	 */
	public static Account edicionDatosDeLaCuenta(Scanner leeteclado, Account ctaEdit, List<User> usuarios,
			List<Bank> bancos, List<TypeOfAccount> toas, List<Currencyy> monedas, UserService userService,
			BankService bankService, CurrencyService currencyService, ToAService toaService) {
		int oldUser = ctaEdit.getAccount_user().getUser_id(); // almacena datos originales que seran editados
		int oldBank = ctaEdit.getAccount_bank_id().getBank_id(); // almacena datos originales que seran editados
		int oldCurrency = ctaEdit.getAccount_currency_id().getCurrency_id(); // almacena datos originales que seran
																				// editados
		int oldToA = ctaEdit.getAccount_type_id().getToa_id(); // almacena datos originales que seran editados
		User usuario; // define entidades para almacenar modificaciones
		Currencyy moneda;
		TypeOfAccount toa;
		Bank banco;
		String mensaje = "";
		userService.findAll(usuarios); // muestra usuarios disponibles
		mensaje = " ".repeat(10) + "Id Usuario        ( " + oldUser + ") ? ";
		System.out.print(mensaje);
		int account_user = ValidadorNumerico.validaInt(leeteclado, mensaje);
		if (account_user > 0) {
			usuario = userService.findById(account_user, usuarios);
			if (usuario == null) // valida exista el usuario
				return null;
		} else {
			usuario = ctaEdit.getAccount_user();
		}
		currencyService.findAll(monedas); // muestra moneda disponibles
		
		mensaje = " ".repeat(10) + "Id moneda         ( " + oldCurrency + ") ? ";
		System.out.print(mensaje);
		int account_currency_id = ValidadorNumerico.validaInt(leeteclado, mensaje);
		
		if (account_currency_id > 0) {
			moneda = currencyService.findById(account_currency_id, monedas);
			if (moneda == null) // valida exista moneda
				return null;
		} else {
			moneda = ctaEdit.getAccount_currency_id();
		}
		toaService.findAll(toas); // muestra tipos de cuenta disponibles
		mensaje = " ".repeat(10) + "Id Tipo de Cuenta ( " + oldToA + ") ? ";
		System.out.print(mensaje);
		int account_type_id = ValidadorNumerico.validaInt(leeteclado, mensaje);
		if (account_type_id > 0) {
			toa = toaService.findById(account_type_id, toas);
			if (toa == null) // valida exista tipo de cuenta
				return null;
		} else {
			toa = ctaEdit.getAccount_type_id();
		}
		bankService.findAll(bancos); // muestra bancos disponibles
		mensaje = " ".repeat(10) + "Id Banco          ( " + oldBank + ") ? ";
		System.out.print(mensaje);
		int account_bank_id = ValidadorNumerico.validaInt(leeteclado, mensaje);
		if (account_bank_id > 0) {
			banco = bankService.findById(account_bank_id, bancos);
			if (banco == null) // valida exista el banco
				return null;
		} else {
			banco = ctaEdit.getAccount_bank_id();
		}
		System.out.println("-".repeat(140));
		Account cuenta = new Account(); // crea un objeto con las modificaciones
		cuenta.setAccount_user(usuario); // configura nueva cuenta
		cuenta.setAccount_currency_id(moneda);
		cuenta.setAccount_type_id(toa);
		cuenta.setAccount_bank_id(banco);
		return cuenta;
	}
}
