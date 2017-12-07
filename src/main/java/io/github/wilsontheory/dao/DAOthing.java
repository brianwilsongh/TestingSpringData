package io.github.wilsontheory.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
			
			conn = dataSource.getConnection(); //eliminate 3 lines above
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

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
