package models;

import play.data.validation.Constraints.Required;

//import play.db.ebean.Model;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

/**
 * This class represents vehicle model. 
 * It is a superclass, inherited by multiple other classes(truck, train, etc).
 * @author nermin vucinic
 * @version 1.0
 * @since 28.07.2015.
 */
@MappedSuperclass
@Deprecated
public class Vehicle extends Model {
	
	public static String ACTIVE = "Active";
	public static String DEACTIVE = "Deactive";
	public static String BROKEN = "Broken";
	public static String REPAIRING = "Repairing";
	
	@Id
	public int id;
	
	@Required
	public String licenseNo;
	
	public double longitude;
	
	public double latitude;
	
	public double mileage;

	
	/**
	 * constructor method
	 * @param licenseNo
	 * @param make
	 * @param model
	 * @param year
	 */
	public Vehicle(String licenseNo, double latitude,  double longitude ){
		this.licenseNo=licenseNo;
		this.latitude=latitude;
		this.longitude=longitude;
		
	}
	
	public static Finder<Long, Vehicle> find = new Finder<Long, Vehicle>(Long.class,
			Vehicle.class);
	
	
	/**
	 * empty constructor method
	 */
	public Vehicle() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Method which finds vehicle in DB by licenseNo
	 * @param licenseNo of vehicle
	 * @return vehicle object
	 */
	public static Vehicle findByLicenseNo(String licenseNo) {
		if(find.where().eq("licenseNo", licenseNo).findUnique() == null)
			return new Vehicle(null,0,0);
		return find.where().eq("licenseNo", licenseNo).findUnique();
	}

	/**
	 * method for saving Vehicle  object to database
	 * @param make
	 * @param model
	 * @param year
	 */
	public static Vehicle saveToDB(String licenseNo, double latitude,double longitude){
		Vehicle newVehicle=new Vehicle(licenseNo, latitude, longitude);
		return newVehicle;
	}
}
