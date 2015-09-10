package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "technicalInfo")
public class TechnicalInfo extends Model {

	@Id
	public long id;

	public String engineSerialNumber;

	public String chassisNumber;

	public String cylinderVolume;

	public String fuelConsumption;

	public String loadingLimit;

	public String fuelTank;

	public String enginePower;

	public String torque;

	public String numOfCylinders;

	public String netWeight;

	public String loadedWeight;

	public String trunkCapacity;
	
	@OneToOne
	public Tires tires;
	
	@OneToOne
	public Vehicle vehicle;

	/**
	 * @param engineSerialNumber
	 * @param chassisNumber
	 * @param cylinderVolume
	 * @param fuelConsumption
	 * @param loadingLimit
	 * @param fuelTank
	 * @param enginePower
	 * @param torque
	 * @param numOfCylinders
	 * @param netWeight
	 * @param loadedWeight
	 * @param trunkCapacity
	 * @param tires
	 */
	public TechnicalInfo(String engineSerialNumber, String chassisNumber,
			String cylinderVolume, String fuelConsumption, String loadingLimit,
			String fuelTank, String enginePower, String torque,
			String numOfCylinders, String netWeight, String loadedWeight,
			String trunkCapacity, Tires tires) {
		super();
		this.engineSerialNumber = engineSerialNumber;
		this.chassisNumber = chassisNumber;
		this.cylinderVolume = cylinderVolume;
		this.fuelConsumption = fuelConsumption;
		this.loadingLimit = loadingLimit;
		this.fuelTank = fuelTank;
		this.enginePower = enginePower;
		this.torque = torque;
		this.numOfCylinders = numOfCylinders;
		this.netWeight = netWeight;
		this.loadedWeight = loadedWeight;
		this.trunkCapacity = trunkCapacity;
		this.tires = tires;
	}

	public static long createTechnicalInfo(String engineSerialNumber,
			String chassisNumber, String cylinderVolume,
			String fuelConsumption, String loadingLimit, String fuelTank,
			String enginePower, String torque, String numOfCylinders,
			String netWeight, String loadedWeight, String trunkCapacity,
			Tires tires) {
		TechnicalInfo ti = new TechnicalInfo(engineSerialNumber, chassisNumber,
				cylinderVolume, fuelConsumption, loadingLimit, fuelTank,
				enginePower, torque, numOfCylinders, netWeight, loadedWeight,
				trunkCapacity, tires);
		ti.save();
		return ti.id;
	}

	/**
	 * Finder for TechnicalInfo object
	 */
	public static Finder<Long, TechnicalInfo> find = new Finder<Long, TechnicalInfo>(
			TechnicalInfo.class);
}
