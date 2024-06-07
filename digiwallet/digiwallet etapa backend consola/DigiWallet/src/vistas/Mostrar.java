package vistas;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Account;
import entities.AccountPayable;
import entities.Transaction;
import entities.User;
import entities.UserAuth;

public class Mostrar {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public static void loginUsuario() {
		System.out.println("\n");
		System.out.println("  ██████  ██  ██████  ██ ██     ██  █████  ██      ██      ███████ ████████ ");
		System.out.println("  ██   ██ ██ ██       ██ ██     ██ ██   ██ ██      ██      ██         ██    ");
		System.out.println("  ██   ██ ██ ██   ███ ██ ██  █  ██ ███████ ██      ██      █████      ██    ");
		System.out.println("  ██   ██ ██ ██    ██ ██ ██ ███ ██ ██   ██ ██      ██      ██         ██    ");
		System.out.println("  ██████  ██  ██████  ██  ███ ███  ██   ██ ███████ ███████ ███████    ██    ");
		System.out.println("\n");
		System.out.println("- Ingreso a Session ".repeat(4));
		System.out.println();
		System.out.println(dateFormat.format(new Date()));
		System.out.println(" ".repeat(20) + "CREDENCIALES DE ACCESO\n");
		System.out.println("-".repeat(83));
	}

	public static void menu(String usuario) {

		System.out.println("\n");
		System.out.println(dateFormat.format(new Date()));
		System.out.println("-".repeat(120));
		System.out.println("		._ _   _  ._        ._  ._ o ._   _ o ._   _. |");
		System.out.println("		| | | (/_ | | |_|   |_) |  | | | (_ | |_) (_| |");
		System.out.println("		                    |                 |        ");
		System.out.println("-".repeat(120));
		System.out.println(" - m e n u ".repeat(10));
		System.out.println("\n");
		System.out.println(" ".repeat(20) + "Usuario Autenticado < " + usuario.toUpperCase() + " >\n");
		System.out.println(" ".repeat(20) + " MENU PRINCIPAL PROCESOS");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.println(" ".repeat(20) + " 1 Mis Saldos");
		System.out.println(" ".repeat(20) + " 2 Movimiento de dinero");
		System.out.println(" ".repeat(20) + " 3 Mis Pagos");
		System.out.println(" ".repeat(20) + " 4 Mis Transferencia");
		System.out.println(" ".repeat(20) + " 5 Transferir Dinero");
		System.out.println(" ".repeat(20) + " 6 Mis Contactos");
		System.out.println(" ".repeat(20) + " 7 Nueva Cuenta Bancaria");
		System.out.println(" ".repeat(20) + " ----------------------------");
		System.out.println(" ".repeat(20) + " 8 Mantenedor General (admin)");
		System.out.println(" ".repeat(20) + " ----------------------------");
		System.out.println(" ".repeat(20) + " 9 Logout Session");
		System.out.println(" ".repeat(20) + " 0 Cerrar Programa");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.print(" ".repeat(20) + "   Opción ? ");
	}

	public static void menuPagos(String usuario) {
		System.out.println("\n");
		System.out.println(dateFormat.format(new Date()));
		System.out.println(" - Proceso de Pagos ".repeat(4));
		System.out.println("\n");
		System.out.println(" ".repeat(20) + "Usuario Autenticado < " + usuario.toUpperCase() + " >\n");
		System.out.println(" ".repeat(20) + " MENU PAGOS DE CUENTAS");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.println(" ".repeat(20) + " 1 Realizar Pago");
		System.out.println(" ".repeat(20) + " 2 Listar Cuentas de Pago");
		System.out.println(" ".repeat(20) + " 3 Agregar Cuentas de Pago");
		System.out.println(" ".repeat(20) + " 4 Eliminar Cuentas de Pago");
		System.out.println(" ".repeat(20) + " 5 Historial de Pagos");
		System.out.println(" ".repeat(20) + " 0 Volver a Principal");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.print(" ".repeat(20) + "   Opción ? ");
	}
	
	public static void menuContactos(String usuario) {
		System.out.println("\n");
		System.out.println(dateFormat.format(new Date()));
		System.out.println(" - contactos usuario ".repeat(4));
		System.out.println("\n");
		System.out.println(" ".repeat(20) + "Usuario Autenticado < " + usuario.toUpperCase() + " >\n");
		System.out.println(" ".repeat(20) + " MENU CONTACTOS");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.println(" ".repeat(20) + " 1 ver Mis Contactos");
		System.out.println(" ".repeat(20) + " 2 Agregar Contactos ");
		System.out.println(" ".repeat(20) + " 3 Modificar (admin)");
		System.out.println(" ".repeat(20) + " 0 Volver a Principal");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.print(" ".repeat(20) + "   Opción ? ");
	}
	
