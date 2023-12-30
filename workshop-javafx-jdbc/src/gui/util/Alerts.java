/*-------------------- packages --------------------*/
package gui.util;

/*-------------------- imports --------------------*/
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/*-------------------- class Alerts --------------------*/
public class Alerts {

	/*-------------------- methods --------------------*/
	public static void showAlert(String title, String header, String content, AlertType alert_type) {
		Alert alert = new Alert(alert_type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}

	public static Optional<ButtonType> showConfirmation(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		return alert.showAndWait();
	}

}