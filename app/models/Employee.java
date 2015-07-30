package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import play.Play;

@Entity
public class Employee extends SuperUser {

	@Past
	public Date dob;

	@NotNull
	public String gender;

	public Date created;

	public Date updated;
	
	@NotNull
	public String status;

	public String profilePicture;
	
	public static Finder<Long, Employee> find = new Finder<Long, Employee>(Long.class,
			Employee.class);
	
	
	// Constants for status codes of employee.
	public static final String ACTIVE = "Active";
	public static final String SICKLEAVE = "Sickleave";
	public static final String HOLIDAYS = "Holidays";
	public static final String RETIRED = "Retired";
	public static final String DELETED = "Deleted";
	
	
	/**
	 * @param name
	 * @param surname
	 * @param email
	 * @param adress
	 * @param city
	 * @param dob
	 * @param gender
	 * @param created
	 * @param updated
	 * @param profilePicture
	 */
	public Employee(String name, String surname, String email, String adress,
			String city, Date dob, String gender, Date created,
			String profilePicture, String status) {
		super(name, surname, email, adress, city);
		this.dob = dob;
		this.gender = gender;
		this.created = new Date();
		this.profilePicture = profilePicture;
		this.status = status;
	}

	

	/**
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @param adress
	 * @param city
	 * @param dob
	 * @param gender
	 * @param profilePicture
	 * @return id of the new Employee
	 */
	public static long createEmployee(String name, String surname, String email, String adress, String city, Date dob, String gender, String profilePicture, String status) {
		Employee newEmployee = new Employee(name, surname, email, adress, city, dob, gender, dob, profilePicture, status);
		newEmployee.save();
		return newEmployee.id;
	}

	
	/**
	 * Method which return all Employees from DB
	 * @return all Employees
	 */
	public static List<Employee> all() {
		List<Employee> all = find.all();
		if(all == null)
			all = new ArrayList<Employee>();
		return all;
	}
	
	/**
	 * 
	 * @return all Employees as List<Employee>
	 */
	public static List<Employee> allList() {
		List<Employee> employees = find.findList();
		return employees;
	}
	
	/**
	 * Method which finds Employee by dob
	 * @param dob of Employee
	 * @return Employee
	 */
	public static SuperUser findByDob(Date dob) {
		return find.where().eq("dob", dob).findUnique();
	}

	/**
	 * Method which finds Employee by gender
	 * @param gender of Employee
	 * @return Employee
	 */
	public static SuperUser findByGender(String gender) {
		return find.where().eq("gender", gender).findUnique();
	}
	
	/**
	 * Method which returns status of employee
	 * @param id of employee
	 * @return status
	 */
	public static String getEmployeeStatus(long id) {
		return findById(id).status;
	}
	
	/**
	 * Method which sets status for employee
	 * @param id of employee
	 * @param status - new status of employee
	 */
	public static void setEmployeeStatus(long id, String status) {
		 findById(id).status = status;
	}
	
	/**
	 * Method which finds Employee by status:ACTIVE
	 * @param status of Employee
	 * @return Employee
	 */
	public static SuperUser findByStatus(String status) {
		if(find.where().eq("status", status).findUnique() == null)
			return null;
		return find.where().eq("status", status).findUnique();
	}
	
	/**
	 * Method which finds certain Employee  by id in DB
	 * @param id of Employee
	 * @return Employee
	 */ 
	public static Employee findById(long id) {
		return find.byId(id);
	}
	
	/**
	 * Method which find Employee by email in DB
	 * @param mail of Employee
	 * @return Employee
	 */
	public static Employee findByEmail(String mail) {
		Employee user = find.where().eq("email", mail).findUnique();

		return user;
	}
	
	public static List<Employee> active() {
		return find.where().eq("status", Employee.ACTIVE).findList();
	}
	
}
