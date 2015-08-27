package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class TruckCompositionController extends Controller {

	static Form<TruckComposition> truckCmpForm = Form.form(TruckComposition.class);

	/**
	 * Finder for TruckComposition class
	 */
	public static Finder<Long, TruckComposition> find = new Finder<Long, TruckComposition>(TruckComposition.class);
	
	
	/**
	 * Renders the view for creating TruckComposition object
	 * @return
	 */
	public Result createTruckCompositionView() {
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Vehicle> availableTrailers=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equalsIgnoreCase("trailer") && v.isLinked==false){
				availableTrailers.add(v);
			}
		}
		if(availableTrailers.size()==0){
			flash("noTrailers",  "NO AVAILABLE TRAILERS!");
			System.out.println("NO AVAILABLE TRAILER OBJECTS ////////////////////");
			return redirect("/");
		}
		List<Vehicle> availableTrucks=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equalsIgnoreCase("truck") && v.isLinked==false){
				availableTrucks.add(v);
			}
		}
		
		return ok(createTruckCompView.render(availableTrucks,availableTrailers));
	}
	
	public Result createTruckComposition(){
		DynamicForm dynamicTruckCompForm = Form.form().bindFromRequest();
		Long truckId;
		Long trailerId;
		String truckIdStr;
		String trailerIdStr;
		Vehicle truck;
		Vehicle trailer;
		try {
			truckIdStr=dynamicTruckCompForm.get("truckID");
			truckId= Long.parseLong(truckIdStr);
			truck=Vehicle.findById(truckId);
			trailerIdStr=dynamicTruckCompForm.get("trailerID");
			trailerId= Long.parseLong(trailerIdStr);
			trailer=Vehicle.findById(trailerId);
		} catch (IllegalStateException e) {
			flash("add_truckComposition_null_field",
					Messages.get("Please fill all the fields in the form!"));
			return redirect("/createtruckcompositionview");
		}
		TruckComposition tc=new TruckComposition();
		tc.truckVehicles.addFirst(truck);
		truck.isLinked=true;
		truck.save();
		tc.truckVehicles.addLast(trailer);
		trailer.isLinked=true;
		trailer.save();
		System.out.println("TRUCK COMPOSITION ADDED: " + tc.id);
		return ok(showTruckComposition.render(tc));
	}
	
	
//	public Result attachTrailerView(long truckId) {
//		Vehicle truck=Vehicle.findById(truckId);
//		if(!truck.typev.name.equals("truck")){
//			flash("noTruck",  "VEHICLE IS NOT TRUCK!");
//			return redirect("/showvehicle/"+truck.id);
//		}
//		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
//		allVehicles=Vehicle.find.all();
//		List<Vehicle> trailers=new ArrayList<Vehicle>();
//		for(Vehicle v:allVehicles){
//			if(v.typev.name.equals("trailer")){
//				trailers.add(v);
//			}
//		}
//		if(trailers.size()==0){
//			flash("noTrailers",  "No trailers!");
//			System.out.println("NO TRAILER OBJECTS ////////////////////");
//			return redirect("/");
//		}
//		return ok(attachTrailerView.render(truck,trailers));
//		
//	}
//	public Result attachTrailer(long id) {
//		DynamicForm attachTrailerForm = Form.form().bindFromRequest();
//		Vehicle truckHead=Vehicle.findById(id);
//	   Form<TruckComposition> TruckCform = Form.form(TruckComposition.class).bindFromRequest();
//		/*if (addTravelOrderForm.hasErrors() || addTravelOrderForm.hasGlobalErrors()) {
//			Logger.debug("Error at apublic Result listVehicles() {
//		if(Vehicle.listOfVehicles() == null)
//			return ok(listAllVehicles.render(new ArrayList<Vehicle>()));
//		return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
//	}dding Travel Order");
//			flash("error", "Error at Travel Order form!");
//			return redirect("/addTravelOrder");
//		}*/
//		String trailerName;
//		try{	
//			
//			trailerName = attachTrailerForm.get("trailerName");
//			Vehicle lastTrailer=Vehicle.findByName(trailerName);
//			if(lastTrailer==null){
//				flash("TrailerIsNull",  "Trailer is null!");
//				return redirect("/");
//
//			}
//			TruckComposition tc=TruckComposition.saveToDB();
//			tc.truckVehicles.addFirst(truckHead);
//			tc.truckVehicles.addLast(lastTrailer);
//		tc.save();
//			Logger.info("ATTACHED TRAILER TO THE TRUCK "+truckHead.id);
//			if(tc!=null){
//				flash("success",  "TRUCK COMPOSITION ADDED!");
//			//return redirect("/alltruckcompositions");
//				return redirect("/alltruckcompositions");
//			}
//			else{
//				flash("error", "Error at adding Travel Order ");
//				//return redirect("/alltravelorderview");
//				return redirect("/");
//
//			}
//		}catch(Exception e){
//		flash("error", "Error at creating Truck Composition ");
//		Logger.error("Creating truck composition error: " + e.getMessage(), e);
//		//return redirect("/addtravelorderview");
//		return redirect("/");
//
//	   }
//	}
	
	public Result editTruckCompositionView(long id) {
		TruckComposition tc = TruckComposition.findById(id);
		// Exception handling.
		if (tc == null) {
			flash("error", "No such Truck composition in database");
			return redirect("/");
		}
		//Form<Vehicle> form = Form.form(Vehicle.class).fill(v);
		return ok(editTruckCompositionView.render(tc));

	}
	
	public Result editTruckComposition(long id) {
		DynamicForm updateTruckcForm = Form.form().bindFromRequest();
		Form<TruckComposition> form = Form.form(TruckComposition.class).bindFromRequest();
		TruckComposition tc = TruckComposition.findById(id);
		try {
			if (form.hasErrors() || form.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editTruckCompositionView.render(tc));
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
	
	public Result listTruckCompositions() {
		List<TruckComposition> allTruckComps=new ArrayList<TruckComposition>();
		allTruckComps=TruckComposition.find.all();
		if(allTruckComps!=null){
		return ok(listAllTruckComps.render(allTruckComps));
		}
		else{
			flash("listTOerror", "NO TRUCK COMPOSITIONS RECORDS IN DATABASE!");
				return redirect("/");
		}
	}
	
	public Result showTruckComposition(long id) {
		TruckComposition tc = TruckComposition.findById(id);
		if (tc == null) {
			Logger.error("TRUCK COMPOSITION NULL ///////////////////");
			flash("error", "Truck composition is null!");
			return redirect("/");
		}
		return ok(showTruckComposition.render(tc));
	}
	
//	public Result deleteTruckComposition(long id) {
//		try {
//			TruckComposition tc = TruckComposition.findById(id);
//			Logger.info("Deleted truck composition: \"" + tc.id + "\"");
//			tc.delete();
//			TruckComposition.deleteTruckComposition(id);
//			return redirect("/");
//		} catch (Exception e) {
//			flash("error", "Error at deleting truck composition!");
//			Logger.error("Error at deleting Truck composition: " + e.getMessage());
//			return redirect("/");
//		}
//	}

	
	}

