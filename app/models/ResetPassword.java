package models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.Logger;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;

/**
 * Reset-password model
 * @author Emir Imamović
 *
 */
@Entity
public class ResetPassword extends Model {
	
	@Id
	public String id;
	
	@Required
	public String userEmail;

	public Date date;
	
	//public static Finder<String, ResetPassword> find = new Finder<String, ResetPassword>(String.class, ResetPassword.class);
	public static Finder<String, ResetPassword> find = new Finder<>(ResetPassword.class);

	/**
	 * Constructor for ResetPassword
	 * @param userEmail
	 */
	public ResetPassword(String userEmail){
		this.id = UUID.randomUUID().toString();
		this.userEmail = userEmail;
		this.date = new Date();
	}
	
	/**
	 * Method for creating request for reseting password
	 * @param userEmail 
	 * @return id
	 */
	public static String createRequest(String userEmail){
		ResetPassword ressetPassword = new ResetPassword(userEmail);
		ressetPassword.save();
		return ressetPassword.id;		
	}
	
	/**
	 * Method which finds email by id
	 * @param id of email
	 * @return email
	 */
	public static String findByID(String id){
		Logger.debug(id);
		String email = "";
		try{
			email = find.byId(id).userEmail;
		} catch(Exception e){
			return null;
		}
		return email;
	}
}
