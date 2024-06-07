package wallet;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

import entities.Account;
import entities.AccountPaid;
import entities.AccountPayable;
import entities.PaymentMethod;
import entities.UserAuth;
import service.AccountPaidService;
import service.AccountPayableService;
import service.AccountService;
import service.ValidadorNumerico;
import vistas.Mostrar;

public class Pagos {

	/**
	 * Menu procesamiento de Cuentas por pagar o Cuentas de pago
	 *
	 * @param scanner, cuentas, cuentas de pago, cuentas pagadas, usuario autorizado
	 * @return void
	 */
	public static void procesarPagos(Scanner leeteclado, List<Account> cuentas, List<AccountPaid> accountPaids,
			List<AccountPayable> accountPayables, UserAuth userAuth) {
		AccountPayableService aps = new AccountPayableService(); // instanciar extidades
		AccountPaidService apids = new AccountPaidService();
		AccountService ctasService = new AccountService();
		String opcion = "";
		do {
			Mostrar.menuPagos(userAuth.getUsername()); // menu opciones
			opcion = leeteclado.next();
			switch (opcion) {
			case "1":
				if (accountPayables.size() > 0) {
					int idCuentaPago = 0; // id del usuario que paga
					do {
						Mostrar.tituloCuentasPagoUsuario(); // listar cuentas por pagar
						aps.findAllByUser(accountPayables, userAuth); // buscas las cuentas de pago
						String mensaje = "\nId Cuenta (0 = termina) ? ";
						System.out.print(" ".repeat(10) + mensaje);
						idCuentaPago = ValidadorNumerico.validaInt(leeteclado, mensaje);
						if (idCuentaPago != 0) {
							AccountPayable ap = aps.findByIdPagoAndUser(idCuentaPago, accountPayables, userAuth); // seleccionar cuenta
							if (ap != null) {
								// solicitar monto
								mensaje = "\nMonto a pagar ? ";
								System.out.print(mensaje);
								BigDecimal montoAP = ValidadorNumerico.validaBigDecimal(leeteclado, mensaje);
								// recupera cuenta bancaria con la cual pagará la cuenta
								Account cuentaCliente = recuperaCuentaCliente(leeteclado, cuentas, userAuth, ctasService); 
								if (cuentaCliente != null) { // cuenta de pago seleccionada válida
									if (cuentaCliente.getAccount_balance().compareTo(montoAP) >= 0) {
										String FPago = seleccionFormaPago(leeteclado); // define forma de pago
										System.out.print("\nConfirma el pago = s ? "); // confirma el pago
										String confirmacion = leeteclado.next();
										if (confirmacion.equals("s")) {
											AccountPaid ctaPaid = // crear entidad cuenta pagada
													new AccountPaid(0, ap, FPago, new Date(), montoAP, cuentaCliente);
											String nroCuenta = cuentaCliente.getAccount_number(); // nro cta utilizada
																									// para el pago
											apids.save(ctaPaid, accountPaids); // registrar cuenta pagada
											boolean resultadoPago = ctasService.descontarDinero(cuentas, nroCuenta,
													montoAP); // Se efectua el pago descontando el saldo del
																// cliente
											if (!resultadoPago) {
												System.out.println(
														"grave, posible inconsistencia en saldos, revertir cambios\navise al administrador de la app");
											}
										}
									} else {
										System.out.println(
												"El monto ingresado (" + montoAP + ") es superior al saldo disponible");
									}
								} else {
									System.out.println("¡ Error !, cuenta bancaria no existe");
								}
							} else {
								System.out.println("¡ Error !, cuenta a pagar no existe, reintente con otra cuenta");
							}
						}
					} while (idCuentaPago > 0);
				} else {
					System.out.println("-".repeat(120));
					System.out.println("\nNo tiene cuentas por pagar registradas");
				}
				break;
			case "2":
				if (accountPayables.size() > 0) { // Listar Cuentas de Pago
					Mostrar.listaCuentasPorPagar(accountPayables, userAuth.getUsername());
				} else {
					System.out.println("-".repeat(120));
					System.out.println("\nNo tiene cuentas por pagar que mostrar");
				}
				break;
			case "3":
				String acuentaPago = ""; // Agregar Cuentas de Pago
				Mostrar.tituloCreacionCuentasPago();
				do {
					System.out.print(" ".repeat(10) + "\nNombre (fin = termina) ? ");
					acuentaPago = leeteclado.next().trim();
					if (!acuentaPago.equals("fin")) {
						AccountPayable ap = new AccountPayable(0, acuentaPago, userAuth.getUser());
						aps.save(ap, accountPayables);
						System.out.println("----------> creada <" + acuentaPago + ">");
						leeteclado.nextLine();
					} else {
						break;
					}
				} while (true);
				break;
			case "4":
				Mostrar.tituloEliminaciónCuentasPago();
				int idCuentaPago = 0; // para id Cuentas de Pago a eliminar
				do {
					Mostrar.listaCuentasPorPagar(accountPayables, userAuth.getUsername());
					String mensaje = " ".repeat(10) + "\nid Cuenta  (0 = termina) ? ";
					System.out.print(mensaje);
					idCuentaPago = ValidadorNumerico.validaInt(leeteclado, mensaje);
					if (idCuentaPago > 0) {
						aps.delete(idCuentaPago, accountPayables);
						leeteclado.nextLine();
					} else {
						break;
					}
				} while (true);
				break;
			case "5":
				if (accountPaids.size() > 0) { // Historial de Pagos
					Mostrar.tituloCuentasPagadasUsuario();
					apids.findAll(accountPaids);
				} else {
					System.out.println("-".repeat(120));
					System.out.println("\nNo tiene cuentas pagadas registradas");
				}
				break;
			default:
				break;
			}
		} while (!opcion.equals("0"));

	}

