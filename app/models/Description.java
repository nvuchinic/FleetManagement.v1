package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "description")
public class Description extends Model {
	@Id
	public long id;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "VehicleDescription", joinColumns = { @JoinColumn(name = "descriptionId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "vehicleId", referencedColumnName = "id") })
	public List<Vehicle> vehicles;

	public String propertyName;
	public String propertyValue;

	/**
	 * @param propertyName
	 * @param propertyValue
	 */
	public Description(String propertyName, String propertyValue) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;

	}

	public Description(String propertyName, String propertyValue,
			List<Vehicle> vehicles) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.vehicles = vehicles;

	}

	/**
	 * Default constructor for Description object
	 */
	public Description() {

	}

	/**
	 * Constructor for Description object
	 */
	public Description(String name) {
		this.propertyName = name;
	}

	/**
	 * Finder for Description object
	 */
	public static Finder<Long, Description> find = new Finder<Long, Description>(
			Long.class, Description.class);

	public static long createDescription(String propertyName,
			String propertyValue) {
		Description d = new Description(propertyName, propertyValue);
		d.save();
		return d.id;
	}

	public static long createDescription(String propertyName,
			String propertyValue, List<Vehicle> vehicles) {
		Description d = new Description(propertyName, propertyValue, vehicles);
		d.save();
		return d.id;
	}

	public static Description findById(long id) {
		return find.where().eq("id", id).findUnique();
	}

	public static List<Description> findByName(String propertyName) {
		return find.where().eq("propertyName", propertyName).findList();
	}

	// public static List<Description> findByType(Type typev) {
	// return find.where().eq("typev", typev).findList();
	// }

	public static List<Description> allDescription() {
		return find.all();
	}
	
//	public static List<Description> findByTypeDescription(Type type) {
//		List<Description> desc = new ArrayList<Description>();
//		List<Vehicle> vehicles = Vehicle.findByType(type);
//		for(int i = 0; i < allDescription().size(); i++) {
//			if(allDescription().contains(vehicles.get(0)))
//				desc.add(allDescription().get(i));
//		}
//		return desc;
//	}
}
