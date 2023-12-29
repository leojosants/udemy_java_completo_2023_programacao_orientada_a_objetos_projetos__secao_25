/*-------------------- packages --------------------*/
package gui.util;

/*-------------------- imports --------------------*/
import javafx.scene.control.TextField;

/*-------------------- class Constraints --------------------*/
public class Constraints {

	/*-------------------- methods --------------------*/
	public static void setTextFieldInteger(TextField text_field) {
		text_field.textProperty().addListener((obs, old_value, new_value) -> {
			if (new_value != null && !new_value.matches("\\d*")) {
				text_field.setText(old_value);
			}
		});
	}

	public static void setTextFieldMaxLength(TextField text_field, int max) {
		text_field.textProperty().addListener((obs, old_value, new_value) -> {
			if (new_value != null && new_value.length() > max) {
				text_field.setText(old_value);
			}
		});
	}

	public static void setTextFieldDouble(TextField text_field) {
		text_field.textProperty().addListener((obs, old_value, new_value) -> {
			if (new_value != null && !new_value.matches("\\d*([\\.]\\d*)?")) {
				text_field.setText(old_value);
			}
		});
	}
}