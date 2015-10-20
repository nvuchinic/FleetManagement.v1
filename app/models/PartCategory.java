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

	@OneToMany(mappedBy = "partCategory", cascade = CascadeType.ALL)
	public List<Part> parts;

	/**
	 * @param name
	 * @param parts
	 */
	public PartCategory(String name) {
		super();
		this.name = name;
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
	public static long createPartCategory(String name) {
		PartCategory partCategory = new PartCategory(name);
		partCategory.save();
		return partCategory.id;
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
	
}
