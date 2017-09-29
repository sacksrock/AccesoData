package modelo;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class coche {
	private String marca;
	private String modelo;
	private String peso;
	private String matricula;
	private String color;

	public coche(String marca, String modelo, String peso, String matricula, String color) {
		this.marca = marca;
		this.modelo = modelo;
		this.peso = peso;
		this.matricula = matricula;
		this.color = color;
	}

	public int guardarCoche(Connection connection) {
		try {
			PreparedStatement instruccion = connection.prepareStatement(
					"INSERT INTO coche (marca, modelo, peso, matricula, color) " + "VALUES (?, ?, ?, ?, ?)");
			instruccion.setString(1, marca);
			instruccion.setString(2, modelo);
			instruccion.setString(3, peso);
			instruccion.setString(4, matricula);
			instruccion.setString(5, color);
			return instruccion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int removeRegistro(Connection connection) {
		try {
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM juegos WHERE marca = ?");
			stmt.setString(1, marca);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static void loadCoches(Connection connection, ObservableList<coche> lista) {
		try {

			Statement stmt = connection.createStatement();

			ResultSet rset = stmt.executeQuery("SELECT marca, modelo, peso, matricula, color FROM coche");

			while (rset.next()) {
				lista.add(new coche(rset.getString("marca"), rset.getString("modelo"), rset.getString("peso"),
						rset.getString("matricula"), rset.getString("color")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void llenarInformacionAlumnos(Connection connection, ObservableList<coche> lista) {
		try {
			Statement instruccion = connection.createStatement();
			ResultSet resultado = instruccion
					.executeQuery("SELECT marca, " + "modelo, " + "peso, " + "matricula, " + "color " + "FROM coche "

			);
			while (resultado.next()) {
				lista.add(new coche(resultado.getString("marca"), resultado.getString("modelo"),
						resultado.getString("peso"), resultado.getString("matricula"), resultado.getString("color")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
