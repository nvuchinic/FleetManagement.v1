package models;

import java.util.List;

import play.data.validation.Constraints.Required;
//import play.db.ebean.Model;
import com.avaje.ebean.Model;
import javax.persistence.*;
import javax.persistence.*;
import com.avaje.ebean.Model.Finder;


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

	public Truck(String licenseNo, long latitude, long longitude, String make,
			String model, String year, int numOfContainers, String status) {
		super(licenseNo, latitude, longitude);
		this.make = make;
		this.model = model;
		this.year = year;
		this.numOfContainers = numOfContainers;
		this.status = status;
	}

	public static Truck saveToDB(String licenseNo, long latitude,
			long longitude, String make, String model, String year,
			int numOfContainers, String status) {
		Truck t = new Truck(licenseNo, latitude, longitude, make, model, year,
				numOfContainers, status);
		t.save();
		return t;
	}

	public static Finder<Long, Truck> find = new Finder<Long, Truck>(
			Long.class, Truck.class);

	public static List<Truck> allTrucks() {
		return find.all();
	}
	
	public static Truck findByLicenceNo(String licenceNo) {
		return find.where().eq("licenseNo", licenceNo).findUnique();
	}
	public static Truck findById(long id) {
		return find.where().eq("id", id).findUnique();
	}
}