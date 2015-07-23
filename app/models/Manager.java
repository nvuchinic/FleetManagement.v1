package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model.Finder;

public class Manager extends SuperUser{
	
	public boolean isManager;
	public boolean isAdmin;
	
	@Required	
	@Pattern(value = "^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9]*$",
	message="Password not valid, only letters and numbers alowed."	)
	public String password;
	
	private static Finder<Long, Manager> find = new Finder<Long, Manager>(Long.class,
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
	
	
}
