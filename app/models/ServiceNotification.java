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

	public int nextServiceMileage;
	
	public Date nextServiceDate;
	
	
	public ServiceNotification(Vehicle vehicle, Service service, int milesToService, Date nextServiceDate){
	this.serviceForSN=service;
	this.vehicle=vehicle;
	this.milesLeftToService=milesToService;
	this.nextServiceDate=nextServiceDate;
	}
	
	
	public ServiceNotification(){
		
		}
		
	
	public static ServiceNotification saveToDB(Vehicle vehicle, Service service, int milesToService, Date nextServiceDate){
		ServiceNotification sn=new ServiceNotification(vehicle, service, milesToService, nextServiceDate);
		sn.save();
		return sn;
	}
	
	
	public static ServiceNotification saveToDB(){
	ServiceNotification sn=new ServiceNotification();
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
	
	
	public static int milesLeftToService(long id){
		int result;
		ServiceNotification sn=ServiceNotification.findById(id);
		result=sn.nextServiceMileage-sn.vehicle.odometer;
		return result;
	}
	
	
	public static int milesLeftToServicePositive(long id){
		int result;
		ServiceNotification sn=ServiceNotification.findById(id);
		result=sn.nextServiceMileage-sn.vehicle.odometer;
		if(result<0){
			result=result*(-1);
		}
		return result;
	}
	
	
	public static boolean alreadyExists(Vehicle v, Service srv){
		boolean exists=false;
		if(ServiceNotification.find.all().size()==0){
			return exists;
		}
		List<ServiceNotification> allSNotifications=ServiceNotification.find.all();
		for(ServiceNotification sn: allSNotifications){
			if(sn.vehicle.id==v.id && srv.stype.equalsIgnoreCase(sn.serviceForSN.stype)){
				exists=true;
			}
		}
		return exists;
	}
	
	
	public static int noOfServiceNotifications(){
		return ServiceNotification.getAll().size();
	}
}
