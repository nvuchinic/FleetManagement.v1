package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class VehicleInspectionController extends Controller {

	/**
	 * Form for creating/editing VehicleInspection object
	 */
	static Form<VehicleInspection> vehicleInspectionForm = Form
			.form(VehicleInspection.class);

	/**
	 * Finder for VehicleInspection object
	 */

	public static Finder<Long, VehicleInspection> find = new Finder<Long, VehicleInspection>(
			VehicleInspection.class);

	/**
	 * Renders the form view for creating VehicleInspection object
	 * 
	 * @return
	 */
	public Result addInspectionView(long id) {
		Vehicle v = Vehicle.findById(id);
		if (v == null) {
			System.out.println("CANNOT FIND VEHICLE IN DATABASE");
			Logger.info("CANNOT FIND VEHICLE IN DATABASE");
			return redirect("/showVehicle/" + v.id);
		}
		return ok(addInspectionForm.render(v));
	}

	/**
	 * Creates a new VehicleInspection object using values from request
	 * @return
	 * @throws ParseException
	 */
	public Result addInspection(long id) {
		DynamicForm dynamicInspectionForm = Form.form().bindFromRequest();
		Form<VehicleInspection> addInspectionForm = Form.form(
				VehicleInspection.class).bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		if (v == null) {
			flash("VehicleNull", "VEHICLE DOESN'T EXIST!");
			return redirect("/");
		}
		/*
		 * if (addInspectionForm.hasErrors() ||
		 * addInspectionForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding vehicleInspection"); flash("error",
		 * "Error at Vehicle Inspection form!"); return
		 * redirect("/addinspection"); }
		 */
		java.util.Date javaInspDate = new java.util.Date();
		java.util.Date javaExpDate = new java.util.Date();
		String stringInspDate, stringExpDate;
		Date sqlInspectDate = null, sqlExpDate=null;
		String vehicleDocument = null;
		String safety = null;
		String body = null;
		String tiresWheels = null;
		String steeringSuspension = null;
		String brakes = null;
		String lightningElSystem = null;
		String glass = null;
		String exhaustSystem = null;
		String emission = null;
		String obd = null;
		String fuelSystem = null;
		String addNotes = null;
		// Vehicle vehicle=null;
		try {
			stringInspDate = dynamicInspectionForm.get("inspDate");
			stringExpDate = dynamicInspectionForm.get("expDate");
			System.out.println("PRINTING INSPECTION DATE:" + stringInspDate);
			System.out.println("PRINTING INSPECTION EXPIRY DATE:" + stringExpDate);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			javaInspDate = format.parse(stringInspDate);
			javaExpDate = format2.parse(stringExpDate);
			sqlInspectDate = new java.sql.Date(javaInspDate.getTime());
			sqlExpDate = new java.sql.Date(javaExpDate.getTime());
			vehicleDocument = addInspectionForm.bindFromRequest().get().vehicleDocumentation;
			if(vehicleDocument.isEmpty()){
				vehicleDocument="N/A";
			}
			safety = addInspectionForm.bindFromRequest().get().safety;
			body = addInspectionForm.bindFromRequest().get().body;
			tiresWheels = addInspectionForm.bindFromRequest().get().tiresWheels;
			steeringSuspension = addInspectionForm.bindFromRequest().get().steeringSuspension;
			brakes = addInspectionForm.bindFromRequest().get().brakes;
			lightningElSystem = addInspectionForm.bindFromRequest().get().lightningElSystem;
			glass = addInspectionForm.bindFromRequest().get().glass;
			exhaustSystem = addInspectionForm.bindFromRequest().get().exhaustSystem;
			emission = addInspectionForm.bindFromRequest().get().emission;
			if(emission.isEmpty()){
				emission="N/A";
			}
			obd = addInspectionForm.bindFromRequest().get().obd;
			fuelSystem = addInspectionForm.bindFromRequest().get().fuelSystem;
			addNotes = addInspectionForm.bindFromRequest().get().addNotes;
			VehicleInspection vi = VehicleInspection.saveToDB(sqlInspectDate,
					vehicleDocument, safety, body, tiresWheels,
					steeringSuspension, brakes, lightningElSystem, glass,
					exhaustSystem, emission, obd, fuelSystem, addNotes, v);
			vi.expiryDate=sqlExpDate;
			vi.save();
							flash("success",
						"VEHICLE INSPECTION SUCCESSFULLY ADDED!");
				return redirect("/allinspections");
			
		} catch (Exception e) {
			flash("error", "ERROR ADDING VEHICLE INSPECTION ");
			Logger.error("Adding Vehicle Inspection error: " + e.getMessage(),
					e);
			return redirect("/addinspectionview/" + id);
		}
	}

	/**
	 * Finds VehicleInspection object using it's ID number and shows it in view
	 * @param id- VehicleInspection object ID number
	 * @return
	 */
	public Result showInspection(long id) {
		VehicleInspection vi = VehicleInspection.findById(id);
		if (vi == null) {
			Logger.error("error", "Vehicle Inspection is null()");
			flash("error", "NO SUCH VEHICLE INSPECTION RECORD IN DATABASE!");
			return redirect("/");
		}
		return ok(showInspection.render(vi));
	}

	/**
	 * Finds Vehicle Inspection object using id and then deletes it from
	 * database
	 * 
	 * @param id
	 *            - Vehicle Inspection id (long)
	 * @return
	 */
	public Result deleteInspection(long id) {
		try {
			VehicleInspection vi = VehicleInspection.findById(id);
			Logger.info("VehicleInspection deleted: \"" + vi.id);
			VehicleInspection.deleteVehicleInspection(id);
			return redirect("/allvehicleinspections");
		} catch (Exception e) {
			flash("deleteInspectionError",
					"Error at deleting Vehicle Inspection!");
			Logger.error("Error at deleting VEHICLE INSPECTION: "
					+ e.getMessage());
			return redirect("/");
		}
	}

	/**
	 * Renders the view for editing Vehicle Inspection object.
	 * 
	 * @param id
	 *            long
	 * @return Result
	 */
	public Result editInspectionView(long id) {
		VehicleInspection vi = VehicleInspection.findById(id);
		// Exception handling.
		if (vi == null) {
			flash("InspectionNull", "VEHICLE INSPECTION IS NULL");
			return redirect("/");
		}
		return ok(editInspectionView.render(vi));

	}

	/**
	 * Method receives an id number, finds the specific Vehicle Inspection
	 * object and updates its information with data collected from
	 * editInspection form again.
	 * 
	 * @param id
	 *            of Vehicle Inspection object
	 * @return Result
	 */
	public Result editInspection(long id) {
		DynamicForm dynamicInspectionForm = Form.form().bindFromRequest();
		Form<VehicleInspection> editInspectionForm = Form.form(
				VehicleInspection.class).bindFromRequest();
		VehicleInspection vi = VehicleInspection.findById(id);
		java.util.Date utilDate1 = new java.util.Date();
		String stringDate1;
		Date inspectDate = null;
		String vehicleDocument = null;
		String safety = null;
		String body = null;
		String tiresWheels = null;
		String steeringSuspension = null;
		String brakes = null;
		String lightningElSystem = null;
		String glass = null;
		String exhaustSystem = null;
		String emission = null;
		String obd = null;
		String fuelSystem = null;
		String addNotes = null;
		Vehicle vehicle = null;
		try {
			// if (inspectionForm.hasErrors() ||
			// inspectionForm.hasGlobalErrors()) {
			// Logger.info("Vehicle Inspection update error");
			// flash("error", "Error in vehicle Inspection update form");
			// return ok(editInspectionView.render(vi));
			// }
			stringDate1 = dynamicInspectionForm.get("inspectDate");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate1 = format.parse(stringDate1);
			inspectDate = new java.sql.Date(utilDate1.getTime());
			vehicleDocument = editInspectionForm.bindFromRequest().get().vehicleDocumentation;
			safety = editInspectionForm.bindFromRequest().get().safety;
			body = editInspectionForm.bindFromRequest().get().body;
			tiresWheels = editInspectionForm.bindFromRequest().get().tiresWheels;
			steeringSuspension = editInspectionForm.bindFromRequest().get().steeringSuspension;
			brakes = editInspectionForm.bindFromRequest().get().brakes;
			lightningElSystem = editInspectionForm.bindFromRequest().get().lightningElSystem;
			glass = editInspectionForm.bindFromRequest().get().glass;
			exhaustSystem = editInspectionForm.bindFromRequest().get().exhaustSystem;
			emission = editInspectionForm.bindFromRequest().get().emission;
			obd = editInspectionForm.bindFromRequest().get().obd;
			fuelSystem = editInspectionForm.bindFromRequest().get().fuelSystem;
			addNotes = editInspectionForm.bindFromRequest().get().addNotes;
			vi.inspectDate = inspectDate;
			vi.vehicleDocumentation = vehicleDocument;
			vi.safety = safety;
			vi.body = body;
			vi.tiresWheels = tiresWheels;
			vi.steeringSuspension = steeringSuspension;
			vi.brakes = brakes;
			vi.lightningElSystem = lightningElSystem;
			vi.glass = glass;
			vi.exhaustSystem = exhaustSystem;
			vi.emission = emission;
			vi.obd = obd;
			vi.fuelSystem = fuelSystem;
			vi.addNotes = addNotes;
			vi.save();
			Logger.info("VEHICLE INSPECTION " + vi.id + "UPDATED");
			flash("success",
					"VEHICLE INSPECTION SUCCESSFULLY UPDATED!");
			return ok(showInspection.render(vi));
		} catch (Exception e) {
			flash("error", "Error at editing Vehicle Registration");
			Logger.error(
					"Error at updating VehicleRegistration: " + e.getMessage(),
					e);
			return redirect("/");
		}
	}

	public Result listInspections() {
		List<VehicleInspection> allInspections = VehicleInspection
				.listOfInspections();
		if (allInspections != null) {
			return ok(listAllInspections.render(allInspections));
		} else {
			flash("listVRegistrationsError",
					"No Vehicle registrations in database!");
			return redirect("/");
		}
	}
	
	
	public Result removeInspectionNotification(long id){
		VehicleInspection insp=VehicleInspection.find.byId(id);
		long vid=insp.vehicle.id;
		RenewalNotification rn=insp.notification;
		insp.checked=true;
		insp.notification=null;
		insp.save();
		//rn.insurances.remove(ins);
		//rn.save();
	//	Vehicle v=Vehicle.findById(id);
		//v.isInsured=false;
		//v.save();
		flash("success", "REMOVED INSPECTION NOTIFICATION. YOU CAN CREATE NEW INSURANCE NOW IF YOU LIKE, OR YOU CAN DO IT LATER");
		return redirect("/addinspectionview/"+vid);
	}
}
