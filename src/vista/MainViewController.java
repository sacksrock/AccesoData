package vista;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.coche;
import modelo.conexion;

public class MainViewController {
	// columnas
	@FXML
	private TableView<coche> datos = new TableView<coche>();
	@FXML
	private TableColumn<coche, String> colmarca;
	@FXML
	private TableColumn<coche, String> colmodelo;
	@FXML
	private TableColumn<coche, String> colpeso;
	@FXML
	private TableColumn<coche, String> colmatricula;
	@FXML
	private TableColumn<coche, String> colcolor;
	// botones y textfield

	@FXML
	private TextField txtmarca;
	@FXML
	private TextField txtmodelo;
	@FXML
	private TextField txtpeso;
	@FXML
	private TextField txtmatricula;
	@FXML
	private TextField txtcolor;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnEliminarTodos;

	@FXML
	private TableView<coche> tblviewcoche;

	private ObservableList<coche> listacoches;

	private static conexion conexion;

	File FicheroDatos = new File("FicheroDatos.txt");
	ArrayList<coche> data = new ArrayList<coche>();

	@FXML
	public void initialize() {
		conexion = new conexion();
		// Inicializar listas

		listacoches = FXCollections.observableArrayList();

		// Llenar listas

		coche.llenarInformacionAlumnos(conexion.getConexion(), listacoches);

		// Enlazar listas con ComboBox y TableView

		tblviewcoche.setItems(listacoches);

		// Enlazar columnas con atributos
		colmarca.setCellValueFactory(new PropertyValueFactory<coche, String>("marca"));
		colmodelo.setCellValueFactory(new PropertyValueFactory<coche, String>("modelo"));
		colpeso.setCellValueFactory(new PropertyValueFactory<coche, String>("peso"));
		colmatricula.setCellValueFactory(new PropertyValueFactory<coche, String>("matricula"));
		colcolor.setCellValueFactory(new PropertyValueFactory<coche, String>("color"));

		conexion.cerrarConexion();
	}

	public void refreshInfoTable() {

		coche.loadCoches(conexion.getConexion(), listacoches);

		datos.setItems(listacoches);

		colmarca.setCellValueFactory(new PropertyValueFactory<coche, String>("marca"));
		colmodelo.setCellValueFactory(new PropertyValueFactory<coche, String>("modelo"));
		colpeso.setCellValueFactory(new PropertyValueFactory<coche, String>("peso"));
		colmatricula.setCellValueFactory(new PropertyValueFactory<coche, String>("matricula"));
		colcolor.setCellValueFactory(new PropertyValueFactory<coche, String>("color"));
	}

	public void eventos() {

		datos.getSelectionModel().selectedItemProperty().addListener(

				new ChangeListener<coche>() {

					@Override
					public void changed(ObservableValue<? extends coche> arg0, coche arg1, coche selectedValue) {

						txtmarca.setText(String.valueOf(selectedValue.getMarca()));
						txtmodelo.setText(String.valueOf(selectedValue.getModelo()));
						txtpeso.setText(String.valueOf(selectedValue.getPeso()));
						txtmatricula.setText(String.valueOf(selectedValue.getMatricula()));
						txtpeso.setText(String.valueOf(selectedValue.getColor()));

						btnEliminar.setDisable(false);
						btnGuardar.setDisable(true);
					}
				});
	}

	@FXML
	public void saveRegistro() {
		// Crear una nueva instancia del tipo coche
		coche car = new coche(txtmarca.getText(), txtmodelo.getText(), txtpeso.getText(), txtmatricula.getText(),
				txtcolor.getText());

		// Llamar al metodo guardarRegistro de la clase coche
		conexion = new conexion();
		int resultado = car.guardarCoche(conexion.getConexion());

		if (resultado == 1) {

			Alert mensaje = new Alert(AlertType.INFORMATION);
			mensaje.setTitle("Registro agregado");
			mensaje.setContentText("El registro ha sido agregado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
			listacoches.clear();
			refreshInfoTable();
		} else {
			Alert msg = new Alert(AlertType.ERROR);
			msg.setTitle("Error");
			msg.setContentText("Todos los campos son obligatorios");
			msg.setHeaderText("Resultado:");
			msg.show();
		}
	}

	@FXML
	public void eliminarRegistro() {

		conexion = new conexion();

		int result = tblviewcoche.getSelectionModel().getSelectedItem().removeRegistro(conexion.getConexion());

		conexion.cerrarConexion();

		if (result == 1) {
			listacoches.remove(tblviewcoche.getSelectionModel().getSelectedItem());
			Alert msg = new Alert(AlertType.INFORMATION);

			msg.setTitle("Coche Borrado");
			msg.setContentText("El coche ha sido borrado en la base de datos");
			msg.setHeaderText("Resultado:");
			msg.show();
			datos.refresh();
		} else {
			Alert msg = new Alert(AlertType.ERROR);
			msg.setTitle("Error");
			msg.setContentText("El coche no se ha podido borrar a la base de datos");
			msg.setHeaderText("Resultado:");
			msg.show();
		}
		
	}
	@FXML
	public void eliminarTodos() {

		conexion = new conexion();

		int result; //= //new coche().removeAll(conexion);

		conexion.cerrarConexion();

		if (result == 1) {
			listacoches.remove(tblviewcoche.getSelectionModel().getSelectedItem());
			Alert msg = new Alert(AlertType.INFORMATION);

			msg.setTitle("Coches Borrados");
			msg.setContentText("Los coches han sido borrado en la base de datos");
			msg.setHeaderText("Resultado:");
			msg.show();
			datos.refresh();
		} else {
			Alert msg = new Alert(AlertType.ERROR);
			msg.setTitle("Error");
			msg.setContentText("Los coches no se han podido borrar a la base de datos");
			msg.setHeaderText("Resultado:");
			msg.show();
		}
		
	}
	
	@FXML
	public void cargarFichero() {
		
	}
}
