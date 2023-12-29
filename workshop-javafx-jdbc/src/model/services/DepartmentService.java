/*-------------------- packages --------------------*/
package model.services;

/*-------------------- imports --------------------*/
import java.util.ArrayList;
import java.util.List;
import model.entities.Department;

/*-------------------- class DepartmentService --------------------*/
public class DepartmentService {

	/*-------------------- methods --------------------*/
	public List<Department> findAll() {
		List<Department> list = instanceateListDepartmentArrayList();
		list.add(instanceateDepartment(generateId(), "Books"));
		list.add(instanceateDepartment(generateId(), "Computers"));
		list.add(instanceateDepartment(generateId(), "Electronics"));
		return list;
	}

	private Department instanceateDepartment(Integer id, String name) {
		return new Department(id, name);
	}

	private Integer generateId() {
		return (int) (Math.random() * 100);
	}

	private List<Department> instanceateListDepartmentArrayList() {
		return new ArrayList<>();
	}
}