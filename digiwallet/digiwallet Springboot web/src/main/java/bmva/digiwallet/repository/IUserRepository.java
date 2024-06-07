package bmva.digiwallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import bmva.digiwallet.models.User_;

@Repository
public interface IUserRepository extends JpaRepository<User_, String>{
	
	//Optional<User_> findUserEntityByUsername(String username);

	// No puedes buscar directamente por contraseña encriptada porque 
    // cada encriptación de bcrypt generará un resultado diferente incluso 
    // para la misma contraseña. En su lugar, deberías obtener el hash de la 
    // contraseña de la base de datos y luego compararlo con el hash de la 
    // contraseña ingresada utilizando bcrypt.
	
	@Query("SELECT u FROM User_ u WHERE u.email = :email")
	public User_ findByEmail(@Param("email")String email);
}
