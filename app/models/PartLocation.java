package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class PartLocation extends Model{

	@Id
	public long id;

	public String name;

	public String description;
	
	@OneToMany(mappedBy = "partLocation", cascade = CascadeType.ALL)
	public List<Part> parts;

	/**
	 * constructor method
	 * @param name
	 * @param description
	 */
	public PartLocation(String name, String description) {
		super();
		this.name = name;
		this.description=description;
		this.parts = new ArrayList<Part>();
	}

	/**
	 * Finder for PartLocation class
	 */

	public static Finder<Long, PartLocation> find = new Finder<Long, PartLocation>(
			PartLocation.class);

	/**
	 * Method for creating and saving a new PartLocation object in database
	 * @param name of PartLocation object
	 * @return id of new PartLocation object
	 */
	public static PartLocation saveToDB(String name, String description) {
		PartLocation pl = new PartLocation(name, description);
		pl.save();
		return pl;
	}
	
	public static PartLocation findById(long id) {
		return find.byId(id);
	}
	
	public static PartLocation findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	public static List<PartLocation> allPartLocations() {
		return find.all();
	}
	
	public static void deletePartLocation(long id) {
		PartLocation.findById(id).delete();
	}
	
}
