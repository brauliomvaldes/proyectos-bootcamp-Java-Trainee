package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Servlet implementation class DepositosRetirosServlet
 */

@WebServlet(name = "DepositosRetirosServlet", urlPatterns = { "/DepositosRetirosServlet" })
public class DepositosRetirosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DepositosRetirosServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// recupera la session, si no existe la crea 
			HttpSession session = request.getSession(false);
			// si la session no existe redirige al index para el login
			if(session == null) {
				response.sendRedirect("index.jsp");
			}
			// recupera variables de session a emplear como información en la operación 
			String nombre = (String)session.getAttribute("nombre");
			BigDecimal saldo = (BigDecimal)session.getAttribute("saldo");
			String mensaje = (String)session.getAttribute("mensaje");
			int idUsuarioWeb = (Integer)session.getAttribute("idusuario"); // para validar mismo usuario login y formulario
			// genera vista para realizar operaciones de depósito o retiro de dinero
			String html = "";
			html += "<!DOCTYPE html>";
			html += "<html>";
			html += "<head>";
			html +=  "<meta charset='UTF-8'>";
			html +=  "<link rel='stylesheet'";
			html +=  "href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>";
			html +=  "<link rel='stylesheet' href='css/style.css' />";
			html +=  "<title>Operaciones Wallet</title>";
			html += "</head>";
			html += "<body>";
			html +=  "<div class='container-login'>";
			html +=  "<div class='col-1'>";
			html +=   "<a class='btn btn-secondary' href='LogoutServlet'>logout</a>";
			html +=  "</div>";
			html +=  "<h1>Operaciones Wallet</h1>";
			html +=  "<h2>Bienvenido "+nombre+"</h2>";
			html +=  "<h3>Su Saldo Disponible es $ "+saldo+"</h3>";
			
			html += "<form method='post' action='OperacionesController'>";
			html +=  "<div class='container-login'>";
			html +=    "<label>Ingrese el monto de la operación :</label>";
			html +=    "<input type='number' name='amount' required>";
			html +=  "</div>";
			html +=  "<div class='container-login'>";
			html +=   "<label>Defina el tipo de operación :</label>";
			html +=   "<select name='operacion'>";
			html +=     "<option value='add'> Depositar Dinero </option>";
			html +=     "<option value='subtract'> Retirar Dinero </option>";
			html +=   "</select>";
			html += "<input type='hidden' name='idUsuarioWeb' value="+idUsuarioWeb+">";
			html +=   "<p><button type='submit' class='btn btn-success'>Procesar Operación</button></p>";
			html +=  "</div>";
			html += "</form>";
			html += "<br> <br> <br> <br>";
			html += "<hr>";
			html += "<div>";
			html += "<p>";
			html += "<h4>"+mensaje+"</h4>";
			html += "</p>";
			html += "</div>";
			html += "</div>";
			html += "</body>";
			html += "</html>";
			response.getWriter().println(html);
		}catch(Exception ignore) {
			System.out.println("posible acceso inválido");
			response.sendRedirect("index.jsp");
		}
	}

}
