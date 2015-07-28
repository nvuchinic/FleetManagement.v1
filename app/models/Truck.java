package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;




//import com.avaje.ebean.Model;
import javax.persistence.*;

import play.data.validation.Constraints.Required;

@Entity
public class Truck extends Vehicle{
	
	@Id
	public int id;
	
	@Required
	public String make;
	
	@Required
	public String model;
	
	@Required
	public String year;
	
	@Required
	public int numOfContainers;
	


public Truck(String licenseNo, long latitude, long longitude, String make, String model, String year, int numOfContainers){
	super(licenseNo, latitude, longitude);
	this.make=make;
	this.model=model;
	this.year=year;
	this.numOfContainers=numOfContainers;
}

public static Truck saveToDB(String licenseNo, long latitude, long longitude, String make, String model, String year, int numOfContainers){
	Truck t=new Truck(licenseNo, latitude, longitude, make, model, year, numOfContainers);
	t.save();
	return t;
}
}