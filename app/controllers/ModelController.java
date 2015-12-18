package controllers;

import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.*;
import controllers.*;
import com.avaje.ebean.Model.Finder;
import play.*;
import play.mvc.*;
import play.Logger;
//import play.api.Routes;
//import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
//import router.Routes;
import views.html.*;


public class ModelController extends Controller{
	/**
	 * finder for VehicleModel object
	 */
	public static Finder<Long, VehicleModel> find = new Finder<Long, VehicleModel>(
			VehicleModel.class);

//	/**
//	 * Generates view(form) for adding new VehicleModel object
//	 * 
//	 * @return
//	 */
//	public Result addModelView() {
//		if(VehicleBrand.listOfVehicleBrands().size()==0){
//			flash("error", "CANNOT CREATE VEHICLE MODEL. THERE IS NO AVAILABLE VEHICLE BRANDS TO ASSOCIATE IT WITH!");
//			return ok(listAllModels.render(VehicleModel.listOfVehicleModels()));
//		}
//		return ok(addModelForm.render());
//	}

	/**
	 * 
	 * Creates a new VehicleModel object or renders the view again if any error
	 * occurs.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addModel(long brandId) {
		DynamicForm dynamicModelForm = Form.form().bindFromRequest();
		Form<VehicleModel> addModelForm = Form.form(VehicleModel.class).bindFromRequest();
		/*
		 * if (addClientForm.hasErrors() || addClientForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Client"); flash("error",
		 * "Error at Client adding  form!"); return redirect("/allclients");
		 *  }
		 */
		String modelName;
		String typeName;
		VehicleBrand vb=null;
		try {
			modelName = dynamicModelForm.get("modelName");
			vb=VehicleBrand.findById(brandId);
			Type t=vb.typev;
			typeName=t.name;
			for(VehicleModel vm:VehicleModel.findByBrandAndType(vb, typeName)){
				if(modelName.equalsIgnoreCase(vm.name)  ){
					flash("error", "CANNOT ADD THAT VEHICLE BRAND!IT ALREADY EXISTS!");
					return redirect("/showbrand/"+brandId);
				}
			}
			VehicleModel vm=VehicleModel.saveToDB(modelName, vb);
			System.out.println("MODEL ADDED SUCCESSFULLY///////////////////////");
			Logger.info("MODEL ADDED SUCCESSFULLY///////////////////////");
			flash("success", "MODEL SUCCESSFULLY ADDED");
			return redirect("/showbrand/"+vb.id);
		} catch (Exception e) {
			flash("error", "ERROR AT ADDING MODEL ");
			Logger.error("ADDING MODEL ERROR: " + e.getMessage(), e);
			return redirect("/showbrand/"+vb.id);
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
			flash("error", "NO BRAND MODEL RECORD IN DATABASE!!!");
			return redirect("/showbrand/"+vm.vehicleBrand.id);
		}
		return ok(showModel.render(vm));
	}

	/**
	 * Finds VehicleModel object using passed ID number as parameter, and then removes
	 * it from database
	 * 
	 * @param id- VehicleModel object ID
	 * @return
	 */
	public Result deleteModel(long id) {
		VehicleModel vm=null;
		try {
			vm = VehicleModel.findById(id);
			Logger.info("VEHICLE MODEL DELETED: \"" + vm.id);
			VehicleModel.deleteVehicleModel(id);
			return redirect("/showbrand/"+vm.vehicleBrand.id);
		} catch (Exception e) {
			flash("error", "ERROR DELETING MODEL!");
			Logger.error("ERROR DELETING MODEL: " + e.getMessage());
			return redirect("/showbrand/"+vm.vehicleBrand.id);
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
			return redirect("/showbrand/"+vm.vehicleBrand.id);
		}
		return ok(editModelView.render(vm));

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
		String typeName;
		try {
			modelName = modelForm.bindFromRequest().get().name;
			brandName = dynamicModelForm.get("brandName");
			VehicleBrand vb=VehicleBrand.findByName(brandName);
			typeName = dynamicModelForm.get("typeName");
			Type t=Type.findByName(typeName);
			vm.name = modelName;
			vm.vehicleBrand=vb;
			vm.vehicleBrand.typev=t;
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
	
	
	public Result getBrandModelsToJson(String brandName){
		return ok(Json.toJson(VehicleModel.findByBrandName(brandName)));
	}
	
	
	public Result getBrandModelsNames(String brandName){
		System.out.println("PRINTING MODEL NAMES");
		String[] modelNames=VehicleModel.modelsByBrand(brandName);
		for(int  i=0;i<modelNames.length;i++){
			if(i<(modelNames.length-1)){
			modelNames[i]=modelNames[i]+",";
			System.out.println(modelNames[i].toUpperCase()+"////////////////////////");
		}
			}
		return ok(ajax_result.render(modelNames));
	}
	
	
	public  Result jsRoutes()
	{
	    response().setContentType("text/javascript");
	    return ok(Routes.javascriptRouter("jsRoutes", 

    		controllers.routes.javascript.ModelController.getBrandModelsNames(),
    		controllers.routes.javascript.RenewalNotificationController.noOfNotifications(),
	                                      controllers.routes.javascript.ModelController.getBrandModelsToJson()));
	}
}
