//package controllers;
//
//import play.Logger;
//import play.mvc.Controller;
//import play.mvc.Result;
//import views.html.addVehicleForm;
//import views.html.listAllVehicles;
//import models.Part;
//import models.PartCategory;
//import models.Vehicle;
//
//import com.avaje.ebean.Model.Finder;
//
//public class PartController extends Controller {
//	/*
//	  Finder for PartCategory class
//	 */
//
//	public static Finder<Long, PartCategory> find = new Finder<Long, PartCategory>(
//			PartCategory.class);
//	
//	/**
//	 * Renders the 'create part' page
//	 * @return
//	 */
//	public Result addPartView() {
//		return ok(addPartForm.render());
//	}
//	
//	/**
//	 * Delete Part using id
//	 * @param id - Part id (long)
//	 * @return redirect to index after delete
//	 */
//	// @Security.Authenticated(AdminFilter.class)
//	public Result deletePart(long id) {
//		try {
//			Part p = Part.findById(id);
//			Logger.info("Deleted part: \"" + p.name + "\"");
//			Part.deletePart(id);
//			flash("success", "Part successfully deleted!");
//			return ok(listAllParts.render(Part.allParts()));
//		} catch (Exception e) {
//			flash("error", "Error at delete part!");
//			Logger.error("Error at delete Part: " + e.getMessage());
//			return ok(listAllParts.render(Part.allParts()));
//		}
//	}
//	
//	
//}
