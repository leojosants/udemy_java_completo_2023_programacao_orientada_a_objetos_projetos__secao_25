/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

/*-------------------- class SellerFormController --------------------*/
public class SellerFormController implements Initializable {

	/*-------------------- attributes --------------------*/
	private Seller entity;
	private SellerService service;
	private List<DataChangeListener> dataChangeListeners = instanceateListDataChangeListenerArrayList();
	
	@FXML
	private TextField textId;
	
	@FXML
	private TextField textName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button buttonSave;
	
	@FXML
	private Button buttonCancel;

	/*-------------------- getters and setters --------------------*/
	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}
	
	/*-------------------- methods --------------------*/
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
		catch (DbException e) {
			Alerts.showAlert("Error save objects", null, e.getMessage(), AlertType.ERROR);
		}
		catch (ValidationException e) {
			this.setErrorMessages(e.getErros());
		}
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

		if (this.textName.getText() == null || this.textName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		
		obj.setName(this.textName.getText());
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
		Constraints.setTextFieldMaxLength(textName, 30);
	}
	
	public void updateFormData() {
		if (this.entity == null) { throw new IllegalStateException("Entity was null"); }
		this.textId.setText(String.valueOf(this.entity.getId()));
		this.textName.setText(this.entity.getName());
	}
	
	private List<DataChangeListener> instanceateListDataChangeListenerArrayList() {
		return new ArrayList<>();
	}
	
	private Seller instanceateSeller() {
		return new Seller();
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) { this.labelErrorName.setText(errors.get("name")); }
	}
}