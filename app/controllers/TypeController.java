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

public class TypeController extends Controller{
	/**
	 * finder for Vehicle Type object
	 */
	public static Finder<Long, Type> find = new Finder<Long, Type>(
			Type.class);

	/**
	 * Generates view(form) for adding new Type object
	 * @return
	 */
	public Result addTypeView() {
		return ok(addTypeForm.render());
	}

	/**
	 * Creates a new Type object using data from request
	 * (collected through the form)
	 * @return
	 * @throws ParseException
	 */
	public Result addType() {
		DynamicForm dynamicTypeForm = Form.form().bindFromRequest();
		Form<Type> addTypeForm = Form.form(Type.class).bindFromRequest();
		/*
		 * if (addClientForm.hasErrors() || addClientForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Client"); flash("error",
		 * "Error at Client adding  form!"); return redirect("/allclients"); }
		 */
		String typeName;
		try {
			typeName = addTypeForm.bindFromRequest().get().name;
			for(Type t:Type.typesList()){
				if(t.name.equalsIgnoreCase(typeName)){
					flash("error", "CANNOT ADD THAT VEHICLE TYPE! IT ALREADY EXISTS!");
					return redirect("/addtypeview");
				}
			}
			Type t=Type.saveToDB(typeName);
			System.out.println("VEHICLE TYPE ADDED SUCCESSFULLY///////////////////////");
			Logger.info("VEHICLE TYPE ADDED SUCCESSFULLY///////////////////////");
			flash("success", "VEHICLE TYPE SUCCESSFULLY ADDED");
			return redirect("/alltypes");
		} catch (Exception e) {
			flash("error", "ERROR AT ADDING TYPE ");
			Logger.error("ADDING TYPE ERROR: " + e.getMessage(), e);
			return redirect("/alltypes");
		}
	}

	/**
	 * Finds Type object by it's ID number
	 * passed as parameter and shows it
	 * in view
	 * @param id- Type object ID
	 * @return
	 */
	public Result showType(long id) {
		Type t= Type.findById(id);
		if (t== null) {
			Logger.error("error", "VEHICLE TYPE IS NULL");
			flash("error", "NO VEHICLE TYPE RECORD IN DATABASE!!!");
			return redirect("/alltypes");
		}
		return ok(showType.render(t));
	}

	/**
	 * Finds Type object by it's ID number
	 * passed  as parameter, and then removes
	 * it from database
	 * 
	 * @param id - Type object ID
	 * @return
	 */
	public Result deleteType(long id) {
		try {
			Type t = Type.findById(id);
			Type.deleteType(id);
			Logger.info("VEHICLE TYPE "+t.name+" DELETED");
			flash("success", "VEHICLE TYPE "+t.name+" DELETED");

			return redirect("/alltypes");
		} catch (Exception e) {
			flash("error", "ERROR DELETING TYPE!");
			Logger.error("ERROR DELETING TYPE: " + e.getMessage());
			return redirect("/alltypes");
		}
	}

	/**
	 * Renders the view for editing Type object.
	 * @param id-Type object ID number
	 * @return
	 */
	public Result editTypeView(long id) {
		Type t= Type.findById(id);
		// Exception handling.
		if (t == null) {
			flash("error", "NO THAT TYPE RECORD IN DATABASE");
			return redirect("/alltypes");
		}
		return ok(editTypeView.render(t));

	}

	/**
	 * finds the specific Type object by it's ID number 
	 * passed as parameter, and updates
	 * it with data from post request (collected through editType view form) .
	 * 
	 * @param id- ID number of Type object
	 * @return Result
	 */
	public Result editType(long id) {
		DynamicForm dynamicTypeForm = Form.form().bindFromRequest();
		Form<Type> typeForm = Form.form(Type.class).bindFromRequest();
		Type t=Type.findById(id);
		String typeName;
		try {
			typeName = typeForm.bindFromRequest().get().name;
			t.name = typeName;
			t.save();
			Logger.info("VEHICLE TYP UPDATED");
			flash("success", "TYPE UPDATED SUCCESSFULLY!");
			return ok(showType.render(t));
		} catch (Exception e) {
			flash("error", "ERROR AT EDITING TYPE ");
			Logger.error("EDITING TYPE ERROR: " + e.getMessage(), e);
			return redirect("/edittypeview");
		}
	}

	public Result listAllTypes() {
		List<Type> allTypes = Type.typesList();
		if (allTypes != null) {
			return ok(listAllTypes.render(allTypes));
		} else {
			flash("error", "NO VEHICLE TYPE RECORDS IN DATABASE!");
			return redirect("/");
		}
	}
}
