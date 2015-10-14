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

	//public String plate;

	@ManyToOne
	public Driver driver;
	
	public Date billDate;

	public String fuelAmount;

	public String fuelPrice;

	public String totalDistance;

	public String totalDistanceGps;

	@ManyToOne
	public Vehicle vehicle;
	
	/**
	 * @param gasStationName
	 * @param plate
	 * @param driver
	 * @param billDate
	 * @param fuelAmount
	 * @param fuelPrice
	 * @param totalDistance
	 * @param totalDistanceGps
	 */
	public FuelBill(Vendor vendor,  
			Date billDate, String fuelAmount, String fuelPrice,
			String totalDistance, String totalDistanceGps,Vehicle v,Driver d) {
		super();
		this.vendor=vendor;
		this.driver=d;
		this.billDate = billDate;
		this.fuelAmount = fuelAmount;
		this.fuelPrice = fuelPrice;
		this.totalDistance = totalDistance;
		this.totalDistanceGps = totalDistanceGps;
		this.vehicle=v;
	}

	public FuelBill(Vendor vendor2, String totalDistanceGps2, Date billDate2,
			String totalDistance2, String totalDistanceGps3,
			String totalDistanceGps4, String totalDistanceGps5, Vehicle v,
			Driver d) {
		this.vendor=vendor2;
	}

	public static long createFuelBill(Vendor vendor, 
			 Date billDate, String fuelAmount, String fuelPrice,
			String totalDistance, String totalDistanceGps,Vehicle v,Driver d) {
		FuelBill fb = new FuelBill(vendor,totalDistanceGps, billDate, totalDistance, totalDistanceGps, totalDistanceGps,
				totalDistanceGps,v,d);
		fb.save();
		return fb.id;
	}

	/**
	 * Finder for FuelBill object
	 */
	public static Finder<Long, FuelBill> find = new Finder<Long, FuelBill>(
			FuelBill.class);

	

}
