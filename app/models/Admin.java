package models;


import helpers.HashHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import play.Logger;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model.Finder;

/**
 * Admin model
 * @author Emir Imamović
 *
 */
@Entity
public class Admin extends SuperUser {
	
	public boolean isAdmin;
	public boolean isManager;
	
	@Required	
	@Pattern(value = "^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9]*$",
	message="Password not valid, only letters and numbers alowed."	)
	public String password;
	
	private static Finder<Long, Admin> find = new Finder<Long, Admin>(Long.class,
			Admin.class);

	public Admin(String name, String surname, String email, String password, String adress,
			String city, boolean isAdmin, boolean isManager) {
		super(name, surname, email, adress, city);
		this.password = password;
		this.isAdmin = isAdmin;
		this.isManager = isManager;
	}

	
	/**
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @param password
	 * @param adress
	 * @param city
	 * @param isAdmin
	 * @param isManager
	 * @return
	 */
	public static long createAdmin(String name, String surname, String email, String password, String adress, String city, boolean isAdmin, boolean isManager) {
		Admin newAdmin = new Admin(name, surname, email, password, adress, city, isAdmin, isManager);
		newAdmin.save();
		return newAdmin.id;
	}
	
	/**
	 * Method which return all Admins from DB
	 * @return all Admins
	 */
	public static List<Admin> all() {
		List<Admin> all = find.all();
		if(all == null)
			all = new ArrayList<Admin>();
		return all;
	}
	
	/**
	 * 
	 * @return all Admins as List<Admin>
	 */
	public static List<Admin> allList() {
		List<Admin> admins = find.findList();
		return admins;
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
			Admin user = find.where().eq("email", mail).findUnique();
			if (user != null) {
				return HashHelper.checkPass(password, user.password);
			} else {
				return false;
			}

		} catch (NullPointerException e) {
			Logger.error(e.getMessage());
			return false;
		}

	}
	
	/**
	 * Method which checks by email if the user exists in DB
	 * @param email of user
	 * @return true if exists, else return false
	 */
	public static boolean checkIfExists(String email) {
		return find.where().eq("email", email).findUnique() != null;
	}
	
	/**
	 * Method which find Admin by email in DB
	 * @param mail of Admin
	 * @return Admin
	 */
	public static Admin findByEmail(String mail) {
		Admin user = find.where().eq("email", mail).findUnique();

		return user;
	}
	
	/**
	 * Method which finds admin by name
	 * @param name of admin
	 * @return Admin
	 */
	public static Admin findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method which finds certain Admin  by id in DB
	 * @param id of Admin
	 * @return Admin
	 */ 
	public static Admin findById(long id) {
		return find.byId(id);
	}
}
