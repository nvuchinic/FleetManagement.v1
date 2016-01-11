package models;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;


@Entity
public class ServiceNotification extends Model{

	@Id
	public Long id;
	
	@ManyToOne
	public Service serviceForSN;
	
	@ManyToOne
	public Vehicle vehicle;
	
	public int milesLeftToService;

	public Date nextServiceDate;
	
	
	public ServiceNotification(Vehicle vehicle, Service service, int milesToService, Date nextServiceDate){
	this.serviceForSN=service;
	this.vehicle=vehicle;
	this.milesLeftToService=milesToService;
	this.nextServiceDate=nextServiceDate;
	}
	
	
	public static ServiceNotification saveToDB(Vehicle vehicle, Service service, int milesToService, Date nextServiceDate){
		ServiceNotification sn=new ServiceNotification(vehicle, service, milesToService, nextServiceDate);
		sn.save();
		return sn;
	}
	
	
	/**
	 * Finder for ServiceNotification object
	 */
	public static Finder<Long, ServiceNotification> find = new Finder<Long, ServiceNotification>(
			ServiceNotification.class);
	
	
	public static ServiceNotification findById(long id) {
		return ServiceNotification.find.byId(id);	
	}
	
	
	public static void deleteServiceNotification(long id) {
		ServiceNotification sn = find.byId(id);
		sn.delete();
	}
	
	
	/**
	 * Returns all ServiceNotification objects from database as list
	  @return List of all ServiceNotification objects
	 */
	public static List<ServiceNotification> getAll() {
		List<ServiceNotification> allServiceNotifications = new ArrayList<ServiceNotification>();
		allServiceNotifications=find.all();
				return allServiceNotifications;
	}
	
	
	
}
