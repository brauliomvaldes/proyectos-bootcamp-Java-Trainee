package entities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User{
	
	private int user_id;  
	private String user_firstname; 
	private String user_lastname; 
	private String user_identity_number;  
	private String user_email;
	private String user_username; 
	private String user_password_hash; 
	private Date user_created_at; 
	private short user_age; 
	private boolean user_sex; 
	private boolean user_married;
	private List<User> user_contact;
	private boolean user_state; 
	private List<Address> address;
	
	  
	public User() {};
	
	public User(int user_id, String user_firstname, String user_lastname, String user_identity_number, String user_email,
			String user_username, String user_password_hash, Date user_created_at, short user_age, boolean user_sex,
			boolean user_married, List<User> user_contact, boolean user_state, List<Address> address) {
		this.user_id = user_id;
		this.user_firstname = user_firstname;
		this.user_lastname = user_lastname;
		this.user_identity_number = user_identity_number;
		this.user_email = user_email;
		this.user_username = user_username;
		this.user_password_hash = user_password_hash;
		this.user_created_at = user_created_at;
		this.user_age = user_age;
		this.user_sex = user_sex;
		this.user_married = user_married;
		this.user_contact = user_contact;
		this.user_state = user_state;
		this.address = address;
	}

	public User(User user) {
		// para copiar algunos campos que son editados 
		this.user_firstname = user.user_firstname;
		this.user_lastname = user.user_lastname;
		this.user_identity_number = user.user_identity_number;
		this.user_email = user.user_email;
		this.user_age = user.user_age;
		this.user_sex =user. user_sex;
		this.user_married = user.user_married;
	}

	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getUser_firstname() {
		return user_firstname;
	}


	public void setUser_firstname(String user_name) {
		this.user_firstname = user_name;
	}


	public String getUser_lastname() {
		return user_lastname;
	}


	public void setUser_lastname(String user_lastname) {
		this.user_lastname = user_lastname;
	}


	public String getUser_identity_number() {
		return user_identity_number;
	}


	public void setUser_identity_number(String user_identity_number) {
		this.user_identity_number = user_identity_number;
	}


	public String getUser_email() {
		return user_email;
	}


	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}


	public String getUser_username() {
		return user_username;
	}


	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}


	public String getUser_password_hash() {
		return user_password_hash;
	}


	public void setUser_password_hash(String user_password_hash) {
		this.user_password_hash = user_password_hash;
	}


	public Date getUser_created_at() {
		return user_created_at;
	}


	public void setUser_created_at(Date user_created_at) {
		this.user_created_at = user_created_at;
	}


	public short isUser_age() {
		return user_age;
	}


	public void setUser_age(short user_age) {
		this.user_age = user_age;
	}


	public boolean isUser_sex() {
		return user_sex;
	}


	public void setUser_sex(boolean user_sex) {
		this.user_sex = user_sex;
	}


	public boolean isUser_married() {
		return user_married;
	}


	public void setUser_married(boolean user_married) {
		this.user_married = user_married;
	}


	public List<User> getUser_contact() {
		return user_contact;
	}


	public void setUser_contact(List<User> user_contact) {
		this.user_contact = user_contact;
	}


	public boolean isUser_state() {
		return user_state;
	}


	public void setUser_state(boolean user_state) {
		this.user_state = user_state;
	}


	public List<Address> getAddress() {
		return address;
	}


	public void setAddress(ArrayList<Address> address) {
		this.address = address;
	}

}
