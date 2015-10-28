package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Currencyy extends Model{

	@Id
	public long id;

	public String name;

	public String symbol;

	public String description;

	@OneToMany(mappedBy = "currencyy", cascade = CascadeType.ALL)
	public List<Part> parts;

	/**
	 * constructor method
	 * @param name
	 * @param symbol
	 * @param description
	 */
	public Currencyy(String name, String symbol, String description) {
		this.name = name;
		this.symbol = symbol;
		this.description = description;
		this.parts=new ArrayList<Part>();
	}

	/**
	 * creates and saves Currencyy object to database
	 * 
	 * @param name
	 * @param symbol
	 * @param description
	 * @return
	 */
	public static Currencyy saveToDB(String name, String symbol, String description) {
		Currencyy c = new Currencyy(name, symbol, description);
		c.save();
		return c;
	}

	/**
	 * finder for the Currency object
	 */
	public static Finder<Long, Currencyy> find = new Finder<Long, Currencyy>(
			Currencyy.class);

	/**
	 * finds Currencyy object in database by it's ID number 
	 *   passed as parameter
	 * @param id-ID of Currencyy object
	 * @return Currency object
	 */
	public static Currencyy findById(long id) {
		return find.byId(id);
	}

	/**
	 * finds Currencyy object by it's name
	 *  passed as parameter
	 * @param name-name of Currencyy object
	 * @return Currencyy object
	 */
	public static Currencyy findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}

	/**
	 * finds Currencyy object by it's ID number
	 *  passed  as parameter, then removes it
	 * from database
	 * @param id-ID number of Currencyy object
	 */
	public static void deleteCurrency(long id) {
		Currencyy c = find.byId(id);
		c.delete();
	}

	/**
	 * Finds all Currencyy objects in database 
	 * and returns them as List
	 * @return all Currencyy objects
	 */
	public static List<Currencyy> listOfCurrencies() {
		List<Currencyy> allCurrencies = new ArrayList<Currencyy>();
		allCurrencies = find.all();
		return allCurrencies;
	}


}
