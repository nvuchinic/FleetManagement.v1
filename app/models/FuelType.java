package models;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class FuelType extends Model {
	
@Id
public long id;

public String ftName;

@OneToMany(mappedBy = "fuelType", cascade = CascadeType.ALL)
public List<FuelBill> fuelBills;

@OneToMany(mappedBy = "fuelType", cascade = CascadeType.ALL)
public List<TechnicalInfo> tInfos;;

/**
 * constructor method
 * @param name-name of fuelType object
 */
public FuelType(String name){
	this.ftName=name;
	fuelBills=new ArrayList<FuelBill>();
}

/**
 * saves created FuelType object to database
 * @param name-name of FuelType object
 * @return
 */
public static FuelType saveToDB(String name){
	FuelType ft =new FuelType(name);
	ft.save();
	return ft;
}

/**
 * Finder for FuelType object
 */
public static Finder<Long, FuelType> find=new Finder<Long, FuelType>(FuelType.class);

/**
 * finds FuelType object by it's  name
 * @param name-name of FulType object
 * @return FuelType object
 */
public static FuelType findByName(String name) {
	return find.where().eq("ftName", name).findUnique();
}

/**
 * finds FuelType object by it's ID number
 * @param id-id of the FuelType object
 * @return FuelType object
 */
public static FuelType findById(long id) {
	return find.byId(id);
}

/**
 * First finds the FuelType object in database by it's ID number,
 *  then deletes it from database
 * @param id-ID of FuelType object
 */
public static void deleteFuelType(long id) {
	FuelType ft = find.byId(id);
	ft.delete();
}

/**
 * finds list of all FuelType objects in database,
 * and returns them
 * @return-list of all FuelType objects
 */
public static List<FuelType> listOfFuelTypes() {
	List<FuelType> allFuelTypes = new ArrayList<FuelType>();
	allFuelTypes = find.all();
	return allFuelTypes;
}
}


