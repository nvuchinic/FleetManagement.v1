package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "description")
public class Description extends Model {
	@Id
	public long id;
	
	@OneToOne
	public Type typev;
	
 public String chassis;
 public String engineNumber;
 public String cCm;
 public String vehicleBrand;
 public String model;
 public String color;
 public String shape;
 public String fuel;
 public String tankage;
 public String currentMileage;
 public String productionDate;
 public String productionState;
 
 
/**
 * @param chassis
 * @param engineNumber
 * @param cCm
 * @param vehicleBrand
 * @param model
 * @param color
 * @param shape
 * @param fuel
 * @param tankage
 * @param currentMileage
 * @param productionDate
 * @param productionState
 */
public Description(String chassis, String engineNumber, String cCm,
		String vehicleBrand, String model, String color, String shape,
		String fuel, String tankage, String currentMileage, String productionDate,
		String productionState) {
	super();
	this.chassis = chassis;
	this.engineNumber = engineNumber;
	this.cCm = cCm;
	this.vehicleBrand = vehicleBrand;
	this.model = model;
	this.color = color;
	this.shape = shape;
	this.fuel = fuel;
	this.tankage = tankage;
	this.currentMileage = currentMileage;
	this.productionDate = productionDate;
	this.productionState = productionState;
}
/**
 * Finder for Description object
 */
public static Finder<Long, Description> find = new Finder<Long, Description>(Long.class,
		Description.class);


public static long createDescription(String chassis, String engineNumber, String cCm, String vehicleBrand,
		String model,String color, String shape, String fuel, String tankage, String currentMileage, String productionDate, String productionState) {
	Description d = new Description(chassis, engineNumber, cCm, vehicleBrand, model, color, shape, fuel, tankage, currentMileage, productionDate, productionState);
	d.save();
	return d.id;
}
 
}

