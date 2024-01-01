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
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM department WHERE Id = ?");
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Department obj = new Department();
				obj.setId(resultSet.getInt("Id"));
				obj.setName(resultSet.getString("Name"));
				return obj;
			}
			
			return null;
		}
		catch (SQLException e) { throw new DbException(e.getMessage()); }
		finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM department ORDER BY Name");
			resultSet = preparedStatement.executeQuery();
			List<Department> list = instanceateListDepartmentArrayList();

			while (resultSet.next()) {
				Department obj = instanceateDepartment();
				obj.setId(resultSet.getInt("Id"));
				obj.setName(resultSet.getString("Name"));
				list.add(obj);
			}
			
			return list;
		}
		catch (SQLException e) { throw new DbException(e.getMessage()); }
		finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement preparedStatement = null;
	
		try {
			preparedStatement = connection.prepareStatement(
				"INSERT INTO department " +
				"(Name) " +
				"VALUES " +
				"(?)", 
				Statement.RETURN_GENERATED_KEYS
			);

			preparedStatement.setString(1, obj.getName());
			int rowsAffected = preparedStatement.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					obj.setId(id);
				}
			}
			else { throw new DbException("Unexpected error! No rows affected!"); }
		}
		catch (SQLException e) { throw new DbException(e.getMessage()); } 
		finally { DB.closeStatement(preparedStatement); }
	}

	@Override
	public void update(Department obj) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(
				"UPDATE department " +
				"SET Name = ? " +
				"WHERE Id = ?"
			);

			preparedStatement.setString(1, obj.getName());
			preparedStatement.setInt(2, obj.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) { throw new DbException(e.getMessage()); } 
		finally { DB.closeStatement(preparedStatement); }
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM department WHERE Id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) { throw new DbIntegrityException(e.getMessage()); } 
		finally { DB.closeStatement(preparedStatement); }
	}
	
	private Department instanceateDepartment() {
		return new Department();
	}

	private List<Department> instanceateListDepartmentArrayList() {
		return new ArrayList<>();
	}
}
