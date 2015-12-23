package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class VehicleInspection extends Model {
	@Id
	public long id;

	public Date inspectDate;

	public String vehicleDocumentation;

	public String safety;

	public String body;

	public String tiresWheels;

	public String steeringSuspension;

	public String brakes;

	public String lightningElSystem;

	public String glass;

	public String exhaustSystem;

	public String emission;

	public String obd;

	public String fuelSystem;

	public String addNotes;

	@ManyToOne
	public Vehicle vehicle;
	
	public Date expiryDate;
	
	@ManyToOne
	public RenewalNotification notification;
	
	public boolean checked;

	public VehicleInspection(Date inspectDate, String vehicleDocument,
			String safety, String body, String tiresWheels,
			String steeringSuspension, String brakes, String lightningElSystem,
			String glass, String exhaustSystem, String emission, String obd,
			String fuelSystem, String addNotes, Vehicle vehicle) {
		this.inspectDate = inspectDate;
		this.vehicleDocumentation = vehicleDocument;
		this.safety = safety;
		this.body = body;
		this.tiresWheels = tiresWheels;
		this.steeringSuspension = steeringSuspension;
		this.brakes = brakes;
		this.lightningElSystem = lightningElSystem;
		this.glass = glass;
		this.exhaustSystem = exhaustSystem;
		this.emission = emission;
		this.obd = obd;
		this.fuelSystem = fuelSystem;
		this.addNotes = addNotes;
		this.vehicle = vehicle;
		this.checked=false;
	}

	public static VehicleInspection saveToDB(Date inspectDate,
			String vehicleDocument, String safety, String body,
			String tiresWheels, String steeringSuspension, String brakes,
			String lightningElSystem, String glass, String exhaustSystem,
			String emission, String obd, String fuelSystem, String addNotes,
			Vehicle vehicle) {
		VehicleInspection vi = new VehicleInspection(inspectDate,
				vehicleDocument, safety, body, tiresWheels, steeringSuspension,
				brakes, lightningElSystem, glass, exhaustSystem, emission, obd,
				fuelSystem, addNotes, vehicle);
		vi.save();
		return vi;
	}

	/**
	 * Finder for VehicleInspection object
	 */
	public static Finder<Long, VehicleInspection> find = new Finder<Long, VehicleInspection>(
			VehicleInspection.class);

	//
	/**
	 * Method which finds VehicleInspection object in DB
	 * 
	 * @param id
	 *            -ID of VehicleInspection object
	 * @return VehicleInspection object
	 */
	public static VehicleInspection findById(long id) {
		return find.byId(id);
	}

	/**
	 * Method for deleting VehicleInspection object
	 * 
	 * @param id
	 *            of VehicleInspection object
	 */
	public static void deleteVehicleInspection(long id) {
		VehicleInspection vi = find.byId(id);
		vi.delete();
	}

	public static List<VehicleInspection> listOfInspections() {
		List<VehicleInspection> allVehicleInspections = new ArrayList<VehicleInspection>();
		allVehicleInspections = find.all();
		return allVehicleInspections;

	}
}
