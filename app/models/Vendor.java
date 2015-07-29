package models;

import play.data.validation.Constraints.Required;

//import play.db.ebean.Model;
import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Vendor extends Model{

	@Id
public int id;

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
