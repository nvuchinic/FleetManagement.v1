package models;

import java.util.List;

import play.data.validation.Constraints.Required;



//import play.db.ebean.Model;
import com.avaje.ebean.Model;

import javax.persistence.*;

import com.avaje.ebean.Model.Finder;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Class for representing Truck model.
 * It extends Vehicle class
 * @author nermin vucinic
 * @version 1.0
 * @since 28.07.2015.
 */
@Entity
@Table(name = "train")
public class Train extends Model  {
	
	@Id
	public long id;
	
	public int size;
	
	public Vehicle locomotive;
	
	public Vehicle lastWagon;
	
	public Train(){
	locomotive=null;
	lastWagon=null;
	size=0;
	}
	
	public Train(Vehicle locomotive, Vehicle lastWagon){
		this.locomotive=locomotive;
		this.lastWagon=lastWagon;
		size=2;
		}

    public boolean isEmpty()    {
    	return size == 0; 
    	}
    
    public int getSize()      {
    	return size;     
    	}
    
    public int getNoOfWagons(){
    	if(size<2){
    		return 0;
    	}
    	else{
    		return size-1;
    	}
    }

	@SuppressWarnings("deprecation")
	public static Finder<Long, Train> find = new Finder<Long, Train>(
			Long.class, Train.class);
/**
 * Finds and returns all the Train objects  in database
 * @return all the Train objects in database
 */
	public static List<Train> allTrains() {
		return find.all();
	}
	
	public static Train saveTrain(Vehicle locomotive, Vehicle lastWagon) {
		Train t = new Train(locomotive, lastWagon);
		t.save();
		return t;	
	}



}
