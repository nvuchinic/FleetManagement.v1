package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Insurance extends Model{
	
@Id
public long id;

public String contractNo;

@OneToOne
public Vehicle vehicle;

public String itype;

public double cost;

public Insurance(String contractNo, Vehicle vehicle, String itype, double cost){
	this.contractNo=contractNo;
	this.vehicle=vehicle;
	this.itype=itype;
	this.cost=cost;
}

public static Insurance saveToDB(String contractNo, Vehicle vehicle, String itype, double cost){
	Insurance ins=new Insurance(contractNo, vehicle, itype, cost);
	ins.save();
	return ins;
}

/**
 * Finder for Insurance object
 */
public static Finder<Long, Insurance> find = new Finder<>(Insurance.class);

/**
 * Method which finds Insurance object in DB by numberTO
 * @param vid of Insurance object
 * @return Insurance object 
 */
public static Insurance findById(long id) {
	return find.byId(id);
}

/**
 * Method for deleting Insurance object
 * @param id of Insurance object
 */
public static void deleteInsurance(long id) {
	Insurance ins= find.byId(id);
	ins.delete();
}

/**
 * Finds all Insurance objects in database and returns them
 * @return all Insurance objects
 */
public static List<Insurance> listOfInsurances() {
	List<Insurance> allInsurances =  new ArrayList<Insurance>();
	allInsurances = find.all();
	return allInsurances;

}
}
