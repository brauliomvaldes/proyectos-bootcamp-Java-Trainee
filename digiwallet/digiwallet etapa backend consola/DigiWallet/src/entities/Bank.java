package entities;

public class Bank  {

	private int bank_id;
	private String bank_name;
	private boolean bank_state;

	public Bank() {};
	
	public Bank(int bank_id, String bank_name, boolean bank_state) {
		super();
		this.bank_id = bank_id;
		this.bank_name = bank_name;
		this.bank_state = bank_state;
	}

	public int getBank_id() {
		return bank_id;
	}

	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}

	public String getbank_name() {
		return bank_name;
	}

	public void setbank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public boolean isBank_state() {
		return bank_state;
	}

	public void setBank_state(boolean bank_state) {
		this.bank_state = bank_state;
	}

}