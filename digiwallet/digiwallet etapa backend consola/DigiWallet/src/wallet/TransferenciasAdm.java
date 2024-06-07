package wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import entities.Account;
import entities.Transaction;
import service.AccountService;
import service.TransactionService;
import service.ValidadorNumerico;
import vistas.Mostrar;

public class TransferenciasAdm {
	/**
	 * Menu mantenedor de Transferencias
	 *
	 * @param transferencias, cuentas, scanner
	 * @return void
	 */
	public static void mantenedorTransferencias(List<Transaction> transacciones, List<Account> cuentas,
			Scanner leeteclado) {
		String opcion;
		TransactionService trf = new TransactionService(); // instancia servicios
		AccountService ctasService = new AccountService();
		String mensaje = "";
		do { // menu transacciones
			Mostrar.mantenedor("TRANSACCIONES");
			opcion = leeteclado.next();
			leeteclado.nextLine();

			switch (opcion) {
			case "1":
				Mostrar.tituloMTransferencias();
				trf.findAll(transacciones);
				break;
			case "2":
				Mostrar.tituloTransferencia();
				mensaje = "\nid Transferencia a mostrar ? ";
				System.out.print(mensaje);
				int id = ValidadorNumerico.validaInt(leeteclado, mensaje);
				Transaction trfxId = trf.findById(id, transacciones);
				if (trfxId != null) {
					Mostrar.imprimirDatosTransferencia(trfxId);
				}
				break;
			case "3":
				Mostrar.tituloReversarTransferencia();
				mensaje = "\nid Transferencia ? ";
				System.out.print(mensaje);
				int idTrf = ValidadorNumerico.validaInt(leeteclado, mensaje);
				Transaction trfReversar = trf.findById(idTrf, transacciones);
				if (trfReversar != null) {
					try {
						if (trfReversar.isTr_state()) { // proceso para reversar fondos originados por la transferencia
														// señalada
							// recupera cuentas y montos involucrados
							String cuentaOrigen = trfReversar.getTr_sender_account_id().getAccount_number();
							BigDecimal montoCuentaOrigen = trfReversar.getTr_amount_sender();
							String cuentaDestino = trfReversar.getTr_receiver_account_id().getAccount_number();
							BigDecimal montoCuentaDestino = trfReversar.getTr_amount_receiver();
							boolean resultado = ctasService.reversar(cuentas, cuentaOrigen, montoCuentaOrigen,
									cuentaDestino, montoCuentaDestino);
							if (resultado)
								trf.delete(idTrf, transacciones);
						} else {
							System.out.println(
									"\nNo se puede realizar la operación, la transferencia señalada ya fue reversada");
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("¡ Error !, no existe la transferencia señalada");
				}
				break;
			}
		} while (!opcion.equals("0"));
	};

}
