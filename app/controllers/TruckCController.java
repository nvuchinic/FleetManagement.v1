package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class TruckCController extends Controller {

	static Form<TruckC> truckCForm = Form.form(TruckC.class);

	/**
	 * Finder for TruckC class
	 */
	public static Finder<Long, TruckC> find = new Finder<Long,TruckC >(Long.class,
			TruckC.class);
	
	
	/**
	 * Renders the 'create truckC' page
	 * @return
	 */
	public Result attachTrailerView(long truckHeadId) {
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Vehicle> trailers=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equals("trailer")){
				trailers.add(v);
			}
		}
		if(trailers.size()==0){
			flash("noTrailers",  "No trailers!");

			return redirect("/");
		}
		Vehicle truckHead=Vehicle.findById(truckHeadId);
		if(truckHead==null){
			flash("noTruckHead",  "You must first add Truck!");

			return redirect("/");
		}
		return ok(attachTrailerView.render(truckHead,trailers));
	}
	
	
	public Result attachTrailer(long id) {
		DynamicForm attachTrailerForm = Form.form().bindFromRequest();
		Vehicle truckHead=Vehicle.findById(id);
	   Form<TruckC> TruckCform = Form.form(TruckC.class).bindFromRequest();
		/*if (addTravelOrderForm.hasErrors() || addTravelOrderForm.hasGlobalErrors()) {
			Logger.debug("Error at apublic Result listVehicles() {
		if(Vehicle.listOfVehicles() == null)
			return ok(listAllVehicles.render(new ArrayList<Vehicle>()));
		return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
	}dding Travel Order");
			flash("error", "Error at Travel Order form!");
			return redirect("/addTravelOrder");
		}*/
		String trailerName;
		try{	
			
			trailerName = attachTrailerForm.get("trailerName");
			Vehicle lastTrailer=Vehicle.findByName(trailerName);
			if(lastTrailer==null){
				flash("TrailerIsNull",  "Trailer is null!");
				return redirect("/");

			}
			//driverName=addTravelOrderForm.bindFromRequest().get().driverName;
			//Driver d=Driver.findByName(driverName);
			
			TruckC tc=TruckC.saveToDB(truckHead, lastTrailer);
			
		
			Logger.info(session("name") + " created Travel Order ");
			if(tc!=null){
				flash("success",  "Truck Composition added!");
			//return redirect("/alltruckcompositions");
				return redirect("/");
			}
			else{
				flash("error", "Error at adding Travel Order ");
				//return redirect("/alltravelorderview");
				return redirect("/");

			}
		}catch(Exception e){
		flash("error", "Error at creating Truck Composition ");
		Logger.error("Creating truck composition error: " + e.getMessage(), e);
		//return redirect("/addtravelorderview");
		return redirect("/");

	   }
	}
	
	public Result editTruckcView(long id) {
		TruckC tc = TruckC.findById(id);
		// Exception handling.
		if (tc == null) {
			flash("error", "No such Truck composition in database");
			return redirect("/");
		}
		//Form<Vehicle> form = Form.form(Vehicle.class).fill(v);
		return ok(editTruckcView.render(tc));

	}
	
	public Result editTruckc(long id) {
		DynamicForm updateTruckcForm = Form.form().bindFromRequest();
		Form<TruckC> form = Form.form(TruckC.class).bindFromRequest();
		TruckC tc = TruckC.findById(id);
		try {
			if (form.hasErrors() || form.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editTruckcView.render(tc));
			}

			/*v.vid = vehicleForm.bindFromRequest().data().get("vid");

			String ownerName = vehicleForm.bindFromRequest().data().get("ownerName");
				
			String ownerEmail = vehicleForm.bindFromRequest().data().get("ownerEmail");
			
			
			String typeName = vehicleForm.bindFromRequest().data().get("typeName");
			
			String description = vehicleForm.bindFromRequest().data().get("typeDescription");
			
			String fleetName = vehicleForm.bindFromRequest().data().get("fleetName");
			
			Fleet f;
			if(Fleet.findByName(fleetName) == null) {
				f = new Fleet("", 0);
				f.save();
			} else {
				f = Fleet.findByName(fleetName);
				f.save();
			}
			
			Type t;
			if(Type.findByName(typeName) == null) {
				t = new Type(typeName, description);
				t.save();
			} else {
				t = Type.findByName(typeName);
				t.description = description;
				t.save();
			}
			
			Owner o;
			if(Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			} else {
				o = Owner.findByName(ownerName);
				o.save();
			}
			Vehicles
			v.typev = t;
			
			v.owner = o;
			
			v.fleet = f;
			
			v.save();
			*/
			Logger.info(session("name") + " updated vehice: " + tc.id);
			//flash("success", v.vid + " successfully updated!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
	public Result listTruckcs() {
		List<TruckC> allTruckcs=new ArrayList<TruckC>();
		allTruckcs=TruckC.find.all();
		if(allTruckcs!=null){
		return ok(listAllTruckcs.render(allTruckcs));
		}
		else{
			flash("listTOerror", "No Truck compositions in database!");
				return redirect("/");
		}
	}
	
	public Result showTruckc(long id) {
		TruckC tc = TruckC.findById(id);
		if (tc == null) {
			Logger.error("error", "Truck composition null at showVehicle()");
			flash("error", "Truck composition is null!");
			return redirect("/");
		}
		return ok(showTruckc.render(tc));
	}
	
	public Result deleteTruckc(long id) {
		try {
			TruckC tc = TruckC.findById(id);
			Logger.info("Deleted truck composition: \"" + tc.id + "\"");
			tc.delete();
			TruckC.deleteTruckcc(id);
			return redirect("/");
		} catch (Exception e) {
			flash("error", "Error at deleting truck composition!");
			Logger.error("Error at deleting Truck composition: " + e.getMessage());
			return redirect("/");
		}
	}

	
	}

