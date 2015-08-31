package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

import com.avaje.ebean.Model.Finder;

/**
 * Type model
 * @author Emir Imamović
 *
 */
@Entity
@Table(name = "type")
public class Type extends Model {
	
	@Id
	public long id;
	
	public String name;
	
	@OneToMany(mappedBy="typev",cascade=CascadeType.ALL)
	public List<Description> description;
	
	@OneToMany(mappedBy="typev",cascade=CascadeType.ALL)
	public List<Vehicle> vehicles;
	
	
	/**
	 * Constructor for dinamicly creating type
	 * @param name of type
	 */
	public Type(String name) {
		this.name = name;
		this.vehicles = new ArrayList<Vehicle>();
		this.description = new ArrayList<Description>();
	}
	
	
	/**
	 * Finder for Type object
	 */
	//public static Finder<Long, Type> find = new Finder<Long, Type>(Long.class,
	//		Type.class);
	public static Finder<Long, Type> find = new Finder<Long, Type>(Type.class);
	
	
	/**
	 * Method which create a new Type object
	 * @param name
	 * @param description
	 * @return new Type object
	 */
	public static long createType(String name) {
		Type t = new Type(name);
		t.save();
		return t.id;		
	}
	
	/**
	 * Method which finds Type object in DB by name
	 * @param name of Type
	 * @return Type object
	 */
	public static Type findByName(String name) {
		
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method which find Type object in DB by id
	 * @param id of Type object
	 * @return Type object
	 */
	public static Type findById(long id) {
		return find.byId(id);
	}
	
	public static List<Type> findTypeList() {
		return find.all();
	}
}
