package bmva.digiwallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bmva.digiwallet.models.Contact;
import bmva.digiwallet.models.User_;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long>{
	
	public List<Contact> findByUser(User_ user);
	
	
	@Query(value = "SELECT * FROM contacts WHERE number = ?1", nativeQuery = true)
    public Contact buscarPorNroCuenta(String nrocuenta);
	
}
