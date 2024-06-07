package bmva.digiwallet.services;

import java.math.BigDecimal;
import java.util.List;

import bmva.digiwallet.dto.AccountDto;
import bmva.digiwallet.models.Account;
import bmva.digiwallet.models.User_;

public interface IAccountService{
	
	public Account save(AccountDto account);
	
	public List<Account> findByUser(User_ user);

	public Account buscarPorId(String idCuenta);
	
	public Account buscarPorNroCuenta(String nrocuenta);
	
	public Account depositar(BigDecimal nuevoBalance, String id);
	
	public Account retirar(BigDecimal montoOperacion, String id);
	
	public List<Account> findByUserWithoutOneIdAccount(String userId, String idAccount);
}
