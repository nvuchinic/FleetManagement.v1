package models;

import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.sql.Date;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;
@Entity
public class TravelOrder extends Model {

	@Id
	public long id;
	
	@Required
	public String numberTO;
	
	@OneToOne
	public Driver driver;
	
	public String driverName;
	
	@OneToOne
	public Vehicle vehicle;
	
	public String vehicleName;
	
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
	public TravelOrder(String numberTO,String destination, Driver driver, Vehicle vehicle,Date startDate, Date returnDate){
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
	public static TravelOrder saveTravelOrderToDB(String numberTO,String destination, Driver driver, Vehicle vehicle,Date startDate, Date returnDate) {
		TravelOrder to = new TravelOrder(numberTO, destination, driver, vehicle,startDate, returnDate);
		to.save();
		return to;		
	}
	
	/**
	 * Finder for TravelOrder object
	 */
	public static Finder<Long, TravelOrder> findTO = new Finder<>(TravelOrder.class);
	
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
	
}
