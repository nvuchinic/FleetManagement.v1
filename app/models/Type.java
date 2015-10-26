package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

/**
 * Type model
 * 
 * @author Emir Imamović
 *
 */

@Entity
@Table(name = "type")
public class Type extends Model {

	@Id
	public long id;

	public String name;

	@OneToMany(mappedBy = "typev", cascade = CascadeType.ALL)
	public List<Vehicle> vehicles;
	
	@OneToMany(mappedBy = "typev", cascade = CascadeType.ALL)
	public List<VehicleBrand> vehicleBrands;
	
	public Type(String name) {
		this.name = name;
		this.vehicleBrands = new ArrayList<VehicleBrand>();
	}

	public Type() {
		this.name = "";
	}

	public static long createType(String name) {
		Type t = new Type(name);
		t.save();
		return t.id;
	}

	public static Type saveToDB(String name) {
		Type t = new Type(name);
		t.save();
		return t;
	}

	
	/**
	 * Finder for Type object
	 */
	public static Finder<Long, Type> find = new Finder<Long, Type>(
			Type.class);

	public static Type findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}

	public static Type findById(long id) {
		return find.byId(id);
	}

	public static List<Type> typesList() {
		return find.all();
	}
	
	/**
	 * finds Type object by it's ID number
	 * passed as parameter, then removes it
	 * from database
	 * 
	 * @param id-ID number of Type object
	 */
	public static void deleteType(long id) {
		Type t = find.byId(id);
		t.delete();
	}
}
