package io.github.wilsontheory.dao;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.stereotype.Component;

import io.github.wilsontheory.Cat;

@Component
public class SimpleJdbcDao extends SimpleJdbcDaoSupport {
	//extends support abstract class to make easier SimpleJdbcTemplate
	
	public void insertCat(Cat thisCat){
		String query = "INSERT INTO cats (ID, NAME) VALUES (?, ?)";
		this.getJdbcTemplate().update(query, new Object[] {thisCat.getId(), thisCat.getName()});
	}

}
