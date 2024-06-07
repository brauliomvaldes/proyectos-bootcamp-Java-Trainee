package service;

import java.text.SimpleDateFormat;
import java.util.List;

import entities.Transaction;
import persistence.IEntitiesService;

public class TransactionService implements IEntitiesService<Transaction>{

	public void findAll(List<Transaction> transactions){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		transactions.forEach(t->{
			System.out.println("id: " + t.getTr_id()+"fecha: "+
								dateFormat.format(t.getTr_date())+" "+"trf:"+
								t.getTr_number()+" "+
								t.getTr_sender_account_id().getAccount_user().getUser_firstname()+" "+
								t.getTr_sender_account_id().getAccount_user().getUser_lastname()+" "+
								t.getTr_sender_account_id().getAccount_number()+"-->"+
								t.getTr_receiver_account_id().getAccount_number()+" "+
								t.getTr_amount_sender()+" "+
								t.getTr_receiver_account_id().getAccount_currency_id().getCurrency_symbol()+
								" estado: "+(t.isTr_state()?"Activa":"Reversada")
								);
		});
	}
	
	public Transaction findById(int id, List<Transaction> transactions) {
		Transaction trf = transactions.stream().filter(t->t.getTr_id()==id).findFirst().orElse(null);
		return trf;
	}
	
	public List<Transaction> edit(int id, Transaction Transaction, List<Transaction> transactions) {
		// no implementada
		return null;
	}
	
	public List<Transaction> save(Transaction Transaction, List<Transaction> transactions) {
		transactions.add(Transaction);
		return transactions;
	}
	
	public List<Transaction> delete(int id, List<Transaction> transactions) {
		// desactiva transacion luego de ser revertida la operación
		for(int i=0; i<transactions.size();i++) {
			if(transactions.get(i).getTr_id()==id) {
				transactions.get(i).setTr_state(false);
				break;
			}
		}
		return transactions;
	}
	
}
