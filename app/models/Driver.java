package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import play.db.ebean.Model;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;


/**
 * Class driver for fleet management application
 * @author Emir Imamović
 *
 */
@Entity
@Table(name = "driver")
public class Driver extends Model {
	
	@Id
	public long id;
	
	@Required
	public String firstName;
	
	
	@Required
	public String lastName;
	
	public String driverName;
	
	@NotNull
	public String phoneNumber;
	
	@Required
	public String adress;
	
	@NotNull
	public String description;
	
	@NotNull
	public String gender;
	
	
	@Past
	public Date dob;
	
	public Date created;
	
	@OneToOne
	public TravelOrder travelOrder;
	
	public boolean engaged;
	
	/**
	 * @param name
	 * @param surname
	 * @param phoneNumber
	 * @param adress
	 * @param description
	 * @param gender
	 * @param dob
	 */
	public Driver(String fname, String surname, String phoneNumber,
			String adress, String description, String gender, Date dob) {
		
		this.firstName = fname;
		this.lastName = surname;
		this.phoneNumber = phoneNumber;
		this.adress = adress;
		this.description = description;
		this.gender = gender;
		this.dob = dob;
		this.created = new Date();
		this.driverName=this.firstName+" "+this.lastName;
		this.engaged=false;
}
	
	/**
	 * Finder for Driver class
	 */
	public static Finder<Long, Driver> find = new Finder<Long, Driver>(Driver.class);
	
	/**
	 * Method for creating Driver object
	 * @param driver's name
	 * @param driver's surname
	 * @param driver's phoneNumber
	 * @param driver's adress
	 * @param description of driver
	 * @param driver's gender
	 * @param dob of driver
	 * @return id of new Driver object
	 */
	public static long createDriver(String name, String surname, String phoneNumber,
			String adress, String description, String gender, Date dob) {
		//Truck t = new Truck();
		Driver driver = new Driver(name, surname, phoneNumber, adress, description, gender, dob);
		driver.save();
		return driver.id;
	
	}
	
	public static Long saveToDB(String name, String surname, String phoneNumber,
			String adress, String description, String gender, Date dob) {
		//Truck t = new Truck();
		Driver driver = new Driver(name, surname, phoneNumber, adress, description, gender, dob);
		driver.save();
		return driver.id;
	
	}
	
	/**
	 * Method for deleting Driver object
	 * @param id of driver
	 */
	public static void deleteDriver(long id) {
		Driver d = find.byId(id);
		d.delete();
	}
	
	/**
	 * Method which finds Driver in DB by Id
	 * @param id of Driver
	 * @return Driver object
	 */
	public static Driver findById(long id) {
		return find.byId(id);
	}
	
	/**
	 * Method which finds Driver in DB by name
	 * @param name of Driver
	 * @return Driver object
	 */
	public static Driver findByName(String driverName) {
		return find.where().eq("driverName", driverName).findUnique();
	}
	
	/**
	 * Method which finds Driver in DB by surname
	 * @param surname of Driver
	 * @return Driver object
	 */
	public static Driver findByLastName(String lastName) {
		return find.where().eq("lastName", lastName).findUnique();
	}
	
	public static List<Driver> listOfDrivers() {
		return find.findList();
	}
	
}
