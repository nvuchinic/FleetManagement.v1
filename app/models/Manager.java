package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model.Finder;

public class Manager extends SuperUser{
	
	public boolean isManager;
	public boolean isAdmin;
	
	private static Finder<Long, Manager> find = new Finder<Long, Manager>(Long.class,
			Manager.class);

	public Manager(String name, String surname, String email, String adress,
			String city) {
		super(name, surname, email, adress, city);
		// TODO Auto-generated constructor stub
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
	
	
}
