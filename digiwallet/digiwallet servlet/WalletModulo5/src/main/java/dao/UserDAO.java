package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import coneccionDB.Coneccion;
import jakarta.servlet.http.HttpSession;
import models.Usuario;

public class UserDAO {

	public static Usuario validaUsuario(String username, String password, HttpSession session) {
		try {
			// llama a instancia BBDD
			Coneccion conn = Coneccion.getInstance();// establece una coneción a la DB
			// prepara el string de la consulta parametrizada
			String consulta = "SELECT id_usuario FROM authusuarios WHERE username = ? AND password = ?";
			// entrega el string de la consulta 
			PreparedStatement preparedStatement = conn.getConeccion().prepareStatement(consulta);
			// setter de los parámetros de la query 
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			// ejecuta la consulta que retornará el id del usuario que coincide
			ResultSet resultado = preparedStatement.executeQuery();// consulta la DB
			// recorre el recordset con la respuesta
			while (resultado.next()) {// si existe
				int idUsuario = resultado.getInt("id_usuario");
				// ejecuta método de busqueda del usuario por el id
				Usuario usuario = recuperaUsuario(idUsuario, conn);
				// si el usuario retonado el válido
				if(usuario != null) {			
					// crea variables de session con datos a utilizar
					session.setAttribute("idusuario", usuario.getId());
					session.setAttribute("nombre", usuario.getNombre());
					session.setAttribute("saldo", usuario.getBalance());
					session.setAttribute("mensaje", "");
					// retorna un objeto usuario
					return usuario;
				}
			}
		} catch (SQLException exceptionSql) {
			exceptionSql.printStackTrace();
		} 
		return null;
	}

	// método para recuperar un usuario según el id
	private static Usuario recuperaUsuario(int idUsuario, Coneccion conn) {
		try {
			// pepara string de query para traer datos relevantes
			String consulta = "SELECT nombre, balance FROM usuarios WHERE id = ?";
			// entrega la query
			PreparedStatement preparedStatement = conn.getConeccion().prepareStatement(consulta);
			// setter de parámetros de la query
			preparedStatement.setInt(1, idUsuario);
			// ejecuta la query
			ResultSet resultado = preparedStatement.executeQuery();// consulta la DB
			// si hay resultado
			while (resultado.next()) {// si existe
				// crea objeto para retornar el resultado
				Usuario usuario = new Usuario();
				// pobla objeto con los datos recuperados
				usuario.setId(idUsuario);
				usuario.setNombre(resultado.getString("nombre"));
				usuario.setBalance(BigDecimal.valueOf(resultado.getDouble("balance")));
				return usuario; // retorna el usuario correspondiente
			}
		} catch (SQLException exceptionSql) {
			exceptionSql.printStackTrace();
		} 
		return null;
	}

	// método para depositar dinero a la cuenta
	// retorna valor booleano por el éxito de la operación
	public static boolean depositar(int idUsuario, double amount, HttpSession session) {
		try {
			Coneccion conn = Coneccion.getInstance();// establece una coneción a la DB
			// recupera el saldo actual del usuario para validar disponible
			Usuario usuario = recuperaUsuario(idUsuario, conn);
			if (usuario != null) {
				// recupera el saldo del usuario
				BigDecimal balance = usuario.getBalance();
				// realiza el abono a la cuenta del monto entregado 
				balance = balance.add(BigDecimal.valueOf(amount));
				String consulta = "UPDATE usuarios SET balance = balance + ? WHERE id = ?";
				PreparedStatement preparedStatement = conn.getConeccion().prepareStatement(consulta);
				// setter de los parámetros de la query
				preparedStatement.setDouble(1, amount);
				preparedStatement.setInt(2, idUsuario);
				// ejecuta la query
				int filasAfectadas = preparedStatement.executeUpdate();// ejecuta cambio en la DB
				if (filasAfectadas > 0) {
					// actualiza variable de session con el saldo
					session.removeAttribute("saldo");
					session.setAttribute("saldo", balance);
					session.setAttribute("mensaje", "Depósito de dinero efectuado existosamente");
					return true;
				}else {
					session.setAttribute("mensaje", "Ocurrió un error inesperado, vuelva a intentar");
				}
			} else {
				return false;
			}
		} catch (SQLException exceptionSql) {
			exceptionSql.printStackTrace();
		} 
		return false;
	}

	// método para retirar dinero de la cuenta
	// retorna valor booleano por el éxito de la operación
	public static boolean retirar(int idUsuario, double amount, HttpSession session) {
		try {
			Coneccion conn = Coneccion.getInstance();// establece una coneción a la DB
			// recupera el saldo actual del usuario para validar disponible
			Usuario usuario = recuperaUsuario(idUsuario, conn);
			if (usuario != null) {
				BigDecimal balance = usuario.getBalance();
				// comprueba si hay saldo suficiente
				if (balance.compareTo(BigDecimal.valueOf(amount)) >= 0) {
					// realiza el descuento del monto en el saldo
					balance = balance.subtract(BigDecimal.valueOf(amount));
					// existe saldo disponible
					String consulta = "UPDATE usuarios SET balance = balance - ? WHERE id = ?";
					// informa query parametrizada para la consulta
					PreparedStatement preparedStatement = conn.getConeccion().prepareStatement(consulta);
					// setter parametros de la consulta
					preparedStatement.setDouble(1, amount);
					preparedStatement.setInt(2, idUsuario);
					// ejecuta query
					int filasAfectadas = preparedStatement.executeUpdate();// consulta la DB
					if (filasAfectadas > 0) {
						//session.removeAttribute("saldo");
						session.setAttribute("saldo", balance);
						session.setAttribute("mensaje", "Retiro de dinero efectuado existosamente");
						return true;
					}else {
						session.setAttribute("mensaje", "Ocurrió un error inesperado, vuelva a intentar");
					}
				} else {
					session.setAttribute("mensaje", "El monto ingresado es superior al saldo disponible");
					return false;
				}
			} else {
				return false;
			}
		} catch (SQLException exceptionSql) {
			exceptionSql.printStackTrace();
		} 
		return false;
	}

	public static void usuarioLogout() {
		Coneccion.cerrar();
	}
}
