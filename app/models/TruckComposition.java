package models;

import java.util.LinkedList;
import java.util.List;
import java.sql.Date;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;



/**
 * Class for representing Truck model. It extends Vehicle class
 * 
 * @author nermin vucinic
 * @version 1.0
 * @since 28.07.2015.
 */
@Entity
@Table(name = "truckComposition")
public class TruckComposition extends Model {

	@Id
	public long id;

	//public int size;

	@OneToMany
	public LinkedList<Vehicle> truckVehicles;

	public Date createdd;

	/**
	 * Constructor method
	 */
	public TruckComposition() {
		this.truckVehicles=new LinkedList<Vehicle>();
		java.util.Date utilDate = new java.util.Date();
		createdd = new java.sql.Date(utilDate.getTime());
	}

	/**
	 * Constructor method
	 */
//	public TruckComposition(Vehicle truck, Vehicle trailer) {
//		this.truckVehicles=new LinkedList<Vehicle>();
//		java.util.Date utilDate = new java.util.Date();
//		createdd = new java.sql.Date(utilDate.getTime());
//	}

	
	public static TruckComposition saveToDB() {
		TruckComposition tc = new TruckComposition();
		tc.save();
		return tc;
	}

	
	public boolean isEmpty() {
		return truckVehicles.isEmpty();
	}

	public int getSize() {
		return truckVehicles.size();
	}

	

	// public static Finder<Long, TruckC> find = new Finder<Long, TruckC>(
	// Long.class, TruckC.class);
	public static Finder<Long, TruckComposition> find = new Finder<>(
			TruckComposition.class);

	/**
	 * Finds all TruckComposition objects stored in database
	 * 
	 * @return all TruckComposition objects stored in database
	 */
	public static List<TruckComposition> allTruckCs() {
		return find.all();
	}

	/**
	 * Finds and returns TruckComposition object based on passed parameter
	 * 
	 * @param id
	 * @return
	 */
	public static TruckComposition findById(long id) {
		return find.where().eq("id", id).findUnique();
	}

	public static void deleteTruckComposition(long id) {
		TruckComposition tc = find.byId(id);
		tc.delete();
	}
}