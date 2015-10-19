package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import models.*;
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

public class FuelTypeController extends Controller {

	/**
	 * Finder for FuelType object
	 */
	public static Finder<Long, FuelType> find = new Finder<Long, FuelType>(
			FuelType.class);

	static Form<FuelType> fuelTypeForm = Form.form(FuelType.class);

	public Result showFuelType(long id) {
		FuelType ft = FuelType.findById(id);
		if (ft == null) {
			Logger.error("error", "FUEL TYPE IS NULL");
			flash("error", "NO SUCH FUEL TYPE RECORD IN DATABASE!!!");
			return redirect("/allfueltypes");
		}
		return ok(showFuelType.render(ft));
	}
	
	/**
	 * Renders the 'add FuelType' page(form) 
	 * @return
	 */
	public Result addFuelTypeView() {
		
		return ok(addFuelTypeForm.render());
	}

	
	/**
	 * Finds FuelBill object by it's ID number, and 
	 * then deletes it from database
	 * 
	 * @param id-  ID number of FuelType object
	 * @return
	 */
	public Result deleteFuelType(long id) {
		try {
			FuelType ft = FuelType.find.byId(id);
			Logger.info("FUEL_TYPE DELETED: \"" + ft.id + "\"");
			ft.delete();
			return redirect("/allfueltypes");
		} catch (Exception e) {
			flash("error", "ERROR AR DELETING FUEL_TYPE!");
			Logger.error("ERROR AT DELETING FUEL_TYPE: " + e.getMessage());
			return redirect("/allfueltypes");
		}
	}

	/**
	 * First finds FuelType object by ID, and then sends it to the rendered
	 * template view for editing
	 * 
	 * @param id -ID number of FuelType object
	 * @return
	 */
	public Result editFuelTypeView(long id) {
		FuelType ft = FuelType.find.byId(id);
		// Exception handling.
		if (ft == null) {
			Logger.error("ERROR AT RENDERING FUELBILL EDITING VIEW. FUELTYPE NULL!  ");
			flash("error", "FUELBILL NULL");
			return ok(editFuelTypeView.render(ft));
		}
		return ok(editFuelTypeView.render(ft));

	}

	/**
	 * Finds FuelType object by ID, then 
	 * binds data entered through editFuelType
	 * form (view), and updates FuelType object with them
	 * @param id-ID number of the FuelType object
	 * @return Result
	 */
	public Result editFuelType(long id) {
		FuelType ft = FuelType.find.byId(id);
		DynamicForm dynamicFuelTypeForm = Form.form().bindFromRequest();
		Form<FuelType> fuelTypeForm = Form.form(FuelType.class)
				.bindFromRequest();
		String ftName;
		try {
			if (fuelTypeForm.hasErrors() || fuelTypeForm.hasGlobalErrors()) {
				Logger.info("FUEL_TYPE EDIT FORM ERROR");
				flash("error", "FUEL_BILL EDIT FORM ERROR");
				return ok(editFuelTypeView.render(ft));
			}
			ftName = fuelTypeForm.bindFromRequest().get().ftName;
			ft.ftName=ftName;
			ft.save();			
			flash("success", "FUEL_TYPE SUCCESSFULLY EDITED!");
			return ok(showFuelType.render(ft));
		} catch (Exception e) {
			flash("error", "Error at editing FuelBill");
			Logger.error("ERROR EDITING FuelBill: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	/**
	 * Uses data entered through AddFuelTypeForm view
	 * for creating new FuelType object
	 * @return
	 * @throws ParseException
	 */
	public Result addFuelType() {
		DynamicForm dynamicFuelTypeForm = Form.form().bindFromRequest();
		Form<FuelType> addFuelTypeForm = Form.form(FuelType.class)
				.bindFromRequest();
		if (addFuelTypeForm.hasErrors() || addFuelTypeForm.hasGlobalErrors()) {
			Logger.debug("ERROR AT ADING_FUEL_TYPE FORM");
			flash("error", "Error at Fuel type form!");
			return redirect("/addfueltypeview");
		}
		String ftName=null;
		try {	
			ftName = addFuelTypeForm.bindFromRequest().get().ftName;
			FuelType ft = FuelType.saveToDB(ftName);
			if (ft != null) {
				flash("addFuelTypeSuccess", "FUEL TYPE SUCCESSFULLY ADDED!");
				return redirect("/allfueltypes");
			} else {
				flash("error", "ERROR AT ADDING FUEL TYPE");
				return redirect("/addfueltypeview");
			}
		} catch (Exception e) {
			flash("error", "ERROR ADDING FUEL TYPE ");
			Logger.error("ERROR ADDING FUEL_TYPE: " + e.getMessage(), e);
			return redirect("/addfueltypeview");
		}
	}

	public Result listFuelTypes() {
		List<FuelType> allFuelTypes = FuelType.find.all();
		if (allFuelTypes != null) {
			return ok(listAllFuelTypes.render(allFuelTypes));
		} else {
			flash("error", "NO FUEL TYPE RECORDS IN DATABASE!");
			return redirect("/");
		}
	}
}
