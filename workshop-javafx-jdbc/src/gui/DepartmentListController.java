/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

/*-------------------- class DepartmentListController --------------------*/
public class DepartmentListController implements Initializable {

	/*-------------------- attributes --------------------*/
	@FXML
	private TableView<Department> table_view_department;
	
	@FXML
	private TableColumn<Department, Integer> table_column_id;
	
	@FXML
	private TableColumn<Department, String> table_column_name;
	
	@FXML
	private Button button_new;
	
	/*-------------------- methods --------------------*/
	@FXML
	public void onButtonNewAction() {
		System.out.println("onButtonNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		initializeNodes();
	}

	private void initializeNodes() {
		this.table_column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.table_column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		this.table_view_department.prefHeightProperty().bind(stage.heightProperty());
	}		
}