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

	public String gasStationName;

	public String plate;

	@OneToMany(mappedBy = "fuelBill", cascade = CascadeType.ALL)
	public List<Driver> drivers;
	
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
	public FuelBill(String gasStationName, String plate, 
			Date billDate, String fuelAmount, String fuelPrice,
			String totalDistance, String totalDistanceGps,Vehicle v) {
		super();
		this.gasStationName = gasStationName;
		this.plate = plate;
		this.drivers = new ArrayList<Driver>();
		this.billDate = billDate;
		this.fuelAmount = fuelAmount;
		this.fuelPrice = fuelPrice;
		this.totalDistance = totalDistance;
		this.totalDistanceGps = totalDistanceGps;
		this.vehicle=v;
	}

	public static long createFuelBill(String gasStationName, String plate,
			 Date billDate, String fuelAmount, String fuelPrice,
			String totalDistance, String totalDistanceGps,Vehicle v) {
		FuelBill fb = new FuelBill(totalDistanceGps, totalDistanceGps, 
				billDate, totalDistanceGps, totalDistanceGps, totalDistanceGps,
				totalDistanceGps,v);
		fb.save();
		return fb.id;
	}

	/**
	 * Finder for FuelBill object
	 */
	public static Finder<Long, FuelBill> find = new Finder<Long, FuelBill>(
			FuelBill.class);

	

}
