package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


//import com.avaje.ebean.Model;
import javax.persistence.*;

import play.data.validation.Constraints.Required;

@Entity
public class Truck extends Vehicle{
	@Required
	public String make;
	
	@Required
	public String model;
	
	@Required
	public String year;
	


public Truck(String make, String model, String year){
	this.make=make;
	this.model=model;
	this.year=year;
}
}