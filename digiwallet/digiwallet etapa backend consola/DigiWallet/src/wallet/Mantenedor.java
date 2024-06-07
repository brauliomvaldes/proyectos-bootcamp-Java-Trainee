package wallet;

import java.util.List;
import java.util.Scanner;

import entities.Account;
import entities.Bank;
import entities.Currencyy;
import entities.Transaction;
import entities.TypeOfAccount;
import entities.User;
import entities.UserAuth;
import service.AuthService;
import vistas.Mostrar;
/**
 * Clase para el usuario Admin
 * Sus métodos son empleados para manipular la información
 */
public final class Mantenedor {

	/**
	 * Menu proceso mantención de usuario, cuentas y transferencias
	 *
	 * @param usuarios, cuentas, transacciones, bancos, tipos de cuentas,
	 * 		   monedas, usuario autorizado
	 *         
	 * @return void
	 */
	public static final void menu(List<User> usuarios, 
			List<Account> cuentas,	List<Transaction> transacciones, 
			List<Bank> bancos, List<TypeOfAccount> toas,
			List<Currencyy> monedas, UserAuth userAuth,
			List<UserAuth> auths) {
		Scanner leeteclado = new Scanner(System.in);
		String opcion;
		do {
			Mostrar.menuMantenedor(userAuth.getUsername()); // menu mantención de registros
			opcion = leeteclado.next();
			leeteclado.nextLine();
			switch (opcion) {
			case "1":
				UsuariosAdm.mantenedorUsuarios(usuarios, leeteclado, userAuth, auths);
				break;
			case "2":
				CuentasAdm.mantenedorCuentas(cuentas, usuarios, bancos, toas, monedas, leeteclado, userAuth);
				break;
			case "3":
				if (transacciones.size() > 0) {
					TransferenciasAdm.mantenedorTransferencias(transacciones, cuentas, leeteclado);
				} else {
					System.out.println("\n"
							+ "		¡ Sin movimientos !\n		no hay transferencias que procesar.\n		Las transferencias se pueden crear en opción 3 del Menu Principal de Procesos");
				}
				break;
			case "4":
				AuthService authService = new AuthService();
				Mostrar.tituloUsuariosAutorizados();
				authService.listAllAuthUser(auths);
			case "0":
				break;
			}
		} while (!opcion.equals("0"));
		return;
	}
}
