package io.github.wilsontheory.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import io.github.wilsontheory.Cat;

@Component
public class DAOthing {
	
	@Autowired //add the DataSource created as bean as this member var, autowire since there is only one DataSource
	private DataSource dataSource;
	
	@Autowired //add jdbc template defined as bean
	private JdbcTemplate jdbcTemplate;
	
	public Cat getCat(int id){
		Connection conn = null;
		Cat finalCat = null;
//		String driver = "org.apache.derby.jdbc.ClientDriver";
		try {
//			Class.forName(driver).newInstance();
//			conn = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
			
			conn = dataSource.getConnection(); //eliminate 3 lines above with this alone
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM cats WHERE id = ?");
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			if (result.next()) finalCat = new Cat(id, result.getString("name").trim());
			result.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return finalCat;
	}
	
	public int getCatCount(){
		String query = "SELECT COUNT(*) FROM cats";
		return jdbcTemplate.queryForInt(query); //so easy! yay
	}
	
	public String getCatName(int id){
		String query = "SELECT name FROM cats WHERE id = ?";
		return jdbcTemplate.queryForObject(query,  new Object[] {id}, String.class);
	}
	
	public Cat getCatObject(int id){
		String query = "SELECT * FROM cats WHERE id = ?";
		//rowmapper (3rd arg) maps columns into fields in a model object
		return (Cat) jdbcTemplate.queryForObject(query,  new Object[] {id}, new catMapper());
	}
	
	public List<Cat> getAllCats(){
		//RowMapper's mapRow() is called for each row returned by query
		String query = "SELECT * FROM cats";
		return jdbcTemplate.query(query, new catMapper());
	}
	
	private static final class catMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) throws SQLException {
			//manually mapping row to an object, row num isn't necessarily used
			Cat thisCat = new Cat();
			thisCat.setId(resultSet.getInt("ID"));
			thisCat.setName(resultSet.getString("NAME"));
			return thisCat;
		}
	}
	
	public void insertCat(Cat thisCat){
		String query = "INSERT INTO cats (ID, NAME) VALUES (?, ?)";
		jdbcTemplate.update(query, new Object[] {thisCat.getId(), thisCat.getName()});
		//.update() is usually used for DML like insert/update/delete
	}
	
	public void resetCatTable(){
		//DDL is done with jdbcTemplate.execute()
		try {
			jdbcTemplate.execute("DROP TABLE cats");
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			System.out.println("**rebuilding cat table from scratch");
			jdbcTemplate.execute("CREATE TABLE cats (ID integer, NAME varchar(50))");
			this.insertCat(new Cat(1, "Chellk"));
		}
	}
	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
