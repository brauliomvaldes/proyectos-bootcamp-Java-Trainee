package service;

import java.text.SimpleDateFormat;
import java.util.List;

import entities.AccountPaid;
import persistence.IEntitiesService;

public class AccountPaidService implements IEntitiesService<AccountPaid>{

	@Override
	public void findAll(List<AccountPaid> accountPaids) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		accountPaids.forEach(ap -> {
			System.out.println(" ".repeat(10) + "id: " + ap.getIdAccountPaid()+" "
					+"pago: "+ap.getAccountPayable().getNamePayable().toUpperCase()+ " monto: "
					+ap.getAmountPaid()+" " 
					+ap.getAccountUser().getAccount_currency_id().getCurrency_symbol()+" "
					+" Método Pago: " + ap.getPaymentMethod()+" fecha: "
					+dateFormat.format(ap.getDatePaid())
					);
		});		
	}

	@Override
	public AccountPaid findById(int id, List<AccountPaid> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountPaid> edit(int id, AccountPaid o, List<AccountPaid> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountPaid> save(AccountPaid accountPaid, List<AccountPaid> accountPaids) {
		int id = accountPaids.size();
		id++;
		accountPaid.setIdAccountPaid(id);
		accountPaids.add(accountPaid);
		return accountPaids;
	}

	@Override
	public List<AccountPaid> delete(int id, List<AccountPaid> t) {
		// TODO Auto-generated method stub
		return null;
	}

}
