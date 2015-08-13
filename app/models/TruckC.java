package models;

import java.util.List;

import play.data.validation.Constraints.Required;






//import play.db.ebean.Model;
import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.persistence.*;

import com.avaje.ebean.Model.Finder;

/**
 * Class for representing Truck model.
 * It extends Vehicle class
 * @author nermin vucinic
 * @version 1.0
 * @since 28.07.2015.
 */
@Entity
@Table(name = "truckC")
public class TruckC extends Model {
	
	@Id
	public long id;
	
	public int size;
	
	public Vehicle truckHead;
	
	public Vehicle lastTrailer;
	
	public TruckC(){
	truckHead=null;
	lastTrailer=null;
	size=0;
	}	

	public TruckC(Vehicle truckHead, Vehicle lastTrailer){
		this.truckHead=truckHead;
		this.lastTrailer=lastTrailer;
		size=2;
		}

	public TruckC(long truckHeadId, long lastTrailerId){
		Vehicle truckHead=Vehicle.findById(truckHeadId);
		this.truckHead=truckHead;
		Vehicle lastTrailer=Vehicle.findById(lastTrailerId);
		this.lastTrailer=lastTrailer;
		size=2;
		}
	
	public static TruckC saveToDB(long truckHeadId, long lastTrailerId) {
		TruckC t = new TruckC(truckHeadId, lastTrailerId);
		t.save();
		return t;	
	}
	
	public static TruckC saveToDB(Vehicle truckHead, Vehicle lastTrailer) {
		TruckC t = new TruckC(truckHead, lastTrailer);
		t.save();
		return t;	
	}
	
    public boolean isEmpty()    {
    	return size == 0; 
    	}
    
    public int getSize()      {
    	return size;     
    	}
    
    public int getNoOfTrailers(){
    	if(size<2){
    		return 0;
    	}
    	else{
    		return size-1;
    	}
    }	

	
//	public static Finder<Long, TruckC> find = new Finder<Long, TruckC>(
//			Long.class, TruckC.class);
	public static Finder<Long, TruckC> find = new Finder<>(TruckC.class);
	/**
	 * Finds all Truck objects stored in database
	 * @return all Truck objects stored in database
	 */
	public static List<TruckC> allTruckCs() {
		return find.all();
	}
	
	
	/**
	 * Finds and returns Truck object based on passed parameter
	 * @param id
	 * @return
	 */
	public static TruckC findById(long id) {
		return find.where().eq("id", id).findUnique();
	}
	
	public static void deleteTruckcc(long id) {
		TruckC tc = find.byId(id);
		tc.delete();
	}
}