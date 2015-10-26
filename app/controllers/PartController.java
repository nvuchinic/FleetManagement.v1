package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.*;

import com.avaje.ebean.Model.Finder;

public class PartController extends Controller {

	/*
	 * Finder for PartCategory class
	 */

	public static Finder<Long, PartCategory> find = new Finder<Long, PartCategory>(
			PartCategory.class);

	static Form<Part> partForm = Form.form(Part.class);

	/**
	 * Renders the 'create part' page
	 * 
	 * @return
	 */
	public Result addPartView() {
		return ok(addPartForm.render());
	}

	/**
	 * First checks if the part form has errors. Creates a new part or renders
	 * the view again if any error occurs.
	 * 
	 * @return redirect to create part view
	 * @throws ParseException
	 */
	public Result addPart() {
		Form<Part> form = Form.form(Part.class).bindFromRequest();
		DynamicForm dynamicPartForm = Form.form().bindFromRequest();
//		if (form.hasErrors() || form.hasGlobalErrors()) {
//			Logger.info("Part update error");
//			flash("error", "Error in part form");
//			return ok(addPartForm.render());
//		}
		try {
			String measurementUnitName=dynamicPartForm.get("measurementUnitName");
			System.out.println("PRINTING MEASUREMENT UNIT NAME :"+measurementUnitName);
			MeasurementUnit mu=MeasurementUnit.findByName(measurementUnitName);
			String name = form.bindFromRequest().get().name;
			long number = form.bindFromRequest().get().number;
			String description = form.bindFromRequest().get().description;
			double cost = form.bindFromRequest().get().cost;
			String manufacturer = form.bindFromRequest().get().manufacturer;
			String vendorName = form.bindFromRequest().field("vendorName")
					.value();
			System.out.println("////////////////////ISPISUJEM VENDOR NAME: "+vendorName);
			if(vendorName==null || vendorName.equalsIgnoreCase("") || vendorName.isEmpty()){
				flash("error", "YOU MUST SELECT VENDOR");
				return redirect ("/addPartView");
			}
			String categoryName = form.bindFromRequest().field("categoryName")
					.value();
			System.out.println("////////////////////////ISPISUJEM CATEGORY_NAME "+categoryName);
			if(categoryName==null || categoryName.equalsIgnoreCase("") || categoryName.isEmpty()){
				flash("error", "YOU MUST SELECT PART CATEGORY");
				return redirect ("/addPartView");
			}
			PartCategory pt=PartCategory.findByName(categoryName);
			Vendor v=null;
			if (Vendor.findByName(vendorName) != null) {
				v = Vendor.findByName(vendorName);
				v.save();
			}
			if (Part.findByNumber(number) != null) {
				Logger.info("error at adding part: part number already exists");
				flash("error", "Part number already exists!");
				return badRequest(addPartForm.render());
			}
			Part part = Part.saveToDB(name, number, pt, cost,
					manufacturer, description, v, mu);
			Logger.info("added part: " + part.name);
			flash("success", "PART SUCCESSFULLY ADDED");
			return ok(listAllParts.render(Part.allParts()));
			
		} catch (Exception e) {
			flash("error", "Error at editing part");
			Logger.error("Error at update part: " + e.getMessage(), e);
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
	public Result showPart(long id) {
		Part part = Part.find.byId(id);
		if (part == null) {
			Logger.error("error", "Part null at showPart()");
			flash("error", "Something went wrong!");
			return redirect("/");
		}
		return ok(showPart.render(part));
	}

	public Result listParts() {
		if (Part.allParts() == null) {

			return ok(listAllParts.render(new ArrayList<Part>()));
		}
		return ok(listAllParts.render(Part.allParts()));
	}

	/**
	 * Renders the view of a Part. Method receives the id of the part and finds
	 * the part by id and send's the part to the view.
	 * 
	 * @param id
	 *            long
	 * @return Result render PartView
	 */
	public Result editPartView(long id) {
		Part part = Part.findById(id);
		// Exception handling.
		if (part == null) {
			flash("error", "Part is not exists");
			return redirect("/");
		}
		return ok(editPartView.render(part));
	}

	/**
	 * Edit Part Method receives an id, finds the specific part and renders the
	 * edit View for the part. If any error occurs, the view is rendered again.
	 * 
	 * @param id
	 *            of part
	 * @return Result render the part edit view
	 */
	public Result editPart(long id) {
		Form<Part> form = Form.form(Part.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();

		Part part = Part.findById(id);
		if (form.hasErrors() || form.hasGlobalErrors()) {
			Logger.info("Part update error");
			flash("error", "Error in part form");
			return badRequest(editPartView.render(part));
		}
		try {
			part.name = form.bindFromRequest().get().name;
			long partNumber = form.bindFromRequest().get().number;
			if (Part.findByNumber(partNumber) != null && partNumber != part.number) {
				Logger.info("error at update part: " + part.name);
				flash("error", part.name + " Part number already exists!");
				return badRequest(editPartView.render(part));
			} else {
				part.number = partNumber;				
			}

			part.description = form.bindFromRequest().get().description;
			part.cost = form.bindFromRequest().get().cost;
			part.manufacturer = form.bindFromRequest().get().manufacturer;
			String vendorName = form.bindFromRequest().field("vendorName")
					.value();
			String categoryName = form.bindFromRequest().field("categoryName")
					.value();
			//String newCategory = form.bindFromRequest().field("newCategory")
			//		.value();

			Vendor v;
			if (Vendor.findByName(vendorName) != null) {
				v = Vendor.findByName(vendorName);
				v.save();
				part.vendor = v;
				part.save();
			}

			PartCategory partCategory;
			//if (!categoryName.equals("New Category")) {
				partCategory = PartCategory.findByName(categoryName);
				partCategory.save();
			//} else {
//				if (newCategory.isEmpty()) {
//					flash("error", "Empty category name");
//					return badRequest(addPartForm.render());
//				}
				/*String newC = newCategory.toLowerCase();
				newC = Character.toUpperCase(newC.charAt(0))
						+ newC.substring(1);
				if (PartCategory.findByName(newC) != null) {
					partCategory = PartCategory.findByName(newC);
					partCategory.save();
				} else {
					partCategory = new PartCategory(newC);
					partCategory.save();
				}*/
			//}

			//partCategory.save();
			part.partCategory = partCategory;
			part.save();
			Logger.info("updated part: " + part.name);
			flash("success", part.name + " successfully updated!");
			return ok(listAllParts.render(Part.allParts()));
		} catch (Exception e) {
			flash("error", "Error at editing part");
			Logger.error("Error at update part: " + e.getMessage(), e);
			return ok(editPartView.render(part));
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
	public Result deletePart(long id) {
		try {
			Part p = Part.findById(id);
			Logger.info("Deleted part: \"" + p.name + "\"");
			Part.deletePart(id);
			flash("success", "Part successfully deleted!");
			return ok(listAllParts.render(Part.allParts()));
		} catch (Exception e) {
			flash("error", "Error at delete part!");
			Logger.error("Error at delete Part: " + e.getMessage());
			return ok(listAllParts.render(Part.allParts()));
		}
	}
}
