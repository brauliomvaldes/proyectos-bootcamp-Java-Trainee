package wallet;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import entities.Account;
import entities.Role;
import entities.Transaction;
import entities.User;
import entities.UserAuth;
import service.AccountService;
import service.TransactionService;
import service.ValidadorNumerico;
import vistas.Mostrar;

public class Transferencias {
	/**
	 * Proceso de transferencias entre cuentas de Usuarios
	 *
	 * @param usuarios, cuentas, transferencias, scanner, id de transferencia, usuario autorizado
	 * @return void
	 */
	public static void transferenciasEntreCuentas(List<User> usuarios, List<Account> cuentas,
			List<Transaction> transferencias, Scanner leeteclado, int id_transferencia, UserAuth userAuth) {

		AccountService ctasService = new AccountService(); // instancias de servicios
		TransactionService trfService = new TransactionService();
		try {
			String continuar = null; // ciclo principal
			String nroCuentaOrigen, nroCuentaDestino; // cuentas seleccionadas
			do {
				Mostrar.tituloTransferencias();
				BigDecimal saldoOrigen = BigDecimal.ZERO; // almacenará el saldo de la cuenta que envia
				Account cuentaOrigen = new Account(); // almacena la cuenta origen
				BigDecimal saldoDestino = BigDecimal.ZERO; // almacenará el saldo de la cuenta que recibe
				Account cuentaDestino = new Account(); // almacena la cuenta destino
				int id_origen = 0; // almacena el id del usuario que esta procesando los pagos
				if (userAuth.getRole().equals(Role.ADMIN)) { // el ADMIN puede transferir entre cuentas
					Mostrar.listaUsuarios(usuarios, "SENDER"); // lista los clientes
					String mensaje = " ".repeat(10) + "- Ingrese Id del cliente origen   : ";
					System.out.print(mensaje);
					id_origen = ValidadorNumerico.validaInt(leeteclado,
							mensaje); /* solicta cuenta origen de los fondos en base a su id */
					leeteclado.nextLine();
				} else {
					id_origen = userAuth.getUser().getUser_id(); // almacena el id del usuario logeado
				}
				List<Account> cuentasCliente = new ArrayList<>(); // almacenar los números de cuentas de los
																		// clientes
				cuentasCliente = Mostrar.listaCuentasUsuario(usuarios, cuentas,
						id_origen); /* almacena las cuentas que posee el cliente origen */
				if (cuentasCliente != null) { // se mostraron las cuentas del usuario origen
					switch (cuentasCliente.size()) { // define cuenta origen
					case 0:
						throw new Exception(" ".repeat(10)
								+ "Cliente Origen sin cuenta(s) asociadas, debe crear cuenta"); /*
																								 * cliente sin cuentas
																								 * asociadas
																								 */
					case 1:
						cuentaOrigen = cuentasCliente.get(0); // toma la única cuenta almacenada
						break;
					default: // si el usuario tiene más de una cuenta solicta el nro de cuenta a utilizar
						do {
							System.out.print(
									" ".repeat(10) + "=> Es necesario especificar cual es nro cuenta de origen : ");
							nroCuentaOrigen = leeteclado.next().trim();
							cuentaOrigen = ctasService.validaNumeroCuenta(nroCuentaOrigen,
									cuentasCliente); /* validar que exista la cuenta ingresada */
							if (cuentaOrigen != null) {
								break; // cuenta seleccionada esta validada
							} else {
								System.out.println(" ".repeat(10) + "¡no existe la cuenta!, vuelva a intentar");
							}
						} while (true);
						break;
					}
					saldoOrigen = cuentaOrigen.getAccount_balance(); // recupera saldo de la cuenta origen
					Mostrar.listaUsuarios(usuarios, "RECEIVER"); // lista los clientes disponibles para recibir dinero
					String mensaje = " ".repeat(10) + "- Ingrese el id del cliente destino  : ";
					System.out.print(mensaje);
					int id_destino = ValidadorNumerico.validaInt(leeteclado, mensaje); // solicita el destino de los fondos
					leeteclado.nextLine();
					cuentasCliente = Mostrar.listaCuentasUsuario(usuarios, cuentas, id_destino); // muestra y cuentas
					if (cuentasCliente.size() > 0) { // se mostraron cuentas del distino
						switch (cuentasCliente.size()) { // para definir cuenta destino
						case 1:
							cuentaDestino = cuentasCliente.get(0); // toma la única cuenta almacenada
							break;
						default:
							do { // si el usuario tiene más de una cuenta solicta el nro de cuenta a utilizar
								System.out.print(
										" ".repeat(10) + "=> Es necesario especificar cual es nro cuenta de origen : ");
								nroCuentaDestino = leeteclado.next().trim();
								// validar que exista la cuenta ingresada
								cuentaDestino = ctasService.validaNumeroCuenta(nroCuentaDestino, cuentasCliente);
								if (cuentaDestino != null) {
									break;
								} else {
									System.out.println(" ".repeat(10) + "¡no existe la cuenta!, vuelva a intentar");
								}
							} while (true);
							break;
						} // la cuenta de origen y destino no pueden ser las mismas
						if (!cuentaOrigen.getAccount_number().equals(
								cuentaDestino.getAccount_number())) { /* valida que no operer las mismas cuentas */
							String monedaOrigen = cuentaOrigen.getAccount_currency_id()
									.getCurrency_symbol(); /* tipo de moneda de la cuenta origen */
							String monedaDestino = cuentaDestino.getAccount_currency_id()
									.getCurrency_symbol(); /* tipo de moneda de la cuenta destino */
							saldoDestino = cuentaDestino.getAccount_balance(); // recupera saldo origen
							System.out.println(" ".repeat(10) + "Saldo disponible de cuenta origen: " /*
																										 * informa el
																										 * saldo
																										 * disponible
																										 */
									+ cuentaOrigen.getAccount_number() + " $ " + saldoOrigen + " " + monedaOrigen);
							if (saldoOrigen.compareTo(BigDecimal.ZERO) == 1) { // si tiene saldo en cuenta para realizar transacciones
								BigDecimal factorCambio = solicitaFactor(monedaOrigen, monedaDestino,
										leeteclado); /* visualiza el saldo disponible de la cuenta origen */
								BigDecimal montoTransferir = solicitaMontoTransferir(monedaOrigen,
										leeteclado); /*
														 * almacena monto a transferir de la cuenta origen sin
														 * conversion
														 */
								BigDecimal montoTrfOrigen = montoTransferir; /* conserva el monto para rebajar del saldo */
								montoTransferir=calculoAplicaFactorConversion(montoTransferir, factorCambio); // aplica factor de cambio
								if (saldoOrigen.compareTo(montoTrfOrigen) >= 0) {
									System.out.print(" ".repeat(10) + "\nSe estan transferiendo " + montoTrfOrigen + " "
											+ monedaOrigen); /* informa el monto a transferir en la moneda de destino */
									if (factorCambio.compareTo(BigDecimal.ONE) != 0) {
										System.out.println(" ".repeat(10) + " -> con factor " + factorCambio
												+ " -> equivale a " + montoTransferir + " " + monedaDestino);
									} else {
										System.out.println();
									}
									System.out.print(" ".repeat(10)
											+ "\nAgregar un comentario a la transferencia : "); /*
																								 * agrega glosa a la
																								 * transferencia
																								 */
									if (leeteclado.hasNext()) {
										leeteclado.nextLine();  // limpia buffer teclado
									}
									String comentario = leeteclado.nextLine();
									System.out.print(" ".repeat(10)
											+ "\n¿ Confirma la transferencia 's' para confirma la operación ? ");
									String confirmaOperacion = leeteclado.next(); // solicta fonfirmar transacción
									if (confirmaOperacion.equalsIgnoreCase("s")) {
										System.out.print(" ".repeat(10) + "\n.... Se ha confirmado la operación\n");
										saldoDestino=saldoDestino.add(montoTransferir); // calcula el nuevo saldo de la cuenta origen
										saldoOrigen=saldoOrigen.subtract(montoTrfOrigen); // calcula el nuevo saldo de la cuenta destino
										ingreso(saldoDestino, cuentas, cuentaDestino); // aumenta saldo cuenta destino
										reintegro(saldoOrigen, cuentas, cuentaOrigen); // disminuye saldo cuenta origen
										id_transferencia++; // genera id de transferencia
										Transaction trf = configuraObjetoTransferencia(montoTrfOrigen, montoTransferir,
												comentario, cuentaOrigen, cuentaDestino,
												id_transferencia); /* configura la entidad transferencia */
										trfService.save(trf, transferencias); /*
																				 * registra la transferencia en registro
																				 * de transferencia
																				 */
										Mostrar.mensajeOperacionExitosa(cuentaOrigen, saldoOrigen, cuentaDestino,
												saldoDestino);
									}
								} else {
									System.out.print("El monto ingresado supera el saldo disponible");
								}
							} else {
								System.out.print("No cuenta con suficientes fondos para transferir");
							}
						} else {
							System.out.println("\n¡ Error !, no es posible realizar la transferencia,"
									+ "\n  ... las cuentas o clientes deben ser distintos");
						}
					} else {
						System.out.println("\n¡ Error !, la cuenta seleccionada no corresponde al cliente destino");
					}

					//
					do {
						System.out.print(
								"\n" + "?".repeat(120) + "\n ¿ Desea realizar otra transferencia de dinero s/n ? ");
						continuar = leeteclado.next();
					} while (!continuar.equals("s") && !continuar.equals("n"));
				} else {
					System.out.println("¡Error!, el usuario origen no tiene cuentas para realizar transferencias");
				}

			} while (!continuar.equalsIgnoreCase("n"));

		} catch (Exception e) {
			System.out.println("\n¡ Ha ocurrido un error al procesar los datos, la operación es cancelada !");
			System.out.println(".." + e.getMessage());
		}
	}

