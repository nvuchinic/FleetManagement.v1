package models;

import java.util.ArrayList;

import java.util.List;
import java.sql.Date;

import play.data.validation.Constraints.Required;

//import com.avaje.ebean.Model;



import javax.persistence.*;

import play.db.ebean.Model;

import com.avaje.ebean.Model.Finder;



/**
 * Class for representing Train Composition model. It extends Vehicle class
 * 
 * @author nermin vucinic
 * @version 1.0
 * @since 28.07.2015.
 */
@Entity
@Table(name = "trainComposition")
public class TrainComposition extends Model {

	@Id
	public long id;

	public long numOfVehicles;

	@OneToMany(mappedBy="trainComposition",cascade=CascadeType.ALL)
	public List<Vehicle> trainVehicles;

	public Date createdd;

	/**
	 * Constructor method
	 */
	public TrainComposition() {
		super();
		this.trainVehicles=new ArrayList<Vehicle>();
		java.util.Date utilDate = new java.util.Date();
		this.numOfVehicles = 0;
		createdd = new java.sql.Date(utilDate.getTime());
	}

	public TrainComposition(Vehicle truck,Vehicle trailer) {
		this.trainVehicles=new ArrayList<Vehicle>();
		trainVehicles.add(truck);
		trainVehicles.add(trailer);
		java.util.Date utilDate = new java.util.Date();
		createdd = new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * Constructor method
	 */
	public TrainComposition(Vehicle truck) {
		this.trainVehicles=new ArrayList<Vehicle>();
		trainVehicles.add(truck);
		this.numOfVehicles = 1;
		java.util.Date utilDate = new java.util.Date();
		createdd = new java.sql.Date(utilDate.getTime());
	}

	
	public static TrainComposition saveToDB(Vehicle train, Vehicle wagon) {
		TrainComposition tc = new TrainComposition(train,wagon);
		tc.save();
		return tc;
	}
	
	public static TrainComposition saveToDB(Vehicle train) {
		TrainComposition tc = new TrainComposition(train);
		tc.save();
		return tc;
	}

	public static TrainComposition saveToDB() {
		TrainComposition tc = new TrainComposition();
		tc.save();
		return tc;
	}
	

	public static Finder<Long, TrainComposition> find = new Finder<Long, TrainComposition>(
			TrainComposition.class);

	public static List<Vehicle> listOfVehicles(long id) {
		TrainComposition tc = find.byId(id);
		List<Vehicle> vhcs = new ArrayList<Vehicle>();
		vhcs = tc.trainVehicles;
		return vhcs;
	}
	
	public static int numOfVehicles(long id) {
		TrainComposition tc = find.byId(id);
		List<Vehicle> vhcs = new ArrayList<Vehicle>();
		vhcs = tc.trainVehicles;
		int size = vhcs.size();
		return size;
	}
	
	/**
	
	/**
	 * Finds all TruckComposition objects stored in database
	 * 
	 * @return all TruckComposition objects stored in database
	 */
	public static List<TrainComposition> allTrainComps() {
		return find.all();
	}

	/**
	 * Finds and returns TrainComposition object based on passed parameter
	 * 
	 * @param id
	 * @return
	 */
	public static TrainComposition findById(long id) {
		return find.byId(id);
	}

	
	public  boolean hasNextVehicle(long vId){
		Vehicle v=Vehicle.findById(vId);
		TrainComposition tc=v.trainComposition;
		int vIndex=tc.trainVehicles.indexOf(v);
		int nextVehicleInd=vIndex+1;
		Vehicle nextVehicle=tc.trainVehicles.get(nextVehicleInd);
		if(nextVehicle==null)
		return false;
		else
			return true;
	}
	
	public static boolean hasPrevVehicle(long vId){
		Vehicle v=Vehicle.findById(vId);
		TrainComposition tc=v.trainComposition;
		int vIndex=tc.trainVehicles.indexOf(v);
		int prevVehicleInd=vIndex-1;
		Vehicle prevVehicle=tc.trainVehicles.get(prevVehicleInd);
		if(prevVehicle==null)
		return false;
		else return true;
	}
	
	public static void deleteTrainComposition(long id) {
		TrainComposition tc = find.byId(id);
		tc.delete();
	}
	
	public static long nextVehicleId(long vId){
		Vehicle v=Vehicle.findById(vId);
		TrainComposition tc=v.trainComposition;
		int vIndex=tc.trainVehicles.indexOf(v);
		Vehicle nextVehicle=tc.trainVehicles.get(vIndex+1);
		long nextVId=nextVehicle.id;
		return nextVId;
	}
	
}