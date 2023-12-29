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
		return dao.findAll();
	}
}