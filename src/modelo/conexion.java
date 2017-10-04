package modelo;

import java.sql.*;

import vista.MainViewController;
import controlador.Main;

public class conexion {
	// Atributos de la clase
	private java.sql.Connection connection;
	private String url = "jdbc:mysql://localhost/coche";
	private String usuario = "root";
	private String contrasena = "";
	private MainViewController mainView;

	public void establecerConexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection(url, usuario, contrasena);
			
			System.out.println("-Conexion Establecida-");
		} catch (Exception e) {
			System.out.println("-Error En La Conexion-");
			e.printStackTrace();
		}
	}
	
	public void setMainView(MainViewController mainView) {
		this.mainView=mainView;
	}
	
	public MainViewController getMainView(){
		return mainView;
	}
	
	public java.sql.Connection getConexion(){
		return connection;
	}
	
	public void setConexion(java.sql.Connection connection ) {
		this.connection = connection;
	}
	
	public void cerrarConexion() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}