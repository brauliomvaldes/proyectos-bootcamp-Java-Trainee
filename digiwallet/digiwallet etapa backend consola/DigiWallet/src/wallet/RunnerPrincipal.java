package wallet;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entities.Account;
import entities.AccountPaid;
import entities.AccountPayable;
import entities.Bank;
import entities.Currencyy;
import entities.Role;
import entities.UserAuth;
import service.AuthService;
import entities.Transaction;
import entities.TypeOfAccount;
import entities.User;
import vistas.Mostrar;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Clase principal con método ejecutor.
 * Los tributos que representan Entidades para almacenar información
 */
public class RunnerPrincipal {
	protected static Bank bank1;
	private static Currencyy currency1;
	private static Currencyy currency2;
	private static TypeOfAccount toa1;
	private static TypeOfAccount toa2;
	private static User user1;
	private static User user2;
	private static User user3;
	private static Account account1;
	private static Account account2;
	private static Account account3;
	private static Account account4;
	private static List<User> usuarios = new ArrayList<>();
	private static List<Account> cuentas = new ArrayList<>();
	private static List<Bank> bancos = new ArrayList<>();
	private static List<Currencyy> monedas = new ArrayList<>();
	private static List<TypeOfAccount> toas = new ArrayList<>();
	private static List<Transaction> transferencias = new ArrayList<>();
	private static List<AccountPayable> accountPayables = new ArrayList<>();
	private static List<AccountPaid> accountPaids = new ArrayList<>();
	private static int id_transacciones = 0; // simula auto_increment id transacciones
	private static List<UserAuth> auths = new ArrayList<>(); // define autenticación y autorización

	/**
	 * Método ejecutor
	 * Contiene el menu de opciones para los procesos
	 * Utiliza los atributos de la Clase Principal para manipular la información
	 */
	public static void main(String[] args) {
		Scanner leeteclado = new Scanner(System.in); // lectura de datos desde teclado
		creacionObjetosEntidades(); // crea los objetos para contener datos
		poblarEntities(); // ingresa datos de prueba para las entidades involucradas
		crearTablasDummies(); // crea listas con las objetos poblados

		String opcion = ""; // opciones menu
		UserAuth userAuth = null; // usuario que hace login
		
		do {// loop principal
			if (opcion.isEmpty()) {  // solicita inicio de session
				userAuth = ingresoDeSession(leeteclado, auths); // login de acceso
			}
			Mostrar.menu(userAuth.getUsername()); // menu principal
			opcion = leeteclado.next();
			leeteclado.nextLine();
			switch (opcion) {
			case "1":
				Mostrar.listaSaldosDisponibles(usuarios, cuentas, userAuth);
				break;
			case "2":
				MovimientoDeCuenta.abonosDepositos(leeteclado, userAuth, cuentas, transferencias);
				break;
			case "3":
				Pagos.procesarPagos(leeteclado, cuentas, accountPaids, accountPayables, userAuth);
				break;
			case "4":
				Mostrar.mostrarHistorialTrf(transferencias, userAuth);
				break;
			case "5":
				Transferencias.transferenciasEntreCuentas(usuarios, cuentas, transferencias, leeteclado,
						id_transacciones, userAuth);
				break;
			case "6":
				Contactos.procesarContactos(leeteclado, usuarios, cuentas, bancos, toas, 
						monedas, userAuth);
				break;
			case "7":
				CuentasAdm.mantenedorCuentas(cuentas, usuarios, bancos, toas, monedas, leeteclado, userAuth);				
				break;
			case "8":
				if (userAuth.getRole().equals(Role.ADMIN)) {
					Mantenedor.menu(usuarios, cuentas, transferencias, bancos, toas, monedas, 
							userAuth, auths);
				} else {
					System.out.println("\n	* * * No tiene autorización para emplear esta funcionalidad * * *");
				}
				break;
			case "9":
				opcion = ""; // borra para solicitar inicio de session
				break;
			case "0":
				System.out.println("\nfin del programa");
				leeteclado.close();
				break;
			default:
				break;
			}
		} while (!opcion.equals("0"));
	}

