package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Person {

	protected String name;
	protected Date dob;			//alternatively use GregorianCalendar
	
	public Person(String name, Date dob)
	{
		this.name = name;
		this.dob = dob;
	}
	public Person(String[] str)
	{
		this.name = str[1];
		
		//Process the date string to get DOB
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		try {
			calendar.setTime(dateFormat.parse(str[2]));
		} catch (ParseException e) {
			//Error processing date, using default date
		}
		dob=calendar.getTime();
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}


	public String toString()
	{
		return name + ", " + dob;
	}
	
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Person)
		{
			Person p = (Person) obj;
			return name.equals(p.name) && dob.equals(p.dob);
		}
		else
			return false;
	}
	
}
