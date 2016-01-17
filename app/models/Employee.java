package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
public class Employee extends Model {

	@Id
	public long id;
	
	@Required
	public String firstName;
	
	@Required
	public String lastName;
	
	public String fullName;
	
	public Date dob;
	
	public String address;
	
	public String phone;
	
	@Email
	public String email;
	
	public boolean isDriver;
	
	public boolean isEngaged;
	
	@OneToMany(mappedBy = "reportingEmployee", cascade = CascadeType.ALL)
	public List<Issue> reportedIssues;

	@OneToMany(mappedBy = "assignedEmployee", cascade = CascadeType.ALL)
	public List<Issue> assignedIssues;
	
	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
	public List<FuelBill> fuelBills;
	
	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
	public List<TravelOrder> travelOrders;
	
	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
	public List<WorkOrder> workOrders;
	
//	// Constants for status codes of employee.
//	public static final String ACTIVE = "Active";
//	public static final String SICKLEAVE = "Sickleave";
//	public static final String HOLIDAYS = "Holidays";
//	public static final String RETIRED = "Retired";
//	public static final String DELETED = "Deleted";

	
	/**
	 * constructor method
	 * @param firstName
	 * @param lastName
	 * @param dob
	 * @param adress
	 * @param phone
	 * @param email
	 * @param isDriver
	 */
	public Employee(String firstName, String lastName, Date dob, String address,
			String phone, String email) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.dob=dob;
		this.address=address;
		this.phone=phone;
		this.email=email;
		isDriver=false;
		isEngaged=false;
		this.fullName=firstName+" "+lastName;
		this.reportedIssues=new ArrayList<Issue>();
		this.assignedIssues=new ArrayList<Issue>();
	}

	/**
	 * creates Employee object using passed parameters,
	 * and then saves it to database
	 * @param firstName
	 * @param lastName
	 * @param dob
	 * @param address
	 * @param phone
	 * @param email
	 * @return
	 */
	public static Employee savetoDB(String firstName, String lastName, Date dob, String address,
			String phone, String email) {
		Employee newEmployee = new Employee(firstName, lastName, dob, address, phone, email);
		newEmployee.save();
		return newEmployee;
	}
	
	/**
	 * Finder for Employee object
	 */
	public static Finder<Long, Employee> find = new Finder<Long, Employee>(
			Employee.class);

	
	/**
	 * Finds Employee object by it's ID number passed as argument,
	 * then removes it from database
	 * @param id
	 */
	public static void deleteEmployee(long id) {
		Employee emp = find.byId(id);
		emp.delete();
	}

	/**
	 * Returns all Employees from database as list
	  @return List of all Employee objects
	 */
	public static List<Employee> all() {
		List<Employee> allEmployees = find.all();
		if (allEmployees == null)
			allEmployees = new ArrayList<Employee>();
		return allEmployees;
	}

	
	/**
	 * Finds Employee by it's dob property passed as parameter
	 * @param dob  - date of birth of Employee
	 * @return Employee
	 */
	public static Employee findByDob(Date dob) {
		return find.where().eq("dob", dob).findUnique();
	}

	/**
	 * Finds Employee object by it's full name property
	 * passed as argument
	 * @param fullName
	 * @return Employee object
	 */
	public static Employee findByName(String fullName) {
		return find.where().eq("fullName", fullName).findUnique();
	}	
	
	/**
	 * Finds  Employee object by it's ID number 
	 * passed as argument
	 * @param id-ID number of Employee
	 * @return Employee
	 */
	public static Employee findById(long id) {
		return find.byId(id);
	}

	
	public static List<Employee> getDrivers(){
		List<Employee> drivers=new ArrayList<Employee>();
		for(Employee e:Employee.find.all()){
			if(e.isDriver==true){
				drivers.add(e);
			}
		}
		return drivers;
	}
	
	

}
