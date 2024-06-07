package entities;

public class UserAuth {

	private String username;
	private String password;
	private Role role;
	private User user;
	
	public UserAuth() {}
	
	public UserAuth(String username, String password, Role role, User user) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.user = user;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
