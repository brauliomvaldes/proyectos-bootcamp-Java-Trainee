package test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.Account;

/*
 * Test unitario para clase Account
 */
class AccountTest {

	Account cuenta;
	
	@BeforeEach
	void setUp() {
		this.cuenta = new Account();	
	}
	
	@Test
	void test() {
		// valida asignación de saldo a la cuenta
		cuenta.setAccount_balance(BigDecimal.valueOf(10000)); // abono inicial
		assertAll(
				() -> assertEquals(BigDecimal.valueOf(10000),cuenta.getAccount_balance())
		);
		// valida que el ingreso de dinero se sume al saldo de la cuenta 
		cuenta.ingreso(BigDecimal.valueOf(4000)); // abono de dinero
		assertAll(
				() -> assertEquals(BigDecimal.valueOf(14000),cuenta.getAccount_balance())
		);
		// comprueba que si se asigna un valor negativo en operación ingreso, se sume
		// el valor absoluto al saldo de la cuenta.
		cuenta.ingreso(BigDecimal.valueOf(-4000)); // abono de dinero valor negativo toma valor absoluto
		assertAll(
				() -> assertEquals(BigDecimal.valueOf(18000),cuenta.getAccount_balance())
		);
		// valida que el retiro (reintegro) de dinero se reste al saldo de la cuenta 
		cuenta.reintegro(BigDecimal.valueOf(2000)); // retiro de dinero
		assertAll(
				() -> assertEquals(BigDecimal.valueOf(16000),cuenta.getAccount_balance())
		);
		// comprueba que si se asigna un valor superior en operación retiro, no se 
		// realice la operacion, el saldo no se modifica
		cuenta.reintegro(BigDecimal.valueOf(20000)); // retiro de dinero mayor al saldo, mantiene saldo
		assertAll(
				() -> assertEquals(BigDecimal.valueOf(16000),cuenta.getAccount_balance())
		);
		// valida que el retiro (reintegro) de dinero se reste al saldo de la cuenta
		cuenta.reintegro(BigDecimal.valueOf(6000)); // retiro de dinero
		assertAll(
				() -> assertEquals(BigDecimal.valueOf(10000),cuenta.getAccount_balance())
		);
		// comprueba que si se asigna un valor negativo en operación retiro, se reste o rebaje
		// el valor absoluto al saldo de la cuenta.
		cuenta.reintegro(BigDecimal.valueOf(-6000)); // retiro valor negativo toma valor absoluto
		assertAll(
				() -> assertEquals(BigDecimal.valueOf(4000),cuenta.getAccount_balance())
		);
		 
	}

}
