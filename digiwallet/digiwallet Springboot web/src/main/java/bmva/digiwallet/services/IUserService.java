package bmva.digiwallet.services;

import bmva.digiwallet.dto.UserDto;
import bmva.digiwallet.models.User_;

public interface IUserService {

	public User_ save(UserDto userDto);
	
	public User_ buscaUsuarioPorEmailYPassword(String email, String password);
	
	public User_ findByEmail(String email);
}
