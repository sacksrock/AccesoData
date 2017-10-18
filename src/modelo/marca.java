package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class marca {
	private int idMarca;
	private String nombre;
	
	public int getIdMarca(String marca) {
		Connection connection = new conexion().getConexion();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT idMarca FROM marca WHERE nombre = ?");
			stmt.setString(1, marca);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				idMarca = rs.getInt(1);
			}
			System.out.println(idMarca);
			return idMarca;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		// "SELECT idMarca FROM marca WHERE nombre = ?"
		// ? =  setString(1, marca);
		}
	}
	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