	public static void menuMovimientosDeCuenta(String usuario) {
		System.out.println("\n" + " ".repeat(120));
		System.out.println(" ".repeat(20) + " MENU MOVIMIENTOS");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.println(" ".repeat(20) + " 1 Abono de Dinero");
		System.out.println(" ".repeat(20) + " 2 Retiro de Dinero");
		System.out.println(" ".repeat(20) + " 0 Volver a Principal");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.print(" ".repeat(20) + "   Opción ? ");		
	}
	
	public static void menuMantenedor(String usuario) {
		System.out.println("\n" + " ".repeat(120));
		System.out.println(dateFormat.format(new Date()));
		System.out.println(" - Procesos de Mantención ".repeat(4));
		System.out.println();
		System.out.println(" ".repeat(20) + "Usuario Autenticado < " + usuario.toUpperCase() + " >\n");
		System.out.println(" ".repeat(20) + "MANTENEDOR ADMINISTRADOR");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.println(" ".repeat(20) + " 1 Usuarios");
		System.out.println(" ".repeat(20) + " 2 Cuentas");
		System.out.println(" ".repeat(20) + " 3 Transferencias");
		System.out.println(" ".repeat(20) + " 4 ver Autorizados");
		System.out.println(" ".repeat(20) + " 0 Volver a Principal");
		System.out.println(" ".repeat(20) + "=".repeat(30));
		System.out.print(" ".repeat(20) + "   Opción ? ");
	}


	
	/**
	 * lista por pantalla saldo del usuario
	 *
	 * @param usuarios, cuentas, usuario autorizado
	 * @return void
	 */
	public static void listaSaldosDisponibles(List<User> usuarios, List<Account> cuentas, UserAuth userAuth) {
		tituloSaldosCuentas();
		for (User user : usuarios) {
			if (user.getUser_id() == userAuth.getUser().getUser_id()) {
				System.out
						.println(" ".repeat(10)+user.getUser_firstname().toUpperCase() + " " + user.getUser_lastname().toUpperCase());
				listaCuentasUsuario(usuarios, cuentas, user.getUser_id());
			}
		}
	}

	/**
	 * lista por pantalla usuarios
	 *
	 * @param trasferencia, target (sender o receiver) dependiendo del caso
	 * @return void
	 */
	// lista todos los usuarios
	public static void listaUsuarios(List<User> usuarios, String target) {
		System.out.println("=".repeat(120));
		System.out.println(" ".repeat(10) + "Cuentas "+target+" Disponibles para Transferencias\n");
		usuarios.forEach(u -> {
			// lista todas cuentas
			System.out.println(" ".repeat(10) + "Id: " + u.getUser_id() + " Nombre: " + u.getUser_firstname() + " "
					+ u.getUser_lastname());
		});
		System.out.println("=".repeat(120));
	}

	/**
	 * listado por pantalla de cuentas del usuario
	 *
	 * @param usuarios, cuentas, id del usuario
	 * @return listado de las cuentas que posee el usuario
	 */
	public static List<Account> listaCuentasUsuario(List<User> usuarios, List<Account> cuentas, int id_usuario) {
		List<Account> cuentasUsuario = new ArrayList<>();
		System.out.print("    Cuentas del Usuario: ");
		usuarios.forEach(usuario -> {
			if (usuario.getUser_id() == id_usuario) {
				System.out.println(
						usuario.getUser_firstname().toUpperCase() + " " + usuario.getUser_lastname().toUpperCase());
				cuentas.forEach(cuenta -> {
					if (cuenta.getAccount_user().equals(usuario)) {
						System.out.println(" ".repeat(10)+"Nro: " + cuenta.getAccount_number() + " saldo disponible $ "
								+ cuenta.getAccount_balance() + " "
								+ cuenta.getAccount_currency_id().getCurrency_symbol());
						cuentasUsuario.add(cuenta);
					}
				});
			}
		});
		System.out.println("\n" + "_".repeat(120));
		return cuentasUsuario;
	}

