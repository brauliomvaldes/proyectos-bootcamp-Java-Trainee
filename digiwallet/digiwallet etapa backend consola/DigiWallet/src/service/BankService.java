package service;

import java.util.List;

import entities.Bank;
import persistence.IEntitiesService;

public class BankService implements IEntitiesService<Bank> {

	@Override
	public void findAll(List<Bank> bks) {
		bks.forEach(b -> {
			System.out.println(" ".repeat(10) + "Id : " + b.getBank_id()+" "+b.getbank_name());
		});
	}

	@Override
	public Bank findById(int id, List<Bank> banks) {
		// TODO Auto-generated method stub
		Bank bank = banks.stream().filter(b->b.getBank_id()==id).findFirst().orElse(null);
		return bank;
	}

	@Override
	public List<Bank> edit(int id, Bank o, List<Bank> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bank> save(Bank o, List<Bank> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bank> delete(int id, List<Bank> t) {
		// TODO Auto-generated method stub
		return null;
	}

}
