package com.greenapplets.planimal;

/**
PersonInCharge.java is a Transfer Object Class.
It defines the attributes of Person-in-charge entity and provides the methods needed such as setters/constructors and getters.
**/

public class PersonInCharge {

private String personIC;
private String password;
	
public PersonInCharge() {
}

public PersonInCharge(String PIC, String pw) {
	personIC=PIC;
	password=pw;
}
	
public String getPICName() {
	return personIC;
}

public String getPassword() {
	return password;
}
	
}
