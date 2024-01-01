/*-------------------- packages --------------------*/
package gui.util;

/*-------------------- imports --------------------*/
import javafx.scene.control.TextField;

/*-------------------- class Constraints --------------------*/
public class Constraints {

	/*-------------------- methods --------------------*/
	public static void setTextFieldInteger(TextField textField) {
		textField.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*")) {
				textField.setText(oldValue);
			}
		});
	}

	public static void setTextFieldMaxLength(TextField textField, int max) {
		textField.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				textField.setText(oldValue);
			}
		});
	}

	public static void setTextFieldDouble(TextField textField) {
		textField.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
				textField.setText(oldValue);
			}
		});
	}
}