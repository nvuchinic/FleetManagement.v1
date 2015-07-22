package models;

import play.data.validation.Constraints.Required;
//import play.db.ebean.Model;
import com.avaje.ebean.Model;
import javax.persistence.*;

import play.data.validation.Constraints.Required;

@MappedSuperclass
@Deprecated
@Entity
public class Vehicle extends Model {
	@Id
	public int id;
	
	@Required
	public String brand;
	
	public String model;
	
	@Required
	public String categoryString;
	
	//@ManyToOne
	//public MainCategory mainCategory;
	
	@Required
	public double price;

	public String publishedDate;
	
	//@ManyToOne
	//public User owner;
}
