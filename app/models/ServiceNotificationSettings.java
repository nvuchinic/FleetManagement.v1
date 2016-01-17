package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
public class ServiceNotificationSettings extends Model {

	@Id
	public long id;
	
	@ManyToOne
	public Service service;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "VehiclesServiceNotifications", joinColumns = { @JoinColumn(name = "serviceNotificationSettingsId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "vehicleId", referencedColumnName = "id") })
	public List<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	public int meterIntervalSize;
	
	public int timeIntervalSize;
	
	public String timeIntervalUnit;
	
	public String meterIntervalUnit;
	
	public int meterThresholdSize;
	
	public String meterThresholdUnit;
	
	public int timeThresholdSize;
	
	public String timeThresholdUnit;
	
	public Date snsDate;
	
	@OneToMany(mappedBy = "sns", cascade = CascadeType.ALL)
	public List<VehicleServiceNotificationSettingsMileage> snsMileages;
		
	public ServiceNotificationSettings(int meterIntervalSize, String meterIntervalUnit, int timeIntervalSize, String timeIntervalUnit, int meterThresholdSize, String meterThresholdUnit, int timeThresholdSize, String timeThresholdUnit) {
	this.vehicles=new ArrayList<Vehicle>();
	this.meterIntervalSize=meterIntervalSize;
	this.meterIntervalUnit=meterIntervalUnit;
	this.timeIntervalSize=timeIntervalSize;
	this.timeIntervalUnit=timeIntervalUnit;
	this.meterThresholdSize=meterThresholdSize;
	this.meterThresholdUnit=meterThresholdUnit;
	this.timeThresholdSize=timeThresholdSize;
	this.timeThresholdUnit=timeThresholdUnit;
	this.snsMileages=new ArrayList<VehicleServiceNotificationSettingsMileage>();
	this.snsDate= new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}
	
	
	public static ServiceNotificationSettings saveToDB(int meterIntervalSize,String meterIntervalUnit, int timeIntervalSize, String timeIntervalUnit, int meterThresholdSize, String meterThresholdUnit, int timeThresholdSize, String timeThresholdUnit){
		ServiceNotificationSettings sns=new ServiceNotificationSettings( meterIntervalSize, meterIntervalUnit,  timeIntervalSize,  timeIntervalUnit,  meterThresholdSize, meterThresholdUnit, timeThresholdSize,  timeThresholdUnit);
		sns.save();
		return sns;
	}
	
	
	/**
	 * Finder for ServiceNotificationSettings object
	 */
	public static Finder<Long, ServiceNotificationSettings> find = new Finder<Long, ServiceNotificationSettings>(
			ServiceNotificationSettings.class);
	
	
	public static ServiceNotificationSettings findById(long id) {
		return ServiceNotificationSettings.find.byId(id);	
	}
	
	
	public static void deleteServiceNotificationSettings(long id) {
		ServiceNotificationSettings sns = find.byId(id);
		sns.delete();
	}
	
	
	/**
	 * Returns all ServiceNotificationSettings objects from database as list
	  @return List of all ServiceNotificationSettings objects
	 */
	public static List<ServiceNotificationSettings> getAll() {
		List<ServiceNotificationSettings> allServiceNotifications = new ArrayList<ServiceNotificationSettings>();
		allServiceNotifications=find.all();
				return allServiceNotifications;
	}
	
	public static List<Vehicle> vehiclesForEditing(long id) {
		ServiceNotificationSettings sns=ServiceNotificationSettings.find.byId(id);
		List<Vehicle> vehiclesForEditing = new ArrayList<Vehicle>();
		vehiclesForEditing=sns.vehicles;
		//List<Vehicle> noServiceNotificationVehicles=Vehicle.getNoNotificationServices();
		for(Vehicle v: Vehicle.getNoNotificationVehicles(sns.id)){
			vehiclesForEditing.add(v);
		}
		return vehiclesForEditing;

	}
	
}
