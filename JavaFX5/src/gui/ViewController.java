/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*-------------------- class ViewController --------------------*/
public class ViewController implements Initializable {
	
	/*-------------------- attribute --------------------*/
	@FXML
	private TextField txt_number_one;
	
	@FXML
	private TextField txt_number_two;
	
	@FXML
	private Label label_result;
	
	@FXML
	private Button button_sum;
	
	/*-------------------- methods --------------------*/
	@FXML
	public void onButtonSumAction() {
		try {
			Locale.setDefault(Locale.US);
			double number_one = Double.parseDouble(this.txt_number_one.getText());
			double number_two = Double.parseDouble(this.txt_number_two.getText());
			double sum = number_one + number_two;
			this.label_result.setText(String.format("%.2f", sum));
		} 
		catch (NumberFormatException e) {
			Alerts.showAlert("Error", "Parse error", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		Constraints.setTextFieldDouble(txt_number_one);
		Constraints.setTextFieldDouble(txt_number_two);
		Constraints.setTextFieldMaxLength(txt_number_one, 12);
		Constraints.setTextFieldMaxLength(txt_number_two, 12);
	}
}
