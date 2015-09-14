package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.*;

import com.avaje.ebean.Model.Finder;

public class EquipmentController extends Controller {

	static Form<Equipment> equipmentForm = Form.form(Equipment.class);
	/**
	 * Finder for Equipment object
	 */
	public static Finder<Long, Equipment> find = new Finder<Long, Equipment>(
			Equipment.class);

	/**
	 * Renders the 'create equipment' page
	 * 
	 * @return
	 */
	public Result addEquipmentView() {
		return ok(addEquipmentForm.render());
	}
	
	/**
	 * Delete Equipment using id
	 * @param id - Vehicle id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteEquipment(long id) {
		try {
			Equipment eq = Equipment.find.byId(id);
			Logger.info("Deleted equipment: \"" + eq.name + "\"");
			Vehicle.deleteVehicle(id);
			return ok(listEquipment.render(Equipment.find.all()));
		} catch (Exception e) {
			flash("error", "Error at delete equipment!");
			Logger.error("Error at delete Equipment: " + e.getMessage());
			return ok(listEquipment.render(Equipment.find.all()));
		}
	}
	
	/**
	 * Renders the view of a Equipment. Method receives the id of the equipment and
	 * finds the equipment by id and send's the equipment to the view.
	 * 
	 * @param id long
	 * @return Result render EquipmentView
	 */
	public Result editEquipmentView(long id) {
		Equipment eq = Equipment.find.byId(id);
		// Exception handling.
		if (eq == null) {
			flash("error", "Equipment is not exists");
			return redirect("/");
		}
		return ok(editEquipmentView.render(eq));

	}
	
	/**
	 * Finds Equipment object by ID, then takes and binds data from
	 * editEquipmentView, and updates Equipment object with them
	 * @param id ID of the Equipment object
	 * @return Result
	 */
	public Result editEquipment(long id) {
		Form<Equipment> equipmentform = Form.form(Equipment.class);
		Equipment eq = Equipment.find.byId(id);
		if (equipmentForm.hasErrors() || equipmentForm.hasGlobalErrors()) {
			Logger.info("Equipment update error");
			flash("error", "Error in equipment form");
			return ok(editEquipmentView.render(eq));
		}
		try {
			eq.name = equipmentForm.bindFromRequest().get().name;
			eq.model = equipmentForm.bindFromRequest().get().model;
			eq.price = equipmentForm.bindFromRequest().get().price;
			
			eq.save();
			flash("success", "EQUIPMENT SUCCESSFULLY EDITED!");
			return ok(listEquipment.render(Equipment.find.all()));
		} catch (Exception e) {
			flash("error", "Error at editing Equipment");
			Logger.error("ERROR EDITING EQUIPMENT: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
	/**
	 * Creates the form for binding data from AddEquipmentForm view, and uses
	 * that data for creating new Equipment object
	 * @return
	 * @throws ParseException
	 */
	public Result addEquipment() {

		Form<Equipment> addEquipmentForm = Form.form(Equipment.class)
				.bindFromRequest();
		
		  if (equipmentForm.hasErrors() || equipmentForm.hasGlobalErrors()) {
		  Logger.debug("Error at adding Equipment");
		  flash("error", "Error at Equipment form!");
		  return redirect("/addEquipmentView"); }
		try {
			String name = addEquipmentForm.bindFromRequest().get().name;
			String model = addEquipmentForm.bindFromRequest().get().model;
			long price = addEquipmentForm.bindFromRequest().get().price;
			
			Equipment eq = Equipment.find.byId(Equipment.createEquipment(name, model, price));

			if(eq!=null){
				Logger.info(session("name") + " CREATED EQUIPMENT ");
				flash("success",  "EQUIPMENT SUCCESSFULLY ADDED!");
			return redirect("/allEquipment");
		} else {
			flash("error", "ERROR ADDING EQUIPMENT ");
			return redirect("/addEquipmentView");
			}
		} catch (Exception e) {
			flash("error", "ERROR ADDING Equipment ");
			Logger.error("ERROR ADDING Equipment: " + e.getMessage(), e);
			return redirect("/addEquipmentView");
		}
	}

	public Result listEquipment() {
		List<Equipment> allEquipment = Equipment.find.all();
		if (!allEquipment.isEmpty()) {
			return ok(listEquipment.render(allEquipment));
		} else {
			flash("error", "NO EQUIPMENT IN DATABASE!");
			return redirect("/");
		}
	}
}
