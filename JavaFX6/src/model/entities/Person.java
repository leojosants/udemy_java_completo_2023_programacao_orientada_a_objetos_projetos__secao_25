/*-------------------- packages --------------------*/
package model.entities;

/*-------------------- imports --------------------*/
import java.io.Serializable;

/*-------------------- class Person --------------------*/
public class Person implements Serializable {

	/*-------------------- attributes --------------------*/
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String email;
	
	/*-------------------- constructors --------------------*/
	public Person() {}

	public Person(Integer id, String name, String email) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
	}

	/*-------------------- constructors --------------------*/
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/*-------------------- methods --------------------*/
	@Override
	public String toString() {
		return "Person\n" +
				String.format("Id .....: %d%n", this.getId()) +
				String.format("Nome ...: %s%n", this.getName()) +
				String.format("Email ..: %s%n", this.getEmail());
	}
	
	public void displayPerson() {
		System.out.println(toString());
	}
}






