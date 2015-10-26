package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class MeasurementUnit extends Model{

	@Id
	public long id;
	
	public String name;

	public String symbol;
	
	public String description;

	@OneToMany
	public List<Part> parts;

	/**
	 * constructor method
	 * @param name-name for the measurement unit
	 * @param symbol-symbol(abbreviation) fr the measurement unit
	 * @param descritpion-descritpion for the measurement unit
	 */
	public MeasurementUnit(String name, String symbol, String descritpion) {
		this.name=name;
		this.symbol=symbol;
		this.description=descritpion;
		this.parts = new ArrayList<Part>();
	}

	/**
	 * creates and saves MeasurementUnit object to database
	 * 
	 * @param cName
	 * @param cType
	 * @param address
	 * @param phone
	 * @param email
	 * @return
	 */
	public static MeasurementUnit saveToDB(String name, String symbol, String description) {
		MeasurementUnit mu = new MeasurementUnit(name, symbol,descritpion );
		mu.save();
		return mu;
	}

	/**
	 * finder for MeasurementUnit object
	 */
	public static Finder<Long, MeasurementUnit> find = new Finder<Long, MeasurementUnit>(
			MeasurementUnit.class);

	/**
	 * finds MeasurementUnit object in database by it's ID number
	 * @param id- of MeasurementUnit object
	 * @return MeasurementUnit object
	 */
	public static MeasurementUnit findById(long id) {
		return find.byId(id);
	}

	/**
	 * finds MeasurementUnit object by name (passed parameter)
	 *  @param name-name of MeasurementUnit object
	 * @return
	 */
	public static MeasurementUnit findByName(String name) {
		return find.where().eq("cName", name).findUnique();
	}

	/**
	 * finds MeasurementUnit object by ID number passed as parameter,
	 *  then removes it from database
	 * @param id   -ID number of MeasurementUnit object
	 */
	public static void deleteM_Unit(long id) {
		MeasurementUnit mu = find.byId(id);
		mu.delete();
	}

	/**
	 * Finds all MeasurementUnit objects in database 
	 * and returns them as List
	 * @return all MeasurementUnit objects as List
	 */
	public static List<MeasurementUnit> listOfM_Units() {
		List<MeasurementUnit> allM_Units = new ArrayList<MeasurementUnit>();
		allM_Units = find.all();
		return allM_Units;
	}

	

}
