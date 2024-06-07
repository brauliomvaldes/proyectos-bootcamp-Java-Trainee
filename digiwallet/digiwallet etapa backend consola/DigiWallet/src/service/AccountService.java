package service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import entities.Account;
import entities.UserAuth;
import persistence.IEntitiesService;

public class AccountService implements IEntitiesService<Account> {

	public void findAll(List<Account> accounts) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		accounts.forEach(a -> {
			System.out.println("id: " + a.getAccount_id() + " " + "Nº " + a.getAccount_number() + " a nombre de: "
					+ a.getAccount_user().getUser_firstname() + " " + a.getAccount_user().getUser_lastname() + " "
					+ " balance: " + a.getAccount_balance() + " " + a.getAccount_currency_id().getCurrency_symbol()
					+ " apertura: " + dateFormat.format(a.getAccount_created_at()) + " estado: "
					+ (a.isAccount_state() ? "Activo" : "Desactivado"));
		});
	}
	
	public List<Account> findAllForUser(List<Account> accounts, UserAuth userAuth) {
		List<Account> accountForUser = new ArrayList<>();
		for(Account account: accounts) {
			if(account.getAccount_user().getUser_id() == userAuth.getUser().getUser_id() ) {
				accountForUser.add(account);
			}
		}
		return accountForUser;
	}

	public Account findById(int id, List<Account> accounts) {
		return accounts.stream().filter(a -> a.getAccount_id() == id).findFirst().orElse(null);
	}

	public List<Account> edit(int id, Account account, List<Account> accounts) {
		Account accountEdit = this.findById(id, accounts);
		if (accountEdit != null) {
			for (int i = 0; i < accounts.size(); i++) {
				if (accounts.get(i).getAccount_id() == id) {
					// ingreso de datos modificados
					accounts.get(i).setAccount_user(account.getAccount_user());
					accounts.get(i).setAccount_bank_id(account.getAccount_bank_id());
					accounts.get(i).setAccount_currency_id(account.getAccount_currency_id());
					accounts.get(i).setAccount_type_id(account.getAccount_type_id());
					return accounts;
				}
			}
		}
		return accounts;
	}

	public List<Account> save(Account account, List<Account> accounts) {
		// simula auto_increment
		int idCuentaNueva = accounts.size();
		idCuentaNueva++;
		account.setAccount_id(idCuentaNueva);
		account.setAccount_state(true);
		// se registra el nuevo usuario

		accounts.add(account);
		return accounts;
	}
	
	public Account validaNumeroCuenta(String nroCuenta, List<Account> accounts) {
		return accounts.stream().filter(a->a.getAccount_number().equals(nroCuenta)).findFirst().orElse(null);
	}

	public List<Account> delete(int id, List<Account> accounts) {
		// busca la cuenta
		boolean existe = accounts.stream().anyMatch(a -> a.getAccount_id() == id);
		if (existe) {
			for (int i = 0; i < accounts.size(); i++) {
				if (accounts.get(i).getAccount_id() == id) {
					accounts.get(i).setAccount_state(false);
					System.out.println("Cuenta id :" + id + " fue desactivada");
				}
			}
		} else {
			System.out.println("Cuentas id :" + id + " no existe");
		}
		return accounts;
	}

	public boolean reversar(List<Account> accounts, String origen, BigDecimal origenDinero, String destino,
			BigDecimal destinoDinero) {
		// check saldo cuenta destino
		Account cuentaDestino = accounts.stream().filter(a->a.getAccount_number().equals(destino)).findFirst().orElse(null);
		Account cuentaOrigen = accounts.stream().filter(c->c.getAccount_number().equals(origen)).findFirst().orElse(null);
		// si no existe la(s) cuenta(s)
		if(cuentaDestino==null || cuentaOrigen==null) {
			System.out.println("\\n¡ Operación cancelada !, cuenta o cuentas no existen");
			return false;		
		}
		// si no tiene fondos suficientes para devolver dinero para revertirla transferencia
		if(cuentaDestino.getAccount_balance().compareTo(destinoDinero) == -1) {
			System.out.println("\n¡ Operación cancelada !, no es posible reversar los fondos para evitar sobregiro en la cuenta destino");
			return false;			
		}
		// se reversaran los fondos involucrados en la transacción
		for (int i = 0; i < accounts.size(); i++) {
			// rebaja monto a cuenta destino
			if (accounts.get(i).getAccount_number().equals(destino)) {
				// revisa si se origina un sobregiro
				accounts.get(i).reintegro(destinoDinero);
				System.out.println("Se rebajaron "+destinoDinero+" a cuenta destino Nº "+destino);
			}
			// ingresa monto a cuenta origen
			if (accounts.get(i).getAccount_number().equals(origen)) {
				accounts.get(i).ingreso(origenDinero);
				System.out.println("Se abonaron "+origenDinero+" a cuenta origen Nº "+origen);
			}
		}
		return true;
	}
	
	public boolean descontarDinero(List<Account> accounts, String nroCuenta, BigDecimal amount) {
		for(int i=0; i<accounts.size(); i++) {
			if(accounts.get(i).getAccount_number().equals(nroCuenta)) {
				accounts.get(i).reintegro(amount);
				return true;
			}
		}
		return false;
	}
	
	public BigDecimal abonarDinero(Account account, List<Account> accounts, BigDecimal amount) {
		BigDecimal balance = BigDecimal.ZERO;
		for(int i=0; i<accounts.size(); i++) {
			if(accounts.get(i).getAccount_number().equals(account.getAccount_number())){
				accounts.get(i).ingreso(amount);
				balance = accounts.get(i).getAccount_balance();
			}
		}
		return balance;
	}
	
	public BigDecimal retirarDinero(Account account, List<Account> accounts, BigDecimal amount) {
		BigDecimal balance = BigDecimal.ZERO;
		for(int i=0; i<accounts.size(); i++) {
			if(accounts.get(i).getAccount_number().equals(account.getAccount_number())){
				balance = accounts.get(i).getAccount_balance();
				if(balance.compareTo(balance) >= 0) {
					accounts.get(i).reintegro(amount);
				}
				balance = accounts.get(i).getAccount_balance();
			}
		}
		return balance;
	}
	
	
}
