package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;
import javax.persistence.*;

import com.avaje.ebean.Model;

/**
 * 
 * This class represents vehicle model.
 * 
 * @author
 * 
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

	@Required
	public String name;

	@ManyToOne
	public Owner owner;

	@ManyToOne
	public Fleet fleet;

	@ManyToOne
	public Type typev;

	@OneToOne
	public TechnicalInfo technicalInfo;
	
	@OneToOne
	public TravelOrder travelOrder;

	public boolean engagedd;

	public String status;

	public boolean isRegistered;

	public boolean isInsured;

	public boolean isAsigned;

	public boolean isLinked;

	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
	public List<Maintenance> maintenances;

	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
	public List<VehicleInspection> inspection;
	
	@OneToOne
	public VehicleRegistration vRegistration;
	
	@OneToOne
	public VehicleWarranty vehicleWarranty;
	
	@ManyToOne
	public TruckComposition truckComposition;

	@ManyToOne
	public TrainComposition trainComposition;

	public boolean isLinkable;

	public int position;

	@ManyToMany(mappedBy = "vehicles", cascade = CascadeType.ALL)
	public List<Description> description;

	public Vehicle(String vid, String name, Owner owner, Type typev,
			List<Description> description, TechnicalInfo technicalInfo) {
		this.vid = vid;
		this.name = name + " " + vid;
		this.owner = owner;
		this.typev = typev;
		//this.fleet = fleet;
		this.status = ACTIVE;
		this.engagedd = false;
		this.isRegistered = false;
		this.isInsured = false;
		this.isAsigned = false;
		this.isLinked = false;
		this.isLinkable = false;
		this.maintenances = new ArrayList<Maintenance>();
		this.description = description;
		this.technicalInfo = technicalInfo;
		this.vRegistration = vRegistration;
		this.vehicleWarranty = vehicleWarranty;

	}

	public Vehicle(String vid, String name, Owner owner, Type typev) {
		this.vid = vid;
		this.name = name + " " + vid;
		this.owner = owner;
		this.typev = typev;
		// this.fleet = fleet;
		this.status = ACTIVE;
		this.engagedd = false;
		this.isRegistered = false;
		this.isInsured = false;
		this.isAsigned = false;
		this.isLinked = false;
		this.isLinkable = false;
		this.maintenances = new ArrayList<Maintenance>();
		this.description = new ArrayList<Description>();
		this.vRegistration = vRegistration;
		this.vehicleWarranty = vehicleWarranty;

	}
	
	public Vehicle(String vid, String name, Owner owner, Type typev, List<Description> description) {
		this.vid = vid;
		this.name = name + " " + vid;
		this.owner = owner;
		this.typev = typev;
		// this.fleet = fleet;
		this.status = ACTIVE;
		this.engagedd = false;
		this.isRegistered = false;
		this.isInsured = false;
		this.isAsigned = false;
		this.isLinked = false;
		this.isLinkable = false;
		this.maintenances = new ArrayList<Maintenance>();
		this.description = description;
		this.vRegistration = vRegistration;
		this.vehicleWarranty = vehicleWarranty;

	}

	/**
	 * empty constructor method
	 */
	public Vehicle() {
		this.name = "defaultName";
		this.owner = new Owner("defaultOwner", "defaultEmail");
		this.typev = new Type();
		this.vid = "000000000";
		this.isAsigned = false;

	}

	public Vehicle(Type nameType) {
		this.typev = nameType;
	}

	/**
	 * Finder for Vehicle object
	 */
	public static Finder<Long, Vehicle> find = new Finder<Long, Vehicle>(
			Vehicle.class);

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

	public static long createVehicle(String vid, String name, Owner owner,
			Type typev, List<Description> description, TechnicalInfo tInfo, VehicleRegistration vRegistration) {
		Vehicle v = new Vehicle(vid, name, owner, typev, description, tInfo);
		v.save();
		return v.id;
	}

	public static long createVehicle(String vid, String name, Owner owner,
			Type typev) {
		Vehicle v = new Vehicle(vid, name, owner, typev);
		v.save();
		return v.id;
	}
	public static long createVehicle(String vid, String name, Owner owner,
			Type typev, List<Description> description) {
		Vehicle v = new Vehicle(vid, name, owner, typev, description);
		v.save();
		return v.id;
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
	public static List<Vehicle> findByType(Type typev) {
		return find.where().eq("typev", typev).findList();
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
	 * 
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
		for (int i = 0; i < allVehicles.size(); i++) {
			if (allVehicles.get(i).isAsigned == false) {
				nonAsigned.add(allVehicles.get(i));
			}
		}
		return nonAsigned;
	}

	public static List<Vehicle> findListByType(Type typev) {
		return find.where().eq("typev", typev).findList();
	}

	public static List<Vehicle> availableVehicles() {
		List<Vehicle> allVehicles = Vehicle.find.all();
		List<Vehicle> availableVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.engagedd == false) {
				availableVehicles.add(v);
			}
		}
		return availableVehicles;
	}

	public int getPosition(long id) {
		int pos = 0;
		Vehicle v = Vehicle.findById(id);
		if (v.typev.name.equalsIgnoreCase("Trailer")
				|| v.typev.name.equalsIgnoreCase("Truck")) {
			pos = v.truckComposition.truckVehicles.indexOf(v) + 1;
		}
		if (v.typev.name.equalsIgnoreCase("Train")
				|| v.typev.name.equalsIgnoreCase("Wagon")) {
			pos = v.trainComposition.trainVehicles.indexOf(v) + 1;
		}
		return pos;
	}
	
	public static List<Vehicle> nonAsignedVehicles(Type typev) {
		List<Vehicle> allVehicles = Vehicle.findListByType(typev);
		List<Vehicle> availableVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.isAsigned == false) {
				availableVehicles.add(v);
			}
		}
		return availableVehicles;
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
