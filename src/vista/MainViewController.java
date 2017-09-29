package vista;

import java.net.URL;
import java.util.ResourceBundle;

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

public class MainViewController implements Initializable {
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
	private TableView<coche> tblviewcoche;

	private ObservableList<coche> listacoches;

	private conexion connection;

	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		connection = new conexion();
		connection.establecerConexion();
		// Inicializar listas

		listacoches = FXCollections.observableArrayList();

		// Llenar listas

		coche.llenarInformacionAlumnos(connection.getConexion(), listacoches);

		// Enlazar listas con ComboBox y TableView

		tblviewcoche.setItems(listacoches);

		// Enlazar columnas con atributos
		colmarca.setCellValueFactory(new PropertyValueFactory<coche, String>("marca"));
		colmodelo.setCellValueFactory(new PropertyValueFactory<coche, String>("modelo"));
		colpeso.setCellValueFactory(new PropertyValueFactory<coche, String>("peso"));
		colmatricula.setCellValueFactory(new PropertyValueFactory<coche, String>("matricula"));
		colcolor.setCellValueFactory(new PropertyValueFactory<coche, String>("color"));

		connection.cerrarConexion();
	}

	public void refreshInfoTable() {

		coche.loadCoches(connection.getConexion(), listacoches);

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
		connection.establecerConexion();
		int resultado = car.guardarCoche(connection.getConexion());

		if (resultado == 1) {

			Alert mensaje = new Alert(AlertType.INFORMATION);
			mensaje.setTitle("Registro agregado");
			mensaje.setContentText("El registro ha sido agregado exitosamente");
			mensaje.setHeaderText("Resultado:");
			mensaje.show();
			listacoches.clear();
			refreshInfoTable();
		}
	}

	@FXML
	public void eliminarRegistro() {

		connection.establecerConexion();

		int result = datos.getSelectionModel().getSelectedItem().removeRegistro(connection.getConexion());
		
		//connection.cerrarConexion();

		if (result == 1) {
			listacoches.remove(datos.getSelectionModel().getSelectedIndex());
			Alert msg = new Alert(AlertType.INFORMATION);

			msg.setTitle("Coche Borrado");
			msg.setContentText("El coche ha sido borrado en la base de datos");
			msg.setHeaderText("Resultado:");
			msg.show();
			datos.refresh();
		}
		else {
			Alert msg = new Alert(AlertType.ERROR);
			msg.setTitle("Error");
			msg.setContentText("El coche no se ha podido borrar a la base de datos");
			msg.setHeaderText("Resultado:");
			msg.show();
		}
	}
}
