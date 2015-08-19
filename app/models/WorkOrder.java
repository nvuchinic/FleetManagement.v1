package models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class WorkOrder {
	@Id
	public long id;
	
	@Required
	public String woNumber;
	
	public Date woDate;
	
	@OneToOne
	public Driver driver;
	
	public String driverName;
	
	@OneToOne
	public Vehicle vehicle;
	
	public String vehicleName;
	
	@Required
	public String woDescription;
	
	public String status;
	
	
	
	
}
