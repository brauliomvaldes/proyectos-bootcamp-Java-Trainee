package bmva.digiwallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bmva.digiwallet.dto.UserDto;
import bmva.digiwallet.models.User_;
import bmva.digiwallet.services.IUserService;
import bmva.digiwallet.services.ToolService;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioController {

	@Autowired
	private IUserService userService;

	@ModelAttribute("usuario")
	public UserDto retornarNuevoUsuarioRegistroDTO() {
		return new UserDto();
	}

	@GetMapping
	public String mostrarFormularioDeRegistro() {
		return "registro";
	}

	@PostMapping
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UserDto registroDTO) {

		if(ToolService.validarRut(registroDTO.getIdentity())) {			
			User_ nuevousuario = userService.findByEmail(registroDTO.getEmail());
			if (nuevousuario != null) { return "redirect:/registro?email"; }
			// no existe el email registrado
			
			User_ user = userService.save(registroDTO);
			if (user != null) {
				return "redirect:/registro?exito";
			}
		}else {
			return "redirect:/registro?rut";
		}
		return "redirect:/registro?error";
	}
	
	
}
