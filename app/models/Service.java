package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
@Table(name = "service")
public class Service extends Model {

	@Id
	public long id;

	@Required
	public String stype;

	@Required
	public String description;

	@ManyToOne
	public Maintenance maintenance;

	public boolean isChosen;

	public Service(String stype, String description) {
		this.stype = stype;
		this.description = description;
		isChosen = false;
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
		return findS.where().eq("id", id).findUnique();
	}

	public static List<Service> listOfServices() {
		List<Service> allServices = new ArrayList<Service>();
		allServices = findS.all();
		return allServices;
	}

	public static void deleteService(long id) {
		Service srv = findS.byId(id);
		srv.delete();
	}

}
