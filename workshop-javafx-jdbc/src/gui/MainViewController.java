/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

/*-------------------- class MainViewController --------------------*/
public class MainViewController implements Initializable {

	/*-------------------- attributes --------------------*/
	@FXML
	private MenuItem menu_item_seller;

	@FXML
	private MenuItem menu_item_department;
	
	@FXML
	private MenuItem menu_item_about;
	
	/*-------------------- methods --------------------*/
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		this.loadView2("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		this.loadView("/gui/About.fxml");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		// TODO Auto-generated method stub		
	}
	
	private synchronized void loadView(String absolute_name) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolute_name));
			VBox new_vbox = loader.load();
			Scene main_scene = Main.getMainScene();
			VBox main_vbox = (VBox) ((ScrollPane) main_scene.getRoot()).getContent();
			Node main_menu = main_vbox.getChildren().get(0);

			main_vbox.getChildren().clear();
			main_vbox.getChildren().add(main_menu);
			main_vbox.getChildren().addAll(new_vbox.getChildren());
		} 
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private synchronized void loadView2(String absolute_name) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolute_name));
			VBox new_vbox = loader.load();
			Scene main_scene = Main.getMainScene();
			VBox main_vbox = (VBox) ((ScrollPane) main_scene.getRoot()).getContent();
			Node main_menu = main_vbox.getChildren().get(0);

			main_vbox.getChildren().clear();
			main_vbox.getChildren().add(main_menu);
			main_vbox.getChildren().addAll(new_vbox.getChildren());
			
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		} 
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}