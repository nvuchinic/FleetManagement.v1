package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class WorkOrder extends Model {
	@Id
	public long id;

	@Required
	public long woNumber;

	public Date woDate;

	@OneToOne
	public Driver driver;

	public String driverName;

	@OneToOne
	public Vehicle vehicle;

	public String vehicleName;

	@Required
	public String description;
	
	//@Required
	public String statusWo;

	public static long commonId=1;
	
	@OneToMany(mappedBy="workOrder")
	public List<Task> tasks; 
	
	public static long getNewId(){
		return WorkOrder.commonId+1;
	}
	/**
	 * Constructor method for creating WorkOrder object
	 * 
	 * @param woNumber
	 * @param woDate
	 * @param driver
	 * @param vehicle
	 * @param description
	 * @param status
	 */
	public WorkOrder(Date woDate, Driver driver,
			Vehicle vehicle, String description, String statusWo, List<Task> tasks) {
		this.woNumber = WorkOrder.commonId;
		WorkOrder.commonId+=1;
		this.woDate = woDate;
		this.driver = driver;
		this.vehicle = vehicle;
		this.description = description;
		this.statusWo = statusWo;
		this.tasks = tasks;
	}
	
	/**
	 * Default constructor
	 */
	public WorkOrder() {
		
	}

	/**
	 * Method for saving WorkOrder object to database
	 * 
	 * @param woNumber
	 * @param woDate
	 * @param driver
	 * @param vehicle
	 * @param description
	 * @param status
	 * @return
	 */
	public static WorkOrder saveToDB(Date woDate, Driver driver,
			Vehicle vehicle, String description, String statusWo, List<Task> tasks) {
		WorkOrder wo = new WorkOrder(woDate, driver, vehicle,
				description, statusWo, tasks);
		wo.save();
		return wo;
	}
	
	public static WorkOrder createWO() {
		WorkOrder wo = new WorkOrder();
		wo.save();
		return wo;
	}

	/**
	 * Finder for WorkOrder object
	 */
	public static Finder<Long, WorkOrder> find = new Finder<Long, WorkOrder>(WorkOrder.class);

	/**
	 * finds WorkOrderOrder object in database based on passed ID number
	 * 
	 * @param id id of WorkOrder object
	 * @return TravelOrder object
	 */
	public static WorkOrder findById(long id) {
		return find.byId(id);
	}

	/**
	 * Method for deleting WorkOrder object
	 * 
	 * @param id id number of WorkOrder object
	 *         
	 */
	public static void deleteWorkOrder(long id) {
		WorkOrder wo = find.byId(id);
		wo.delete();
	}

	/**
	 * finds all WorkOrder objects in database
	 * 
	 * @return all WorkOrder objects  found in database
	 */
	public static List<WorkOrder> listOfWorkOrders() {
		List<WorkOrder> allWorkOrders = new ArrayList<WorkOrder>();
		allWorkOrders = find.all();
		return allWorkOrders;
	}

}
