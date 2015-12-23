package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import models.*;
import helpers.*;
import com.avaje.ebean.Model.Finder;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class VRegistrationController extends Controller {

	/**
	 * Form for creating/editing VehicleRegistration object
	 */
	static Form<VehicleRegistration> vehicleRegistrationForm = Form
			.form(VehicleRegistration.class);

	/**
	 * Finder for VehicleRegistration object
	 */
	public static Finder<Long, VehicleRegistration> find = new Finder<Long, VehicleRegistration>(
			VehicleRegistration.class);

	public Result listUnregisteredVehicles() {
		List<Vehicle> allVehicles = Vehicle.find.all();
		List<Vehicle> unregVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.isRegistered == false) {
				unregVehicles.add(v);
			}
		}
		if (unregVehicles.size() == 0) {
			flash("NoUnregisteredVehicles", "All vehicles are registered");
			return redirect("/allVehicles");
		}
		return ok(listUnregisteredVehiclesForm.render(unregVehicles));
	}

	/**
	 * Renders the form view for creating VehicleRegistration object
	 * 
	 * @return
	 */
	public Result addVRegistrationView(long id) {
		Vehicle v = Vehicle.findById(id);
		if (v.isRegistered == true) {
			flash("VehicleRegistered", "Vehicle is already registered!");
			return redirect("/showVehicle/" + v.id);
		}
		return ok(addVRegistrationForm.render(v));
	}

	
	/**
	 * Creates new Vehicle Registration object using values from request
	 * (collected through form)
	 * @return
	 * @throws ParseException
	 */
	public Result addVRegistration(long id) {
		if(Vehicle.findById(id)==null){
			flash("error", "CANNOT CREATE REGISTRATION, VEHICLE IS NULL!");
			return redirect("/");
		}
		Vehicle v = Vehicle.findById(id);
		DynamicForm dynamicVRegistrationForm = Form.form().bindFromRequest();
		Form<VehicleRegistration> addVRegistrationForm = Form.form(
				VehicleRegistration.class).bindFromRequest();
						/*
		 * if (addTravelOrderForm.hasErrors() ||
		 * addTravelOrderForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Travel Order"); flash("error",
		 * "Error at Travel Order form!"); return redirect("/addTravelOrder"); }
		 */
		java.util.Date javaRegDate = new java.util.Date();
		java.util.Date javaExpDate = new java.util.Date();
		String stringRegDate;
		String stringExpDate;
		String regNo;
		Date sqlRegDate;
		Date sqlExpDate = null;
		try {
			regNo = addVRegistrationForm.bindFromRequest().get().regNo;
			if(regNo.isEmpty() || regNo==null){
				flash("error", "YOU MUST PROVIDE REGISTRATION NUMBER!");
				return ok("/addvregistrationview/"+v.id);
			}
//			String email = dynamicVRegistrationForm.get("email");
//			Owner o = null;
//			if (Owner.findByEmail(email) == null) {
//				flash("error", "Owner with that email does not exist!");
//				return redirect("/addvregistrationview/" + id);
//			} else {
//				o = Owner.findByEmail(email);
//			}
			//String certificateNo = addVRegistrationForm.get().certificateNo;
		//	String trailerLoadingLimit = addVRegistrationForm.get().trailerLoadingLimit;
			String city = addVRegistrationForm.get().city;
			stringRegDate = dynamicVRegistrationForm.get("regDate");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			javaRegDate = format.parse(stringRegDate);
			stringExpDate = dynamicVRegistrationForm.get("expDate");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			javaExpDate = format2.parse(stringExpDate);
			sqlExpDate = new java.sql.Date(javaExpDate.getTime());
			sqlRegDate = new java.sql.Date(javaRegDate.getTime());
			VehicleRegistration vr = VehicleRegistration.saveToDB(regNo);
			v.isRegistered = true;
			v.vRegistration=vr;
			v.save();
			vr.vehicle=v;
			vr.save();
			
			if(sqlRegDate!=null){
				vr.registrationDate=sqlRegDate;
			}
			if(sqlExpDate!=null){
				vr.expirationDate=sqlExpDate;
			}
//			if(!(city.isEmpty())){
//				vr.city=city;
//			}
			vr.save();
			flash("success","VEHICLE REGISTRATION SUCCESSFULLY ADDED!");
				return redirect("/showvregistration/"+vr.id);
			
		} catch (Exception e) {
			flash("error",
					"Error at adding vehicle registration ");
			Logger.error(
					"Adding vehicle registration error: " + e.getMessage(), e);
			return redirect("/addvregistrationview/" + id);
		}
	}
	

	/**
	 * Finds VehicleRegistration object using it's ID number and displays it in view
	 * @param id- VehicleRegistration object ID number
	 * @return
	 */
	public Result showVRegistration(long id) {
		VehicleRegistration vr = VehicleRegistration.findById(id);
		if (vr == null) {
			Logger.error("error", "Vehicle Registration null()");
			flash("error", "There isn't such  Vehicle Registration!");
			return redirect("/");
		}
		return ok(showVRegistration.render(vr));
	}

	
	/**
	 * Finds Vehicle Registration object by it's  ID number
	 *  and then deletes it from database
	 * @param id- Vehicle registration object ID number
	 * @return 
	 */
	public Result deleteVRegistration(long id) {
		try {
			VehicleRegistration vr = VehicleRegistration.findById(id);
			Logger.info("VehicleRegistration deleted: \"" + vr.id);
			VehicleRegistration.deleteVehicleRegistration(id);
			return redirect("/allvehicleregistrations");
		} catch (Exception e) {
			flash("deleteVRegistrationError",
					"Error at deleting vehicle registration!");
			Logger.error("Error at deleting vehicle registration: "
					+ e.getMessage());
			return redirect("/");
		}
	}

	
	/**
	 * Renders the view for editing Vehicle registration object.
	 * 
	 * @param id
	 *            long
	 * @return Result
	 */
	public Result editVRegistrationView(long id) {
		VehicleRegistration vr = VehicleRegistration.findById(id);
		// Exception handling.
		if (vr == null) {
			flash("VRegistrationNull", "Vehicle registration doesn't exist");
			return redirect("/");
		}
		return ok(editVRegistrationView.render(vr));

	}

	
	/**
	 * Finds the specific Vehicle registration object using it's ID number passed as argument,
	 *  and then updates it's properties(fields) with values from request 
	 * (collected from editVRegistration form). 
	 * If Vehicle Registration object has reference to Notification object(non null),
	 * and if it's newly entered expiry date is not near or passed(as set in notification settings),
	 * method will remove this Registration object from list of objects that are referenced in related  Notification object
	 * (and also remove reference from Registration object to Notification object) 
	 	 * @param id-ID number of Vehicle Registration object
	 * @return Result
	 */
	public Result editVRegistration(long id) {
		DynamicForm dynamicVRegistrationForm = Form.form().bindFromRequest();
		Form<VehicleRegistration> vRegistrationForm = Form.form(
				VehicleRegistration.class).bindFromRequest();
		VehicleRegistration vr = VehicleRegistration.findById(id);
		String regNo;
		java.util.Date newJavaDate = new java.util.Date();
		String stringDate;
		Date expiryDate;
		try {
			// if (vRegistrationForm.hasErrors() ||
			// vRegistrationForm.hasGlobalErrors()) {
			// Logger.info("Vehicle Registration update error");
			// flash("error", "Error in vehicle registration update form");
			// return ok(editVRegistrationView.render(vr));
			// }
			java.sql.Date newSqlDate = null;
			regNo = dynamicVRegistrationForm.get("numReg");
			stringDate = dynamicVRegistrationForm.get("dateExp");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			newJavaDate = format.parse(stringDate);
			RenewalNotification rn=null;
			if(vr.notification!=null){
			rn=vr.notification;
			java.util.Date oldExpiryJavaDate = new java.util.Date(vr.expirationDate.getTime());
			 newSqlDate = new java.sql.Date(newJavaDate.getTime());
			if(oldExpiryJavaDate.compareTo(newJavaDate)!=0){
				if(!(NotificationHelper.isDateNear(newSqlDate))){
				vr.notification=null;
				vr.checked=false;
				vr.save();
				rn.registrations.remove(vr);		
							rn.save();
							if(rn.registrations.size()==0){
							RenewalNotification.deleteRenewalNotification(rn.id);
							}
				}
				}
			}
			expiryDate = newSqlDate;
			vr.regNo = regNo;
			vr.expirationDate = expiryDate;
			vr.save();
			flash("success",
					"REGISTRATION SUCCESSFULLY UPDATED!");
			return redirect("/showvregistration/"+vr.id);
		} catch (Exception e) {
			flash("error", "ERROR EDITING REGISTRATION");
			Logger.error(
					"ERROR UPDATING VEHICLE REGISTRATION: " + e.getMessage(),
					e);
			return redirect("/");
		}
	}

	
	public Result listVRegistrations() {
		List<VehicleRegistration> allVRegistrations = VehicleRegistration
				.listOfVRegistrations();
		if (allVRegistrations != null) {
			return ok(listAllVRegistrations.render(allVRegistrations));
		} else {
			flash("listVRegistrationsError",
					"No Vehicle registrations in database!");
			return redirect("/");
		}
	}
}
