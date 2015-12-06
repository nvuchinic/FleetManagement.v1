package models;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "fuelBill")
public class FuelBill extends Model {

	@Id
	public long id;

	@ManyToOne
	public Vendor vendor;

	@ManyToOne
	public Employee driver;
	
	public Date billDate;

	public double fuelAmount;

	public double fuelPrice;

	public double totalDistance;

	public double totalDistanceGps;

	@ManyToOne
	public Vehicle vehicle;
	
	@ManyToOne
	public FuelType fuelType;
	
	/**
	 * @param Vendor
	 * @param billDate
	 * @param fuelAmount
	 * @param fuelPrice
	 * @param totalDistance
	 * @param totalDistanceGps
	 * @param Vehicle
	 * @param driver
	 */
	public FuelBill(Vendor vendor,  
			Date billDate, double fuelAmount, double fuelPrice,
			double totalDistance, double totalDistanceGps,Vehicle v,Employee driver,FuelType ft) {
		super();
		this.vendor=vendor;
		this.driver=driver;
		this.billDate = billDate;
		this.fuelAmount = fuelAmount;
		this.fuelPrice = fuelPrice;
		this.totalDistance = totalDistance;
		this.totalDistanceGps = totalDistanceGps;
		this.vehicle=v;
		this.fuelType=ft;
	}


	public static long createFuelBill(Vendor vendor, Date billDate, double fuelAmount, double fuelPrice,
			double totalDistance, double totalDistanceGps,Vehicle v,Employee driver, FuelType ft) {
		FuelBill fb = new FuelBill(vendor,billDate, fuelAmount,fuelPrice, totalDistance, totalDistanceGps, v, driver, ft);
		fb.save();
		return fb.id;
	}

	/**
	 * Finder for FuelBill object
	 */
	public static Finder<Long, FuelBill> find = new Finder<Long, FuelBill>(
			FuelBill.class);

	

}
