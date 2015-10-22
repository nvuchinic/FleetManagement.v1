package controllers;

import java.sql.Date;
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

public class PartCategoryController extends Controller{
	/*
	 * Finder for PartCategory class
	 */

	public static Finder<Long, PartCategory> find = new Finder<Long, PartCategory>(
			PartCategory.class);

	static Form<PartCategory> partCategoryForm = Form.form(PartCategory.class);

	/**
	 * Renders the Form for adding new part category
	 * 
	 * @return
	 */
	public Result addPartCategoryView() {
		return ok(addPartCategoryForm.render());
	}

	/**
	 * First checks if the part form has errors. Creates a new part or renders
	 * the view again if any error occurs.
	 * 
	 * @return redirect to create part view
	 * @throws ParseException
	 */
	public Result addNewPartCategory() {
		Form<PartCategory> partCategoryForm = Form.form(PartCategory.class).bindFromRequest();
//		if (form.hasErrors() || form.hasGlobalErrors()) {
//			Logger.info("Part update error");
//			flash("error", "Error in part form");
//			return ok(addPartForm.render());
//		}
		try {
			String name =partCategoryForm.bindFromRequest().get().name;
			String description = partCategoryForm.bindFromRequest().get().description;
			PartCategory pc = PartCategory.saveToDB(name, description);
			
			flash("success",  " PART CATEGORY SUCCESSFULLY ADDED!");
			return ok(listAllPartCategories.render(PartCategory.allPartCategories()));
			
		} catch (Exception e) {
			flash("error", "ERROR ADDING PART CATEGORY");
			Logger.error("Error ADDING PART CATEGORY: " + e.getMessage(), e);
			return ok(listAllPartCategories.render(PartCategory.allPartCategories()));
		}
	}

	public Result addNewPartCategory2() {
		Form<PartCategory> partCategoryForm = Form.form(PartCategory.class).bindFromRequest();
//		if (form.hasErrors() || form.hasGlobalErrors()) {
//			Logger.info("Part update error");
//			flash("error", "Error in part form");
//			return ok(addPartForm.render());
//		}
		try {
			String name =partCategoryForm.bindFromRequest().get().name;
			String description = partCategoryForm.bindFromRequest().get().description;
			PartCategory pc = PartCategory.saveToDB(name, description);
			flash("success",  " PART CATEGORY SUCCESSFULLY ADDED!");
			return ok(addPartForm.render());			
		} catch (Exception e) {
			flash("error", "ERROR ADDING PART CATEGORY");
			Logger.error("Error ADDING PART CATEGORY: " + e.getMessage(), e);
			return ok(addPartForm.render());
			}
	}
	
	/**
	 * Finds part using id and shows it
	 * 
	 * @param id
	 *            - Part id
	 * @return redirect to the part view
	 */
	public Result showPartCategory(long id) {
		PartCategory pc = PartCategory.find.byId(id);
		if (pc == null) {
			Logger.error("error", "PART CATEGORY NULL");
			flash("error", "NO PART CATEGORY!");
			return redirect("/");
		}
		return ok(showPartCategory.render(pc));
		//return ok("/");
	}

	public Result listPartCategories() {
		if (PartCategory.allPartCategories() == null) {
			return ok(listAllPartCategories.render(new ArrayList<PartCategory>()));
		}
		return ok(listAllPartCategories.render(PartCategory.allPartCategories()));
	}

	/**
	 * Renders the view for editing Part Category
	 * 
	 * @param id - ID number
	 * @return Result
	 */
	public Result editPartCategoryView(long id) {
		PartCategory pc = PartCategory.findById(id);
		// Exception handling.
		if (pc == null) {
			flash("error", "PART IS NULL");
			return redirect("/");
		}
		return ok(editPartCategoryView.render(pc));
	}

	/**
	 *  First finds the specific Part Category object,
	 * then edits  its properties with values received through the form
	 * @param id -id of part category object
	 * @return Result 
	 */
	public Result editPartCategory(long id) {
		Form<PartCategory> partCategoryForm = Form.form(PartCategory.class).bindFromRequest();
		DynamicForm partCategoryDynamicForm = Form.form().bindFromRequest();
		PartCategory pc = PartCategory.findById(id);
//		if (form.hasErrors() || form.hasGlobalErrors()) {
//			Logger.info("Part update error");
//			flash("error", "Error in part form");
//			return badRequest(editPartView.render(part));
//}
		String pCategoryName=null;
		String pCategoryDesc=null;
		try {
			pCategoryName = partCategoryForm.bindFromRequest().get().name;
			pCategoryDesc = partCategoryForm.bindFromRequest().get().description;
			pc.description = pCategoryDesc;
			pc.name=pCategoryName;
				pc.save();
			Logger.info("PART CATEGORY UPDATED: " + pc.name);
			flash("success", pc.name + "PART CATEGORY SUCCESSFULLY UPDATED!");
			return ok(listAllPartCategories.render(PartCategory.allPartCategories()));
		} catch (Exception e) {
			flash("error", "Error  editing part category");
			Logger.error("Error  editing part category: " + e.getMessage(), e);
			return ok(editPartCategoryView.render(pc));
		}
	}

	/**
	 * Delete Part using id
	 * 
	 * @param id
	 *            - Part id (long)
	 * @return redirect to index after delete
	 */
	// @Security.Authenticated(AdminFilter.class)
	public Result deletePartCategory(long id) {
		try {
			PartCategory pc = PartCategory.findById(id);
			PartCategory.deletePartCategory(id);
			Logger.info("Deleted part category: \"" + pc.name + "\"");
			flash("success", "PART CATEGORY SUCCESSFULLY DELETED!");
			return redirect("/allPartCategories");
		} catch (Exception e) {
			flash("error", "Error at delete part!");
			Logger.error("Error at delete Part: " + e.getMessage());
			return ok(listAllPartCategories.render(PartCategory.allPartCategories()));
		}
	}
}
