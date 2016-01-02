package models;

import java.util.ArrayList;
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
	
	@OneToMany(mappedBy = "notificationSettings", cascade = CascadeType.ALL)
	public List<Service> services;
	
	public String intervalType;
	
	public int intervalSize;
	
	public String intervalUnit;
	
	public int thresholdSize;
	
	public String thresholdUnit;
	
		
	public ServiceNotificationSettings(String intervalType, int intervalSize, String intervalUnit, int thresholdSize, String thresholdUnit) {
	this.services=new ArrayList<Service>();
	this.intervalType=intervalType;
	this.intervalSize=intervalSize;
	this.intervalUnit=intervalUnit;
	this.thresholdSize=thresholdSize;
	this.thresholdUnit=thresholdUnit;
	}
	
	
	public static ServiceNotificationSettings saveToDB(String intervalType, int intervalSize, String intervalUnit, int thresholdSize, String thresholdUnit){
		ServiceNotificationSettings sns=new ServiceNotificationSettings(intervalType, intervalSize, intervalUnit, thresholdSize,thresholdUnit);
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
	
}
