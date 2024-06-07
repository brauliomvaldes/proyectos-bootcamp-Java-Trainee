package bmva.digiwallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bmva.digiwallet.models.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, String>{

	// trae todos los movimientos del usuario, historial secuencial, salida o ingreso de dinero
	// el primer select recoge todas las transacciones donde sender o receiver sea el usuario
	// el segundo select devuelve una lista de cuentas del usuario
	
	@Query(value = "SELECT * FROM transactions t WHERE sender_id AND receiver_id IN (SELECT a.id FROM accounts a INNER JOIN users u ON a.user_id = u.id where a.user_id = ?1) ORDER BY t.date DESC", nativeQuery = true)
	public List<Transaction> findByIdSender(String id_user);

	// recupera sólo los movimientos por cliente y cuenta 
	// trae todos los movimientos del usurio, salida o ingreso de dinero
	// el primer select recoge todas las transacciones donde sender o receiver sea el usuario
	// el segundo select devuelve una lista de cuentas del usuario
	@Query(value = "SELECT * FROM (SELECT * FROM transactions t WHERE sender_id or receiver_id IN (SELECT a.id FROM accounts a INNER JOIN users u ON a.user_id = u.id WHERE a.user_id = ?1 )) AS trf WHERE trf.sender_id = ?2 OR trf.receiver_id = ?2 ORDER BY trf.date DESC", nativeQuery = true)
	public List<Transaction> findByIdUserAndIdAccount(String id_user, String id_account);
	
	
}
