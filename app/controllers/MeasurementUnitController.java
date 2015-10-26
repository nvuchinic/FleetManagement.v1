package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

public class MeasurementUnitController extends Controller{

	/**
	 * finder for MeasurementUnit object
	 */
	public static Finder<Long, MeasurementUnit> find = new Finder<Long, MeasurementUnit>(
			MeasurementUnit.class);

	/**
	 * Generates view(form) for adding new MeasurementUnit object
	 * 
	 * @return
	 */
	public Result addMeasurementUnitView() {
		return ok(addMeasurementUnitForm.render());
	}

	/**
	 * 
	 * Creates a new MeasurementUnit object using data from request (data entered on the form)
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addMeasurementUnit() {
		DynamicForm dynamicmeasurementUnitForm = Form.form().bindFromRequest();
		Form<MeasurementUnit> addMeasurementUnitForm = Form.form(MeasurementUnit.class).bindFromRequest();
		/*
		 * if (addMeasurementUnitForm.hasErrors() || addMeasurementUnitForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding MeasurementUnit"); flash("error",
		 * "Error at MeasurementUnit adding  form!"); return redirect("/allmeasurementunits"); }
		 */
		String name;
		String symbol;
		String description;
		try {
			name = addMeasurementUnitForm.bindFromRequest().get().name;
			symbol = addMeasurementUnitForm.bindFromRequest().get().symbol;
			description = addMeasurementUnitForm.bindFromRequest().get().description;
			MeasurementUnit mu= MeasurementUnit.saveToDB(name, symbol, description);
			System.out.println("MEASUREMENT UNIT ADDED SUCCESSFULLY///////////////////////");
			Logger.info("MEASUREMENT UNIT ADDED SUCCESSFULLY///////////////////////");
			flash("success", "MEASUREMENT UNIT SUCCESSFULLY ADDED");

			return redirect("/allmeasurementunits");
		} catch (Exception e) {
			flash("error", "ERROR  ADDING MEASUREMENT UNIT ");
			Logger.error("ADDING MEASUREMENT UNIT ERROR: " + e.getMessage(), e);
			return redirect("/addmeasurementunitview");
		}
	}

	/**
	 * Finds MeasurementUnit object by it's ID number 
	 * passed as parameter and shows it  in view
	 * @param id- MeasurementUnit object ID
	 * @return
	 */
	public Result showMeasurementUnit(long id) {
		MeasurementUnit mu = measurementUnit.findById(id);
		if (mu == null) {
			Logger.error("error", "MEASUREMENT UNIT IS NULL");
			flash("error", "NO MEASUREMENT UNIT RECORD IN DATABASE!!!");
			return redirect("/allmeasurementunits");
		}
		return ok(showMeasurementUnit.render(mu));
	}

	/**
	 * Finds MeasurementUnit object by ID number passed as parameter,
	 *  and then removes it from database
	 * @param id- MeasurementUnit object ID
	 * @return
	 */
	public Result deleteMeasurementUnit(long id) {
		try {
			MeasurementUnit mu = MeasurementUnit.findById(id);
			Logger.info("MEASUREMENT UNIT DELETED: \"" + mu.id);
			MeasurementUnit.deleteM_Unit(id);
			return redirect("/allmeasurementunits");
		} catch (Exception e) {
			flash("deleteMeasurementUnit	Error", "ERROR DELETING MEASUREMENT UNIT!");
			Logger.error("ERROR DELETING MEASUREMENT UNIT: " + e.getMessage());
			return redirect("/allmeasurementunits");
		}
	}

	/**
	 * Renders the view for editing MeasurementUnit object.
	 * @param id- MeasurementUnit object ID number
	 * @return
	 */
	public Result editMeasurementUnitView(long id) {
		MeasurementUnit mu = MeasurementUnit.findById(id);
		// Exception handling.
		if (mu == null) {
			flash("error", "NO MEASUREMENT UNIT RECORD IN DATABASE");
			return redirect("/allmeasurementunits");
		}
		return ok(editMeasurementUnitView.render(mu));

	}

	/**
	 * finds the specific MeasurementUnit object by ID number passed as parameter, 
	 * and updates it using data collected from request(entered through editClient view form).
	 * @param id-ID number of MeasurementUnit object
	 * @return Result
	 */
	public Result editMeasurementUnit(long id) {
		DynamicForm dynamicMeasurementUnitForm = Form.form().bindFromRequest();
		Form<MeasurementUnit> measurementUnitForm = Form.form(MeasurementUnit.class).bindFromRequest();
		MeasurementUnit mu = MeasurementUnit.findById(id);
		String name;
		String symbol;
		String description;
		try {
			name = measurementUnitForm.bindFromRequest().get().name;
			symbol = measurementUnitForm.bindFromRequest().get().symbol;
			description = measurementUnitForm.bindFromRequest().get().description;
			mu.name = name;
			mu.symbol=symbol;
			mu.description=description;
			mu.save();
			Logger.info("MEASUREMENT UNIT UPDATED");
			flash("ClientUpdateSuccess", "MEASUREMENT UNIT UPDATED SUCCESSFULLY!");
			return ok(showmeasurementUnit.render(c));
		} catch (Exception e) {
			flash("error", "ERROR AT EDITING MEASUREMENT UNIT");
			Logger.error("EDITING MEASUREMENT UNIT ERROR: " + e.getMessage(), e);
			return redirect("/editmeasurementunitview");
		}
	}

	public Result listMeasurementUnits() {
		List<MeasurementUnit> allMeasurementUnits = MeasurementUnit.listOfM_Units();
		if (allMeasurementUnits != null) {
			return ok(listAllMeasurementUnits.render(allMeasurementUnits));
		} else {
			flash("error", "NO MEASUREMENT UNIT RECORDS IN DATABASE!");
			return redirect("/");
		}
	}
}
