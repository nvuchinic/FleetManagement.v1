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
