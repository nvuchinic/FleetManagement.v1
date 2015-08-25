package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Route extends Model{

	@Id
	public long id;
	
	public String startPoint;
	
	public String endPoint;
	 
	public String rName;
	
	@OneToMany
	public TravelOrder travelOrder;
	
	/**
	 * constructor method
	 * @param startPoint
	 * @param endPoint
	 */
	public Route(String startPoint, String endPoint){
		this.startPoint=startPoint;
		this.endPoint=endPoint;
		this.rName=startPoint+" - "+endPoint;
	}
	
	/**
	 * creates Route object and saves it to database
	 * @param startPoint
	 * @param endPoint
	 * @return Route object
	 */
	public static Route saveToDB(String startPoint, String endPoint){
		Route r=new Route(startPoint, endPoint);
		r.save();
		return r;
	}
	
	/**
	 * Finder for Route object
	 */
	public static Finder<Long, Route> find = new Finder<>(Route.class);
	
	/**
	 * finds Route object in DB based on passed ID
	 * @param id of Route object
	 * @return Route object 
	 */
	public static Route findById(long id) {
		return find.byId(id);
	}

	/**
	 * finds Route object using string passed as parameter
	 * @param name name of Route object
	 * @return
	 */
	public static Route findByName(String name) {
		return find.where().eq("rName", name).findUnique();
	}
	
	/**
	 * delete Route object
	 * @param id of Route object
	 */
	public static void deleteRoute(long id) {
		Route r= find.byId(id);
		r.delete();
	}

	/**
	 * Finds all Route objects in database and returns them in
	 * @return all Insurance objects
	 */
	public static List<Route> listOfRoutes() {
		List<Route> allRoutes =  new ArrayList<Route>();
		allRoutes= find.all();
		return allRoutes;

	}
}
