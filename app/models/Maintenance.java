package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Maintenance extends Model{

	@Id
	public long id;
	
	@ManyToOne
	public Vehicle vehicle;
	
	@OneToMany
	public List<Service> services;
	
	public String serviceType;
	
	public Date mDate;
	
	public Maintenance(Vehicle v,Service s,Date d){
		vehicle=v;
		services=new ArrayList<Service>();
		services.add(s);
		mDate = d;
	}
	
	public static Maintenance saveToDB(Vehicle v, Service s, Date d){
		Maintenance mnt=new Maintenance(v,s, d);
		mnt.save();
		return mnt;
	}
	
	/**
	 * Finder for Maintenance object
	 */
	public static Finder<Long, Maintenance> find = new Finder<Long, Maintenance>(Long.class,
			Maintenance.class);
	
	/**
	 * Method which finds TravelOrder object in DB by numberTO
	 * @param vid of vehicle
	 * @return TravelOrder object 
	 */
	public static Maintenance findById(long id) {
		return find.byId(id);
	}
	
	/**
	 * Method for deleting Maintenance object
	 * @param id of Maintenance object
	 */
	public static void deleteMaintenance(long id) {
		Maintenance mnt= find.byId(id);
		mnt.delete();
	}

	public static List<Maintenance> listOfMaintenances() {
		List<Maintenance> allMaintenances =  new ArrayList<Maintenance>();
		allMaintenances = find.all();
		return allMaintenances;
	
	}
	
	
}
