/*-------------------- packages --------------------*/
package model.dao;

/*-------------------- imports --------------------*/
import java.util.List;
import model.entities.Department;
import model.entities.Seller;

/*-------------------- interface SellerDao --------------------*/
public interface SellerDao {

	/*-------------------- methods --------------------*/
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
}
