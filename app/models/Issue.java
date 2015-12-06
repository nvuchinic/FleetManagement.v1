package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

import play.data.validation.Constraints.Required;

@Entity
public class Issue extends Model{

	@Id
	public long id;
	
	@ManyToOne
	public Vehicle vehicle;
	
	//@Required
	public Date issueDate;

	@Required
	public String summary;

	public String description;
	
	public long odometer;
	
	public String status;

	@ManyToOne
	public Employee reportingEmployee;
	
	@ManyToOne
	public Employee assignedEmployee;

	public Date resolveDate;
	

	private static final String OPEN = "OPEN";
	private static final String OVERDUE = "OVERDUE";
	private static final String RESOLVED = "RESOLVED";
	private static final String CLOSED = "CLOSED";
	
	/**
	 * constructor method
	 * @param vehicle
	 * @param date
	 * @param summary
	 * @param description
	 * @param odometer
	 * @param repEmployee
	 * @param assignedEmployee
	 */
	public Issue(Vehicle vehicle, Date date,String summary, String description, long odometer, Employee repEmployee, Employee assignedEmployee){
		this.vehicle=vehicle;
		this.issueDate=date;
		this.summary=summary;
		this.description=description;
		this.odometer=odometer;
		this.reportingEmployee=repEmployee;
		this.assignedEmployee=assignedEmployee;
		this.status=OPEN;
	}

	/**
	 * Creates new Issue object and then saves it to database
	 * @param vehicle
	 * @param date
	 * @param summary
	 * @param description
	 * @param odometer
	 * @param repEmployee
	 * @param assignEmployee
	 * @return
	 */
	public static Issue saveToDB(Vehicle vehicle, Date date,String summary, String description, long odometer, Employee repEmployee, Employee assignEmployee) {
		Issue i = new Issue(vehicle, date, summary, description, odometer, repEmployee, assignEmployee);
		i.save();
		return i;
	}

	/**
	 * finder for Issue object
	 */
	public static Finder<Long, Issue> find = new Finder<Long, Issue>(
			Issue.class);

	/**
	 * finds Issue object in database using it's ID number passed as argument
	 * @param id-ID number of Issue object
	 * @return Issue object
	 */
	public static Issue findById(long id) {
		return find.byId(id);
	}

	/**
	 * Finds Issue object in database by it's summary passed as argument
	 * @param summary-summary of the Issue object
	 * @return
	 */
	public static Issue findBySummary(String summary) {
		return find.where().eq("summary", summary).findUnique();
	}

	/**
	 * First finds Issue object by it's ID number passed as argument,
	 *  then removes it from database
	 * @param id-ID number of the Issue object
	 */
	public static void deleteIssue(long id) {
		Issue i = find.byId(id);
		i.delete();
	}

	/**
	 * Finds all Issue objects in database and returns them as list
	 * @return all Issue objects as list
	 */
	public static List<Issue> listOfIssues() {
		List<Issue> allIssues = new ArrayList<Issue>();
		allIssues = find.all();
		return allIssues;
	}
	
	/**
	 * Finds all Issue objects in database with status "open" 
	 *  and returns them as list
	 * @return all Issue objects with status "open" as list
	 */
	public static List<Issue> openIssues() {
		List<Issue> allIssues = new ArrayList<Issue>();
		allIssues = find.all();
		List<Issue> openIssues=new ArrayList<Issue>();
		for(Issue i:allIssues){
			if(i.status.equalsIgnoreCase(OPEN)){
				openIssues.add(i);
			}
		}
		return openIssues;
	}

	/**
	 * Finds all Issue objects in database with status "closed" 
	 *  and returns them as list
	 * @return all Issue objects with status "closed" as list
	 */
	public static List<Issue> closedIssues() {
		List<Issue> allIssues = new ArrayList<Issue>();
		allIssues = find.all();
		List<Issue> closedIssues=new ArrayList<Issue>();
		for(Issue i:allIssues){
			if(i.status.equalsIgnoreCase(CLOSED)){
				closedIssues.add(i);
			}
		}
		return closedIssues;
	}
	
	/**
	 * Finds all Issue objects in database with status "overdue" 
	 *  and returns them as list
	 * @return all Issue objects with status "overdue" as list
	 */
	public static List<Issue> overdueIssues() {
		List<Issue> allIssues = new ArrayList<Issue>();
		allIssues = find.all();
		List<Issue> overdueIssues=new ArrayList<Issue>();
		for(Issue i:allIssues){
			if(i.status.equalsIgnoreCase(OVERDUE)){
				overdueIssues.add(i);
			}
		}
		return overdueIssues;
	}
	
	/**
	 * Finds all Issue objects in database with status "resolved" 
	 *  and returns them as list
	 * @return all Issue objects with status "resolved" as list
	 */
	public static List<Issue> resolvedIssues() {
		List<Issue> allIssues = new ArrayList<Issue>();
		allIssues = find.all();
		List<Issue> resolvedIssues=new ArrayList<Issue>();
		for(Issue i:allIssues){
			if(i.status.equalsIgnoreCase(RESOLVED)){
				resolvedIssues.add(i);
			}
		}
		return resolvedIssues;
	}
	
	public static int unresolvedIssuesNo(Vehicle v){
		List<Issue> unresolvedIssues=new ArrayList<Issue>();
		for(Issue i:v.issues){
			if(OPEN.equalsIgnoreCase(i.status) || OVERDUE.equalsIgnoreCase(i.status)){
				unresolvedIssues.add(i);
			}
		}
		return unresolvedIssues.size();
	}
	
	public static List<Issue> unresolvedIssues(Vehicle v){
		List<Issue> unresolvedIssues=new ArrayList<Issue>();
		for(Issue i:v.issues){
			if(OPEN.equalsIgnoreCase(i.status) || OVERDUE.equalsIgnoreCase(i.status)){
				unresolvedIssues.add(i);
			}
		}
		return unresolvedIssues;
	}
}
