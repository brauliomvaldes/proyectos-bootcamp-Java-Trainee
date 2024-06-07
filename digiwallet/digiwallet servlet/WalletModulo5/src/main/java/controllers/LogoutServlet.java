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
 * Servlet implementation class Logout
 */

@WebServlet(name="LogoutServlet", urlPatterns= {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// proceso de logout
		// 
		HttpSession session = request.getSession(false);
		// si la session existe
		if(session != null) {
			// si la variable que almacena el id del usuario existe, la remueve
			if(session.getAttribute("idusuario") != null) {
				session.removeAttribute("idusuario");
			}
		}
		// desconecta 
		UserDAO.usuarioLogout();  // desconecta la BD
		// se redirige a index para el login
		response.sendRedirect("index.jsp");
	}

}
