package models;

import java.sql.Date;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "fuelBill")
public class FuelBill extends Model {

	@Id
	public long id;
	
	public String gasStationName;
	
	public String plate;
	
	@OneToOne
	public Driver driver;
	
	public Date billDate;
	
	public String fuelAmount;
	
	public String fuelPrice;
	
	public String totalDistance;
	
	public String totalDistanceGps;

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
	public FuelBill(String gasStationName, String plate, Driver driver,
			Date billDate, String fuelAmount, String fuelPrice,
			String totalDistance, String totalDistanceGps) {
		super();
		this.gasStationName = gasStationName;
		this.plate = plate;
		this.driver = driver;
		this.billDate = billDate;
		this.fuelAmount = fuelAmount;
		this.fuelPrice = fuelPrice;
		this.totalDistance = totalDistance;
		this.totalDistanceGps = totalDistanceGps;
	}
	
	public static long createFuelBill(String gasStationName, String plate, Driver driver,
			Date billDate, String fuelAmount, String fuelPrice,
			String totalDistance, String totalDistanceGps) {
		FuelBill fb = new FuelBill(totalDistanceGps, totalDistanceGps, driver, billDate,
				totalDistanceGps, totalDistanceGps, totalDistanceGps, totalDistanceGps);
		fb.save();
		return fb.id;
	}
	
	/**
	 * Finder for FuelBill object
	 */
	public static Finder<Long, FuelBill> find = new Finder<Long, FuelBill>(FuelBill.class);

}