	/**
	 * Proceso selección forma de pago
	 *
	 * @param scanner
	 * @return String
	 */
	public static String seleccionFormaPago(Scanner leeteclado) {
		PaymentMethod[] mpagos = PaymentMethod.values();
		do {
			System.out.println("\nMétodos de Pago Disponibles");
			for (int i = 0; i < mpagos.length; i++) {
				System.out.println(" ".repeat(10) + "id:" + (i + 1) + " " + mpagos[i]);
			}
			String mensaje = "seleccione ? ";
			System.out.print(mensaje);
			int mp = ValidadorNumerico.validaInt(leeteclado, mensaje);
			if (mp > 0 && mp < mpagos.length) {
				String metodoSeleccionado = mpagos[mp - 1].toString();
				System.out.println("\n----> seleccionado: " + metodoSeleccionado);
				return metodoSeleccionado;
			}
		} while (true);
	}

	/**
	 * Busca y retorna Cuenta para realizar el pago
	 *
	 * @param scanner, cuentas, usuario autorizado, servicio cuentas
	 * @return Cuenta
	 */
	public static Account recuperaCuentaCliente(Scanner leeteclado, List<Account> cuentas, 
			UserAuth userAuth, AccountService ctasService) {
		int nroCuenta = 1;
		Account ctaDelCliente = null; // almacenará cuenta/s 
		List<Account> ctas = ctasService.findAllForUser(cuentas, userAuth); // despliega cuentas del usuario
		if (ctas != null) {
			if (ctas.size() > 1) {
				System.out.println("\nDebe seleccionar la cuenta que utilizará para pagar."); // seleccionar cuenta bancaria
				for (Account cta : ctas) {
					System.out.println(" ".repeat(10) + "Id: " + cta.getAccount_id() + " nro: "
							+ cta.getAccount_number() + " saldo " + cta.getAccount_currency_id().getCurrency_symbol()
							+ " " + cta.getAccount_balance());
				}
				String mensaje = "\nSeleccione id ? ";
				System.out.print(mensaje);
				nroCuenta = ValidadorNumerico.validaInt(leeteclado, mensaje);
			}else {
				nroCuenta = ctas.get(0).getAccount_id(); // toma el id de la única cuenta del usuario
			}
			ctaDelCliente = ctasService.findById(nroCuenta, ctas);
		} else {
			System.out.println(
					"Usted no posee Instrumentos Bancarios registrados \nno puede realizar una operación de pago");
		}
		return ctaDelCliente;
	}
}
