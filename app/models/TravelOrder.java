package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;
@Entity
public class TravelOrder extends Model {

	@Id
	public long id;
	
	@Required
	public long numberTO;
	
	@OneToOne
	public Driver driver;
	
	@OneToOne
	public Vehicle vehicle;
	
	@Required
	public String destination;
	
	@Required
	public String startDate;
	
	@Required
	public String returnDate;

	/**
	 * Constructor method
	 * @param numberTO
	 * @param destination
	 * @param startDate
	 * @param returnDate
	 * @param driver
	 * @param vehicle
	 */
	public TravelOrder(long numberTO,String destination, String startDate, String returnDate,Driver driver, Vehicle vehicle){
		this.numberTO=numberTO;
		this.destination=destination;
		this.startDate=startDate;
		this.returnDate=returnDate;
		this.driver=driver;
		this.vehicle=vehicle;
	}
	
	/**
	 * Method for saving TravelOrder object to database
	 * @param numberTO
	 * @param destination
	 * @param startDate
	 * @param returnDate
	 * @param driver
	 * @param vehicle
	 * @return
	 */
	public static TravelOrder saveTravelOrderToDB(long numberTO,String destination, String startDate, String returnDate, Driver driver, Vehicle vehicle) {
		TravelOrder to = new TravelOrder(numberTO, destination, startDate, returnDate,driver, vehicle);
		to.save();
		return to;		
	}
	
	/**
	 * Finder for TravelOrder object
	 */
	public static Finder<Long, TravelOrder> findTO = new Finder<Long, TravelOrder>(Long.class,
			TravelOrder.class);
	
	/**
	 * Method which finds TravelOrder object in DB by numberTO
	 * @param vid of vehicle
	 * @return TravelOrder object 
	 */
	public static TravelOrder findById(long numberTO) {
		return findTO.where().eq("numberTO", numberTO).findUnique();
	}
	
	/**
	 * Method for deleting TravelOrder object
	 * @param id of TravelOrder object
	 */
	public static void deleteTravelOrder(long id) {
		TravelOrder to= findTO.byId(id);
		to.delete();
	}

	public static List<TravelOrder> listOfTravelOrders() {
		List<TravelOrder> allTravelOrders =  new ArrayList<TravelOrder>();
		allTravelOrders = findTO.all();
		return allTravelOrders;
	}
	
}
