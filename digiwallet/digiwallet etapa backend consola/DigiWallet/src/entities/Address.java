package entities;

public class Address{
	
	private int address_id;
	private String address_street; 
	private City address_city; 
	private Country address_country; 
	private String address_phone_number;  
	  
	  
	public int getAddress_id() {
		return address_id;
	}


	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}


	public String getAddress_street() {
		return address_street;
	}


	public void setAddress_street(String address_street) {
		this.address_street = address_street;
	}


	public City getAddress_city() {
		return address_city;
	}


	public void setAddress_city(City address_city) {
		this.address_city = address_city;
	}


	public Country getAddress_country() {
		return address_country;
	}


	public void setAddress_country(Country address_country) {
		this.address_country = address_country;
	}


	public String getAddress_phone_number() {
		return address_phone_number;
	}


	public void setAddress_phone_number(String address_phone_number) {
		this.address_phone_number = address_phone_number;
	}

}
