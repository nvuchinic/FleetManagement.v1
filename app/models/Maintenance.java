package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
@Table(name = "maintenance")
public class Maintenance extends Model {

	@Id
	public long id;

	@ManyToOne
	public Vehicle vehicle;

	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mnts_services", joinColumns = { @JoinColumn(name = "maintenanceId", referencedColumnName = "id") }, 
    inverseJoinColumns = { @JoinColumn(name = "serviceId", referencedColumnName = "id") })
	public List<Service> services;

	public String serviceType;

	public Date mDate;

	public Maintenance(Vehicle vehicle, Date mDate) {
		this.vehicle = vehicle;
		this.services = new ArrayList<Service>();
		this.mDate = mDate;
	}

	public static Maintenance saveToDB(Vehicle v, Date mDate) {
		Maintenance mnt = new Maintenance(v, mDate);
		mnt.save();
		return mnt;
	}

	/**
	 * Finder for Maintenance object
	 */
	public static Finder<Long, Maintenance> find = new Finder<Long, Maintenance>(
			Maintenance.class);

	/**
	 * Method which finds TravelOrder object in DB by numberTO
	 * 
	 * @param vid
	 *            of vehicle
	 * @return TravelOrder object
	 */
	public static Maintenance findById(long id) {
		return find.byId(id);
	}

	/**
	 * Method for deleting Maintenance object
	 * 
	 * @param id
	 *            of Maintenance object
	 */
	public static void deleteMaintenance(long id) {
		Maintenance mnt = find.byId(id);
		mnt.delete();
	}

	public static List<Maintenance> listOfMaintenances() {
		List<Maintenance> allMaintenances = new ArrayList<Maintenance>();
		allMaintenances = find.all();
		return allMaintenances;

	}

}
