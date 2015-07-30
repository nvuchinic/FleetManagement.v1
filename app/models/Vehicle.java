package models;

import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import javax.persistence.*;

import play.data.validation.Constraints.Required;

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
	
	public long longitude;
	
	public long latitude;

	
	/**
	 * constructor method
	 * @param licenseNo
	 * @param make
	 * @param model
	 * @param year
	 */
	public Vehicle(String licenseNo, long latitude, long longitude ){
		this.licenseNo=licenseNo;
		this.latitude=latitude;
		this.longitude=longitude;
		
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
	public static Vehicle saveToDB(String licenseNo, long latitude,long longitude){
		Vehicle newVehicle=new Vehicle(licenseNo, latitude, longitude);
		return newVehicle;
	}
}