	/**
	 * Login de Acceso.
	 *
	 * @param Scanner pedir datos
	 * @param Credenciales de usuarios
	 * @return Usuario Autenticado
	 */
	public static final UserAuth ingresoDeSession(Scanner leeteclado, List<UserAuth> auths) {
		AuthService authService = new AuthService(); // instancia servicio
		do {
			Mostrar.loginUsuario();
			System.out.print(" ".repeat(20) + "Ingrese su username: ");
			String user = leeteclado.next();
			System.out.println();
			System.out.print(" ".repeat(20) + "Ingrese su password: ");
			String pass = leeteclado.next();
			UserAuth loginUser = authService.login(auths, user, pass);
			if(loginUser != null) return loginUser;
			System.out.println("\n" + " * ".repeat(6) + "Acceso  No Concedido" + " * ".repeat(15) + "\n");
		} while (true);
	}

	/**
	 * Creación de objetos para ser poblados.
	 *
	 * @param void
	 * @return void
	 */
	public static void creacionObjetosEntidades() {
		System.out.println("inicio procesos de creación ......................");
		System.out.println("creación de entidades ............................");
		bank1 = new Bank(); // creacion de banco
		currency1 = new Currencyy(); // creacion de currency
		currency2 = new Currencyy();
		toa1 = new TypeOfAccount(); // crear tipos de cuenta
		toa2 = new TypeOfAccount();
		user1 = new User(); // creación de usuarios
		user2 = new User();
		user3 = new User();
		account1 = new Account(); // creación de cuentas
		account2 = new Account();
		account3 = new Account();
		account4 = new Account();
	}
	
	/**
	 * Crea objetos simulan tablas dummy
	 *
	 * @param void
	 * @return void
	 */
	public static void crearTablasDummies() {
		System.out.println("simulando tabla usuarios .........................");
		usuarios.add(user1); // simula tabla usuarios
		usuarios.add(user2);
		usuarios.add(user3);
		System.out.println("simulando tabla cuentas ..........................");
		cuentas.add(account1); // simula tabla cuentas
		cuentas.add(account2);
		cuentas.add(account3);
		cuentas.add(account4);
		System.out.println("simulando tabla bancos ...........................");
		bancos.add(bank1); // simula tabla bancos
		System.out.println("simulando tabla monedas ..........................");
		monedas.add(currency1); // simula tabla monedas
		monedas.add(currency2);
		System.out.println("simulando tabla tipos de cuentas .................");
		toas.add(toa1); // simula tabla tipos de cuentas
		toas.add(toa2);
		System.out.println("fin procesos de creación .........................");
		System.out.println(".".repeat(50));
		System.out.println(">>>>>>  username: braulio  marcela  admin  <<<<<<<");
		System.out.println(">>>>>>  password: 123      123      super  <<<<<<<");
		System.out.println(".".repeat(50));
	}
	
