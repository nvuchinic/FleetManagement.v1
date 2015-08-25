package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class VehicleRegistration extends Model{
	@Id
	public long id;
	
	@Required
	public String regNo;
	
	@OneToOne
	public Vehicle vehicle;
	
	//public Date regDate;
	
	public Date expirationDate;
	
	public VehicleRegistration(String regNo, Vehicle vehicle, Date expirationDate){
		this.regNo=regNo;
		this.vehicle=vehicle;
		//this.regDate=regDate;
		this.expirationDate=expirationDate;
	}
	
	public static VehicleRegistration saveToDB(String regNo, Vehicle vehicle,Date expirationDate){
		VehicleRegistration vr=new VehicleRegistration(regNo, vehicle,expirationDate);
		vr.save();
		return vr;
	}
	
	/**
	 * Finder for VehicleRegistration object
	 */
	public static Finder<Long, VehicleRegistration> find = new Finder<Long, VehicleRegistration>(VehicleRegistration.class);
	
	/**
	 * Method which finds VehicleRegistration object in DB by numberTO
	 * @param vid of VehicleRegistration object
	 * @return VehicleRegistration object 
	 */
	public static VehicleRegistration findById(long id) {
		return find.byId(id);
	}
	
	/**
	 * Method for deleting VehicleRegistration object
	 * @param id of VehicleRegistration object
	 */
	public static void deleteVehicleRegistration(long id) {
		VehicleRegistration vr= find.byId(id);
		vr.delete();
	}

	public static List<VehicleRegistration> listOfVRegistrations() {
		List<VehicleRegistration> allVehicleRegistrations =  new ArrayList<VehicleRegistration>();
		allVehicleRegistrations = find.all();
		return allVehicleRegistrations;
	
	}
}
