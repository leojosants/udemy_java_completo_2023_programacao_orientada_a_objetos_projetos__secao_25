/*-------------------- packages --------------------*/
package model.services;

/*-------------------- imports --------------------*/
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

/*-------------------- class SellerService --------------------*/
public class SellerService {
	
	/*-------------------- attributes --------------------*/
	private SellerDao dao = DaoFactory.createSellerDao();
	
	/*-------------------- methods --------------------*/
	public List<Seller> findAll() {
		return this.dao.findAll();
	}
	
	public void saveOrUpdate(Seller obj) {
		if (obj.getId() == null) { this.dao.insert(obj); }
		else { this.dao.update(obj); }
	}
	
	public void remove(Seller obj) {
		this.dao.deleteById(obj.getId());
	}
}