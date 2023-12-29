/*-------------------- packages --------------------*/
package model.entities;

/*-------------------- imports --------------------*/
import java.io.Serializable;
import java.util.Date;

/*-------------------- class Seller --------------------*/
public class Seller implements Serializable {

	/*-------------------- attributes --------------------*/
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String email;
	private Date birth_date;
	private Double base_salary;
	private Department department;
	
	/*-------------------- packages --------------------*/
	public Seller() {
	}

	public Seller(Integer id, String name, String email, Date birth_date, Double base_salary, Department department) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setBirthDate(birth_date);
		this.setBaseSalary(base_salary);
		this.setDepartment(department);
	}

	/*-------------------- packages --------------------*/
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birth_date;
	}

	public void setBirthDate(Date birthDate) {
		this.birth_date = birthDate;
	}

	public Double getBaseSalary() {
		return base_salary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.base_salary = baseSalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/*-------------------- packages --------------------*/
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

		Seller other = (Seller) obj;
		
		if (this.getId() == null) {
			if (other.getId() != null) return false;
		} 
		else if (!this.getId().equals(other.getId())) return false;

		return true;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", email=" + email + ", birthDate=" + birth_date + ", baseSalary="
				+ base_salary + ", department=" + department + "]";
	}
}
