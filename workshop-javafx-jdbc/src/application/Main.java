/*-------------------- packages --------------------*/
package application;

/*-------------------- imports --------------------*/
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/*-------------------- class Alerts --------------------*/
public class Main extends Application {

	/*-------------------- functions --------------------*/
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			
			ScrollPane scroll_pane = loader.load();
			scroll_pane.setFitToHeight(true);
			scroll_pane.setFitToWidth(true);
			
			Scene mainScene = new Scene(scroll_pane);
			
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();
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