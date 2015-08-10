package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;
/*
@Entity
public class Service extends Model {

	@Id
	public long id;
	
	@Required
	public String type;
	
	@Required
	public String description;
	
	public Service(String type, String description){
		this.type=type;
		this.description=description;	
	}
	
	public static Service saveToDB(String type, String description) {
		Service srv = new Service(type, description);
		srv.save();
		return srv;		
	}
	
	*//**
	 * Finder for Service object
	 *//*
	public static Finder<Long, Service> find = new Finder<Long, Service>(Long.class,
			Service.class);
	
	
	public static Service findByType(String type) {
		return find.where().eq("type", type).findUnique();
	}
	
	public static Service findById(long id) {
		return find.where().eq("id", id).findUnique();
	}
	
	public static List<Service> listOfServices() {
		List<Service> allServices =  new ArrayList<Service>();
		allServices = find.all();
		return allServices;
	}
	
	public static void deleteService(long id) {
		Service srv = find.byId(id);
		srv.delete();
	}
	
	

}*/
