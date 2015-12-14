package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class VehicleRegistration extends Model {
	@Id
	public long id;

	public String regNo;

	public String certificateNo;

	public String trailerLoadingLimit;

	@ManyToOne
	public Owner registrationHolder;

	public String city;

	public Date registrationDate;

	public Date expirationDate;

	public boolean checked;
	
	public Vehicle vehicle;
	
	@ManyToOne
	public RenewalNotification notification;

	public VehicleRegistration(String regNo, String certificateNo,
			Owner registrationHolder, String city, Date registrationDate,
			Date expirationDate, String trailerLoadingLimit, Vehicle vehicle) {
		this.regNo = regNo;
		this.certificateNo = certificateNo;
		this.registrationHolder = registrationHolder;
		this.city = city;
		this.registrationDate = registrationDate;
		this.expirationDate = expirationDate;
		this.trailerLoadingLimit = trailerLoadingLimit;
		this.vehicle = vehicle;
		this.checked=false;
	}

	public VehicleRegistration(String regNo, String certificateNo,
			 String city, Date registrationDate,
			Date expirationDate, String trailerLoadingLimit, Vehicle vehicle) {
		this.regNo = regNo;
		this.certificateNo = certificateNo;
		this.city = city;
		this.registrationDate = registrationDate;
		this.expirationDate = expirationDate;
		this.trailerLoadingLimit = trailerLoadingLimit;
		this.vehicle = vehicle;
		this.checked=false;
	}
	
	public static VehicleRegistration saveToDB(String regNo,
			String certificateNo, String city,
			Date registrationDate, Date expirationDate,
			String trailerLoadingLimit, Vehicle vehicle) {
		VehicleRegistration vr = new VehicleRegistration(regNo, certificateNo,
				 city, registrationDate, expirationDate,
				trailerLoadingLimit, vehicle);
		vr.save();
		return vr;
	}

	/**
	 * Finder for VehicleRegistration object
	 */
	public static Finder<Long, VehicleRegistration> find = new Finder<Long, VehicleRegistration>(
			VehicleRegistration.class);

	/**
	 * Method which finds VehicleRegistration object in DB by numberTO
	 * 
	 * @param vid
	 *            of VehicleRegistration object
	 * @return VehicleRegistration object
	 */
	public static VehicleRegistration findById(long id) {
		return find.byId(id);
	}

	/**
	 * Method for deleting VehicleRegistration object
	 * 
	 * @param id
	 *            of VehicleRegistration object
	 */
	public static void deleteVehicleRegistration(long id) {
		VehicleRegistration vr = find.byId(id);
		vr.delete();
	}

	public static List<VehicleRegistration> listOfVRegistrations() {
		List<VehicleRegistration> allVehicleRegistrations = new ArrayList<VehicleRegistration>();
		allVehicleRegistrations = find.all();
		return allVehicleRegistrations;

	}
}
