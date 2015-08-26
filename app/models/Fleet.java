package models;

import java.util.ArrayList;
import java.util.List;
import play.data.format.Formats;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.db.ebean.Model;
import com.avaje.ebean.Model.Finder;

/**
 * Fleet model
 * @author Emir Imamović
 *
 */
@Entity
@Table(name = "fleet")
public class Fleet extends Model {

	@Id
	public long id;
	
	@NotNull
	public String name;
	
	public Date arrival;
	
	public Date departure;
	
	public String pickupPlace;
	
	public String returnPlace;
	
	public long numOfVehicles;
	
	@OneToMany(mappedBy="fleet",cascade=CascadeType.ALL)
	public List<Vehicle> vehicles;

	public Date createdd;
	
	/**
	 * @param name
	 * @param numOfVehicles
	 * @param vehicles
	 */
	public Fleet(String name, long numOfVehicles, Date departure, Date arrival, String pickupPlace, String returnPlace) {
		super();
		this.name = name;
		this.departure = departure;
		this.arrival = arrival;
		this.pickupPlace = pickupPlace;
		this.returnPlace = returnPlace;
		this.numOfVehicles = numOfVehicles;
		this.vehicles = new ArrayList<Vehicle>();
		createdd = new java.sql.Date(new java.util.Date().getTime());
	}
	
	/**
	 * Empty(Default) constructor
	 */
	public Fleet() {
		
	}

	/**
	 * Finder for Fleet object
	 */
	public static Finder<Long, Fleet> find = new Finder<Long, Fleet>(Fleet.class);
	
	/**
	 * Method which create a new Fleet object into the DB
	 * @param name
	 * @param numOfVehicles
	 * @param vehicles
	 * @return id of new Fleet object
	 */
	public static long createFleet(String name, long numOfVehicles, Date departure, Date arrival, String pickupPlace, String returnPlace) {
		Fleet f = new Fleet(name, numOfVehicles, departure, arrival, pickupPlace, returnPlace);
		f.save();
		return f.id;		
	}
	
	/**
	 * Method which finds Fleet object in DB by name
	 * @param name of Fleet
	 * @return Fleet object
	 */
	public static Fleet findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method which finds Fleet object in DB by id
	 * @param id of Fleet
	 * @return Fleet object
	 */
	public static Fleet findById(long id) {
		return find.byId(id);
	}
	
	/**
	 * Method which delete finds fleet in DB by id and delete it
	 * @param id of fleet object
	 */
	public static void deleteFleet(long id) {
		Fleet f = find.byId(id);
		f.delete();
	}
	
	/**
	 * Method which finds List of Fleet objects
	 * @return list of Fleet objects
	 */
	public static List<Fleet> listOfFleets() {
		List<Fleet> allFleets =  new ArrayList<Fleet>();
		allFleets = find.all();
		return allFleets;
	}
	
	public static List<Vehicle> listOfVehicles(long id) {
		Fleet f = find.byId(id);
		List<Vehicle> vs = new ArrayList<Vehicle>();
		vs = f.vehicles;
		return vs;
	}
	
	public static int numOfVehicles(long id) {
		Fleet f = find.byId(id);
		List<Vehicle> vs = new ArrayList<Vehicle>();
		vs = f.vehicles;
		int size = vs.size();
		return size;
	}
	/**
	 * Method which finds list of Types in DB
	 * @return list of Type objects
	 */
	public static List<Type> listOfTypes() {
		List<Type> ts = new ArrayList<Type>();
		ts = Type.find.all();
		return ts;
	}
}
