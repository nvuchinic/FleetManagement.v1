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

	public String profilePicture = Play.application().configuration()
			.getString("defaultProfilePicture");
	
	private static Finder<Long, Employee> find = new Finder<Long, Employee>(Long.class,
			Employee.class);
	
	
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
			String profilePicture) {
		super(name, surname, email, adress, city);
		this.dob = dob;
		this.gender = gender;
		this.created = new Date();
		this.profilePicture = profilePicture;
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
	public static long createEmployee(String name, String surname, String email, String adress, String city, Date dob, String gender, String profilePicture) {
		Employee newEmployee = new Employee(name, surname, email, adress, city, dob, gender, dob, profilePicture);
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
	public static int getEmployeeStatus(long id) {
		return findById(id).status;
	}
	
	/**
	 * Method which sets status for employee
	 * @param id of employee
	 * @param status - new status of employee
	 */
	public static void setEmployeeStatus(long id, int status) {
		 findById(id).status = status;
	}
	
	/**
	 * Method which finds Employee by status:ACTIVE
	 * @param status of Employee
	 * @return Employee
	 */
	public static SuperUser findByActiveStatus() {
		return find.where().eq("status", SuperUser.ACTIVE).findUnique();
	}
	
}
