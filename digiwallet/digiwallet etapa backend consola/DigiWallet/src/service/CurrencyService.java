package service;

import java.util.List;

import entities.Currencyy;
import persistence.IEntitiesService;

public class CurrencyService implements IEntitiesService<Currencyy>{

	@Override
	public void findAll(List<Currencyy> c) {
		c.forEach(cy -> {
			System.out.println(" ".repeat(10) + "Id : " + cy.getCurrency_id()+" "+cy.getCurrency_name());
		});
	}

	@Override
	public Currencyy findById(int id, List<Currencyy> currencies) {
		Currencyy currency = currencies.stream().filter(c->c.getCurrency_id()==id).findFirst().orElse(null);
		return currency;
	}

	@Override
	public List<Currencyy> edit(int id, Currencyy o, List<Currencyy> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Currencyy> save(Currencyy o, List<Currencyy> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Currencyy> delete(int id, List<Currencyy> t) {
		// TODO Auto-generated method stub
		return null;
	}

}
