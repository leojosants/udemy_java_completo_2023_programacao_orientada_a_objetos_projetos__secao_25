/*-------------------- packages --------------------*/
package model.dao.impl;

/*-------------------- imports --------------------*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

/*-------------------- class DepartmentDaoJDBC --------------------*/
public class DepartmentDaoJDBC implements DepartmentDao {

	/*-------------------- methods --------------------*/
	private Connection connection;
	
	/*-------------------- methods --------------------*/
	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Department findById(Integer id) {
		PreparedStatement prepared_statement = null;
		ResultSet result_set = null;
	
		try {
			prepared_statement = connection.prepareStatement("SELECT * FROM department WHERE Id = ?");
			prepared_statement.setInt(1, id);
			
			result_set = prepared_statement.executeQuery();
			
			if (result_set.next()) {
				Department obj = new Department();
				obj.setId(result_set.getInt("Id"));
				obj.setName(result_set.getString("Name"));
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

	@Override
	public List<Department> findAll() {
		PreparedStatement prepared_statement = null;
		ResultSet result_set = null;
	
		try {
			prepared_statement = connection.prepareStatement("SELECT * FROM department ORDER BY Name");
			result_set = prepared_statement.executeQuery();

			List<Department> list = new ArrayList<>();

			while (result_set.next()) {
				Department obj = new Department();
				obj.setId(result_set.getInt("Id"));
				obj.setName(result_set.getString("Name"));
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
	public void insert(Department obj) {
		PreparedStatement prepared_statement = null;
	
		try {
			prepared_statement = connection.prepareStatement(
				"INSERT INTO department " +
				"(Name) " +
				"VALUES " +
				"(?)", 
				Statement.RETURN_GENERATED_KEYS
			);

			prepared_statement.setString(1, obj.getName());

			int rows_affected = prepared_statement.executeUpdate();
			
			if (rows_affected > 0) {
				ResultSet rs = prepared_statement.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
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
	public void update(Department obj) {
		PreparedStatement prepared_statement = null;
		try {
			prepared_statement = connection.prepareStatement(
				"UPDATE department " +
				"SET Name = ? " +
				"WHERE Id = ?"
			);

			prepared_statement.setString(1, obj.getName());
			prepared_statement.setInt(2, obj.getId());
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
			prepared_statement = connection.prepareStatement("DELETE FROM department WHERE Id = ?");
			prepared_statement.setInt(1, id);
			prepared_statement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(prepared_statement);
		}
	}
}
