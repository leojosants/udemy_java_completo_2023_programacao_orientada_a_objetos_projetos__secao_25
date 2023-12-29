/*-------------------- packages --------------------*/
package model.entities;

/*-------------------- imports --------------------*/
import java.io.Serializable;

/*-------------------- class Department --------------------*/
public class Department implements Serializable {

	/*-------------------- attributes --------------------*/
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;

	/*-------------------- constructors --------------------*/
	public Department() {}

	public Department(Integer id, String name) {
		this.setId(id);
		this.setName(name);
	}

	/*-------------------- getters and setters --------------------*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*-------------------- methods --------------------*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Department other = (Department) obj;
		
		if (this.getId() == null) {
			if (other.getId() != null) return false;
		} 
		else if (!this.getId().equals(other.getId())) return false;

		return true;
	}

	@Override
	public String toString() {
		return String.format("Department [id: %d, name: %s%n]", this.getId(), this.getName());
	}
}