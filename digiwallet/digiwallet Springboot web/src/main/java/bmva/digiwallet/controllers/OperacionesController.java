package bmva.digiwallet.controllers;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import bmva.digiwallet.dto.Operacion;
import bmva.digiwallet.models.Account;
import bmva.digiwallet.models.User_;
import bmva.digiwallet.services.IAccountService;
import bmva.digiwallet.services.ITransactionService;
import jakarta.servlet.http.HttpSession;

@Controller
public class OperacionesController {

	@Autowired
	IAccountService accountService;
	
	@Autowired
	ITransactionService transactionService;
	
	@ModelAttribute("procesooperacion")
	public Operacion retornarOperacion() {
		return new Operacion();
	}
	

	@PostMapping("/operaciones")
	public String retirarDepositarDinero(@ModelAttribute("procesooperacion")Operacion operacion,  Model model, HttpSession session) {
 
		if(session.getAttribute("usuario")!=null) {		
			
			BigDecimal balance = (BigDecimal)session.getAttribute("balance");
			String idCuenta = (String)session.getAttribute("idcuenta");
			Account cuenta = (Account)session.getAttribute("cuenta");
			model.addAttribute("msg", null);
			
			// Consulta si el valor ingresado es positivo y la operación válida
			if(operacion.getMonto() != null && operacion.getTipo() != null) {
				BigDecimal montoOperacion = operacion.getMonto();
				Account cuentaActualizada = null;
				if(montoOperacion.compareTo(BigDecimal.ZERO) > 0) {
					// monto ingresado es positivo
					if(operacion.getTipo().equals("SUMAR")) {
						// depositar dinero
						cuentaActualizada = accountService.depositar(montoOperacion, idCuenta);
						session.setAttribute("msg", "exitosumar");
					}else {
						if(montoOperacion.compareTo(balance)<=0) {							
							// retirar dinero
							cuentaActualizada = accountService.retirar(montoOperacion, idCuenta);
							session.setAttribute("msg", "exitorestar");
						}else {
							session.setAttribute("msg", "saldo");
						}
					}
					if(cuentaActualizada!=null) {
						// operación exitosa
						transactionService.registrarOperacionEnTransacciones(operacion.getTipo(), montoOperacion, cuenta);
					}else {
						session.setAttribute("msg", "error");
					}
				}else {
					session.setAttribute("msg", "negativo");
				}
			}
			return "redirect:/operar/"+idCuenta;
		}
		return "redirect:/logout";
	}
	
	@GetMapping("/operar/{id}")
	public String retircarDepositarDinero(@PathVariable String id, Model model, HttpSession session) {
		
		if(session.getAttribute("usuario")!=null) {	

			User_ usuario = (User_)session.getAttribute("usuario");
			
			Account cuenta = accountService.buscarPorId(id);
			if(cuenta != null) {
				model.addAttribute("nrocuenta", cuenta.getNumber());
				model.addAttribute("balance", cuenta.getBalance());
				model.addAttribute("nombreusuario", usuario.getFirstname()+" "+usuario.getLastname());
				session.setAttribute("nrocuenta", cuenta.getNumber());
				session.setAttribute("balance", cuenta.getBalance());
				session.setAttribute("idcuenta", id);
				session.setAttribute("cuenta", cuenta);
				model.addAttribute("msg", null);
				if(session.getAttribute("msg")!=null) {
					String msg = (String)session.getAttribute("msg");
			 
					if(msg.equals("exitosumar")) model.addAttribute("msg", "Depósito en cuenta se realizo existosamente");
					if(msg.equals("exitorestar")) model.addAttribute("msg", "Retiro (Giro a otros destinos) se realizao existosamente");
					if(msg.equals("saldo")) model.addAttribute("msg", "¡Error!, El monto ingresado supera el saldo disponible");
					if(msg.equals("negativo")) model.addAttribute("msg", "¡Error!, Debe ingresar un monto positivo");
					if(msg.equals("error")) model.addAttribute("msg", "¡Error de Comunicación!, No fue posible realizar la operación");
				}
				return "operaciones";
			}else {
				return "cuentas";				
			}
		
		}
		return "redirect:/logout";
	}
	
	
}
