package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
//import com.avaje.ebean.Model;
import javax.persistence.*;

import play.data.validation.Constraints.Required;

@MappedSuperclass
@Deprecated
@Entity
public class Vehicle extends Model {
	@Id
	public int id;
	
	@Required
	public String make;
	
	public String model;
	
	public String year;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * constructor method
	 * @param make
	 * @param model
	 * @param year
	 */
	public Vehicle(String make, String model,String year){
		this.make=make;
		this.model=model;
		this.year=year;
	}
	
	/**
	 * method for saving Vehicle  object to database
	 * @param make
	 * @param model
	 * @param year
	 */
	public static Vehicle saveToDB(String make, String model, String year){
		Vehicle newVehicle=new Vehicle(make, model,year);
		newVehicle.save();
		return newVehicle;
	}
}
