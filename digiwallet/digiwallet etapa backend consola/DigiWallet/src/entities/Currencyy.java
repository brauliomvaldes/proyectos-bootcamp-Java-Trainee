package entities;

public class Currencyy{
	
	private int currency_id;  
	private String currency_name; 
	private String currency_symbol; 
	private boolean currency_state;
	
	public Currencyy() {};
	
	public Currencyy(int currency_id, String currency_name, String currency_symbol, boolean currency_state) {
		super();
		this.currency_id = currency_id;
		this.currency_name = currency_name;
		this.currency_symbol = currency_symbol;
		this.currency_state = currency_state;
	}


	public int getCurrency_id() {
		return currency_id;
	}


	public void setCurrency_id(int currency_id) {
		this.currency_id = currency_id;
	}


	public String getCurrency_name() {
		return currency_name;
	}


	public void setCurrency_name(String currency_name) {
		this.currency_name = currency_name;
	}


	public String getCurrency_symbol() {
		return currency_symbol;
	}


	public void setCurrency_symbol(String currency_symbol) {
		this.currency_symbol = currency_symbol;
	}


	public boolean isCurrency_state() {
		return currency_state;
	}


	public void setCurrency_state(boolean currency_state) {
		this.currency_state = currency_state;
	}

}
