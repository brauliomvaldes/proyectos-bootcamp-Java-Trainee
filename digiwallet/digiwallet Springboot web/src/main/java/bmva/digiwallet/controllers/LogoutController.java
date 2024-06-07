package bmva.digiwallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@GetMapping
	public String cerrarSesion(Model model, HttpSession session) {
		session.setAttribute("usuario", null);
		session.setAttribute("nrocuenta", null);
		session.setAttribute("balance", null);
		session.setAttribute("msg", null);
		session.setAttribute("msgcuentas", null);
		session.setAttribute("msgcontactos", null);
		session.setAttribute("msgtransaferencia", null);
		return "login";
	}
}
