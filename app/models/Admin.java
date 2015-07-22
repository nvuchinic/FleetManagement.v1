package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model.Finder;

public class Admin extends SuperUser {
	
	public boolean isAdmin;
	public boolean isManager;
	
	private static Finder<Long, Admin> find = new Finder<Long, Admin>(Long.class,
			Admin.class);

	public Admin(String name, String surname, String email, String adress,
			String city, boolean isAdmin, boolean isManager) {
		super(name, surname, email, adress, city);
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
