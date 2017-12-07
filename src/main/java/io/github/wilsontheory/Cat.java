package io.github.wilsontheory;

public class Cat {
	
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
}
