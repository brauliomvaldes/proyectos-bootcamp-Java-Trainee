package entities;

public class TypeOfAccount{
	
	private int toa_id;  
	private String toa_name; 
	private boolean toa_state;  
	
	public TypeOfAccount() {};
	  
	public TypeOfAccount(int toa_id, String toa_name, boolean toa_state) {
		super();
		this.toa_id = toa_id;
		this.toa_name = toa_name;
		this.toa_state = toa_state;
	}


	public int getToa_id() {
		return toa_id;
	}


	public void setToa_id(int toa_id) {
		this.toa_id = toa_id;
	}


	public String getToa_name() {
		return toa_name;
	}


	public void setToa_name(String toa_name) {
		this.toa_name = toa_name;
	}


	public boolean isToa_state() {
		return toa_state;
	}


	public void setToa_state(boolean toa_state) {
		this.toa_state = toa_state;
	}

}
