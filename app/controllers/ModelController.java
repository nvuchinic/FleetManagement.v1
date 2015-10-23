package controllers;

import java.util.List;

iimport java.sql.Date;
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
public class ModelController extends Controller{
	/**
	 * finder for VehicleModel object
	 */
	public static Finder<Long, VehicleModel> find = new Finder<Long, VehicleModel>(
			VehicleModel.class);

	/**
	 * Generates view(form) for adding new VehicleModel object
	 * 
	 * @return
	 */
	//public Result addModelView() {
	//	return ok(addModelForm.render());
	//}

	/**
	 * 
	 * Creates a new VehicleModel object or renders the view again if any error
	 * occurs.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addModel() {
		DynamicForm dynamicModelForm = Form.form().bindFromRequest();
		Form<VehicleBrand> addModelForm = Form.form(VehicleModel.class).bindFromRequest();
		/*
		 * if (addClientForm.hasErrors() || addClientForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Client"); flash("error",
		 * "Error at Client adding  form!"); return redirect("/allclients"); }
		 */
		String modelName;
		String brandName;
		try {
			modelName = addModelForm.bindFromRequest().get().name;
			brandName = dynamicModelForm.get("brandName");
			
			System.out.println("MODEL ADDED SUCCESSFULLY///////////////////////");
			Logger.info("MODEL ADDED SUCCESSFULLY///////////////////////");
			flash("success", "MODEL SUCCESSFULLY ADDED");
			return redirect("/allmodels");
		} catch (Exception e) {
			flash("error", "ERROR AT ADDING MODEL ");
			Logger.error("ADDING MODEL ERROR: " + e.getMessage(), e);
			return redirect("/allmodels");
		}
	}

	/**
	 * Finds VehicleModel object based on passed ID number as parameter and shows it
	 * in view
	 * @param id- VehicleModel object ID
	 * @return
	 */
	public Result showModel(long id) {
		VehicleModel vm = VehicleModel.findById(id);
		if (vm== null) {
			Logger.error("error", "VEHICLE MODEL IS NULL");
			flash("error", "NO VEHICLE MODEL RECORD IN DATABASE!!!");
			return redirect("/allmodels");
		}
		return ok(showModel.render(vm));
	}

	/**
	 * Finds VehicleModel object using passed ID number as parameter, and then removes
	 * it from database
	 * 
	 * @param id
	 *            - VehicleModel object ID
	 * @return
	 */
	public Result deleteModel(long id) {
		try {
			VehicleModel vm = VehicleModel.findById(id);
			Logger.info("VEHICLE MODEL DELETED: \"" + vm.id);
			VehicleModel.deleteVehicleModel(id);
			return redirect("/allmodels");
		} catch (Exception e) {
			flash("deleteModel	Error", "ERROR DELETING MODEL!");
			Logger.error("ERROR DELETING MODEL: " + e.getMessage());
			return redirect("/allmodels");
		}
	}

	/**
	 * Renders the view for editing VehicleModel object.
	 * @param id-VehicleModel object ID number
	 * @return
	 */
	public Result editModelView(long id) {
		VehicleModel vm = VehicleModel.findById(id);
		// Exception handling.
		if (vm == null) {
			flash("modelNull", "NO VEHICLE MODEL RECORD IN DATABASE");
			return redirect("/allmodels");
		}
		return ok(editBrandView.render(vm));

	}

	/**
	 * finds the specific VehicleModel object using passed ID parameter, and updates
	 * it with information collected from editModel view form again.
	 * 
	 * @param id- ID number of VehicleModel object
	 * @return Result
	 */
	public Result editModel(long id) {
		DynamicForm dynamicModelForm = Form.form().bindFromRequest();
		Form<VehicleModel> modelForm = Form.form(VehicleModel.class).bindFromRequest();
		VehicleModel vm = VehicleModel.findById(id);
		String modelName;
		String brandName;
	
		try {
			modelName = modelForm.bindFromRequest().get().name;
			brandName = dynamicModelForm.get("brandName");
			VehicleBrand vb=VehicleBrand.findByName(brandName);
			vm.name = modelName;
			vm.vehicleBrand=vb;
			vm.save();
			Logger.info("VEHICLE MODEL UPDATED");
			flash("ModelUpdateSuccess", "VEHICLE MODEL UPDATED SUCCESSFULLY!");
			return ok(showModel.render(vm));
		} catch (Exception e) {
			flash("updateBrandError", "ERROR AT EDITING VEHICLE MODEL ");
			Logger.error("EDITING MODEL ERROR: " + e.getMessage(), e);
			return redirect("/editmodelview");
		}
	}

	public Result listModels() {
		List<VehicleModel> allModels = VehicleModel.listOfVehicleModels();
		if (allModels != null) {
			return ok(listAllModels.render(allModels));
		} else {
			flash("listModelsError", "NO VEHICLE MODEL RECORDS IN DATABASE!");
			return redirect("/");
		}
	}
}
