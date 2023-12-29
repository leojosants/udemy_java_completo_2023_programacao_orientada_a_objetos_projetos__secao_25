/*-------------------- packages --------------------*/
package application;

/*-------------------- imports --------------------*/
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/*-------------------- class Main --------------------*/
public class Main extends Application {

	/*-------------------- attributes --------------------*/
	private static Scene main_scene;
	
	/*-------------------- methods --------------------*/
	@Override
	public void start(Stage primary_stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			
			ScrollPane scroll_pane = loader.load();
			scroll_pane.setFitToHeight(true);
			scroll_pane.setFitToWidth(true);
			
			main_scene = new Scene(scroll_pane);
			
			primary_stage.setScene(main_scene);
			primary_stage.setTitle("Sample JavaFX application");
			primary_stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return main_scene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}