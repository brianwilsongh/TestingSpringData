package io.github.wilsontheory.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="cats")
public class Cat {
	
	@Id
	int id;
	
	String name;
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    public Cat(int inputId, String inputName){
    	id = inputId;
    	name = inputName;
    }

	public Cat() {
	}
}
