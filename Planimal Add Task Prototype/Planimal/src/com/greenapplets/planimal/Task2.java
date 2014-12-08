package com.greenapplets.planimal;

/**
Task.java is a Transfer Object Class.
It defines the attributes of Person-in-charge entity and provides the methods needed such as setters/constructors and helping method like convert().
Author: Algina Castillo
**/

import java.util.*;
import java.text.*;

public class Task2 {
	String taskname;
	String desc;
	String venue;
	//String ddl;
	Date deadline;
	PersonInCharge pic;
	
	public Task2() {
		
	}
 
	public Task2(String taskname, String desc, String venue,Date deadline, String personIC, String password) {
		this.taskname= taskname;
		this.desc=desc;
		this.deadline = deadline;
		this.venue=venue;
		this.pic=new PersonInCharge(personIC, password);
	}
	
	/**
		convert() method parse the given String to Dare
		@param date is the string to be parsed into Date
		@return dd is the Date form or value of parameter date
	**/
	public Date convert(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		Date dd = df.parse(date);
		return dd;
	}
	
	// used to populate the list to show in the horizontal ui
	public String getTask(){
		String task = new String();
		String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(deadline);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    
	    task = month + "/" + day + "/" + year + " - " + days[cal.get(Calendar.DAY_OF_WEEK) - 1] + "\n" +
	    		taskname + "-" + venue + "\n";
		
		return task;		
	}
}
