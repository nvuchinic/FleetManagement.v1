package models;

import java.util.ArrayList;

import java.sql.Date;
import java.util.List;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
public class TravelOrder extends Model {

	@Id
	public long id;
	
	@Required
	public long numberTO;
	
	@Required
	public String name;
	
	@Required
	public String reason;
	
	public Date date;
	
	@OneToOne
	public Driver driver;
	
	@OneToOne
	public Vehicle vehicle;
	
	@Required
	public String destination;
	
	public Date startDate;
	
	public Date returnDate;

	/**
	 * Constructor method
	 * @param numberTO
	 * @param destination
	 * @param startDate
	 * @param returnDate
	 * @param driver
	 * @param vehicle
	 */

	public TravelOrder(long numberTO, String name, String reason, String destination, Date startDate, Date returnDate,Driver driver, Vehicle vehicle){

		this.numberTO=numberTO;
		this.name = name;
		this.date = new java.sql.Date(0, 0, 0);
		this.reason = reason;
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

	public static TravelOrder saveTravelOrderToDB(long numberTO,String name, String reason, String destination, Date startDate, Date returnDate, Driver driver, Vehicle vehicle) {
		TravelOrder to = new TravelOrder(numberTO, name, reason, destination, startDate, returnDate,driver, vehicle);
		to.save();
		return to;		
	}
	
	/**
	 * Finder for TravelOrder object
	 */
	public static Finder<Long, TravelOrder> findTO = new Finder<Long, TravelOrder>(TravelOrder.class);
	
	/**
	 * Method which finds TravelOrder object in DB by numberTO
	 * @param vid of vehicle
	 * @return TravelOrder object 
	 */
	public static TravelOrder findById(long id) {
		return findTO.byId(id);
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
	
	public static long numberTo() {
		List<TravelOrder> tos = findTO.all();
		long numberTo = tos.get(tos.size() - 1).numberTO;
		return ++numberTo;
	}
	
	public static TravelOrder findByNumberTo(long numTo) {
		return findTO.where().eq("numberTO", numTo).findUnique();
	}
}
