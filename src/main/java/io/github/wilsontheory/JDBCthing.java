package io.github.wilsontheory;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.github.wilsontheory.dao.DAOthing;
import io.github.wilsontheory.dao.SimpleJdbcDao;

public class JDBCthing {
	
	private DataSource dataSource;
	public static void main(String[] args){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		
		DAOthing daoThing = (DAOthing) context.getBean("DAOthing");
		SimpleJdbcDao simpleDao = context.getBean("simpleJdbcDao", SimpleJdbcDao.class);
		
		daoThing.resetCatTable();
		Cat cat = daoThing.getCat(1);
		System.out.println("cat " + cat.getName() + " has been retrieved");
		System.out.println("cat count: " + daoThing.getCatCount());
		System.out.println("cat name : " + daoThing.getCatName(1));
		System.out.println("get representative cat object " + daoThing.getCat(1).toString());
		
		daoThing.insertCat(new Cat(2, "Mittens"));
		daoThing.insertCatNamedParams(new Cat(3, "Khajiit"));
		simpleDao.insertCat(new Cat(4, "Sylvester"));
		
		daoThing.getAllCats().stream().forEach((thisCat) -> System.out.println("Catlist: " + thisCat.getName() + " is " + thisCat.toString()));
	}
}