	/**
	 * Define el factor de conversión en operación entre cuentas con distintas monedas
	 *
	 * @param moneda de origen, moneda de destino, scanner
	 * @return valor tipo double 
	 */
	// ingreso de factor de conversion de moneda
	public static BigDecimal solicitaFactor(String monedaOrigen, String monedaDestino, Scanner leeteclado) {
		BigDecimal factorCambio = BigDecimal.ONE;
		if (!monedaOrigen.equals(monedaDestino)) {
			System.out
					.println("\n" + " ".repeat(10) + "Esta transfiriendo de " + monedaOrigen + " -> " + monedaDestino);
			System.out.println(" ".repeat(10) + "Debe ingresar el factor de conversión a moneda destino");
			String mensaje = " ".repeat(10) + "Favor indicar ¿ 1 " + monedaOrigen + " equivale a cuentos "
					+ monedaDestino + " ? ";
			System.out.print(mensaje);
			factorCambio = ValidadorNumerico.validaBigDecimal(leeteclado, mensaje); // pide factor de cambio de moneda
		}
		return factorCambio;
	}
	
	/**
	 * Aplica factor de conversión al monto a transferir
	 *
	 * @param monto a transferir, factor de cambio
	 * @return valor tipo double valor convertido a moneda destino
	 */
	public static BigDecimal calculoAplicaFactorConversion(BigDecimal montoTransferir , BigDecimal factorCambio) {
		return montoTransferir.multiply(factorCambio); // aplica factor de cambio
	}
	
