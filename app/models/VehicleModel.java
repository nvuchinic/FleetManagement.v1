package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "vehicleModel")
public class VehicleModel extends Model {

	@Id
	public long id;
	
	public String name;
	
	@ManyToOne
	public VehicleBrand vehicleBrand;

	@OneToMany(mappedBy = "vehicleModel", cascade = CascadeType.ALL)
	public List<Vehicle> vehicles;
	
	/**Constructor
	 * @param name
	 * @param carBrand
	 */
	public VehicleModel(String name, VehicleBrand brand) {
		super();
		this.name = name;
		this.vehicleBrand = brand;
	}
	
	/**
	 * Finder for VehicleModel object
	 */
	public static Finder<Long, VehicleModel> find = new Finder<Long, VehicleModel>(
			VehicleModel.class);
	
	/**
	 * Method which creates new VehicleModel object and saves it into database
	 * @param name of vehicle model
	 * @return id of new object
	 */
	public static long createVehicleModel(String name, VehicleBrand brand) {
		VehicleModel model = new VehicleModel(name, brand);
		model.save();
		return model.id;
	}

	public static VehicleModel saveToDB(String name, VehicleBrand brand) {
		VehicleModel model = new VehicleModel(name, brand);
		model.save();
		return model;
	}
	
	/**
	 * Method which finds VehicleModel object in DB by name
	 * @param name of VehicleModel
	 * @return VehicleModel object
	 */
	public static VehicleModel findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method which finds VehicleModel object in DB by id
	 * @param id of VehicleModel
	 * @return VehicleModel object
	 */
	public static VehicleModel findById(long id) {
		return find.byId(id);
	}

	/**
	 * Method which finds VehicleModel in DB by id and delete it
	 * @param id of VehicleModel object
	 */
	public static void deleteVehicleModel(long id) {
		VehicleModel model = find.byId(id);
		model.delete();
	}

	/**
	 * Method which finds List of VehicleModel objects
	 * @return list of VehicleModel objects
	 */
	public static List<VehicleModel> listOfVehicleModels() {
		List<VehicleModel> allVehicleModels = new ArrayList<VehicleModel>();
		allVehicleModels = find.all();
		return allVehicleModels;
	}
	
	public static List<VehicleModel> findByBrand(VehicleBrand brand) {
		return find.where().eq("vehicleBrand", brand).findList();
	}
}
