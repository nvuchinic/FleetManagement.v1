package models;

import java.util.List;

import play.data.validation.Constraints.Required;
//import play.db.ebean.Model;
import com.avaje.ebean.Model;
import javax.persistence.*;
import javax.persistence.*;
import com.avaje.ebean.Model.Finder;

/**
 * Class for representing Truck model.
 * It extends Vehicle class
 * @author nermin vucinic
 * @version 1.0
 * @since 28.07.2015.
 */
@Entity
public class Truck extends Vehicle {

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
	
	public String status;

	/**
	 * constructor method
	 * @param licenseNo
	 * @param latitude
	 * @param longitude
	 * @param make
	 * @param model
	 * @param year
	 * @param numOfContainers
	 * @param status
	 */
	public Truck(String licenseNo, double latitude, double longitude, String make,
			String model, String year, int numOfContainers, String status,double mileage) {
		super(licenseNo, latitude, longitude);
		this.make = make;
		this.model = model;
		this.year = year;
		this.numOfContainers = numOfContainers;
		this.status = status;
		this.mileage=mileage;
	}

	/**
	 * For storing newly created Truck object to database
	 * @param licenseNo
	 * @param latitude
	 * @param longitude
	 * @param make
	 * @param model
	 * @param year
	 * @param numOfContainers
	 * @param status
	 * @return
	 */
	public static Truck saveToDB(String licenseNo, double latitude,
			double longitude, String make, String model, String year,
			int numOfContainers, String status, double mileage) {
		Truck t = new Truck(licenseNo, latitude, longitude, make, model, year,
				numOfContainers, status, mileage);
		t.save();
		return t;
	}

	public static Finder<Long, Truck> find = new Finder<Long, Truck>(
			Long.class, Truck.class);

	/**
	 * Finds all Truck objects stored in database
	 * @return all Truck objects stored in database
	 */
	public static List<Truck> allTrucks() {
		return find.all();
	}
	/**
	 * Finds and returns Truck object based on string parameter 
	 * @param licenceNo
	 * @return
	 */
	public static Truck findByLicenceNo(String licenceNo) {
		return find.where().eq("licenseNo", licenceNo).findUnique();
	}
	
	/**
	 * Finds and returns Truck object based on passed parameter
	 * @param id
	 * @return
	 */
	public static Truck findById(long id) {
		return find.where().eq("id", id).findUnique();
	}
}