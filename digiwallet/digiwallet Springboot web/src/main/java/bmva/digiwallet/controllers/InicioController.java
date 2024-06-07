package bmva.digiwallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import bmva.digiwallet.models.User_;
import jakarta.servlet.http.HttpSession;

@Controller
public class InicioController {

	@GetMapping("/inicio")
	public String principal(Model model, HttpSession session) {
		if(session.getAttribute("usuario")!=null) {
			User_ usuario = (User_)session.getAttribute("usuario");
			 
			model.addAttribute("nombreusuario", "¡ Hola ! "+usuario.getFirstname().toUpperCase()+" "+usuario.getLastname().toUpperCase());
			return "index";
		}
		return "redirect:/logout";
	}

}
