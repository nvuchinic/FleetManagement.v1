package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import models.Driver;
import models.FuelBill;
import models.Vehicle;
import models.WarehouseWorkOrder;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class FuelBillController extends Controller {

	/**
	 * Finder for FuelBill object
	 */
	public static Finder<Long, FuelBill> find = new Finder<Long, FuelBill>(
			FuelBill.class);

	static Form<FuelBill> fuelBillForm = Form.form(FuelBill.class);

	/**
	 * Renders the 'add FuelBill' page
	 * 
	 * @return
	 */
	public Result addFuelBillView() {
		return ok(addFuelBillForm.render());
	}
//
//	/**
//	 * Finds FuelBill object by id and shows it
//	 * 
//	 * @param id
//	 *            - FuelBill object id
//	 * @return
//	 */
//	public Result showFuelBill(long id) {
//		FuelBill fb = FuelBill.find.byId(id);
//		if (fb == null) {
//			Logger.error("error", "FuelBill NULL");
//			flash("error", "FUELBILL NULL!");
//			return redirect("/");
//		}
//		return ok(showFuelBill.render(fb));
//	}

	/**
	 * Finds FuelBill object by ID passed as parameter, and then deletes it from
	 * database
	 * 
	 * @param id
	 *            - FuelBill object id
	 * @return redirect to index after delete
	 */
	public Result deleteFuelBill(long id) {
		try {
			FuelBill fb = FuelBill.find.byId(id);
			Logger.info("WorkOrder deleted: \"" + fb.id + "\"");
			fb.delete();
			return redirect("/allFuelBills");
		} catch (Exception e) {
			flash("error", "Error at deleting FuelBill!");
			Logger.error("ERROR AT DELETING FUELBILL: " + e.getMessage());
			return redirect("/allFuelBills");
		}
	}

	/**
	 * First finds FuelBill object by ID, and then sends it to the rendered
	 * template view for editing
	 * 
	 * @param id
	 *            long
	 * @return
	 */
	public Result editFuelBillView(long id) {
		FuelBill fb = FuelBill.find.byId(id);
		// Exception handling.
		if (fb == null) {
			Logger.error("ERROR AT RENDERING FUELBILL EDITING VIEW  ");
			flash("error", "FUELBILL NULL");
			return ok(editFuelBillView.render(fb));
		}

		return ok(editFuelBillView.render(fb));

	}

	/**
	 * Finds FuelBill object by ID, then takes and binds data from editFuelBill
	 * view, and updates FuelBill object with them
	 * 
	 * @param id
	 *            ID of the FuelBill object
	 * @return Result
	 */
	public Result editFuelBill(long id) {
		DynamicForm dynamicFuelBillForm = Form.form().bindFromRequest();
		Form<FuelBill> fuelBillForm = Form.form(FuelBill.class)
				.bindFromRequest();
		FuelBill fb = FuelBill.find.byId(id);
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date billDate = null;
		try {
			if (fuelBillForm.hasErrors() || fuelBillForm.hasGlobalErrors()) {
				Logger.info("FUELBILL EDIT FORM ERROR");
				flash("error", "FUELBILL EDIT FORM ERROR");
				return ok(editFuelBillView.render(fb));
			}
			String gasStationName = fuelBillForm.bindFromRequest().get().gasStationName;
			String plate = fuelBillForm.bindFromRequest().get().plate;
			String driverName = fuelBillForm.bindFromRequest()
					.field("driverName").value();
			String fuelAmount = fuelBillForm.bindFromRequest().get().fuelAmount;
			String fuelPrice = fuelBillForm.bindFromRequest().get().fuelPrice;
			String totalDistance = fuelBillForm.bindFromRequest().get().totalDistance;
			String totalDistanceGps = fuelBillForm.bindFromRequest().get().totalDistanceGps;
			stringDate = dynamicFuelBillForm.get("billD");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			billDate = new java.sql.Date(utilDate.getTime());
			Driver d = Driver.findByDriverName(driverName);
			if (d == null) {
				Logger.info("Driver does not exists");
				flash("error", "Driver does not exist");
				return ok(editFuelBillView.render(fb));
			}
			fb.gasStationName = gasStationName;
			fb.plate = plate;
			fb.driver = d;
			fb.billDate = billDate;
			fb.fuelAmount = fuelAmount;
			fb.fuelPrice = fuelPrice;
			fb.totalDistance = totalDistance;
			fb.totalDistanceGps = totalDistanceGps;
			fb.save();

			Logger.info(session("name") + " EDITED FUELBILL: " + fb.id);
			List<FuelBill> allFuelBills = FuelBill.find.all();
			flash("success", "FUEL BILL SUCCESSFULLY EDITED!");
			return ok(listAllFuelBills.render(allFuelBills));
		} catch (Exception e) {
			flash("error", "Error at editing FuelBill");
			Logger.error("ERROR EDITING FuelBill: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	/**
	 * Creates the form for binding data from AddFuelBillForm view, and uses
	 * that data for creating new FuelBill object
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addFuelBill() {
		DynamicForm dynamicFuelBillForm = Form.form().bindFromRequest();
		Form<FuelBill> addFuelBillForm = Form.form(FuelBill.class)
				.bindFromRequest();

		if (addFuelBillForm.hasErrors() || addFuelBillForm.hasGlobalErrors()) {
			Logger.debug("Error at adding FuelBill");
			flash("error", "Error at Fuel Bill form!");
			return redirect("/addFuelBillView");
		}
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date billDate = null;
		String driverName = null;
		try {
			String gasStationName = addFuelBillForm.bindFromRequest().get().gasStationName;
			String plate = addFuelBillForm.bindFromRequest().get().plate;
			driverName = addFuelBillForm.bindFromRequest()
					.field("driverName").value();
			String fuelAmount = fuelBillForm.bindFromRequest().get().fuelAmount;
			String fuelPrice = fuelBillForm.bindFromRequest().get().fuelPrice;
			String totalDistance = fuelBillForm.bindFromRequest().get().totalDistance;
			String totalDistanceGps = fuelBillForm.bindFromRequest().get().totalDistanceGps;
			stringDate = dynamicFuelBillForm.get("billD");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			billDate = new java.sql.Date(utilDate.getTime());
			Driver d = Driver.findByDriverName(driverName);
			if (d == null) {
				Logger.info("Driver does not exists");
				flash("error", "Driver does not exist");
				return redirect("/addFuelBillView");
			}
			FuelBill fb = FuelBill.find.byId(FuelBill.createFuelBill(
					gasStationName, plate, d, billDate, fuelAmount, fuelPrice,
					totalDistance, totalDistanceGps));
			if (fb != null) {
				Logger.info(session("name") + " CREATED FUELBILL ");
				flash("success", "FUELBILL SUCCESSFULLY ADDED!");
				return redirect("/allFuelBills");
			} else {
				flash("error", "ERROR ADDING FUELBILL ");
				return redirect("/addFuelBillView");
			}
		} catch (Exception e) {
			System.out.println(Driver.listOfDrivers().size() + driverName);
			flash("error", "ERROR ADDING FUELBILL ");
			Logger.error("ERROR ADDING FUELBILL: " + e.getMessage(), e);
			return redirect("/addFuelBillView");
		}
	}

	public Result listFuelBills() {
		List<FuelBill> allFuelBills = FuelBill.find.all();
		if (allFuelBills != null) {
			return ok(listAllFuelBills.render(allFuelBills));
		} else {
			flash("error", "NO FUELBILLS IN DATABASE!");
			return redirect("/");
		}
	}
}
