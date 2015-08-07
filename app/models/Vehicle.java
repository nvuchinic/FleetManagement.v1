package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import play.Logger;
import play.data.validation.Constraints.Required;








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

@Entity
@Table(name = "vehicle")
public class Vehicle extends Model {
	
	public static String ACTIVE = "Active";
	public static String DEACTIVE = "Deactive";
	public static String BROKEN = "Broken";
	public static String REPAIRING = "Repairing";
	
	@Id
	public long id;
	
	
	public String vid;
	
	@ManyToOne
	public Owner owner;
		
	@OneToOne
	public Data data;
	
	@ManyToOne
	public Fleet fleet;
	
	@ManyToOne
	public Type typev;

	
	/**
	 * constructor method
	 * @param licenseNo
	 * @param make
	 * @param model
	 * @param year
	 */
	public Vehicle(String vid, Owner owner, Type typev, Fleet fleet){
		this.vid = vid;
		this.owner = owner;
		this.typev = typev;
		this.fleet = fleet;
		
		
	}
	
	/**
	 * Method for creating a new Vehicle object
	 * @param vid
	 * @param description
	 * @param owner
	 * @param type
	 * @param data
	 * @param fleet
	 * @return id of new Vehicle object
	 */
	public static long createVehicle(String vid, Owner owner, Type typev, Fleet fleet) {
		Vehicle v = new Vehicle(vid, owner, typev, fleet);
		v.save();
		return v.id;		
	}
	
	/**
	 * Finder for Vehicle object
	 */
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

	/**
	 * Method for deleting Vehicle object
	 * @param id of vehicle object
	 */
	public static void deleteVehicle(long id) {
		Vehicle v = find.byId(id);
		v.delete();
	}

	/**
	 * Method which finds Vehicle object in DB by type
	 * @param type of Vehicle
	 * @return Vehicle object
	 */
	public static Vehicle findByType(Type type) {
		return find.where().eq("typev", type).findUnique();
	}
	
	/**
	 * Method which finds List of Vehicle objects
	 * @return list of Vehicle objects
	 */
	public static List<Vehicle> listOfVehicles() {
		List<Vehicle> allVehicles =  new ArrayList<Vehicle>();
		allVehicles = find.all();
		return allVehicles;
	}
	
	/**
	 * Method which finds Vehicle object by Owner of Vehicle
	 * @param owner
	 * @return Vehicle object
	 */
	public static Vehicle findByOwner(Owner owner) {
		return find.where().eq("owner", owner).findUnique();
	}
	
	/**
	 * Method which finds list of Vehicles in certain Fleet
	 * @param fleet
	 * @return list of Vehicles
	 */
	public static List<Vehicle> findByFleet(Fleet fleet) {
		return find.where().eq("fleet", fleet).findList();
	}
	
	/**
	 * Method which finds vehicle in DB by id
	 * @param id of vehicle
	 * @return vehicle object
	 */
	public static Vehicle findById(long id) {
		return find.where().eq("id", id).findUnique();
	}
	
	// Static constants we're going to use for sorts.
		public static final int SORT_ASCENDING = 1;
		public static final int SORT_DESCENDING = -1;

		/**
		 * Method sorting vehicles by name.
		 * @param method  which you choose to sort, 1 for ascending, -1 for descending.
		 * @return
		 */
		public static List<Vehicle> sortByName(int method) {
			List<Vehicle> all = find.all();

			// Handling exceptions.
			if (all == null) {
				return new ArrayList<Vehicle>();
			}
			/*
			 * Implementing comparator. Comparing category names and return its
			 * string compare value.
			 */
			Comparator<Vehicle> comparator = new Comparator<Vehicle>() {
				@Override
				public int compare(Vehicle v1, Vehicle v2) {
					return v1.typev.name.compareTo(v2.typev.name);
				}
			};

			if (method == SORT_ASCENDING) {
				all.sort(comparator);
			} else if (method == SORT_DESCENDING) {
				all.sort(comparator.reversed());
			} else {
				Logger.error("Wrong method type for sorting");
				return null;
			}
			return all;
		}

}
