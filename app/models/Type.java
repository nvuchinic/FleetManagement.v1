package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "type")
public class Type extends Model {
	
	@Id
	public long id;
	
	public String name;
	
	public String description;

	/**
	 * @param name
	 * @param description
	 */
	public Type(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Finder for Type object
	 */
	public static Finder<Long, Type> find = new Finder<Long, Type>(Long.class,
			Type.class);
	
	/**
	 * Method which create a new Type object
	 * @param name
	 * @param description
	 * @return new Type object
	 */
	public static long createType(String name, String description) {
		Type t = new Type(name, description);
		return t.id;		
	}
	
}
