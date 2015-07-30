package models;

import play.data.validation.Constraints.Required;

//import play.db.ebean.Model;
import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Vendor extends Model{

	@Id
public int id;

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

@Required
public String name;

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

public Vendor(String name, String address, String city, String country, String phone, String email){
	this.name=name;
	this.address=address;
	this.city=city;
	this.country=country;
	this.phone=phone;
	this.email=email;
}

public static Vendor saveToDB(String name, String address, String city, String country, String phone, String email){
	Vendor v=new Vendor(name, address, city, country, phone, email);
	v.save();
	return v;
}

}
