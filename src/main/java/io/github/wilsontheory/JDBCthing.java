package io.github.wilsontheory;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.github.wilsontheory.dao.DAOthing;

public class JDBCthing {
	
	private DataSource dataSource;
	public static void main(String[] args){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		DAOthing daoThing = (DAOthing) context.getBean("DAOthing");
		Cat cat = daoThing.getCat(1);
		System.out.println("cat " + cat.getName() + " has been retrieved");
		System.out.println("cat count: " + daoThing.getCatCount());
		System.out.println("cat name : " + daoThing.getCatName(1));
		System.out.println("get representative cat object " + daoThing.getCat(1).toString());
		daoThing.getAllCats().stream().forEach((thisCat) -> System.out.println("Catlist: " + thisCat.toString()));
	}
}
