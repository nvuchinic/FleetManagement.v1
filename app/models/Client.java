package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Client extends Model {

	@Id
	public long id;

	public String cName;

	public String cType;

	public String address;

	public String phone;

	public String email;

	@OneToMany
	public List<WorkOrder> wOrders;

	/**
	 * constructor method
	 * 
	 * @param cName
	 * @param cType
	 * @param address
	 * @param phone
	 * @param email
	 */
	public Client(String cName, String cType, String address, String phone,
			String email) {
		this.cName = cName;
		this.cType = cType;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.wOrders = new ArrayList<WorkOrder>();
	}

	/**
	 * creates and saves Client object to database
	 * 
	 * @param cName
	 * @param cType
	 * @param address
	 * @param phone
	 * @param email
	 * @return
	 */
	public static Client saveToDB(String cName, String cType, String address,
			String phone, String email) {
		Client c = new Client(cName, cType, address, phone, email);
		c.save();
		return c;
	}

	/**
	 * finder for Client object
	 */
	public static Finder<Long, Client> find = new Finder<Long, Client>(
			Client.class);

	/**
	 * finds Client object in DB based on passed ID
	 * 
	 * @param id
	 *            of Client object
	 * @return Route object
	 */
	public static Client findById(long id) {
		return find.byId(id);
	}

	/**
	 * finds Client object using string passed as parameter
	 * 
	 * @param name
	 *            name of Client object
	 * @return
	 */
	public static Client findByName(String name) {
		return find.where().eq("cName", name).findUnique();
	}

	/**
	 * finds Client object using passed ID number as parameter, then removes it
	 * from database
	 * 
	 * @param id
	 *            of Client object
	 */
	public static void deleteClient(long id) {
		Client c = find.byId(id);
		c.delete();
	}

	/**
	 * Finds all Client objects in database and returns them as List
	 * 
	 * @return all Client objects
	 */
	public static List<Client> listOfClients() {
		List<Client> allClients = new ArrayList<Client>();
		allClients = find.all();
		return allClients;
	}

	public static List<Client> findAllByType(String clType) {
		List<Client> allClients = new ArrayList<Client>();
		allClients = find.all();
		List<Client> allByType = new ArrayList<Client>();
		for (Client client : allClients) {
			if (client.cType.equals(clType)) {
				allByType.add(client);
			}
		}
		return allByType;

	}

}
