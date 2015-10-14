package models;

import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

/**
 * Class for representing vendor model.
 * 
 * @author nermin vucinic
 * @version 1.0
 * @since 28.07.2015.
 */
@Entity
public class Vendor extends Model {

	@Id
	public int id;

	@Required
	public String name;

	@Required
	public String vendorType;
	
	@Required
	public String address;

	@Required
	public String city;

	@Required
	public String country;

	@Required
	public String phone;

	@Required
	public String email;

	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
	public List<FuelBill> fuelBills;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Constructor method
	 * 
	 * @param name
	 * @param address
	 * @param city
	 * @param country
	 * @param phone
	 * @param email
	 */
	public Vendor(String name, String vType,String address, String city, String country,
			String phone, String email) {
		this.name = name;
		this.vendorType=vType;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.email = email;
	}

	/**
	 * Finder for Vehicle object
	 */
	public static Finder<Long, Vendor> find = new Finder<Long, Vendor>(
			Vendor.class);
	
	/**
	 * Stores newly created Vendor object to database
	 * 
	 * @param name
	 * @param address
	 * @param city
	 * @param country
	 * @param phone
	 * @param email
	 * @return newly created Vendor object to database
	 */
	public static Vendor saveToDB(String name, String vType, String address, String city,
			String country, String phone, String email) {
		Vendor v = new Vendor(name, vType, address, city, country, phone, email);
		v.save();
		return v;
	}
	
	public static Vendor findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}


}
