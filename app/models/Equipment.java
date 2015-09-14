package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "equipment")
public class Equipment extends Model {

	@Id
	public long id;
	
	public String name;
	
	public String model;
	
	public long price;

	/**
	 * @param name
	 * @param model
	 * @param price
	 */
	public Equipment(String name, String model, long price) {
		super();
		this.name = name;
		this.model = model;
		this.price = price;
	}
	
	public static long createEquipment(String name, String model, long price) {
		Equipment equipment = new Equipment(name, model, price);
		equipment.save();
		return equipment.id;
	}
	
	/**
	 * Finder for Equipment object
	 */
	public static Finder<Long, Equipment> find = new Finder<Long, Equipment>(
			Equipment.class);
}
