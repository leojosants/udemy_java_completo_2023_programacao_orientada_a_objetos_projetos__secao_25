/*-------------------- packages --------------------*/
package model.dao;

/*-------------------- imports --------------------*/
import java.util.List;
import model.entities.Department;

/*-------------------- interface DepartmentDao --------------------*/
public interface DepartmentDao {

	/*-------------------- methods --------------------*/
	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
