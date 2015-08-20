package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Service extends Model {

	@Id
	public long id;
	
	@Required
	public String stype;
	
	@Required
	public String description;
	
	@ManyToOne
	Maintenance maintenance;
	
	
	public Service(String stype, String description){
		this.stype=stype;
		this.description=description;	
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
	public static Finder<Long, Service> find = new Finder<>(Service.class);
	
	
	public static Service findByType(String stype) {
		return find.where().eq("stype", stype).findUnique();
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
	
	

}
