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
	 * First checks if the form for adding VehicleInspection object has errors.
	 * Creates a new VehicleInspection object or renders the view again if any
	 * error occurs.
	 * 
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
		java.util.Date utilDate1 = new java.util.Date();
		String stringDate;
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
		// Vehicle vehicle=null;
		try {
			stringDate = dynamicInspectionForm.get("inspDate");
			System.out.println("ISPISUJEM DATUM SA FORME:" + stringDate);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate1 = format.parse(stringDate);
			inspectDate = new java.sql.Date(utilDate1.getTime());
			vehicleDocument = addInspectionForm.bindFromRequest().get().vehicleDocumentation;
			safety = addInspectionForm.bindFromRequest().get().safety;
			body = addInspectionForm.bindFromRequest().get().body;
			tiresWheels = addInspectionForm.bindFromRequest().get().tiresWheels;
			steeringSuspension = addInspectionForm.bindFromRequest().get().steeringSuspension;
			brakes = addInspectionForm.bindFromRequest().get().brakes;
			lightningElSystem = addInspectionForm.bindFromRequest().get().lightningElSystem;
			glass = addInspectionForm.bindFromRequest().get().glass;
			exhaustSystem = addInspectionForm.bindFromRequest().get().exhaustSystem;
			emission = addInspectionForm.bindFromRequest().get().emission;
			obd = addInspectionForm.bindFromRequest().get().obd;
			fuelSystem = addInspectionForm.bindFromRequest().get().fuelSystem;
			addNotes = addInspectionForm.bindFromRequest().get().addNotes;
			VehicleInspection vi = VehicleInspection.saveToDB(inspectDate,
					vehicleDocument, safety, body, tiresWheels,
					steeringSuspension, brakes, lightningElSystem, glass,
					exhaustSystem, emission, obd, fuelSystem, addNotes, v);
			Logger.info(session("name") + " created vehicle registration ");
			if (vi != null) {
				flash("addVehicleInspectionSuccess",
						"Vehicle Inspection successfully added!");
				return redirect("/allinspections");
			} else {
				flash("addInspectionError", "Vehicle Inspection is null ");
				return redirect("/");
			}
		} catch (Exception e) {
			flash("addInspectionError", "Error at adding Vehicle Inspection ");
			Logger.error("Adding Vehicle Inspection error: " + e.getMessage(),
					e);
			return redirect("/addinspectionview/" + id);
		}
	}

	/**
	 * Finds VehicleInspection object using id and shows it
	 * 
	 * @param id
	 *            - VehicleInspection id
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
			flash("vehicleRegistrationUpdateSuccess",
					"Vehicle registration successfully updated!");
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
}
