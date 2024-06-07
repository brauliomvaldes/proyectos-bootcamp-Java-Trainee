package bmva.digiwallet.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bmva.digiwallet.dto.TransactionDto;
import bmva.digiwallet.models.Account;
import bmva.digiwallet.models.Contact;
import bmva.digiwallet.models.Transaction;
import bmva.digiwallet.models.User_;
import bmva.digiwallet.services.IAccountService;
import bmva.digiwallet.services.IContactService;
import bmva.digiwallet.services.ITransactionService;
import jakarta.servlet.http.HttpSession;

@Controller
public class TransferenciasController {
	
	@Autowired
	IAccountService accountService;
	
	@Autowired
	IContactService contactService;
	
	@Autowired
	ITransactionService transactionService;

	@ModelAttribute("procesotransferencia")
	public TransactionDto retornarTransferenciaDto() {
		return new TransactionDto();
	}
	

	@PostMapping("/transferencias")
	public String transferenciasEntreCuentas(@ModelAttribute("procesotransferencia")TransactionDto transactionDto, 
			@RequestParam("selectedDestino") String selectedDestino, Model model, HttpSession session) {
		
		session.setAttribute("msgtransferencia", null);
		if(session.getAttribute("usuario")!=null && session.getAttribute("sender") != null) {		
			 
			//String nrocuenta = (String)session.getAttribute("nrocuenta");
			BigDecimal balance = (BigDecimal)session.getAttribute("balance");
			String idCuenta = (String)session.getAttribute("idcuenta");
			
			//String glosa = transactionDto.getDetail();
			BigDecimal montoTransferir = transactionDto.getAmount();
			
			// el monto ingresado es positivo
			 if(montoTransferir.compareTo(BigDecimal.ONE)>0) {
				 if(balance.compareTo(montoTransferir)>=0) {
					 // tiene saldo disponible para la transferencia
					 // almacena cuenta destino
					 Account receiver;
					 if(selectedDestino.equals("PROPIAS")) {
						 // cuenta destino viene desde cuentas del usuario
						 receiver = transactionDto.getAccount(); // si es cuentas propias se almaceno receiver
					 }else {
						 // cuenta destino viene desde sus contactos
						 // el nro de la cuenta de contacto se almacenó en Number
						 String nroCuentaReceiverMisContactos = transactionDto.getNumber();
						 receiver = accountService.buscarPorNroCuenta(nroCuentaReceiverMisContactos);
					 }
					 // almacena cuenta del destinatario
					 transactionDto.setAccount(receiver);
					 // revisar factor de conversión
					 BigDecimal factor = transactionDto.getFactor();
					 // si es mayor a cero
					 if(factor.compareTo(BigDecimal.ZERO)>0) {
						 // aplicar factor al monto de la transacción
						 transactionDto.setFactoramount(transactionDto.getAmount().multiply(factor));
					 }else {
						 // copia el mismo monto original
						 transactionDto.setFactoramount(transactionDto.getAmount());
					 }
					 // recupera cuenta origen de la transferencia
					 Account sender = (Account)session.getAttribute("sender");
					 transactionDto.setSender(sender);
					 Transaction trf = transactionService.saveTransaction(transactionDto);
					 if(trf != null) {
						 
						 // aumenta el saldo del destinatario
						 accountService.depositar(trf.getAmount_receiver(), trf.getReceiver().getId());
						 
						 // rebajar el saldo del origen
						 accountService.retirar(trf.getAmount_sender(), trf.getSender().getId());
						 session.setAttribute("msgtransferencia", "exito");
					 }else {
						 // error an crear tranferencia
						 session.setAttribute("msgtransferencia", "error");
					 }
				 }else {
					 // saldo no insuficiente
					 session.setAttribute("msgtransferencia", "saldo");
				 }
			 }else {
				 // monto es negativo
				 session.setAttribute("msgtransferencia", "negativo");
			 }
			
			 session.setAttribute("sender", null);
			return "redirect:/transferir/"+idCuenta;
		}
		return "redirect:/logout";
	}
	
	@GetMapping("/transferir/{id}")
	public String retircarDepositarDinero(@PathVariable String id, Model model, HttpSession session) {
		
		if(session.getAttribute("usuario")!=null) {		
		 
			User_ usuario = (User_)session.getAttribute("usuario");
			String idUsuario = usuario.getId();
			// valida cuenta referida exista
			Account cuenta = accountService.buscarPorId(id);
			if(cuenta != null) {
				
				model.addAttribute("msgtransferencia", null);
				// reunir todas las cuentas del usuario
				List<Account> listaCuentasDelUsuario = accountService.findByUserWithoutOneIdAccount(idUsuario, id);
				
				// reunir las cuentas de sus contactos
				List<Contact> listaCuentasContactosDelUsuario = contactService.findByUser(usuario);
				
				// envia datos de la cuenta origen a la vista
				// el número de la cuenta
				model.addAttribute("nrocuenta", cuenta.getNumber());
				// el saldo en la cuenta
				model.addAttribute("balance", cuenta.getBalance());
				// el tipo de moneda
				model.addAttribute("moneda", cuenta.getCurrencyy().getSymbol());
				// nombre del usuario
				model.addAttribute("nombreusuario", usuario.getFirstname()+" "+usuario.getLastname());
				// sus cuentas
				model.addAttribute("suscuentas",listaCuentasDelUsuario);
				// sus contactos
				model.addAttribute("suscontactos",listaCuentasContactosDelUsuario);
				// almacena datos para procesar operacion transferencia
				session.setAttribute("nrocuenta", cuenta.getNumber());
				session.setAttribute("balance", cuenta.getBalance());
				session.setAttribute("idcuenta", id);
				session.setAttribute("sender", cuenta);
				
				if(session.getAttribute("msgtransferencia")!=null) {
					String msg = (String)session.getAttribute("msgtransferencia");
					
					if(msg.equals("exito")) model.addAttribute("msgtransferencia", "Operación realizada existosamente");
					if(msg.equals("saldo")) model.addAttribute("msgtransferencia", "¡Error!, El monto ingresado supera el saldo disponible");
					if(msg.equals("negativo")) model.addAttribute("msgtransferencia", "¡Error!, Debe ingresar un monto positivo");
					if(msg.equals("error")) model.addAttribute("msgtransferencia", "¡Error!, No fue posible realizar la operación, intente más tarde");
				}				
				return "transferencias";
			}else {
				session.setAttribute("sender", null);
				return "cuentas";				
			}
		
		}
		return "redirect:/logout";
	}
	
	
	
	
	
	
	
	
	
	
	
}
