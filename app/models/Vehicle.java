package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
//import com.avaje.ebean.Model;
import javax.persistence.*;

import play.data.validation.Constraints.Required;

@MappedSuperclass
@Deprecated

public class Vehicle extends Model {
	@Id
	public int id;
	
	@Required
	public String licenseNo;
	
	@Required
	public String make;
	
	public String model;
	
	public String year;
	
	public long longitude;
	
	public long latitude;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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
	 * @param licenseNo
	 * @param make
	 * @param model
	 * @param year
	 */
	public Vehicle(String licenseNo, String make, String model,String year){
		this.licenseNo=licenseNo;
		this.make=make;
		this.model=model;
		this.year=year;
	}
	
	public Vehicle() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * method for saving Vehicle  object to database
	 * @param make
	 * @param model
	 * @param year
	 */
	public static Vehicle saveToDB(String licenseNo, String make, String model, String year){
		Vehicle newVehicle=new Vehicle(licenseNo, make, model,year);
		newVehicle.save();
		return newVehicle;
	}
}
