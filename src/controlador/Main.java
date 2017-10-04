package controlador;


import java.io.IOException;

//imports javafx
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.*;
import vista.MainViewController;


//imports packet

import vista.MainViewController;
import modelo.conexion;

public class Main extends Application{

	private static Stage primaryStage;
	
	private static MainViewController mainView;
	
	private static Connection connect;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Main.primaryStage = primaryStage;
		
		showMainView();

	}
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
	public void showMainView() {
        try {
        	
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../vista/MainView.fxml"));
            
            AnchorPane rootPane=(AnchorPane) loader.load();
            
            mainView = loader.getController();
            
            //mainView.setProgramaPrincipal(this);
            
            Scene scene = new Scene(rootPane);
            
            primaryStage.setTitle("Search Games");
            
            primaryStage.setScene(scene);
            
            primaryStage.show();

        } catch (IOException e) {
        	
            e.printStackTrace();
            
        }
   }

	public static Connection getConnect() {
		return connect;
	}

	public static void setConnect(Connection connect) {
		Main.connect = connect;
	}
	
}