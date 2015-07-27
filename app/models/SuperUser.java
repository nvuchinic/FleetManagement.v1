package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@MappedSuperclass
public abstract class SuperUser extends Model {

	// Constants for status codes of employee.
	public static final int ACTIVE = 0;
	public static final int SICKLEAVE = 1;
	public static final int HOLIDAYS = 2;
	public static final int RETIRED = -1;
	public static final int DELETED = -2;

	@Id
	public long id;
	
	@Required
	@MinLength(4)
	@MaxLength(45)
	@Pattern(value = "^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$",
	message="Name not valid, only letters and numbers alowed."	)
	public String name;
	
	@Required
	@MinLength(4)
	@MaxLength(65)
	@Pattern(value = "^[A-Za-z\\u00A1-\\uFFFF0-9]*"
			+ "[A-Za-z\\u00A1-\\uFFFF0-9][A-Za-z\\u00A1-\\uFFFF0-9]*$",
			message="Surname not valid, only letters and numbers alowed."	)
	public String surname;
	
	
	@Email
	public String email;
	
	@Required
	@MinLength(6)
	@MaxLength(165)
	@Pattern(value = "^[A-Za-z\\u00A1-\\uFFFF0-9 .,]*"
			+ "[A-Za-z\\u00A1-\\uFFFF0-9][A-Za-z\\u00A1-\\uFFFF0-9 .,]*$",
			message="Adress not valid, only letters and numbers alowed."	)
	
	public String adress;
	
	
	@Required
	@MinLength(6)
	@MaxLength(165)
	@Pattern(value = "^[A-Za-z\\u00A1-\\uFFFF0-9 .,]*"
			+ "[A-Za-z\\u00A1-\\uFFFF0-9][A-Za-z\\u00A1-\\uFFFF0-9 .,]*$",
			message="City not valid, only letters and numbers alowed."	)
	
	public String city;
	
	
	public int status = ACTIVE;

	
	private static Finder<Long, SuperUser> find = new Finder<Long, SuperUser>(Long.class,
			SuperUser.class);
	
	
	/**
	 * Constructor for SuperUser
	 * @param email
	 * @param password
	 * @param adress
	 * @param city
	 */
	public SuperUser(String name, String surname, String email, String adress, String city) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.city = city;
	}
	
	/**
	 * Method which finds all super users in DB
	 * @return
	 */
	public static List<SuperUser> allSuperUsers() {
		List<Employee> allEmployees = Employee.all();
		List<Admin> allAdmins = Admin.all();
		List<Manager> allManagers = Manager.all();
		
		//In case user list or company list is null.
		if(allEmployees == null){
			allEmployees = new ArrayList<Employee>();
		}
		if(allAdmins == null){
			allAdmins = new ArrayList<Admin>();
		}
		if(allManagers == null){
			allManagers = new ArrayList<Manager>();
		}
		
		List<SuperUser> all = new ArrayList<SuperUser>();
		all.addAll(allEmployees);
		all.addAll(allAdmins);
		all.addAll(allManagers);
		return all;
	}
	
	/**
	 * Method which checks if the SuperUser is Admin
	 * @return
	 */
	public boolean isAdmin() {
		return (this instanceof Admin);
	}

	/**
	 * Method which checks if the SuperUser is Manager
	 * @return
	 */
	public boolean isManager() {
		return (this instanceof Manager);
	}
	
	/**
	 * Method which checks if the SuperUser is Employee
	 * @return
	 */
	public boolean isEmployee() {
		return (this instanceof Employee);
	}

	/**
	 * Method which returns Admin
	 * @return
	 */
	public Admin getAdmin() {
		return (Admin) this;
	}

	/**
	 * Method which returns Manager
	 * @return
	 */
	public Manager getManager() {
		return (Manager) this;
	}
	
	/**
	 * Method which returns Employee
	 * @return
	 */
	public Employee getEmployee() {
		return (Employee) this;
	}

	/**
	 * Method checks if user with this email already exists.
	 * @param email
	 * @return
	 */
	public static boolean checkIfExistsUser(String email) {
		Admin admin = (Admin) findByEmail(email);
		Manager manager = (Manager) findByEmail(email);
		Employee employee = (Employee) findByEmail(email);
		if (admin == null && manager == null && employee == null) {
			return true;
		} else
			return false;

	}

	/**
	 * Finds and returns a Admin or Manager or Employee by provided email
	 * @param email String
	 * @return SuperUser (admin or manager or employee)
	 */
	public static SuperUser getSuperUser(String email) {
		Admin admin = (Admin) findByEmail(email);
		Manager manager = (Manager) findByEmail(email);
		Employee employee = (Employee) findByEmail(email);
		if (admin != null) {
			return admin;
		}
		if (manager != null) {
			return manager;
		}
		else {
			return employee;
		}
	}
	
	
	/**
	 * Method which find SuperUser by email in DB
	 * @param mail of SuperUser
	 * @return SuperUser
	 */
	public static SuperUser findByEmail(String mail) {
		SuperUser user = find.where().eq("email", mail).findUnique();

		return user;
	}
	
	/**
	 * Method which deleting SuperUser from DB
	 * @param id of SuperUser
	 */
	public static void delete(long id) {
		SuperUser user = find.byId(id);
		user.status = DELETED;
		user.save();
	}

	/**
	 * Method which finds certain SuperUser  by id in DB
	 * @param id of SuperUser
	 * @return SuperUser
	 */ 
	public static SuperUser findById(long id) {
		return find.byId(id);
	}
	
	
	
	/**
	 * Method which finds user by surname
	 * @param surname of user
	 * @return SuperUser
	 */
	public static SuperUser findBySurame(String surname) {
		return find.where().eq("surname", surname).findUnique();
	}
	
	/**
	 * Method which finds user by adress
	 * @param adress of user
	 * @return SuperUser
	 */
	public static SuperUser findByAdress(String adress) {
		return find.where().eq("adress", adress).findUnique();
	}
	
	/**
	 * Method which finds user by city
	 * @param city of user
	 * @return SuperUser
	 */
	public static SuperUser findByCity(String city) {
		return find.where().eq("city", city).findUnique();
	}
	

	/**
	 * Method which checks by email if the user exists in DB
	 * @param email of user
	 * @return true if exists, else return false
	 */
	public static boolean checkIfExists(String email) {
		return find.where().eq("email", email).findUnique() != null;
	}
}
