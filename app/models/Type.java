package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name = "type")
public class Type extends Model {

	@Id
	public long id;
	
	public String name;
	
	@OneToMany(mappedBy="typev",cascade=CascadeType.ALL)
	public List<Vehicle> vehicles;
	
	public Type(String name) {
		this.name = name;
	}
	
	public Type() {
		this.name = "";
	}
	
	public static long createType(String name) {
		Type t = new Type(name);
		t.save();
		return t.id;
	}
	
	/**
	 * Finder for Description object
	 */
	public static Finder<Long, Type> find = new Finder<Long, Type>(
			Long.class, Type.class);
	
	public static Type findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	public static Type findById(long id) {
		return find.byId(id);
	}
	
	public static List<Type> typesList() {
		return find.all();
	}
}
