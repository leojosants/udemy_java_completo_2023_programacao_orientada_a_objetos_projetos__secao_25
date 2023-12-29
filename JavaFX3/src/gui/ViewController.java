/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/*-------------------- class ViewController --------------------*/
public class ViewController {
	
	/*-------------------- attribute --------------------*/
	@FXML
	private Button button_test;
	
	/*-------------------- methods --------------------*/
	@FXML
	public void onButtonTestAction() {
		Alerts.showAlert("Alert title", null, "Hello", AlertType.ERROR);
	}
}
