package wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import entities.Account;
import entities.Transaction;
import entities.UserAuth;
import service.AccountService;
import service.TransactionService;
import service.ValidadorNumerico;
import vistas.Mostrar;

public class MovimientoDeCuenta {

	public static void abonosDepositos(Scanner leeteclado, UserAuth userAuth, List<Account> cuentas,
			List<Transaction> transferencias) {
		String mensaje = "";
		Transaction trf; // define objeto que contendra transferecias por abono y giros
		AccountService ctasService = new AccountService(); // instancia servicios
		TransactionService trfService = new TransactionService();
		int id_transferencia = transferencias.size(); // número de transferencias (simula auto_increment)
		do {
			Mostrar.tituloMovimientosCuenta(userAuth.getUsername());
			int idCuenta = 0;
			Mostrar.tituloCuentas();
			List<Account> cuentasUsuario = ctasService.findAllForUser(cuentas, userAuth); // buscas las cuentas del
																							// usuario
			ctasService.findAll(cuentasUsuario);
			mensaje = "\nId Cuenta a procesar (0 = termina) ? ";
			System.out.print(" ".repeat(10) + mensaje);
			idCuenta = ValidadorNumerico.validaInt(leeteclado, mensaje);
			if (idCuenta != 0) {
				Account cuentaUsuario = ctasService.findById(idCuenta, cuentasUsuario); // recupera la cuenta del
																						// usuario
				if (cuentaUsuario != null) {
					Account cuentaFake = cuentaUsuario.clone(); // clona cuenta como destino del giro u origen del abono
					mensaje = "\nIngrese monto de dinero a operar ? ";
					System.out.print(" ".repeat(10) + mensaje);
					BigDecimal montoOperacion = ValidadorNumerico.validaBigDecimal(leeteclado, mensaje);
					Mostrar.menuMovimientosDeCuenta(userAuth.getUsername()); // menu opciones
					String operacion = leeteclado.next();

					switch (operacion) {
					case "1":
						cuentaFake.setAccount_number("ABONO");
						ctasService.abonarDinero(cuentaUsuario, cuentasUsuario, montoOperacion);
						id_transferencia++; // incrementa el id de transferencias
						trf = Transferencias.configuraObjetoTransferencia(montoOperacion, montoOperacion,
								"ABONO DE DINERO", cuentaFake, cuentaUsuario, id_transferencia);

						trfService.save(trf, transferencias); // registra el abono de dinero como una transferencia

						System.out.println("\n" + " ".repeat(10) + "...... Abono de dinero realizado");
						break;
					case "2":
						if (cuentaUsuario.getAccount_balance().compareTo(montoOperacion) >= 0) {
							cuentaFake.setAccount_number("GIRO");
							ctasService.retirarDinero(cuentaUsuario, cuentasUsuario, montoOperacion);
							id_transferencia++; // incrementa el id de transferencias
							trf = Transferencias.configuraObjetoTransferencia(montoOperacion, montoOperacion,
									"GIRO DE DINERO", cuentaUsuario, cuentaFake, id_transferencia);

							trfService.save(trf, transferencias); // registra el retiro de dinero como una transferencia
							System.out.println("\n" + " ".repeat(10) + "...... Retiro de dinero realizado");
						} else {
							System.out.println("\n" + " ".repeat(10)
									+ "¡Error!, el monto ingresado es superior al saldo de la cuenta, operación cancelada");
						}
						break;
					case "0":
						return;
					default:
						System.out.println("opción inválida");
					}
				} else {
					System.out.println("La cuenta señalada no existe");
				}
			}else {
				break;
			}
		} while (true);
	}
}