	/**
	 * lista porpantalla transferencias realizadas 
	 *
	 * @param transferencias, usuario autorizado
	 * @return void
	 */
	public static void mostrarHistorialTrf(List<Transaction> transferencias, UserAuth userAuth) {
		if (transferencias.size() > 0) {
			tituloHistorialTransferencias();
			for (Transaction trf : transferencias) {
				if (trf.getTr_sender_account_id().getAccount_user().getUser_username().equals(userAuth.getUsername())) {
					printTransaccionDetail(trf);
				}
			}
		} else {
			System.out.println("\nNo existen registros de transacciones que mostrar");
		}
		System.out.println("-".repeat(120));
	}

	/**
	 * lista por pantalla del detalle una transferencia realizada
	 *
	 * @param transferencia
	 * @return void
	 */
	public static void printTransaccionDetail(Transaction trf) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("id: " + trf.getTr_id() + " " + trf.getTr_number() + " "
				+ trf.getTr_sender_account_id().getAccount_user().getUser_firstname() + " "
				+ trf.getTr_sender_account_id().getAccount_user().getUser_lastname() + " "
				+ dateFormat.format(trf.getTr_date()) 
				+ " ctas:" + " " 
				+ trf.getTr_sender_account_id().getAccount_number() 
				+ " -> " 
				+ trf.getTr_receiver_account_id().getAccount_number() 
				+ " " 
				+ trf.getTr_amount_sender() + " "
				+ trf.getTr_sender_account_id().getAccount_currency_id().getCurrency_symbol() + " -> "
				+ trf.getTr_amount_receiver() + " "
				+ trf.getTr_receiver_account_id().getAccount_currency_id().getCurrency_symbol() + " "
				+ trf.getTr_detail() + " - Estado: " + (trf.isTr_state() ? "Activa" : "Reversada"));
	}

	/**
	 * Mensaje operación exitosa
	 *
	 * @param cuenta origen, saldo origen, cuenta destino, saldo destino
	 * @return void
	 */
	public static void mensajeOperacionExitosa(Account cuentaOrigen, BigDecimal saldoOrigen, Account cuentaDestino,
			BigDecimal saldoDestino) {
		System.out.println(".".repeat(120));
		System.out.println(" ".repeat(20) + "* * * Transferencia exitosa * * *");
		System.out.println(".".repeat(120));
		System.out.println("\nObservación: Se muestras ambos saldos para test del proceso");
		System.out.println("\nEl nuevo saldo Cuenta origen : " + cuentaOrigen.getAccount_number() + " $ " + saldoOrigen
				+ " " + cuentaOrigen.getAccount_currency_id().getCurrency_symbol());
		// check
		System.out.println("El nuevo saldo Cuenta destino: " + cuentaDestino.getAccount_number() + " $ " + saldoDestino
				+ " " + cuentaDestino.getAccount_currency_id().getCurrency_symbol());
	}
	/**
	 * lista por pantalla cuentas por pagar del usuario
	 *
	 * @param cuentas de pago, usuario autorizado
	 * @return void
	 */
	public static void listaCuentasPorPagar(List<AccountPayable> accountPayables, String userAuth) {
		if (accountPayables.size() > 0) {
			tituloCuentasPorPagar();
			for (AccountPayable ap : accountPayables) {
				if (ap.getUserAccount().getUser_username().equals(userAuth)) {
					System.out.println(" ".repeat(10)+"Id: " + ap.getIdPayable() + " " + ap.getNamePayable().toUpperCase() + " -> "
							+ ap.getUserAccount().getUser_firstname());
				}
			}
		} else {
			System.out.println("No hay Cuentas de Pago que mostrar");
		}
	}

	public static void tituloTransferencias() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Transferencias entre Usuarios");
	}

	public static void tituloMovimientosCuenta(String usuario) {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Movimiento de la Cuenta del Usuario <"+usuario+">");
	}
	
	public static void tituloCuentasPagoUsuario() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Lista Las Cuentas Pago del Usuario");
		System.out.println("-".repeat(120));
	}

	public static void tituloCuentasPagadasUsuario() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Pagos Efectuados");
		System.out.println("-".repeat(120));
	}

	public static void tituloSaldosCuentas() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Saldos Disponibles en Cuentas");
		System.out.println("-".repeat(120));
	}

	public static void tituloCuentasPorPagar() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(20) + "Listado de Cuentas de Pago o Por Pagar");
		System.out.println("-".repeat(120));
	}

	public static void tituloHistorialTransferencias() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(20) + "Transferencias Realizadas");
		System.out.println("-".repeat(120));
	}
	/**
	 * lista por pantalla datos de un usuario
	 *
	 * @param usuario
	 * @return void
	 */
	public static void imprimirDatosUsuario(User user) {
		String sexo = user.isUser_sex() ? "Hombre" : "Mujer";
		String ecivil = user.isUser_married() ? "casado" : "soltero";
		String estado = user.isUser_state() ? "Activo" : "No activo";
		System.out.println(" ".repeat(10) + "_____________Datos Del Usuario_________________");
		System.out.println();
		System.out.println(" ".repeat(10) + "Id      : " + user.getUser_id());
		System.out.println(" ".repeat(10) + "Nombre  : " + user.getUser_firstname());
		System.out.println(" ".repeat(10) + "Apellido: " + user.getUser_lastname());
		System.out.println(" ".repeat(10) + "DNI/RUT : " + user.getUser_identity_number());
		System.out.println(" ".repeat(10) + "Username: " + user.getUser_username());
		System.out.println(" ".repeat(10) + "Creación: " + user.getUser_created_at());
		System.out.println(" ".repeat(10) + "E.Civil : " + ecivil);
		System.out.println(" ".repeat(10) + "Edad    : " + user.isUser_age());
		System.out.println(" ".repeat(10) + "Correo  : " + user.getUser_email());
		System.out.println(" ".repeat(10) + "Sexo    : " + sexo);
		System.out.println(" ".repeat(10) + "Estado  : " + estado);
		System.out.println("-".repeat(120));
	}
	
	/**
	 * lista por pantalla detalle de una transferencia
	 *
	 * @param trasferencia
	 * @return void
	 */
	public static void imprimirDatosTransferencia(Transaction trf) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String estado = trf.isTr_state() ? "Activo" : "No activo";
		System.out.println("\n_____________Datos De La Transacción_________________");
		System.out.println();
		System.out.println("Id              : " + trf.getTr_id());
		System.out.println("Núumero Trf     : " + trf.getTr_number());
		System.out.println("Cuenta Origen   : " + trf.getTr_sender_account_id().getAccount_number() + " "
				+ trf.getTr_sender_account_id().getAccount_user().getUser_firstname() + " "
				+ trf.getTr_sender_account_id().getAccount_user().getUser_lastname());
		System.out.println("Monto Enviado   : " + trf.getTr_amount_sender());
		System.out.println(
				"Moneda          : " + trf.getTr_sender_account_id().getAccount_currency_id().getCurrency_symbol());
		System.out.println("Cuenta Destino  : " + trf.getTr_receiver_account_id().getAccount_number() + " "
				+ trf.getTr_receiver_account_id().getAccount_user().getUser_firstname() + " "
				+ trf.getTr_receiver_account_id().getAccount_user().getUser_lastname());
		System.out.println("Monto Recibido  : " + trf.getTr_amount_receiver());
		System.out.println(
				"Moneda          : " + trf.getTr_receiver_account_id().getAccount_currency_id().getCurrency_symbol());
		System.out.println("fecha           : " + dateFormat.format(trf.getTr_date()));
		System.out.println("Detalle         : " + trf.getTr_detail());
		System.out.println("Estado          : " + estado);
		System.out.println("_____________________________________________________\n");
	}

	public static void tituloCreacionUsuario() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "\nNUEVO USUARIO\n");
		System.out.println(" ".repeat(10) + "Se solicitará ingresar lo siguinte:\n");
		System.out.println(" ".repeat(10) + "Nombre, Apellido, DNI/RUT, E-Mail, Username, Password, Edad, Sexo, E.Civil\n");
		System.out.println("-".repeat(120));
	}

	public static void tituloUsuariosAutorizados() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "\nUSUARIOS AUTORIZADOS\n");
		System.out.println("-".repeat(120));
	}
	
	public static void tituloCreacionCuentasPago() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "\nNUEVA CUENTA DE PAGO\n");
		System.out.println("-".repeat(120));
	}

	public static void tituloCreacionNuevaCuenta() {
		System.out.println("\n" + "-".repeat(140));
		System.out.println(" ".repeat(10) + "Creación de Nueva Cuenta");
		System.out.println("-".repeat(140));
	}
	
	public static void tituloEliminaciónCuentasPago() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "\nELIMINACION DE CUENTAS PAGO");
		System.out.println("-".repeat(120));
	}

	public static void tituloEdicionUsuario() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println("\n"+" ".repeat(10) + "EDICION DE USUARIO\n");
		System.out.println(" ".repeat(10) + "Pude modifcar lo siguinte:\n");
		System.out.println(" ".repeat(10) + "NOMBRE, APELLIDO, DNI/RUT, E.CIVIL, EDAD, CORREO, SEXO\n");
		System.out.println("-".repeat(120) + "\n");
	}

	public static void tituloEdicionCuentas() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println("\n"+" ".repeat(10) + "EDICION DATOS DE LA CUENTA\n");
		System.out.println(" ".repeat(10) + "Pude modifcar lo siguinte:\n");
		System.out.println(" ".repeat(10) + "USUARIO, BANCO, TIPO CUENTA, MONEDA\n");
		System.out.println("-".repeat(120) + "\n");
		System.out.println(" ".repeat(10) + "( '0' mantiene sin modificar )");
	}

	public static void tituloDesactivarCuenta() {
		System.out.println("\n" + "4".repeat(120));
		System.out.println("\n"+" ".repeat(10) + "DESACTIVACION DE LA CUENTA\n");
		System.out.println("-".repeat(120) + "\n");
	}
	
	/**
	 * lista por pantalla detalle de una cuenta
	 *
	 * @param cuenta
	 * @return void
	 */
	public static void imprimirDatosCuenta(Account cuenta) {
		String estado = cuenta.isAccount_state() ? "Activo" : "No activo";
		System.out.println("\n_____________Información de la Cuenta_________________");
		System.out.println();
		System.out.println(" ".repeat(10) + "Id      : " + cuenta.getAccount_id());
		System.out.println(" ".repeat(10) + "Usuario : " + cuenta.getAccount_user().getUser_firstname() + " "
				+ cuenta.getAccount_user().getUser_lastname());
		System.out.println(" ".repeat(10) + "Número  : " + cuenta.getAccount_number());
		System.out.println(" ".repeat(10) + "Balance : " + cuenta.getAccount_balance());
		System.out.println(" ".repeat(10) + "Moneda  : " + cuenta.getAccount_currency_id().getCurrency_symbol());
		System.out.println(" ".repeat(10) + "Apertura: " + cuenta.getAccount_created_at());
		System.out.println(" ".repeat(10) + "Tipo    : " + cuenta.getAccount_type_id().getToa_name());
		System.out.println(" ".repeat(10) + "Banco   : " + cuenta.getAccount_bank_id().getbank_name());
		System.out.println(" ".repeat(10) + "Estado  : " + estado);
		System.out.println(" ".repeat(120));
	}


	
	public static void mantenedor(String proceso) {
		System.out.println("\n" + " ".repeat(120));
		System.out.println(dateFormat.format(new Date()));
		System.out.println((" - " + proceso).repeat(6));
		System.out.println();
		System.out.println(" ".repeat(20) + " MANTENEDOR " + proceso);
		System.out.println(" ".repeat(20) + "=".repeat(40));
		System.out.println(" ".repeat(20) + " 1 Lista");
		System.out.println(" ".repeat(20) + " 2 Buscar");
		if (!proceso.equals("TRANSACCIONES")) {
			System.out.println(" ".repeat(20) + " 3 Editar (admin)");
			System.out.println(" ".repeat(20) + " 4 Crea");
			System.out.println(" ".repeat(20) + " 5 Desactivar (admin)");
		} else {
			System.out.println(" ".repeat(20) + " 3 Reversar");
		}
		System.out.println(" ".repeat(20) + " 0 menu anterior");
		System.out.println(" ".repeat(20) + "=".repeat(40));
		System.out.print(" ".repeat(20) + "   Opción ? ");
	}

	public static void tituloUsuarios() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Lista todos los Usuarios");
		System.out.println("-".repeat(120));
	}

	public static void tituloContactosUsuarios() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Lista todos los Contactos del Usuario");
		System.out.println("-".repeat(120));
	}

	public static void tituloAgregaContactosUsuarios() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Agrega Nuevos contactos");
		System.out.println("-".repeat(120));
	}
	
	public static void tituloUsuario() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Lista Datos de Usuario");
		System.out.println("-".repeat(120));
	}

	public static void tituloCuentas() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Mis Cuentas registradas");
		System.out.println("-".repeat(120));
	}

	public static void tituloCuenta() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Información de la Cuenta");
		System.out.println("-".repeat(120));
	}

	public static void tituloMTransferencias() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println("Lista todos las Transferencias");
		System.out.println("-".repeat(120));
	}

	public static void tituloTransferencia() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Lista Datos de la Transferencia");
		System.out.println("-".repeat(120));
	}

	public static void tituloReversarTransferencia() {
		System.out.println("\n" + "-".repeat(120));
		System.out.println(" ".repeat(10) + "Proceso Reversa Operación de Transferencia");
		System.out.println("-".repeat(120));
	}

}
