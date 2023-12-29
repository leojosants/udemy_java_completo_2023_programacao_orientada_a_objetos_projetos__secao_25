/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/*-------------------- class ViewController --------------------*/
public class ViewController {
	
	/*-------------------- attribute --------------------*/
	@FXML
	private Button button_test;
	
	/*-------------------- methods --------------------*/
	@FXML
	public void onButtonTestAction() {
		System.out.println("click");
	}
}
