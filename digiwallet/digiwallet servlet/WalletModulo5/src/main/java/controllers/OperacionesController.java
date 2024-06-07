package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.UserDAO;

/**
 * Servlet implementation class OperacionesController
 */

@WebServlet(name="OperacionesController", urlPatterns={"/OperacionesController"})
public class OperacionesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OperacionesController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Get-Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// lógica del negocio
		try {
			// recuera la session
			HttpSession session = request.getSession(false);
			if(session == null) {
				// se redirige al index para el login
				response.sendRedirect("index.jsp");
			}
			// si la id del usuario es distinta a la id de la vista, se ha accedido al
			// cache del navegador con datos erroneos
			if(session.getAttribute("idusuario") == null || request.getParameter("idUsuarioWeb") == null) {
				// se redirige al index para el login
				response.sendRedirect("index.jsp");
			}
			// recupera el tipo de operación a realizar
			String operacion = request.getParameter("operacion");
			// recupera el monto de la operación
			double amount = Integer.parseInt(request.getParameter("amount"));
			// recupera el id del usuario
			int idUsuarioWeb = Integer.parseInt(request.getParameter("idUsuarioWeb"));
			// valida que la operación sea válida
			if (operacion != null && amount > 0) {
				int idUsuario = (int)session.getAttribute("idusuario");
				if(idUsuario == idUsuarioWeb) {	
					// si operación depositar
					if (operacion.equals("add")) {
						UserDAO.depositar(idUsuario, amount, session);
						// si operación es retirar
					} else if (operacion.equals("subtract")) {
						UserDAO.retirar(idUsuario, amount, session);
					}
					// redirige a operaciones
					response.sendRedirect("DepositosRetirosServlet");
				}else {
					// redirige a index para el login
					response.sendRedirect("index.jsp");  // formulario no corresponde con el login
				}
				//RequestDispatcher dispatcher = request.getRequestDispatcher("UsuarioController");
				//dispatcher.forward(request, response);
			} else {
				// vista por una operacion inválida
				session.setAttribute("mensaje", "");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().println("<html><body>");
				response.getWriter().println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'>");
				response.getWriter().println("<link rel='stylesheet' href='css/style.css'>");
				response.getWriter().println("<body>");
				response.getWriter().println("</body></html>");
				response.getWriter().println("<div class='container-login'>");
				response.getWriter().println("<h1>Operación no válida !!</h1>");
				response.getWriter().println("<br>");
				response.getWriter().println("<a class='btn btn-secondary' href='DepositosRetirosServlet'>reintentar</a>"); 
				response.getWriter().println("</div>"); 
				response.getWriter().println("</body></html>");
				response.getWriter().append("Served at: ").append(request.getContextPath());
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			doGet(request, response);
		}
	}
}
