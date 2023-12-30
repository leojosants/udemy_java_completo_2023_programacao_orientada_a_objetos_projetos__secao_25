/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

/*-------------------- class DepartmentFormController --------------------*/
public class DepartmentFormController implements Initializable {

	/*-------------------- attributes --------------------*/
	private Department entity;
	private DepartmentService service;
	private List<DataChangeListener> data_change_listeners = instanceateListDataChangeListenerArrayList();
	
	@FXML
	private TextField text_id;
	
	@FXML
	private TextField text_name;
	
	@FXML
	private Label label_error_name;
	
	@FXML
	private Button button_save;
	
	@FXML
	private Button button_cancel;

	/*-------------------- getters and setters --------------------*/
	public void setDepartment(Department entity) {
		this.entity = entity;
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	/*-------------------- methods --------------------*/
	public void subscribeDataChangeListener(DataChangeListener listener) {
		this.data_change_listeners.add(listener);
	}
	
	public void onButtonSaveAction(ActionEvent event) {
		if (this.entity == null) { throw new IllegalStateException("Entity was null"); }
		if (this.service == null) { throw new IllegalStateException("Service was null"); }
		
		try {
			this.entity = getFormDate();
			this.service.saveOrUpdate(this.entity);
			this.notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} 
		catch (DbException e) {
			Alerts.showAlert("Error save objects", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : this.data_change_listeners) {
			listener.onDataChanged();
		}
	}

	private Department getFormDate() {
		Department obj = instanceateDepartment();
		obj.setId(Utils.tryParseToInt(this.text_id.getText()));
		obj.setName(this.text_name.getText());
		return obj;
	}

	public void onButtonCancelAction(ActionEvent event){
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		this.initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(text_id);
		Constraints.setTextFieldMaxLength(text_name, 30);
	}
	
	public void updateFormData() {
		if (this.entity == null) { throw new IllegalStateException("Entity was null"); }
		this.text_id.setText(String.valueOf(this.entity.getId()));
		this.text_name.setText(this.entity.getName());
	}
	
	private List<DataChangeListener> instanceateListDataChangeListenerArrayList() {
		return new ArrayList<>();
	}
	
	private Department instanceateDepartment() {
		return new Department();
	}
}