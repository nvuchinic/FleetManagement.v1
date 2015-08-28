package models;

import javax.persistence.*;

import com.avaje.ebean.Model;


/**
 * The Class TypeDescription helps connect models Description and Vehicle.
 */
@Entity
@Embeddable
public class VehicleDescription extends Model {

	/** The description_id. */
	public int description_id;

	/** The vehicle_id. */
	public int vehicle_id;

}
