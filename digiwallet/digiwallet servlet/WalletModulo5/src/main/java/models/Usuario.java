package models;

import java.math.BigDecimal;

public class Usuario {
	private int id;
	private String nombre;
	private BigDecimal balance;
	
	public Usuario() {}
	
	public Usuario(int id, String nombre, BigDecimal balance) {
		this.id = id;
		this.nombre = nombre;
		this.balance = balance;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	
	
}
