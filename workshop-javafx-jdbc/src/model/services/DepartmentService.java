/*-------------------- packages --------------------*/
package model.services;

/*-------------------- imports --------------------*/
import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

/*-------------------- class DepartmentService --------------------*/
public class DepartmentService {
	
	/*-------------------- attributes --------------------*/
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	/*-------------------- methods --------------------*/
	public List<Department> findAll() {
		return this.dao.findAll();
	}
	
	public void saveOrUpdate(Department obj) {
		if (obj.getId() == null) {
			this.dao.insert(obj);
		}
		else {
			this.dao.update(obj);
		}
	}
}