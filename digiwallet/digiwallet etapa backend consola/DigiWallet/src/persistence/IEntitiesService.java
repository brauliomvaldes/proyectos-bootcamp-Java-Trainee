package persistence;

import java.util.List;

public interface IEntitiesService<T>{

	void findAll(List<T> t);
	
	T findById(int id, List<T> t);
	
	List<T> edit(int id, T o, List<T> t);
	
	List<T> save(T o, List<T> t);
	
	List<T> delete(int id, List<T> t);
}
