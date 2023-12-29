/*-------------------- packages --------------------*/
package gui;

/*-------------------- imports --------------------*/
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.Person;

/*-------------------- class ViewController --------------------*/
public class ViewController implements Initializable {

	/*-------------------- attribute --------------------*/
	@FXML
	private ComboBox<Person> combobox_person;
	
	@FXML
	private Button button_all;

	private ObservableList<Person> observable_list;

	/*-------------------- methods --------------------*/
	@FXML
	public void onButtonAllAction() {
		for (Person person : this.combobox_person.getItems()) {
			System.out.println(person);
		}
	}
	
	@FXML
	public void onComboBoxPersonAction() {
		Person person = this.combobox_person.getSelectionModel().getSelectedItem();
		System.out.println(person);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resource_bundle) {
		List<Person> list = instanceateListPersonArrayList();
		list.add(instanceatePerson(generateId(), "Maria", "maria@gmail.com"));
		list.add(instanceatePerson(generateId(), "Alex", "alex@gmail.com"));
		list.add(instanceatePerson(generateId(), "Bob", "bob@gmail.com"));

		this.observable_list = FXCollections.observableArrayList(list);
		this.combobox_person.setItems(this.observable_list);

		Callback<ListView<Person>, ListCell<Person>> factory = lv -> new ListCell<Person>() {
			@Override
			protected void updateItem(Person item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		
		this.combobox_person.setCellFactory(factory);
		this.combobox_person.setButtonCell(factory.call(null));
	}

	private Integer generateId() {
		return (int) (Math.random() * 1000);
	}

	private Person instanceatePerson(Integer id, String name, String email) {
		return new Person(id, name, email);
	}

	private List<Person> instanceateListPersonArrayList() {
		return new ArrayList<>();
	}
}
