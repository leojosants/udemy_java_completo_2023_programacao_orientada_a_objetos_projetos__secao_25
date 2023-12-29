/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.ResourceBundle;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

/*-------------------- class DepartmentFormController --------------------*/
public class DepartmentFormController implements Initializable {

	/*-------------------- attributes --------------------*/
	private Department entity;
	
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
	
	/*-------------------- methods --------------------*/
	public void onButtonSaveAction() {
		System.out.println("onButtonSaveAction");
	}
	
	public void onButtonCancelAction() {
		System.out.println("onButtonCancelAction");
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
		if (this.entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		this.text_id.setText(String.valueOf(this.entity.getId()));
		this.text_name.setText(this.entity.getName());
	}
}