package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name = "tires")
public class Tires extends Model {

	@Id
	public long id;
	
	public String frontTireSize;
	
	public String rearTireSize;
	
	public String frontTirePressure;
	
	public String rearTirePressure;

	@OneToOne
	public TechnicalInfo technicalInfo;
	/**
	 * @param id
	 * @param frontTireSize
	 * @param rearTireSize
	 * @param frontTirePressure
	 * @param rearTirePressure
	 */
	public Tires(String frontTireSize, String rearTireSize,
			String frontTirePressure, String rearTirePressure) {
		super();
		this.frontTireSize = frontTireSize;
		this.rearTireSize = rearTireSize;
		this.frontTirePressure = frontTirePressure;
		this.rearTirePressure = rearTirePressure;
	}
	
	public static long createTires(String frontTireSize, String rearTireSize,
			String frontTirePressure, String rearTirePressure) {
		Tires t = new Tires(frontTireSize, rearTireSize, frontTirePressure, rearTirePressure);
		return t.id;
	}
}
