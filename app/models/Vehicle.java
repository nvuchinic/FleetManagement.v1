package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

/**

 * This class represents vehicle model. 
 * 
 * @author nermin vucinic

 * @version 1.0
 * @param <T>
 * @since 28.07.2015.
 */

@Entity
public class Vehicle extends Model {

	public static String ACTIVE = "Active";
	public static String DEACTIVE = "Deactive";
	public static String BROKEN = "Broken";
	public static String REPAIRING = "Repairing";

	@Id
	public long id;

	public String vid;

	
	@Required
	public String name;
	
	@ManyToOne
	public Owner owner;

	@ManyToOne
	public Fleet fleet;

	@ManyToOne
	public Type typev;

	
	@OneToOne
	public TravelOrder travelOrder;
	
	@OneToOne
	public Vehicle prev;
	
	@OneToOne
	public Vehicle next;

	public boolean engagedd;
	
	public String status;
	
	public boolean isRegistered;
	
	public boolean isInsured;
	
	public boolean isAsigned;
	
	@OneToMany(mappedBy="vehicle",cascade=CascadeType.ALL)
	public List<Maintenance> maintenances;
	
	@OneToOne
	public VehicleRegistration vRegistration;
	
	
	public Vehicle(String vid, String name, Owner owner, Type typev) {
		this.vid = vid;
		this.name=name+" "+vid;
		this.owner = owner;
		this.typev = typev;
		this.fleet = fleet;
		this.status=ACTIVE;
		this.engagedd=false;
		this.isRegistered=false;
		this.isInsured=false;
		this.isAsigned = false;
		this.maintenances=new ArrayList<Maintenance>();

	}
	/**
	 * Finder for Vehicle object
	 */
	public static Finder<Long, Vehicle> find = new Finder<Long, Vehicle>(
			Long.class, Vehicle.class);

	/**
	 * Method for creating a new Vehicle object
	 * 
	 * @param vid
	 * @param description
	 * @param owner
	 * @param type
	 * @param data
	 * @return id of new Vehicle object
	 */

	

	public static long createVehicle(String vid,String name, Owner owner, Type typev) {
		Vehicle v = new Vehicle(vid, name,owner, typev);
		v.save();
		return v.id;
	}


	/**
	 * empty constructor method
	 */
	public Vehicle() {
		this.name="defaultName";
		this.owner=new Owner("defaultOwner", "defaultEmail");
		this.typev=new Type("defaultType", new Description());
		this.vid="000000000";
		this.isAsigned = false;
		
	}

	/**
	 * Method which finds vehicle in DB by vid
	 * 
	 * @param vid
	 *            of vehicle
	 * @return vehicle object
	 */
	public static Vehicle findByVid(String vid) {
		return find.where().eq("vid", vid).findUnique();
	}

	/**
	 * Method for deleting Vehicle object
	 * 
	 * @param id
	 *            of vehicle object
	 */
	public static void deleteVehicle(long id) {
		Vehicle v = find.byId(id);
		v.delete();
	}

	/**
	 * Method which finds Vehicle object in DB by type
	 * 
	 * @param type
	 *            of Vehicle
	 * @return Vehicle object
	 */
	public static Vehicle findByType(Type type) {
		return find.where().eq("typev", type).findUnique();
	}

	/**
	 * Method which finds List of Vehicle objects
	 * 
	 * @return list of Vehicle objects
	 */
	public static List<Vehicle> listOfVehicles() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = find.all();
		return allVehicles;
	}

	/**
	 * Method which finds Vehicle object by Owner of Vehicle
	 * 
	 * @param owner
	 * @return Vehicle objectvehicle
	 */
	public static Vehicle findByOwner(Owner owner) {
		return find.where().eq("owner", owner).findUnique();
	}

	/**
	 * Method which finds Vehicle object by name of the Vehicle
	 * @param name
	 * @return Vehicle object
	 */
	public static Vehicle findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method which finds list of Vehicles in certain Fleet
	 * 
	 * @param fleet
	 * @return list of Vehicles
	 */
	public static List<Vehicle> findByFleet(Fleet fleet) {
		return find.where().eq("fleet", fleet).findList();
	}

	/**
	 * Method which finds vehicle in DB by id
	 * 
	 * @param id
	 *            of vehicle
	 * @return vehicle object
	 */
	public static Vehicle findById(long id) {
		return find.where().eq("id", id).findUnique();

	}
	
	/**
	 * Method which finds List of Vehicle objects
	 * 
	 * @return list of Vehicle objects
	 */
	public static List<Vehicle> nonAsigned() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = find.all();
		List<Vehicle> nonAsigned = new ArrayList<Vehicle>();
		for(int i = 0; i < allVehicles.size(); i++) {
			if(allVehicles.get(i).isAsigned == false){
				nonAsigned.add(allVehicles.get(i));
			}
		}
		return nonAsigned;
	}
	
	public static List<Vehicle> findByTypeList(String typeName) {
		return find.where().eq("typev_name", typeName).findList();
	}
	
	public static List<Vehicle> findByType(String typeName) {
		List<Vehicle> vehicles = find.all();
		List<Vehicle> vs = new ArrayList<Vehicle>();
		for(Vehicle v : vehicles) {
			if(v.isAsigned == false && v.typev.name.equals(typeName))
				vs.add(v);
		}
		return vs;
	}
}

