package models;

import java.util.List;

import play.data.validation.Constraints.Required;
//import play.db.ebean.Model;
import com.avaje.ebean.Model;
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
public class Train extends Vehicle {
	public int numOfWagons;
	
	/**
	 * constructor method
	 * @param licenseNo
	 * @param latitude
	 * @param longitude
	 * @param numOfWagons
	 */
	public Train(String licenseNo, double latitude, double longitude,
			int numOfWagons) {
		super(licenseNo, latitude, longitude);
		this.numOfWagons = numOfWagons;
	
	}

	/**
	 * Stores newly created Train object to database 
	 * @param licenseNo
	 * @param latitude
	 * @param longitude
	 * @param numOfWagons
	 * @return newly created Train object
	 */
	public static Train saveToDB(String licenseNo, double latitude,
			double longitude, int numOfWagons) {
		Train t = new Train(licenseNo, latitude, longitude, numOfWagons);
		t.save();
		return t;
	}

	public static Finder<Long, Train> find = new Finder<Long, Train>(
			Long.class, Train.class);
/**
 * Finds and returns all the Train objects  in database
 * @return all the Train objects in database
 */
	public static List<Train> allTrains() {
		return find.all();
	}


public int getNumOfWagons() {
	return numOfWagons;
}

public void setNumOfWagons(int numOfWagons) {
	this.numOfWagons = numOfWagons;
}

}
