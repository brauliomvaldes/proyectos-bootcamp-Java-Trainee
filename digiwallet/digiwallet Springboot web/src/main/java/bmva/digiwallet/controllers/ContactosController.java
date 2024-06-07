package bmva.digiwallet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import bmva.digiwallet.dto.ContactoDto;
import bmva.digiwallet.models.Account;
import bmva.digiwallet.models.Contact;
import bmva.digiwallet.models.User_;
import bmva.digiwallet.services.IAccountService;
import bmva.digiwallet.services.IContactService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContactosController {

	@Autowired
	private IContactService contactService;

	@Autowired
	private IAccountService accountService;

	@GetMapping("/contactos")
	public String contactosUsuario(Model model, HttpSession session) {
		session.setAttribute("msgcontactos", null);
		if (session.getAttribute("usuario") != null) {
			User_ usuario = (User_) session.getAttribute("usuario");

			List<Contact> contactos = contactService.findByUser(usuario);
			model.addAttribute("contactos", contactos);
			model.addAttribute("nombreusuario",
					usuario.getFirstname().toUpperCase() + " " + usuario.getLastname().toUpperCase());
			return "contactos";
		}
		return "redirect:/logout";
	}

	@GetMapping("/registrocontactos")
	public String agregarCuenta(Model model, HttpSession session) {
		if (session.getAttribute("usuario") != null) {

			if (session.getAttribute("msgcontactos") != null) {
				String msg = (String) session.getAttribute("msgcontactos");
				if (msg.equals("exito"))
					model.addAttribute("mensajecontactos", "Creación existosa de nuevo contacto");
				if (msg.equals("nrocuenta"))
					model.addAttribute("mensajecontactos", "¡Error!, El número de cuenta proporcionado no es válido");
				if (msg.equals("nrocontacto"))
					model.addAttribute("mensajecontactos",
							"¡Error!, El número de cuenta ya existe como contacto del usuario");
				if (msg.equals("nrocontactoespropio"))
					model.addAttribute("mensajecontactos",
							"¡Error!, El número de cuenta proporcionado ya esta registrado a su nombre");
			}
			User_ usuario = (User_)session.getAttribute("usuario");
			model.addAttribute("nombreusuario",
					usuario.getFirstname().toUpperCase() + " " + usuario.getLastname().toUpperCase());
			return "registrocontactos";
		}
		return "redirect:/logout";
	}

	@ModelAttribute("micontacto")
	public ContactoDto retornarNuevoContactoDTO() {
		return new ContactoDto();
	}

	@PostMapping("/registrocontactos")
	public String registrarNuevoContacto(@ModelAttribute("micontacto") ContactoDto contactoDto, Model model,
			HttpSession session) {

		if (session.getAttribute("usuario") != null) {
			User_ usuario = (User_) session.getAttribute("usuario");
			// id del usuario logeado
			String idUsuario = usuario.getId();
			// recupera numero de cuenta ingresado
			String nroCuenta = contactoDto.getNumber();
			// validar que la cuenta no sea un contacto del usuario
			if (contactService.buscarPorNroCuenta(nroCuenta) == null) {
				// validar la cuenta bancaria que este ya exista
				Account cuentaExiste = accountService.buscarPorNroCuenta(nroCuenta); 
				if (cuentaExiste != null) {
					// validar que la cuenta no sea una de sus cuentas
					String usuarioDeLaCuentaEncontrada = cuentaExiste.getUser().getId();
					if(usuarioDeLaCuentaEncontrada.equals(idUsuario)) {
						// el usuario ya tiene esta cuenta registrada como propia
						session.setAttribute("msgcontactos", "nrocontactoespropio");
					}else {						
						// almacena el usuario
						contactoDto.setUser(usuario); // el propietario del contacto
						contactoDto.setBank(cuentaExiste.getBank().getName());
						contactoDto.setCurrencyy(cuentaExiste.getCurrencyy().getSymbol());
						// cuenta existente en la wallet
						contactService.crearNuevoContacto(contactoDto);
						session.setAttribute("msgcontactos", "exito");
					}
				} else {
					session.setAttribute("msgcontactos", "nrocuenta");
				}
			} else {
				session.setAttribute("msgcontactos", "nrocontacto");
			}
			return "redirect:/registrocontactos";
		}
		return "redirect:/logout";
	}

}
