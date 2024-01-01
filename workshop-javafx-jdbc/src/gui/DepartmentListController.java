/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import application.Main;
import db.DbIntegrityException;
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
import javafx.scene.control.ButtonType;
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
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private TableColumn<Department, Department> tableColumnEdit;

	@FXML
	private TableColumn<Department, Department> tableColumnRemove;

	@FXML
	private Button buttonNew;

	private ObservableList<Department> observableList;

	/*-------------------- methods --------------------*/
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@FXML
	public void onButtonNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department object = instanceateDepartment();
		this.createDialogForm(object, "/gui/DepartmentForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.initializeNodes();
	}

	private void initializeNodes() {
		this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		this.tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (this.service == null) { throw new IllegalStateException("Service was null"); }
		List<Department> list = this.service.findAll();
		this.observableList = FXCollections.observableArrayList(list);
		this.tableViewDepartment.setItems(this.observableList);
		this.initEditButtons();
		this.initRemoveButtons();
	}

	private void createDialogForm(Department object, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DepartmentFormController controller = loader.getController();
			controller.setDepartment(object);
			controller.setDepartmentService(instanceateDepartmentService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = instanceateStage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} 
		catch (IOException e) {
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
		this.tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		this.tableColumnEdit.setCellFactory(param -> new TableCell<Department, Department>() {
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

	private void initRemoveButtons() {
		this.tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		
		this.tableColumnRemove.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				
				if (obj == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Department obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if (result.get() == ButtonType.OK) { 
			if (this.service == null) { throw new IllegalStateException("Service was null"); }
			try {
				this.service.remove(obj);	
				this.updateTableView();
			} 
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		 }
	}
}