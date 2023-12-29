/*-------------------- packages --------------------*/
package gui.util;

/*-------------------- imports --------------------*/
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/*-------------------- class Utils --------------------*/
public class Utils {

	/*-------------------- methods --------------------*/
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
}