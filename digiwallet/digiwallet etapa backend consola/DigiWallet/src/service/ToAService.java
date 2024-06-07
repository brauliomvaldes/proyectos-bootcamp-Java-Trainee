package service;

import java.util.List;

import entities.TypeOfAccount;
import persistence.IEntitiesService;

public class ToAService implements IEntitiesService<TypeOfAccount>{

	@Override
	public void findAll(List<TypeOfAccount> toas) {
		toas.forEach(t -> {
			System.out.println(" ".repeat(10) + "Id : " + t.getToa_id()+" "+t.getToa_name());
		});
	}

	@Override
	public TypeOfAccount findById(int id, List<TypeOfAccount> toas) {
		TypeOfAccount toa = toas.stream().filter(t->t.getToa_id()==id).findFirst().orElse(null);
		return toa;
	}

	@Override
	public List<TypeOfAccount> edit(int id, TypeOfAccount o, List<TypeOfAccount> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeOfAccount> save(TypeOfAccount toa, List<TypeOfAccount> toas) {
		int id = toas.size();
		id++;
		toa.setToa_id(id);
		toas.add(toa);
		return toas;
	}

	@Override
	public List<TypeOfAccount> delete(int id, List<TypeOfAccount> t) {
		// TODO Auto-generated method stub
		return null;
	}

}
