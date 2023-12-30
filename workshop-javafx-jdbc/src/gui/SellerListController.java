/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.Date;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

/*-------------------- class SellerListController --------------------*/
public class SellerListController implements Initializable, DataChangeListener {

	/*-------------------- attributes --------------------*/
	private SellerService service;

	@FXML
	private TableView<Seller> table_view_seller;

	@FXML
	private TableColumn<Seller, Integer> table_column_id;

	@FXML
	private TableColumn<Seller, String> table_column_name;
	
	@FXML
	private TableColumn<Seller, String> table_column_email;
	
	@FXML
	private TableColumn<Seller, Date> table_column_birth_date;
	
	@FXML
	private TableColumn<Seller, Double> table_column_base_salary;

	@FXML
	private TableColumn<Seller, Seller> table_column_edit;

	@FXML
	private TableColumn<Seller, Seller> table_column_remove;

	@FXML
	private Button button_new;

	private ObservableList<Seller> observable_list;

	/*-------------------- methods --------------------*/
	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@FXML
	public void onButtonNewAction(ActionEvent event) {
		Stage parent_stage = Utils.currentStage(event);
		Seller obj = instanceateSeller();
		this.createDialogForm(obj, "/gui/SellerForm.fxml", parent_stage);
	}

	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		this.initializeNodes();
	}

	private void initializeNodes() {
		this.table_column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.table_column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.table_column_email.setCellValueFactory(new PropertyValueFactory<>("email"));
		this.table_column_birth_date.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
		Utils.formatTableColumnDate(this.table_column_birth_date, "dd/MM/yyyy");
		this.table_column_base_salary.setCellValueFactory(new PropertyValueFactory<>("base_salary"));		
		Utils.formatTableColumnDouble(this.table_column_base_salary, 2);
		Stage stage = (Stage) Main.getMainScene().getWindow();
		this.table_view_seller.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (this.service == null) { throw new IllegalStateException("Service was null"); }
		List<Seller> list = this.service.findAll();
		this.observable_list = FXCollections.observableArrayList(list);
		this.table_view_seller.setItems(this.observable_list);
		this.initEditButtons();
		this.initRemoveButtons();
	}

	private void createDialogForm(Seller obj, String absolute_name, Stage parent_stage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolute_name));
//			Pane pane = loader.load();
//
//			SellerFormController controller = loader.getController();
//			controller.setSeller(obj);
//			controller.setSellerService(instanceateSellerService());
//			controller.subscribeDataChangeListener(this);
//			controller.updateFormData();
//
//			Stage dialog_stage = instanceateStage();
//			dialog_stage.setTitle("Enter Seller data");
//			dialog_stage.setScene(new Scene(pane));
//			dialog_stage.setResizable(false);
//			dialog_stage.initOwner(parent_stage);
//			dialog_stage.initModality(Modality.WINDOW_MODAL);
//			dialog_stage.showAndWait();
//		} 
//		catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error loading biew", e.getMessage(), AlertType.ERROR);
//		}
	}

	private Stage instanceateStage() {
		return new Stage();
	}

	private SellerService instanceateSellerService() {
		return new SellerService();
	}

	private Seller instanceateSeller() {
		return new Seller();
	}

	@Override
	public void onDataChanged() {
		this.updateTableView();
	}

	private void initEditButtons() {
		this.table_column_edit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		this.table_column_edit.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		this.table_column_remove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		
		this.table_column_remove.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
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

	private void removeEntity(Seller obj) {
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