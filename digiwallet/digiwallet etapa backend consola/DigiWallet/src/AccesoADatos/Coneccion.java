package AccesoADatos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Coneccion {
		// declarada static para ser invovada estáticamente
	    public static Coneccion instancia;
	    // atributo a retornar con la instancia de la conección
	    private Connection connection;

	    // parametriza e instancia el driver de conección a mysql
	    // registra el driver de mysql, se carga dinámicamente el conjtrolador de base de datos
	 	// se carga el controlador mysql en memoria
	    private Coneccion() {
	        String driver = "com.mysql.cj.jdbc.Driver";
	        String url = "jdbc:mysql://localhost:3306/digiwallet";
	        String usuario = "root";
	        String pass = "150919";
	        try {
	        	// desde jdk 6 el jdbc 4 se carga automaticamente si esta en el classpath del JVM
	            Class.forName(driver);
	            connection = DriverManager.getConnection(url, usuario, pass);
	            System.out.println("Conección a DB existosa!!");
	        } catch (Exception ex) {
	            System.out.println("Ocurrio un error al intentar conectar a la DB !!"+ex.getMessage());
	        }
	    }

	    // patrón singleton para la instancia de conección a la DB
	    public static Coneccion getInstance() {
	        if (instancia == null) {
	            instancia = new Coneccion();
	        }
	        return instancia;
	    }

	    public Connection getConeccion() {
	        return connection;
	    }

	    public void cerrar() {
	        instancia = null;
	    }
	}
