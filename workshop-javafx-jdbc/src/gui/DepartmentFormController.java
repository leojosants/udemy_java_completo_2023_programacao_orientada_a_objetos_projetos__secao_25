/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*-------------------- class DepartmentFormController --------------------*/
public class DepartmentFormController implements Initializable {

	/*-------------------- attributes --------------------*/
	@FXML
	private TextField text_ld;
	
	@FXML
	private TextField text_name;
	
	@FXML
	private Label label_error_name;
	
	@FXML
	private Button button_save;
	
	@FXML
	private Button button_cancel;
	
	/*-------------------- methods --------------------*/
	public void onButtonSaveAction() {
		System.out.println("onButtonSaveAction");
	}
	
	public void onButtonCancelAction() {
		System.out.println("onButtonCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(text_ld);
		Constraints.setTextFieldMaxLength(text_name, 30);
	}
}