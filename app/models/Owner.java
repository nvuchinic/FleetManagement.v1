package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

/**
 * Owner model
 * @author Emir Imamović
 *
 */
@Entity
@Table(name = "owner")
public class Owner extends Model {
	@Id
	public long id;
	
	public String name;
	
	public String email;

	@OneToMany(mappedBy="owner",cascade=CascadeType.ALL)
	public List<Vehicle> vehicles;
	
	/**
	 * @param name
	 * @param email
	 */
	public Owner(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	/**
	 * Finder for Owner object
	 */
	public static Finder<Long, Owner> find = new Finder<Long, Owner>(Owner.class);
	
	/**
	 * Method which create a new Owner object
	 * @param name
	 * @param email
	 * @return new Owner object
	 */
	public static long createOwner(String name, String email) {
		Owner o = new Owner(name, email);
		o.save();
		return o.id;		
	}
	
	/**
	 * Method which finds Owner object in DB by name
	 * @param name of Owner
	 * @return Owner object
	 */
	public static Owner findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method which find Owner object in DB by id
	 * @param id of Owner object
	 * @return Owner object
	 */
	public static Owner findById(long id) {
		return find.byId(id);
	}
}
