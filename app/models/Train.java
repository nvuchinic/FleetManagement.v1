package models;

import java.util.List;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

//import com.avaje.ebean.Model;
import javax.persistence.*;

import com.avaje.ebean.Model.Finder;

import play.data.validation.Constraints.Required;

@Entity
public class Train extends Vehicle {
	public int numOfWagons;
	
	public Train(String licenseNo, long latitude, long longitude,
			int numOfWagons) {
		super(licenseNo, latitude, longitude);
		this.numOfWagons = numOfWagons;
	
	}

	public static Train saveToDB(String licenseNo, long latitude,
			long longitude, int numOfWagons) {
		Train t = new Train(licenseNo, latitude, longitude, numOfWagons);
		t.save();
		return t;
	}

	public static Finder<Long, Train> find = new Finder<Long, Train>(
			Long.class, Train.class);

	public static List<Train> allTrains() {
		return find.all();
	}

}
