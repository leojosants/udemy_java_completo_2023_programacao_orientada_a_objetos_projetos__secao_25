/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

/*-------------------- class DepartmentListController --------------------*/
public class DepartmentListController implements Initializable, DataChangeListener {

	/*-------------------- attributes --------------------*/
	private DepartmentService service;

	@FXML
	private TableView<Department> table_view_department;

	@FXML
	private TableColumn<Department, Integer> table_column_id;

	@FXML
	private TableColumn<Department, String> table_column_name;

	@FXML
	private TableColumn<Department, Department> table_column_edit;

	@FXML
	private Button button_new;

	private ObservableList<Department> observable_list;

	/*-------------------- methods --------------------*/
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@FXML
	public void onButtonNewAction(ActionEvent event) {
		Stage parent_stage = Utils.currentStage(event);
		Department obj = instanceateDepartment();
		this.createDialogForm(obj, "/gui/DepartmentForm.fxml", parent_stage);
	}

	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		this.initializeNodes();
	}

	private void initializeNodes() {
		this.table_column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.table_column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		this.table_view_department.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (this.service == null) { throw new IllegalStateException("Service was null"); }
		List<Department> list = this.service.findAll();
		this.observable_list = FXCollections.observableArrayList(list);
		this.table_view_department.setItems(this.observable_list);
		this.initEditButtons();
	}

	private void createDialogForm(Department obj, String absolute_name, Stage parent_stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolute_name));
			Pane pane = loader.load();

			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(instanceateDepartmentService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialog_stage = instanceateStage();
			dialog_stage.setTitle("Enter Department data");
			dialog_stage.setScene(new Scene(pane));
			dialog_stage.setResizable(false);
			dialog_stage.initOwner(parent_stage);
			dialog_stage.initModality(Modality.WINDOW_MODAL);
			dialog_stage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading biew", e.getMessage(), AlertType.ERROR);
		}
	}

	private Stage instanceateStage() {
		return new Stage();
	}

	private DepartmentService instanceateDepartmentService() {
		return new DepartmentService();
	}

	private Department instanceateDepartment() {
		return new Department();
	}

	@Override
	public void onDataChanged() {
		this.updateTableView();
	}

	private void initEditButtons() {
		this.table_column_edit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		
		this.table_column_edit.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		});
	}
}