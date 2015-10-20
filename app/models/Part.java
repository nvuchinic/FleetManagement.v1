package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "part")
public class Part extends Model {

	@Id
	public long id;

	public String name;

	public long number;

	@ManyToOne
	public PartCategory partCategory;

	public String description;

	public double cost;

	public String manufacturer;

	@OneToMany(mappedBy = "part", cascade = CascadeType.ALL)
	public List<Vendor> vendors;

	/**
	 * @param name
	 * @param number
	 * @param category
	 * @param description
	 * @param cost
	 * @param manufacturer
	 * @param vendors
	 */
	public Part(String name, long number, PartCategory partCategory,
			String description, double cost, String manufacturer) {
		super();
		this.name = name;
		this.number = number;
		this.partCategory = partCategory;
		this.description = description;
		this.cost = cost;
		this.manufacturer = manufacturer;
		this.vendors = new ArrayList<Vendor>();
	}

	/**
	 * Finder for Part class
	 */

	public static Finder<Long, Part> find = new Finder<Long, Part>(Part.class);

	/**
	 * Method for creating and saving Part object in database
	 * 
	 * @param name
	 * @param number
	 * @param category
	 * @param description
	 * @param cost
	 * @param manufacturer
	 * @return id of new Part object
	 */
	public static long createPart(String name, long number,
			PartCategory category, String description, double cost,
			String manufacturer) {
		Part part = new Part(name, number, category, description, cost,
				manufacturer);
		part.save();
		return part.id;
	}

	public static Part findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}

	public static Part findById(long id) {
		return find.byId(id);
	}

	public static Part findByNumber(long number) {
		return find.where().eq("number", number).findUnique();
	}

	public static List<Part> findByCategory(PartCategory category) {
		return find.where().eq("partCategory", category).findList();
	}

	public static List<Part> allParts() {
		return find.all();
	}
	
	public static void deletePart(long id) {
		Part.findById(id).delete();
	}
}
