package bmva.digiwallet.services;

import java.util.List;

import bmva.digiwallet.dto.ContactoDto;
import bmva.digiwallet.models.Contact;
import bmva.digiwallet.models.User_;

public interface IContactService {

	public List<Contact> findByUser(User_ user);
	
	public Contact crearNuevoContacto(ContactoDto contactoDto);
	
	public Contact buscarPorNroCuenta(String nrocuenta);
}
