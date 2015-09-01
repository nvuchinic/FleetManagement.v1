package models;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import com.avaje.ebean.Model;

/**
 * The Class TypeDescription helps connect models Description and Type.
 */
@Entity
@Embeddable
public class TypeDescription extends Model {

	/** The description_id. */
	public int description_id;

	/** The type_id. */
	public int type_id;

}
