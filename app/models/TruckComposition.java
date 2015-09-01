package models;

import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.List;
import java.sql.Date;

import play.data.validation.Constraints.Required;

//import com.avaje.ebean.Model;



import javax.persistence.*;

import play.db.ebean.Model;

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

	public long numOfVehicles;

	@OneToMany(mappedBy="truckComposition",cascade=CascadeType.ALL)
	public List<Vehicle> truckVehicles;

	public Date createdd;

	/**
	 * Constructor method
	 */
	public TruckComposition() {
		super();
		this.truckVehicles=new ArrayList<Vehicle>();
		java.util.Date utilDate = new java.util.Date();
		this.numOfVehicles = 0;
		createdd = new java.sql.Date(utilDate.getTime());
	}

	public TruckComposition(Vehicle truck,Vehicle trailer) {
		this.truckVehicles=new ArrayList<Vehicle>();
		truckVehicles.add(truck);
		truckVehicles.add(trailer);
		java.util.Date utilDate = new java.util.Date();
		createdd = new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * Constructor method
	 */
	public TruckComposition(Vehicle truck) {
		this.truckVehicles=new ArrayList<Vehicle>();
		truckVehicles.add(truck);
		java.util.Date utilDate = new java.util.Date();
		createdd = new java.sql.Date(utilDate.getTime());
	}

	
	public static TruckComposition saveToDB(Vehicle truck, Vehicle trailer) {
		TruckComposition tc = new TruckComposition(truck,trailer);
		tc.save();
		return tc;
	}
	
	public static TruckComposition saveToDB(Vehicle truck) {
		TruckComposition tc = new TruckComposition(truck);
		tc.save();
		return tc;
	}

	public static TruckComposition saveToDB() {
		TruckComposition tc = new TruckComposition();
		tc.save();
		return tc;
	}
	
//	public  int getVehiclePosition(Vehicle v){
//		int pos=0;
//		//TruckComposition tc=v.truckComposition;
//		pos=this.truckVehicles.indexOf(v);
//		return pos;
//	}
//	
//	public boolean isEmpty() {
//		return truckVehicles.isEmpty();
//	}
//
//	public int getSize() {
//		return truckVehicles.size();
//	}

	

	// public static Finder<Long, TruckC> find = new Finder<Long, TruckC>(
	// Long.class, TruckC.class);
	public static Finder<Long, TruckComposition> find = new Finder<Long, TruckComposition>(
			TruckComposition.class);

	public static List<Vehicle> listOfVehicles(long id) {
		TruckComposition tc = find.byId(id);
		List<Vehicle> vhcs = new ArrayList<Vehicle>();
		vhcs = tc.truckVehicles;
		return vhcs;
	}
	
	public static int numOfVehicles(long id) {
		TruckComposition tc = find.byId(id);
		List<Vehicle> vhcs = new ArrayList<Vehicle>();
		vhcs = tc.truckVehicles;
		int size = vhcs.size();
		return size;
	}
	/**
	
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
		return find.byId(id);
	}

	
	
	public static void deleteTruckComposition(long id) {
		TruckComposition tc = find.byId(id);
		tc.delete();
	}
}