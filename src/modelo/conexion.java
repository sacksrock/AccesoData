package modelo;

import java.sql.*;

import vista.MainViewController;
import controlador.Main;

public class conexion {
	// Atributos de la clase
	private static Connection conexion = null;
	private static String url = "jdbc:mysql://localhost/coche";
	private static String usuario = "root";
	private static String contrasena = "";
	private MainViewController mainView;

	public conexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conexion = DriverManager.getConnection(url, usuario, contrasena);
			
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
	
	public Connection getConexion(){
		return conexion;
	}
	
	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}