package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "partCategory")
public class PartCategory extends Model {

	@Id
	public long id;

	public String name;

	public String description;
	
	@OneToMany(mappedBy = "partCategory", cascade = CascadeType.ALL)
	public List<Part> parts;

	/**
	 * @param name
	 * @param parts
	 */
	public PartCategory(String name, String description) {
		super();
		this.name = name;
		this.description=description;
		this.parts = new ArrayList<Part>();
	}

	/**
	 * Finder for PartCategory class
	 */

	public static Finder<Long, PartCategory> find = new Finder<Long, PartCategory>(
			PartCategory.class);

	/**
	 * Method for creating and saving a new PartCategory object in database
	 * @param name of PartCategory object
	 * @return id of new PartCategory object
	 */
	public static PartCategory saveToDB(String name, String description) {
		PartCategory pc = new PartCategory(name, description);
		pc.save();
		return pc;
	}
	
	public static PartCategory findById(long id) {
		return find.byId(id);
	}
	
	public static PartCategory findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	public static List<PartCategory> allPartCategories() {
		return find.all();
	}
	
	public static void deletePartCategory(long id) {
		PartCategory.findById(id).delete();
	}
	
}
