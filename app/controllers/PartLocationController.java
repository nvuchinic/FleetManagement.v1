package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.*;

public class PartLocationController extends Controller {
	/*
	 * Finder for PartLocation class
	 */
	public static Finder<Long, PartLocation> find = new Finder<Long, PartLocation>(
			PartLocation.class);

	static Form<PartLocation> partLocationForm = Form.form(PartLocation.class);

	/**
	 * Renders the Form for adding new PartLocation object
	 * @return
	 */
	public Result addPartLocationView() {
		return ok(addPartLocationForm.render());
	}

	/**
	 * Creates new PartLocation object 
	 * using data from request (collected through
	 * addPartLocationForm view)
	 * @return
	 * @throws ParseException
	 */
	public Result addPartLocation() {
		Form<PartLocation> partLocationForm = Form.form(PartLocation.class)
				.bindFromRequest();
		// if (form.hasErrors() || form.hasGlobalErrors()) {
		// Logger.info("Part update error");
		// flash("error", "Error in part form");
		// return ok(addPartForm.render());
		// }
		try {
			String name = partLocationForm.bindFromRequest().get().name;
			String description = partLocationForm.bindFromRequest().get().description;
			PartLocation pl = PartLocation.saveToDB(name, description);
			flash("success", " PART LOCATION SUCCESSFULLY ADDED!");
			return redirect("/allpartlocations");
		} catch (Exception e) {
			flash("error", "ERROR ADDING PART LOCATION");
			Logger.error("Error ADDING PART LOCATION: " + e.getMessage(), e);
			return ok(listAllPartLocations.render(PartLocation
					.allPartLocations()));
		}
	}

	/**
	 * Finds PartLocation object by it's ID number
	 *  and shows it in view
	 * @param id - PartLocation object ID number
	 * @return
	 */
	public Result showPartLocation(long id) {
		PartLocation pl = PartLocation.find.byId(id);
		if (pl == null) {
			Logger.error("error", "PART LOCATION NULL");
			flash("error", "NO PART LOCATION!");
			return redirect("/");
		}
		return ok(showPartLocation.render(pl));
		// return ok("/");
	}

	/**
	 * returns all PartLocation objects as List
	 * @return
	 */
	public Result listPartLocations() {
		if (PartLocation.allPartLocations() == null) {
			return ok(listAllPartLocations
					.render(new ArrayList<PartLocation>()));
		}
		return ok(listAllPartLocations.render(PartLocation.allPartLocations()));
	}

	/**
	 * Renders the view for editing Part Location
	 * @param id- PartLocation object ID number
	 * @return Result
	 */
	public Result editPartLocationView(long id) {
		PartLocation pl = PartLocation.findById(id);
		// Exception handling.
		if (pl == null) {
			flash("error", "PART LOCATION IS NULL");
			return redirect("/");
		}
		return ok(editPartLocationView.render(pl));
	}

	/**
	 * First finds the specific Part Location object, then updates its
	 * properties with data collected through the form
	 * 
	 * @param id- ID number of PartLocation object
	 * @return Result
	 */
	public Result editPartLocation(long id) {
		Form<PartLocation> partLocationForm = Form.form(PartLocation.class)
				.bindFromRequest();
		DynamicForm partLocationDynamicForm = Form.form().bindFromRequest();
		PartLocation pl = PartLocation.findById(id);
		// if (form.hasErrors() || form.hasGlobalErrors()) {
		// Logger.info("Part update error");
		// flash("error", "Error in part form");
		// return badRequest(editPartView.render(part));
		// }
		String name = null;
		String description = null;
		try {
			name = partLocationForm.bindFromRequest().get().name;
			description = partLocationForm.bindFromRequest().get().description;
			pl.description = description;
			pl.name = name;
			pl.save();
			Logger.info("PART LOCATION EDITED: " + pl.name);
			flash("success", pl.name + "PART LOCATION SUCCESSFULLY UPDATED!");
			return ok(listAllPartLocations.render(PartLocation
					.allPartLocations()));
		} catch (Exception e) {
			flash("error", "ERROR EDITING PART LOCATION");
			Logger.error("ERROR EDITING PART LOCATION: " + e.getMessage(), e);
			return ok(editPartLocationView.render(pl));
		}
	}

	/**
	 * First finds PartLocation object by it's ID number, then removes it from
	 * database
	 * 
	 * @param id
	 *            - PartLocation object id (long)
	 * @return
	 */
	// @Security.Authenticated(AdminFilter.class)
	public Result deletePartLocation(long id) {
		PartLocation pl = null;
		try {
			pl = PartLocation.findById(id);
			PartLocation.deletePartLocation(id);
			Logger.info("DELETED PART LOCATION: \"" + pl.name + "\"");
			flash("success", "PART LOCATION SUCCESSFULLY DELETED!");
			return redirect("/allpartlocations");
		} catch (Exception e) {
			flash("error", "ERROR DELETING PART LOCATIONS!");
			Logger.error("ERROR DELETING PART LOCATIONS: " + e.getMessage());
			return ok(listAllPartLocations.render(PartLocation
					.allPartLocations()));
		}
	}
}
