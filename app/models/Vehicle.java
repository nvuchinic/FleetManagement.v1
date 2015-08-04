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
 * @param <T>
 * @since 28.07.2015.
 */
@MappedSuperclass
@Deprecated
public class Vehicle<T> extends Model {
	
	public static String ACTIVE = "Active";
	public static String DEACTIVE = "Deactive";
	public static String BROKEN = "Broken";
	public static String REPAIRING = "Repairing";
	
	@Id
	public long id;
	
	@Required
	public String vid;
	
	public Owner owner;
	
	public String description;
	
	public Data data;
	
	public Fleet fleet;
	
	public T type;

	
	/**
	 * constructor method
	 * @param licenseNo
	 * @param make
	 * @param model
	 * @param year
	 */
	public Vehicle(String vid, String description, Owner owner, T type, Data data, Fleet fleet){
		this.vid = vid;
		this.description = description;
		this.owner = owner;
		this.type = type;
		this.data = data;
		this.fleet = fleet;
		
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
	 * Method which finds vehicle in DB by vid
	 * @param vid of vehicle
	 * @return vehicle object
	 */
	public static Vehicle findByVid(String vid) {
		return find.where().eq("vid", vid).findUnique();
	}

}
