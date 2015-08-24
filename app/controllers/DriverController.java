package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;

import com.avaje.ebean.Model.Finder;

import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


/**
 * Controller for Driver model
 * @author Emir Imamović
 *
 */
public class DriverController extends Controller {

	static Form<Driver> driverForm = Form.form(Driver.class);

	/**
	 * Finder for Driver class
	 */
	//public static Finder<Long, Driver> find = new Finder<Long, Driver>(Long.class,
		//	Driver.class);
	public static Finder<Long, Driver> find = new Finder<>(Driver.class);
	
	/**
	 * Renders the 'add driver' page
	 * 
	 * @return
	 */
	public Result addDriverView() {
		return ok(addDriverForm.render());
	}

	/**
	 * Finds driver using id and shows it
	 * 
	 * @param id
	 *            - Driver id
	 * @return redirect to the driver profile
	 */
	public Result showDriver(long id) {
		Driver d = Driver.findById(id);
		if (d == null) {
			Logger.error("error", "Driver null at showDriver()");
			flash("error", "Something went wrong!");
			return redirect("/");
		}
		return ok(showDriver.render(d));
	}

	/**
	 * Delete Driver using id
	 * 
	 * @param id
	 *            - Driver id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteDriver(long id) {
		try {
			Driver d = Driver.findById(id);
			Logger.info("Deleted driver: \"" + d.driverName + "\"");
			Driver.deleteDriver(id);
			return redirect("/");
		} catch (Exception e) {
			flash("error", "Error at delete driver!");
			Logger.error("Error at delete Driver: " + e.getMessage());
			return redirect("/");
		}
	}

	/**
	 * Renders the view of a Driver. Method receives the id of the driver and
	 * finds the driver by id and send's the driver to the view.
	 * 
	 * @param id
	 *            long
	 * @return Result render DriverView
	 */
	public Result editDriverView(long id) {
		Driver d = Driver.findById(id);
		// Exception handling.
		if (d == null) {
			flash("error", "Driver is not exists");
			return redirect("/");
		}
		Form<Driver> form = Form.form(Driver.class).fill(d);
		return ok(editDriverView.render(d));

	}

	/**
	 * Edit Driver Method receives an id, finds the specific driver and renders
	 * the edit View for the driver. If any error occurs, the view is rendered
	 * again.
	 * 
	 * @param id
	 *            of driver
	 * @return Result render the driver edit view
	 */
	public Result editDriver(long id) {
		//DynamicForm updateDriverForm = Form.form().bindFromRequest();
		//Form<Driver> form = Form.form(Driver.class).bindFromRequest();
		Driver d = Driver.findById(id);
		try {
//			if (form.hasErrors() || form.hasGlobalErrors()) {
//				Logger.info("Driver update error");
//				flash("error", "Error in driver form");
//				return ok(editDriverView.render(d));
//			}

			d.firstName = driverForm.bindFromRequest().field("name").value();

			if (d.firstName.length() > 20) {
				Logger.info(session("name")
						+ "entered a too long driver name in driver update");
				flash("error", "Driver has too long name");
				return ok(editDriverView.render(d));
			}

			d.lastName = driverForm.bindFromRequest().field("surname").value();

			if (d.lastName.length() > 20) {
				Logger.info(session("surname")
						+ "entered a too long driver surname in driver update");
				flash("error", "Driver has too long  surname");
				return ok(editDriverView.render(d));
			}
			
			d.adress = driverForm.bindFromRequest().field("adress").value();
			d.description = driverForm.bindFromRequest().field("description").value();
			d.dob = driverForm.bindFromRequest().get().dob;
			//d.gender = driverForm.bindFromRequest().field("gender").value();
			d.phoneNumber = driverForm.bindFromRequest().field("phoneNumber").value();	
			//String licenseNo = driverForm.bindFromRequest().field("licenseNo").value();
			d.save();
			
			Logger.info(session("name") + " updated driver: " + d.id);
			flash("success", d.driverName + " successfully updated!");
			return ok(editDriverView.render(d));
		} catch (Exception e) {
			flash("error", "Error at editing driver");
			Logger.error("Error at updateDriver: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
	/**
	 * First checks if the driver form has errors. Creates a new driver or
	 * renders the view again if any error occurs.
	 * @return redirect to create driver view
	 * @throws ParseException
	 */
	public Result addDriver() {
		DynamicForm dynamicDriverForm = Form.form().bindFromRequest();
		Form<Driver> addDriverForm = Form.form(Driver.class).bindFromRequest();
		
		java.util.Date utilDate = new java.util.Date();
		Date dob;
		String stringDate;
		try{	
			
			String name = addDriverForm.bindFromRequest().get().firstName;
			String description = addDriverForm.bindFromRequest().get().description;
			String surname = addDriverForm.bindFromRequest().get().lastName;
			String adress = addDriverForm.bindFromRequest().get().adress;
			//String gender = addDriverForm.bindFromRequest().get().gender;
			String phoneNumber = addDriverForm.bindFromRequest().get().phoneNumber;
			String licenseNo = addDriverForm.bindFromRequest().field("licenseNo").value();
			stringDate  = dynamicDriverForm.get("dateB");
			 SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
			 utilDate = format.parse( stringDate );
			   //utilDate = java.text.DateFormat.getDateInstance().parse(stringDate);
				dob = new java.sql.Date(utilDate.getTime());
				Driver d= Driver.saveToDb(name, surname, phoneNumber, adress, description,dob);
				//Driver d = Driver.findById(id);
				//d.truck = t;
				//d.save();
				Logger.info(session("name") + " created driver ");
				flash("success",  " successfully added!");
				return redirect("/allDrivers");
			
		}catch(Exception e){
		flash("error", "Error at adding driverafasdfasdffsadfasdf");
		Logger.error("Error at addDriver: " + e.getMessage(), e);
		return redirect("/addDriver");
	   }
	}
	
	public Result listDrivers() {
		if(find.all().isEmpty())
			return ok(listAllDrivers.render(new ArrayList<Driver>()));
		return ok(listAllDrivers.render(find.all()));
	}
	
	
}
