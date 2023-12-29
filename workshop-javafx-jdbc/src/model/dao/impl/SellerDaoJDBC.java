/*-------------------- packages --------------------*/
package model.dao.impl;

/*-------------------- imports --------------------*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/*-------------------- class SellerDaoJDBC --------------------*/
public class SellerDaoJDBC implements SellerDao {
	
	/*-------------------- attributes --------------------*/
	private Connection connection;

	/*-------------------- methods --------------------*/
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement prepared_statement = null;
	
		try {
			prepared_statement = connection.prepareStatement(
				"INSERT INTO seller " +
				"(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
				"VALUES " +
				"(?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			
			prepared_statement.setString(1, obj.getName());
			prepared_statement.setString(2, obj.getEmail());
			prepared_statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			prepared_statement.setDouble(4, obj.getBaseSalary());
			prepared_statement.setInt(5, obj.getDepartment().getId());
			
			int rows_affected = prepared_statement.executeUpdate();
			
			if (rows_affected > 0) {
				ResultSet result_set = prepared_statement.getGeneratedKeys();
				if (result_set.next()) {
					int id = result_set.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(result_set);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared_statement);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement prepared_statement = null;
	
		try {
			prepared_statement = connection.prepareStatement(
				"UPDATE seller " +
				"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
				"WHERE Id = ?"
			);
			
			prepared_statement.setString(1, obj.getName());
			prepared_statement.setString(2, obj.getEmail());
			prepared_statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			prepared_statement.setDouble(4, obj.getBaseSalary());
			prepared_statement.setInt(5, obj.getDepartment().getId());
			prepared_statement.setInt(6, obj.getId());
			prepared_statement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared_statement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement prepared_statement = null;
		
		try {
			prepared_statement = connection.prepareStatement("DELETE FROM seller WHERE Id = ?");
			prepared_statement.setInt(1, id);
			prepared_statement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared_statement);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement prepared_statement = null;
		ResultSet result_set = null;

		try {
			prepared_statement = connection.prepareStatement(
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"WHERE seller.Id = ?"
			);
			
			prepared_statement.setInt(1, id);
			result_set = prepared_statement.executeQuery();
		
			if (result_set.next()) {
				Department dep = instantiateDepartment(result_set);
				Seller obj = instantiateSeller(result_set, dep);
				return obj;
			}
			
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared_statement);
			DB.closeResultSet(result_set);
		}
	}

	private Seller instantiateSeller(ResultSet result_set, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(result_set.getInt("Id"));
		obj.setName(result_set.getString("Name"));
		obj.setEmail(result_set.getString("Email"));
		obj.setBaseSalary(result_set.getDouble("BaseSalary"));
		obj.setBirthDate(result_set.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet result_set) throws SQLException {
		Department department = new Department();
		department.setId(result_set.getInt("DepartmentId"));
		department.setName(result_set.getString("DepName"));
		return department;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement prepared_statement = null;
		ResultSet result_set = null;
		
		try {
			prepared_statement = connection.prepareStatement(
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"ORDER BY Name"
			);
			
			result_set = prepared_statement.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (result_set.next()) {
				
				Department department = map.get(result_set.getInt("DepartmentId"));
				
				if (department == null) {
					department = instantiateDepartment(result_set);
					map.put(result_set.getInt("DepartmentId"), department);
				}
				
				Seller obj = instantiateSeller(result_set, department);
				list.add(obj);
			}
			
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared_statement);
			DB.closeResultSet(result_set);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement prepared_statement = null;
		ResultSet result_set = null;
	
		try {
			prepared_statement = connection.prepareStatement(
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"WHERE DepartmentId = ? " +
				"ORDER BY Name"
			);
			
			prepared_statement.setInt(1, department.getId());
			
			result_set = prepared_statement.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (result_set.next()) {
				Department dep = map.get(result_set.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(result_set);
					map.put(result_set.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instantiateSeller(result_set, dep);
				list.add(obj);
			}
			
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared_statement);
			DB.closeResultSet(result_set);
		}
	}
}
