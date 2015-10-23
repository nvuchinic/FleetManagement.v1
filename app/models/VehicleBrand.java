package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "vehicleBrand")
public class VehicleBrand extends Model {

	@Id
	public long id;

	public String name;

	@OneToMany(mappedBy = "vehicleBrand", cascade = CascadeType.ALL)
	public List<Vehicle> vehicles;

	@OneToMany(mappedBy = "vehicleBrand", cascade = CascadeType.ALL)
	public List<VehicleModel> vehicleModels;

	@ManyToOne
	public Type typev;
	
	public static VehicleBrand brandd = null;

	
	/**
	 * @param name
	 * @param vehicles
	 * @param carModels
	 */
	public VehicleBrand(String name) {
		super();
		this.name = name;
		this.vehicles = new ArrayList<Vehicle>();
		this.vehicleModels = new ArrayList<VehicleModel>();
		}

	/**
	 * @param name
	 * @param vehicles
	 * @param carModels
	 */
	public VehicleBrand(String name, Type type) {
		super();
		this.name = name;
		this.vehicles = new ArrayList<Vehicle>();
		this.vehicleModels = new ArrayList<VehicleModel>();
		this.typev=type;
		}
	
	/**
	 * Finder for VehicleBrand object
	 */
	public static Finder<Long, VehicleBrand> find = new Finder<Long, VehicleBrand>(
			VehicleBrand.class);

	/**
	 * Method which creates new VehicleBrand object and saves it into database
	 * @param name of car brand
	 * @return id of new object
	 */
	public static long createVehicleBrand(String name) {
		VehicleBrand brand = new VehicleBrand(name);
		brand.save();
		return brand.id;
	}

	public static VehicleBrand saveToDB(String name, Type t) {
		VehicleBrand brand = new VehicleBrand(name, t);
		brand.save();
		return brand;
	}
	
	/**
	 * Method which finds VehicleBrand object in DB by name
	 * 
	 * @param name
	 *            of VehicleBrand
	 * @return VehicleBrand object
	 */
	public static VehicleBrand findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/** Method which finds brands by type
	 * @param typev
	 * @return list of brands
	 */
	public static List<VehicleBrand> findByType(Type typev) {
		return find.where().eq("typev", typev).findList();
	}
	
	/**
	 * Method which finds VehicleBrand object in DB by id
	 * @param id of VehicleBrand
	 * @return VehicleBrand object
	 */
	public static VehicleBrand findById(long id) {
		return find.byId(id);
	}

	/**
	 * Method which finds VehicleBrand in DB by id and delete it
	 * @param id of VehicleBrand object
	 */
	public static void deleteVehicleBrand(long id) {
		VehicleBrand brand = find.byId(id);
		brand.delete();
	}

	/**
	 * Method which finds List of VehicleBrands objects
	 * @return list of VehicleBrands objects
	 */
	public static List<VehicleBrand> listOfVehicleBrands() {
		List<VehicleBrand> allVehicleBrands = new ArrayList<VehicleBrand>();
		allVehicleBrands = find.all();
		return allVehicleBrands;
	}
	
}
