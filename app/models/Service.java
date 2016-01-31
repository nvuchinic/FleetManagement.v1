package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
@Table(name = "service")
public class Service extends Model {

	@Id
	public long id;

	public String stype;

	public String description;

	@ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
	public List<Maintenance> maintenances = new ArrayList<Maintenance>();

	public boolean isChosen;
	
	public boolean hasNotification;
	
	//reference to ServiceNotificationSettings model
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
	public List<ServiceNotificationSettings> serviceNotifications;

	
	//reference to ServiceNotification model
	@OneToMany(mappedBy = "serviceForSN", cascade = CascadeType.ALL)
	public List<ServiceNotification> sNotifications;

	
	
	public Service(String stype, String description) {
		this.stype = stype;
		this.description = description;
		isChosen = false;
		hasNotification=false;
		this.serviceNotifications=new ArrayList<ServiceNotificationSettings>();
	}

	public Service(String service) {
		this.stype = service;
		isChosen = false;
		hasNotification=false;
	}
	
	public Service() {
		// TODO Auto-generated constructor stub
	}

	public static Service saveToDB(String stype, String description) {
		Service srv = new Service(stype, description);
		srv.save();
		return srv;
	}

	public static Long createService(String stype, String description) {
		Service srv = new Service(stype, description);
		srv.save();
		return srv.id;
	}

	/**
	 * Finder for Service object
	 */
	public static Finder<Long, Service> findS = new Finder<Long, Service>(
			Service.class);

	public static Service findByType(String stype) {
		return findS.where().eq("stype", stype).findUnique();
	}

	public static Service findById(long id) {
		return Service.findS.byId(id);
	}

	public static List<Service> listOfServices() {
		List<Service> allServices = new ArrayList<Service>();
		allServices = findS.all();
		return allServices;
	}

	public static List<Service> getNoNotificationServices() {
		List<Service> allServices = new ArrayList<Service>();
		allServices = findS.all();
		List<Service> noNotificationServices=new ArrayList<Service>();
		for(Service s: allServices){
			if(s.hasNotification==false){
				noNotificationServices.add(s);
			}
		}
		return noNotificationServices;
	}
	
//	public static List<Service> servicesForEditing(long id) {
//		ServiceNotificationSettings sns=ServiceNotificationSettings.find.byId(id);
//		List<Service> servicesForEditing = new ArrayList<Service>();
//		servicesForEditing=sns.services;
//		List<Service> noNotificationServices=Service.getNoNotificationServices();
//		for(Service srv: noNotificationServices){
//			servicesForEditing.add(srv);
//		}
//		return servicesForEditing;
//	}
	
	
	public static void deleteService(long id) {
		Service srv = findS.byId(id);
		srv.delete();
	}

	
	public static Service createService(String newService) {
		Service s =new Service(newService);
		s.save();
		return s;
	}
	
	
	/**
	 * Checks if already exists service with name same as string passed as argument
	 * @param candidate-string 
	 * @return-boolean
	 */
	public static boolean alreadyExists(String candidate){
		List<Service> allServices=listOfServices();
		for(Service s:allServices){
			if(s.stype.equalsIgnoreCase(candidate))
				return true;
		}
		return false;
	}
	
	
	public static boolean existsNotification(Service s){
		boolean exists=false;
		for(ServiceNotificationSettings sns:ServiceNotificationSettings.find.all()){
			if(sns.service.id==s.id){
				exists=true;
				break;
			}
		}
		return exists;
	}
	
	
	public static boolean hasStillNotification(long oldServiceId, long serviceNotifSettId){
		boolean hasStillNotification=false;
		Service oldService=Service.findById(oldServiceId);
		ServiceNotificationSettings sns =ServiceNotificationSettings.findById(serviceNotifSettId);
			if(sns.service.id==oldServiceId){
				hasStillNotification=true;
			}
				if(hasStillNotification==true){
		return true;
		}else{
			return false;
		}
	}

}
