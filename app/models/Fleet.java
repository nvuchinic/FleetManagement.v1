package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "fleet")
public class Fleet extends Model {

	@Id
	public long id;
	
	public String name;
	
	public long numOfVehicles;
	
	@OneToMany(mappedBy="fleet",cascade=CascadeType.ALL)
	public List<Vehicle> vehicles;

	
	/**
	 * @param name
	 * @param numOfVehicles
	 * @param vehicles
	 */
	public Fleet(String name, long numOfVehicles) {
		super();
		this.name = name;
		this.numOfVehicles = numOfVehicles;
		this.vehicles = new ArrayList<Vehicle>();
	}
	
	/**
	 * Empty(Default) contstructor
	 */
	public Fleet() {
		
	}

	/**
	 * Finder for Fleet object
	 */
	public static Finder<Long, Fleet> find = new Finder<Long, Fleet>(Long.class,
			Fleet.class);
	
	/**
	 * Method which create a new Fleet object into the DB
	 * @param name
	 * @param numOfVehicles
	 * @param vehicles
	 * @return id of new Fleet object
	 */
	public static long createFleet(String name, long numOfVehicles) {
		Fleet f = new Fleet(name, numOfVehicles);
		return f.id;		
	}
	
	/**
	 * Method which finds Fleet object in DB by name
	 * @param name of Fleet
	 * @return Fleet object
	 */
	public static Fleet findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
}
