package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class VehicleServiceNotificationSettingsMileage extends Model{

	@Id
	public long id;
	
	//vehicle ID
	public long vid;
	
	//serviceNotificationSettings time mileage
	public int mileage;
	
	@ManyToOne
	public  ServiceNotificationSettings sns;
	
	
	//default constructor
	public VehicleServiceNotificationSettingsMileage(){
		
	}
	
	
public VehicleServiceNotificationSettingsMileage(long vid, int mileage){
		this.vid=vid;
		this.mileage=mileage;
	}
	

public static VehicleServiceNotificationSettingsMileage saveToDB(){
	VehicleServiceNotificationSettingsMileage snsMileage=new VehicleServiceNotificationSettingsMileage();
	snsMileage.save();
	return snsMileage;
}


public static VehicleServiceNotificationSettingsMileage saveToDB(long vid, int mileage){
	VehicleServiceNotificationSettingsMileage snsMileage=new VehicleServiceNotificationSettingsMileage(vid, mileage);
	snsMileage.save();
	return snsMileage;
}


/**
 * Finder for ServiceNotificationSettingsmileage object
 */
public static Finder<Long, VehicleServiceNotificationSettingsMileage> find = new Finder<Long, VehicleServiceNotificationSettingsMileage>(
		VehicleServiceNotificationSettingsMileage.class);


public static VehicleServiceNotificationSettingsMileage findById(long id) {
	return VehicleServiceNotificationSettingsMileage.find.byId(id);	
}


public static void deleteSnsMileage(long id) {
	VehicleServiceNotificationSettingsMileage snsMileage = find.byId(id);
	snsMileage.delete();
}


/**
 * Returns all ServiceNotificationSettingsMileage objects from database as list
  @return List of all ServiceNotificationSettingsMileage objects
 */
public static List<VehicleServiceNotificationSettingsMileage> getAll() {
	List<VehicleServiceNotificationSettingsMileage> allSnsMileages = new ArrayList<VehicleServiceNotificationSettingsMileage>();
	allSnsMileages=find.all();
			return allSnsMileages;
}
}
