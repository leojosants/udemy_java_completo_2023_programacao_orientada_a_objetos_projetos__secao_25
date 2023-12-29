/*-------------------- packages --------------------*/
package application;

/*-------------------- imports --------------------*/
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*-------------------- class Main --------------------*/
public class Main extends Application {

	/*-------------------- functions --------------------*/
	@Override
	public void start(Stage stage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/gui/View.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*-------------------- main method --------------------*/
	public static void main(String[] args) {
		launch(args);
	}
}