/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

/*-------------------- class SellerFormController --------------------*/
public class SellerFormController implements Initializable {

	/*-------------------- attributes --------------------*/
	private Seller entity;
	private SellerService service;
	private DepartmentService departmentService;
	private List<DataChangeListener> dataChangeListeners = instanceateListDataChangeListenerArrayList();
	
	@FXML
	private TextField textId;
	
	@FXML
	private TextField textName;
	
	@FXML
	private TextField textEmail;
	
	@FXML
	private ComboBox<Department> comboBoxDepartment;
	
	@FXML
	private DatePicker datePickerBirthDate;

	@FXML
	private TextField textBaseSalary;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;

	@FXML
	private Label labelErrorBaseSalary;
	
	@FXML
	private Button buttonSave;
	
	@FXML
	private Button buttonCancel;
	
	private ObservableList<Department> observableList ;

	/*-------------------- getters and setters --------------------*/
	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	/*-------------------- methods --------------------*/
	public void setServices(SellerService service, DepartmentService departmentService) {
		this.service = service;
		this.departmentService = departmentService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		this.dataChangeListeners.add(listener);
	}
	
	public void onButtonSaveAction(ActionEvent event) {
		if (this.entity == null) { throw new IllegalStateException("Entity was null"); }
		if (this.service == null) { throw new IllegalStateException("Service was null"); }
		
		try {
			this.entity = this.getFormData();
			this.service.saveOrUpdate(this.entity);
			this.notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} 
		catch (DbException e) { Alerts.showAlert("Error save objects", null, e.getMessage(), AlertType.ERROR); }
		catch (ValidationException e) { this.setErrorMessages(e.getErros()); }
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : this.dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Seller getFormData() {
		Seller obj = instanceateSeller();
		ValidationException exception = new ValidationException("Validation error");
	
		obj.setId(Utils.tryParseToInt(this.textId.getText()));

		if (this.textName.getText() == null || this.textName.getText().trim().equals("")) { exception.addError("name", "Field can't be empty"); }
		
		obj.setName(this.textName.getText());
		
		if (this.textEmail.getText() == null || this.textEmail.getText().trim().equals("")) { exception.addError("email", "Field can't be empty"); }
		
		obj.setEmail(this.textEmail.getText());
		
		if (this.datePickerBirthDate.getValue() == null ) { exception.addError("birthDate", "Field can't be empty"); }
		else {
			Instant instant = Instant.from(this.datePickerBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setBirthDate(Date.from(instant));			
		}

		if (this.textBaseSalary.getText() == null || this.textBaseSalary.getText().trim().equals("")) { exception.addError("baseSalary", "Field can't be empty"); }
		
		obj.setBaseSalary(Utils.tryParseToDouble(this.textBaseSalary.getText()));
		obj.setDepartment(this.comboBoxDepartment.getValue());
		
		if (exception.getErros().size() > 0) { throw exception; }
		
		return obj;
	}

	public void onButtonCancelAction(ActionEvent event){
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(textId);
		Constraints.setTextFieldMaxLength(textName, 70);
		Constraints.setTextFieldDouble(textBaseSalary);
		Constraints.setTextFieldMaxLength(textEmail, 60);
		Utils.formatDatePicker(datePickerBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
	}
	
	public void updateFormData() {
		if (this.entity == null) { throw new IllegalStateException("Entity was null"); }
		
		this.textId.setText(String.valueOf(this.entity.getId()));
		this.textName.setText(this.entity.getName());
		this.textEmail.setText(this.entity.getEmail());
		
		Locale.setDefault(Locale.US);
		this.textBaseSalary.setText(String.format("%.2f", this.entity.getBaseSalary()));
		
		if (this.entity.getBirthDate() != null) {
			this.datePickerBirthDate.setValue(LocalDate.ofInstant(this.entity.getBirthDate().toInstant(), ZoneId.systemDefault()));			
		}
		
		if (this.entity.getDepartment() == null) { this.comboBoxDepartment.getSelectionModel().selectFirst(); }
		else { this.comboBoxDepartment.setValue(this.entity.getDepartment()); }
	}
	
	public void loadAssociatedObjects() {
		if (this.departmentService == null) { throw new IllegalStateException("DepartmentService eas null"); }
		List<Department> list = this.departmentService.findAll();
		this.observableList = FXCollections.observableArrayList(list);
		this.comboBoxDepartment.setItems(this.observableList);
	}
	
	private List<DataChangeListener> instanceateListDataChangeListenerArrayList() {
		return new ArrayList<>();
	}
	
	private Seller instanceateSeller() {
		return new Seller();
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		this.labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
		this.labelErrorEmail.setText((fields.contains("email") ? errors.get("email"): ""));
		this.labelErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate"): ""));
		this.labelErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary"): ""));
	}
	
	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		 };
		
		 comboBoxDepartment.setCellFactory(factory);
		 comboBoxDepartment.setButtonCell(factory.call(null));
	} 
}