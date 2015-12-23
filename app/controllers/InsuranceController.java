package controllers;

import helpers.NotificationHelper;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class InsuranceController extends Controller {

	/**
	 * Form for creating/editing Insurance object
	 */
	static Form<Insurance> insuranceForm = Form.form(Insurance.class);

	/**
	 * Finder for Insurance object
	 */
	
	public static Finder<Long, Insurance> find = new Finder<Long, Insurance>(
			Insurance.class);

	

	/**
	 * Renders the form view for creating Insurance object
	 * 
	 * @return
	 */
	public Result addInsuranceView(long id) {
		Vehicle v = Vehicle.findById(id);
		//if (v.isInsured == true) {
			//flash("VehicleInsured", "Vehicle is already insured!");
			//return redirect("/showVehicle/" + v.id);
		//}
		return ok(addInsuranceForm.render(v));
	}

	/**
	 * First checks if the form for adding Insurance has errors. Creates a new
	 * insurance object or renders the view again if any error occurs.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addInsurance(long id) {
		DynamicForm dynamicInsuranceForm = Form.form().bindFromRequest();
		Form<Insurance> addInsuranceForm = Form.form(Insurance.class)
				.bindFromRequest();
		if (Vehicle.findById(id) == null) {
			flash("error", "VEHICLE IS NULL!");
			return redirect("/");
		}
		Vehicle v = Vehicle.findById(id);
		
		/*
		 * if (addInsuranceForm.hasErrors() ||
		 * addInsuranceForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Insurance"); flash("error",
		 * "Error at add Insurance form!"); return redirect("/addInsurance"); }
		 */
		String contractNo;
		java.util.Date javaExpiryDate = new java.util.Date();
		String stringExpiryDate;
		Date sqlExpiryDate;
		String insuranceType=null;
		double cost;
		try {
			contractNo = addInsuranceForm.bindFromRequest().get().contractNo;
			insuranceType = dynamicInsuranceForm.get("insuranceT");
			System.out.println("//////////////PRINTING INSURANCE TYPE: "+insuranceType);
			cost = addInsuranceForm.bindFromRequest().get().cost;
			stringExpiryDate = dynamicInsuranceForm.get("expDate");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			javaExpiryDate = format.parse(stringExpiryDate);
			sqlExpiryDate = new java.sql.Date(javaExpiryDate.getTime());
			Insurance ins = Insurance.saveToDB(contractNo, insuranceType, cost,
					sqlExpiryDate);
			v.isInsured = true;
			v.insurances.add(ins);
			v.save();
			ins.vehicle=v;
			ins.save();
				flash("success", "INSURANCE SUCCESSFULLY ADDED!");
				return redirect("/allinsurances");
			
		} catch (Exception e) {
			flash("addInsuranceError", "Error at adding Insurance ");
			Logger.error("Adding Insurance error: " + e.getMessage(), e);
			return redirect("/addinsuranceview/" + id);
		}
	}

	/**
	 * Finds Insurance object using id and shows it
	 * 
	 * @param id
	 *            - Insurance id
	 * @return
	 */
	public Result showInsurance(long id) {
		Insurance ins = Insurance.findById(id);
		if (ins == null) {
			Logger.error("error", "Insurance null()");
			flash("error", "There isn't such  Insurance!");
			return redirect("/");
		}
		return ok(showInsurance.render(ins));
	}

	/**
	 * Finds Insurance object using id and then deletes it from database
	 * 
	 * @param id
	 *            - Insurance id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteInsurance(long id) {
		try {
			Insurance ins = Insurance.findById(id);
			Logger.info("Insurance deleted: \"" + ins.id);
			Insurance.deleteInsurance(id);
			return redirect("/allinsurances");
		} catch (Exception e) {
			flash("deleteInsuranceError", "Error at deleting insurance!");
			Logger.error("Error at deleting insurance: " + e.getMessage());
			return redirect("/");
		}
	}

	/**
	 * Renders the view for editing Insurance object.
	 * 
	 * @param id
	 *            long
	 * @return Result
	 */
	public Result editInsuranceView(long id) {
		Insurance ins = Insurance.findById(id);
		// Exception handling.
		if (ins == null) {
			flash("InsuranceNull", "Insurance doesn't exist");
			return redirect("/");
		}
		// Form<TravelOrder> travelOrderForm =
		// Form.form(TravelOrder.class).fill(to);
		return ok(editInsuranceView.render(ins));

	}

	/**
	 * Method receives an id, finds the specific Insurance object and updates
	 * its information with data collected from editInsurance form again.
	 * 	 @param id-ID number of Insurance object
	 * @return Result
	 */
	public Result editInsurance(long id) {
		DynamicForm dynamicInsuranceForm = Form.form().bindFromRequest();
		Form<Insurance> insuranceForm = Form.form(Insurance.class)
				.bindFromRequest();
		Insurance ins = Insurance.findById(id);
		String contractNo;
		java.util.Date newJavaDate = new java.util.Date();
		String stringExpiryDate;
		Date sqlExpiryDate;
		String itype;
		double cost;
		try {
//			if (insuranceForm.hasErrors() || insuranceForm.hasGlobalErrors()) {
//				Logger.info("Insurance update error");
//				flash("error", "Error in insurance update form");
//				return ok(editInsuranceView.render(ins));
//			}
			contractNo = insuranceForm.bindFromRequest().get().contractNo;
			if (contractNo == null || contractNo.isEmpty()) {
				contractNo = ins.contractNo;
			}
			java.sql.Date newSqlExpiryDate = null;
			stringExpiryDate = dynamicInsuranceForm.get("expDate");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			newJavaDate = format.parse(stringExpiryDate);
			newSqlExpiryDate = new java.sql.Date(newJavaDate.getTime());
			java.util.Date oldExpiryJavaDate = new java.util.Date(ins.expirationDate.getTime());
			RenewalNotification rn=null;
						if(ins.notification!=null){
				rn=ins.notification;
				 newSqlExpiryDate = new java.sql.Date(newJavaDate.getTime());
				if(oldExpiryJavaDate.compareTo(newJavaDate)!=0){
					if(!(NotificationHelper.isDateNear(newSqlExpiryDate))){
					ins.notification=null;
					ins.checked=false;
					ins.save();
					rn.insurances.remove(ins);		
								rn.save();
								if(rn.insurances.size()==0){
								RenewalNotification.deleteRenewalNotification(rn.id);
								}
					}
					}
				}
			
			itype = insuranceForm.bindFromRequest().get().itype;
			cost = insuranceForm.bindFromRequest().get().cost;
			ins.contractNo = contractNo;
			ins.expirationDate = newSqlExpiryDate;
			ins.itype = itype;
			ins.cost = cost;
			ins.save();
			Logger.info(session("name") + " updated insurance: " + ins.id);
			flash("success", "INSURANCE SUCCESSFULLY UPDATED!");
			return ok(showInsurance.render(ins));
		} catch (Exception e) {
			flash("error", "Error at editing Insurance");
			Logger.error("Error at updating Insurance: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	

	public Result removeInsuranceNotification(long id){
		Insurance ins=Insurance.find.byId(id);
		long vid=ins.vehicle.id;
		RenewalNotification rn=ins.notification;
		ins.checked=true;
		ins.notification=null;
		ins.save();
		//rn.insurances.remove(ins);
		//rn.save();
	//	Vehicle v=Vehicle.findById(id);
		//v.isInsured=false;
		//v.save();
		flash("success", "REMOVED INSURANCE NOTIFICATION. YOU CAN CREATE NEW INSURANCE NOW IF YOU LIKE, OR YOU CAN DO IT LATER");
		return redirect("/addinsuranceview/"+vid);
	}
	
	
	public Result listInsurances() {
		List<Insurance> allInsurances = Insurance.listOfInsurances();
		if (allInsurances != null) {
			return ok(listAllInsurances.render(allInsurances));
		} else {
			flash("listInsurancesError", "No Insurance records in database!");
			return redirect("/");
		}
	}
	

	public Result listUninsuredVehicles() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = Vehicle.listOfVehicles();
		if (allVehicles.size() == 0) {
			return ok(listAllVehicles.render(allVehicles));
		}
		List<Vehicle> uninsuredVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.isInsured == false) {
				uninsuredVehicles.add(v);
			}
		}
		if (uninsuredVehicles.size() == 0) {
			flash("noUninsuredVehicles", "All Vehicles are insured!");
			return ok(listUninsuredVehicles.render(uninsuredVehicles));
		} else {
			return ok(listUninsuredVehicles.render(uninsuredVehicles));
		}
	}
}
