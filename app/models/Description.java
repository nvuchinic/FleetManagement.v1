package models;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "description")
public class Description extends Model {
	@Id
	public long id;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "TypeDescription", joinColumns = { @JoinColumn(name = "descriptionId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "typeId", referencedColumnName = "id") })
	public List<Type> types;

	public String propertyName;
	public String propertyValue;

	/**
	 * @param propertyName
	 * @param propertyValue
	 */
	public Description(String propertyName, String propertyValue) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	/**
	 * Default constructor for Description object
	 */
	public Description() {
		
	}
	/**
	 * Finder for Description object
	 */
	public static Finder<Long, Description> find = new Finder<Long, Description>(
			Long.class, Description.class);

	public static long createDescription(String propertyName, String propertyValue) {
		Description d = new Description(propertyName, propertyValue);
		d.save();
		return d.id;
	}
	
	public static Description findByName(String propertyName) {
		return find.where().eq("propertyName", propertyName).findUnique();
	}
}