	/**
	 * Poblar tablas dummy
	 *
	 * @param void
	 * @return void
	 */
	public static void poblarEntities() {
		System.out.println("poblando usuarios ................................");
		user1.setUser_id(1); // poblando usuario 1
		user1.setUser_firstname("braulio");
		user1.setUser_lastname("valdes");
		user1.setUser_identity_number("24878196-3");
		user1.setUser_username("braulio");
		user1.setUser_password_hash("123");
		user1.setUser_created_at(new Date());
		user1.setUser_married(true);
		user1.setUser_age((short) 55);
		user1.setUser_email("braulio@mail.com");
		user1.setUser_sex(true);
		user1.setAddress(null);
		user1.setUser_state(true);
		user2.setUser_id(2); // poblando usuario 1
		user2.setUser_firstname("marcela");
		user2.setUser_lastname("campos");
		user2.setUser_identity_number("12096543-3");
		user2.setUser_username("marcela");
		user2.setUser_password_hash("123");
		user2.setUser_created_at(new Date());
		user2.setUser_married(true);
		user2.setUser_age((short) 31);
		user2.setUser_email("marcela@mail.com");
		user2.setUser_sex(false);
		user2.setAddress(null);
		user2.setUser_state(true);
		user3.setUser_id(3); // poblando usuario 1
		user3.setUser_firstname("isabel");
		user3.setUser_lastname("muller");
		user3.setUser_identity_number("22160491-1");
		user3.setUser_username("admin");
		user3.setUser_password_hash("super");
		user3.setUser_created_at(new Date());
		user3.setUser_married(false);
		user3.setUser_age((short) 27);
		user3.setUser_email("isabel@mail.com");
		user3.setUser_sex(false);
		user3.setAddress(null);
		user3.setUser_state(true);
		System.out.println("poblando contactos ...............................");
		List<User> contact_user = new ArrayList<>(); // crea contactos a usuario 1
		contact_user.add(user2);
		contact_user.add(user3);
		user1.setUser_contact(contact_user);
		contact_user = new ArrayList<>(); // crea contactos a usuario 2
		contact_user.add(user1);
		contact_user.add(user3);
		user2.setUser_contact(contact_user);
		contact_user = new ArrayList<>(); // crea contactos a usuario 3
		contact_user.add(user1);
		contact_user.add(user2);
		user3.setUser_contact(contact_user);
		System.out.println("poblando bancos ..................................");
		bank1 = new Bank(1, "Banco de Chile", true); // poblando banco 1
		System.out.println("poblando tipos de monedas.........................");
		currency1 = new Currencyy(1, "Pesos Chilenos", "CLP", true); // poblando monedas
		currency2 = new Currencyy(2, "Dolares Americanos", "USD", true);
		System.out.println("poblando tipos de cuentas.........................");
		toa1 = new TypeOfAccount(1, "cuenta vista", true); // poblando tipos de cuentas
		toa2 = new TypeOfAccount(2, "cuenta en dolares", true);
		System.out.println("poblando cuentas .................................");
		account1.setAccount_id(1); // poblando cuenta 1
		account1.setAccount_user(user1);
		account1.setAccount_balance(BigDecimal.valueOf(1000000));
		account1.setAccount_bank_id(bank1);
		account1.setAccount_currency_id(currency1);
		account1.setAccount_type_id(toa1);
		account1.setAccount_number("100-111-001");
		account1.setAccount_created_at(new Date());
		account1.setAccount_state(true);
		account2.setAccount_id(2); // poblando cuenta 2
		account2.setAccount_user(user2);
		account2.setAccount_balance(BigDecimal.valueOf(1000000));
		account2.setAccount_bank_id(bank1);
		account2.setAccount_currency_id(currency1);
		account2.setAccount_type_id(toa1);
		account2.setAccount_number("200-222-002");
		account2.setAccount_created_at(new Date());
		account2.setAccount_state(true);
		account3.setAccount_id(3); // poblando cuenta 3
		account3.setAccount_user(user3);
		account3.setAccount_balance(BigDecimal.valueOf(1000000));
		account3.setAccount_bank_id(bank1);
		account3.setAccount_currency_id(currency1);
		account3.setAccount_type_id(toa1);
		account3.setAccount_number("505-522-003");
		account3.setAccount_created_at(new Date());
		account3.setAccount_state(true);
		account4.setAccount_id(4); // poblando cuenta 4
		account4.setAccount_user(user1);
		account4.setAccount_balance(BigDecimal.valueOf(10000));
		account4.setAccount_bank_id(bank1);
		account4.setAccount_currency_id(currency2);
		account4.setAccount_type_id(toa2);
		account4.setAccount_number("105-722-001");
		account4.setAccount_created_at(new Date());
		account4.setAccount_state(true);
		System.out.println("creando credenciales de acceso ...................");
		UserAuth u1 = new UserAuth(); // creacion de autenticaciones y autorizaciones
		u1.setUsername(user1.getUser_username());
		u1.setPassword(user1.getUser_password_hash());
		u1.setRole(Role.USER);
		u1.setUser(user1);
		UserAuth u2 = new UserAuth();
		u2.setUsername(user2.getUser_username());
		u2.setPassword(user2.getUser_password_hash());
		u2.setRole(Role.USER);
		u2.setUser(user2);
		UserAuth u3 = new UserAuth();
		u3.setUsername(user3.getUser_username());
		u3.setPassword(user3.getUser_password_hash());
		u3.setRole(Role.ADMIN);
		u3.setUser(user3);
		auths.add(u1);
		auths.add(u2);
		auths.add(u3);
	}
}
