package entities;

public class AccountPayable {

	private int idPayable;
	private String namePayable;
	private User userAccount;
	
	 
	public AccountPayable(int idPayable, String namePayable, User userAccount) {
		this.idPayable = idPayable;
		this.namePayable = namePayable;
		this.userAccount = userAccount;
	}
	public int getIdPayable() {
		return idPayable;
	}
	public void setIdPayable(int idPayable) {
		this.idPayable = idPayable;
	}
	public String getNamePayable() {
		return namePayable;
	}
	public void setNamePayable(String namePayable) {
		this.namePayable = namePayable;
	}
	
	public User getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(User userAccount) {
		this.userAccount = userAccount;
	}
	
}