	/**
	 * Solicita el monto de la transferencia
	 *
	 * @param moneda de origen, scanner
	 * @return valor tipo double
	 */
	public static BigDecimal solicitaMontoTransferir(String monedaOrigen, Scanner leeteclado) {
		BigDecimal montoTransferir = BigDecimal.ZERO;
		String mensaje = " ".repeat(10) + "Ingrese el monto a transferir " + monedaOrigen + " $ ";
		System.out.print(mensaje);
		montoTransferir = ValidadorNumerico.validaBigDecimal(leeteclado, mensaje);
		return montoTransferir;
	}

	/**
	 * Configiura o preparar el objeto tipo transferencia con los datos aportados en la operación
	 *
	 * @param monto origen transferencia, monto a transferir, comentario, cuenta origen, cuenta destino, 
	 *        id de la transferencia 
	 * @return objeto tipo transferencia
	 */
	public static Transaction configuraObjetoTransferencia(BigDecimal montoTrfOrigen, BigDecimal montoTransferir,
			String comentario, Account cuentaOrigen, Account cuentaDestino, int id_transferencia) {
		Random rand = new Random(); 
		int contadorTransacciones = rand.nextInt(1000000); // el nro trf será aleatorio para fines prácticos
		Transaction trf = new Transaction(); // crea la entidad transferencia
		trf.setTr_id(id_transferencia); // proceso de poblado de datos
		trf.setTr_amount_sender(montoTrfOrigen);
		trf.setTr_amount_receiver(montoTransferir);
		trf.setTr_date(new Date());
		trf.setTr_detail(comentario);
		trf.setTr_number(String.valueOf(contadorTransacciones));
		trf.setTr_receiver_account_id(cuentaDestino);
		trf.setTr_sender_account_id(cuentaOrigen);
		trf.setTr_state(true);
		return trf;
	}

	/**
	 * Aumenta el saldo de la cuenta destino de transferencia
	 *
	 * @param saldo de cuenta destino, cuentas, cuenta destino
	 * @return void
	 */
	public static void ingreso(BigDecimal saldoDestino, List<Account> cuentas, Account cuentaDestino) {
		for (Account c : cuentas) {
			if (c.getAccount_number().equals(cuentaDestino.getAccount_number())) {
				c.setAccount_balance(saldoDestino);  // actualiza saldo en la cuenta destino transferencia
			}
		}
	}

	/**
	 * Rebaja el saldo de la cuenta destino de transferencia
	 *
	 * @param saldo de cuenta origen, cuentas, cuenta origen
	 * @return void
	 */
	public static void reintegro(BigDecimal saldoOrigen, List<Account> cuentas, Account cuentaOrigen) {
		for (Account c : cuentas) {
			if (c.getAccount_number().equals(cuentaOrigen.getAccount_number())) {
				c.setAccount_balance(saldoOrigen); // actualiza saldo en la cuenta origen transferencia
			}
		}
	}

}
