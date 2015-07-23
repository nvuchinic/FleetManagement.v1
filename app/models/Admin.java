package models;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model.Finder;


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
}
