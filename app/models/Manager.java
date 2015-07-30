package models;

import helpers.HashHelper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import play.Logger;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model.Finder;

@Entity
public class Manager extends SuperUser{
	
	public boolean isManager;
	public boolean isAdmin;
	
	@Required	
	@Pattern(value = "^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9]*$",
	message="Password not valid, only letters and numbers alowed."	)
	public String password;
	
	public static Finder<Long, Manager> find = new Finder<Long, Manager>(Long.class,
			Manager.class);

	public Manager(String name, String surname, String email, String password, String adress,
			String city, boolean isManager, boolean isAdmin) {
		super(name, surname, email, adress, city);
		this.password = password;
		this.isManager = isManager;
		this.isAdmin = isAdmin;
	}

	
	/**
	 * Method which return all Managers from DB
	 * @return all Managers
	 */
	public static List<Manager> all() {
		List<Manager> all = find.all();
		if(all == null)
			all = new ArrayList<Manager>();
		return all;
	}
	
	/**
	 * 
	 * @return all Managers as List<Manager>
	 */
	public static List<Manager> allList() {
		List<Manager> managers = find.findList();
		return managers;
	}
	
	/**
	 * Login verification Verifies if the email and password exists by checking
	 * in the database
	 * 
	 * @param mail
	 *            String
	 * @param password
	 *            String
	 * @return boolean true or false
	 */
	public static boolean verifyLogin(String mail, String password) {
		try {
			Manager manager = find.where().eq("email", mail).findUnique();
			if(manager != null &&  manager.isManager == true){
				return HashHelper.checkPass(password, manager.password);
			}
			else{
				return false;
			}			
		} catch (NullPointerException e) {
			Logger.error(e.getMessage());
			return false;
		}
	}


	/**
	 * Method which finds manager by name
	 * @param name of manager
	 * @return Manager
	 */
	public static Manager findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method which find Manager by email in DB
	 * @param mail of Manager
	 * @return Manager
	 */
	public static Manager findByEmail(String mail) {
			Manager user = find.where().eq("email", mail).findUnique();

		return user;
	}
	
}
