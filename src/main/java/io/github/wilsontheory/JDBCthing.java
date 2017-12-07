package io.github.wilsontheory;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.github.wilsontheory.dao.DAOthing;
import io.github.wilsontheory.dao.HibernateDao;
import io.github.wilsontheory.dao.SimpleJdbcDao;
import io.github.wilsontheory.model.Cat;

public class JDBCthing {
	
	private DataSource dataSource;
	public static void main(String[] args){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		
		DAOthing daoThing = (DAOthing) context.getBean("DAOthing");
		SimpleJdbcDao simpleDao = context.getBean("simpleJdbcDao", SimpleJdbcDao.class);
		HibernateDao hibernateDao = context.getBean("hibernateDao", HibernateDao.class);
		
		daoThing.resetCatTable();
		Cat cat = daoThing.getCat(1);
		System.out.println("cat " + cat.getName() + " has been retrieved the old fashioned way");
		System.out.println("cat count: " + daoThing.getCatCount());
		System.out.println("cat name : " + daoThing.getCatName(1));
		System.out.println("get representative cat object " + daoThing.getCat(1).toString());
		
		daoThing.insertCat(new Cat(2, "Mittens"));
		daoThing.insertCatNamedParams(new Cat(3, "Khajiit"));
		simpleDao.insertCat(new Cat(4, "Sylvester"));
		hibernateDao.insertCat(new Cat(5, "Hibernatio"));
		
		daoThing.getAllCats().stream().forEach((thisCat) -> System.out.println("Iteration: " + thisCat.getName() + " is " + thisCat.toString()));
	}
}
