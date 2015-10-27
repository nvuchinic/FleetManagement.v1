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

public class BrandController extends Controller{
	/**
	 * finder for VehicleBrand object
	 */
	public static Finder<Long, VehicleBrand> find = new Finder<Long, VehicleBrand>(
			VehicleBrand.class);

	/**
	 * Generates view(form) for adding new Brand object
	 * 
	 * @return
	 */
	public Result addBrandView() {
		if(Type.typesList().size()==0){
			flash("error", "CANNOT CREATE VEHICLE BRAND. THERE IS NO AVAILABLE VEHICLE TYPES TO ASSOCIATE IT WITH!");
			return ok(listAllBrands.render(VehicleBrand.listOfVehicleBrands()));
		}
		return ok(addBrandForm.render());
	}

	/**
	 * 
	 * Creates a new Client object or renders the view again if any error
	 * occurs.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addBrand() {
		DynamicForm dynamicBrandForm = Form.form().bindFromRequest();
		Form<VehicleBrand> addBrandForm = Form.form(VehicleBrand.class).bindFromRequest();
		/*
		 * if (addClientForm.hasErrors() || addClientForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Client"); flash("error",
		 * "Error at Client adding  form!"); return redirect("/allclients"); }
		 */
		String brandName;
		String typeName;
		try {
			typeName=dynamicBrandForm.get("typeName");
			Type t=Type.findByName(typeName);
			List<VehicleBrand> sameTypeBrands=new ArrayList<VehicleBrand>();
			sameTypeBrands=VehicleBrand.findByType(t);
			brandName = addBrandForm.bindFromRequest().get().name;
			for(VehicleBrand vb:sameTypeBrands){
				if(brandName.equalsIgnoreCase(vb.name)){
					flash("error", "CANNOT ADD THAT VEHICLE BRAND! IT ALREADY EXISTS!");
					return redirect("/addbrandview");
				}
			}
			VehicleBrand vb=VehicleBrand.saveToDB(brandName, t);
			System.out.println("BRAND ADDED SUCCESSFULLY///////////////////////");
			Logger.info("BRAND ADDED SUCCESSFULLY///////////////////////");
			flash("success", "BRAND SUCCESSFULLY ADDED");
			return redirect("/allbrands");
		} catch (Exception e) {
			flash("error", "ERROR AT ADDING BRAND ");
			Logger.error("ADDING BRAND ERROR: " + e.getMessage(), e);
			return redirect("/allbrands");
		}
	}

	/**
	 * Finds VehicleBrand object based on passed ID number as parameter and shows it
	 * in view
	 * @param id- VehicleBrand object ID
	 * @return
	 */
	public Result showBrand(long id) {
		VehicleBrand vb = VehicleBrand.findById(id);
		if (vb== null) {
			Logger.error("error", "VEHICLE BRAND IS NULL");
			flash("error", "NO VEHICLE BRAND RECORD IN DATABASE!!!");
			return redirect("/allbrands");
		}
		return ok(showBrand.render(vb));
	}

	/**
	 * Finds VehicleBrand object using passed ID number as parameter, and then removes
	 * it from database
	 * 
	 * @param id - VehicleBrand object ID
	 * @return
	 */
	public Result deleteBrand(long id) {
		try {
			VehicleBrand vb = VehicleBrand.findById(id);
			Logger.info("VEHICLE BRAND DELETED: \"" + vb.id);
			VehicleBrand.deleteVehicleBrand(id);
			return redirect("/allbrands");
		} catch (Exception e) {
			flash("deleteBrand	Error", "ERROR DELETING BRAND!");
			Logger.error("ERROR DELETING BRAND: " + e.getMessage());
			return redirect("/allbrands");
		}
	}

	/**
	 * Renders the view for editing VehicleBrand object.
	 * @param id-VehicleBrand object ID number
	 * @return
	 */
	public Result editBrandView(long id) {
		VehicleBrand vb = VehicleBrand.findById(id);
		// Exception handling.
		if (vb == null) {
			flash("brandNull", "NO VEHICLE BRAND RECORD IN DATABASE");
			return redirect("/allbrands");
		}
		return ok(editBrandView.render(vb));

	}

	/**
	 * finds the specific VehicleBrand object using passed ID parameter, and updates
	 * it with information collected from editBrand view form again.
	 * 
	 * @param id- ID number of VehicleBrand object
	 * @return Result
	 */
	public Result editBrand(long id) {
		DynamicForm dynamicBrandForm = Form.form().bindFromRequest();
		Form<VehicleBrand> brandForm = Form.form(VehicleBrand.class).bindFromRequest();
		VehicleBrand vb = VehicleBrand.findById(id);
		String brandName;
		String typeName;
		
		try {
			brandName = brandForm.bindFromRequest().get().name;
			typeName = dynamicBrandForm.get("typeName");
			Type type=Type.findByName(typeName);
			vb.name = brandName;
			vb.typev = type;
			vb.save();
			Logger.info("VEHICLE BRAND UPDATED");
			flash("ClientUpdateSuccess", "CLIENT UPDATED SUCCESSFULLY!");
			return ok(showBrand.render(vb));
		} catch (Exception e) {
			flash("updateBrandError", "ERROR AT EDITING BRAND ");
			Logger.error("EDITING BRAND ERROR: " + e.getMessage(), e);
			return redirect("/editbrandview");
		}
	}

	public Result listBrands() {
		List<VehicleBrand> allBrands = VehicleBrand.listOfVehicleBrands();
		if (allBrands != null) {
			return ok(listAllBrands.render(allBrands));
		} else {
			flash("listBrandsError", "NO VEHICLE BRAND RECORDS IN DATABASE!");
			return redirect("/");
		}
	}
}
