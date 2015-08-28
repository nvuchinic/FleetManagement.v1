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
		tc.save();
		System.out.println("TRUCK COMPOSITION ADDED: " + tc.id);
		return ok(showTruckComposition.render(tc));
	}
	
	
	public Result attachTrailerView(long truckCompID) {
		TruckComposition tc=TruckComposition.findById(truckCompID);
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Vehicle> trailers=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equalsIgnoreCase("trailer") && (!v.isLinked)){
				trailers.add(v);
			}
		}
		if(trailers.size()==0){
			flash("noTrailers",  "NO TRAILERS");
			System.out.println("NO TRAILER OBJECTS ////////////////////");
			return redirect("/showtruckcomposition"+tc.id);
		}
		return ok(attachTrailerView.render(tc,trailers));
		
	}
	
	
	public Result attachTrailer(long tcId) {
		DynamicForm dynamAttachTrailerForm = Form.form().bindFromRequest();
		TruckComposition tc=TruckComposition.findById(tcId);
	   Form<TruckComposition> attachTrailerForm = Form.form(TruckComposition.class).bindFromRequest();
		String trailerName;
		try{	
			String t = attachTrailerForm.bindFromRequest().field("t").value();
			//int num = 0;
			//Vehicle trailer;
			String[] vids = t.split(",");
			List<Vehicle> trailers = new ArrayList<Vehicle>();
			String vi = null;
			for(int i = 0; i < vids.length; i++) {
				vi = vids[i];
				System.out.println("ISPISUJEM NIZ ID STRINGOVA U ATTACH_TRAILER METODI:"+vi);

				long trailerId=Long.parseLong(vi);
				Vehicle trailer=Vehicle.findById(trailerId);
				//num = Integer.parseInt(attachTrailerForm.bindFromRequest().field(vi).value());
				tc.truckVehicles.add(trailer);
				tc.save();
			}
			//	System.out.println("/////////////BROJ IZABRANIH TRAILERA" + num + vi);
			//if (!Vehicle.findByType(vi).isEmpty()) {
				//List<Vehicle> vs = Vehicle.findByType(vi);
//				for(int m = 0; m < num; m++) {
//				vehicles.add(vs.get(m));
//				}
//			//	}
//				System.out.println("/////////////" + num + vi);
//				for(Vehicle v : vehicles) {
//			if (v.fleet != null) {
//				Logger.info("Fleet update error");
//				flash("error", "Vehicle is already in fleet!");
//				return ok(editFleetView.render(f));
//			}
//			f.vehicles.addAll(vehicles);
//			f.numOfVehicles = f.vehicles.size();
//			v.fleet = f;
//			v.isAsigned = true;
//			v.save();
//			f.save();
//				}	
//				vehicles.clear();
		
//			System.out.println("/////////////" + num + vi);
//			Logger.info(session("name") + " updated fleet: " + f.name);
//			flash("success", f.name + " successfully updated!");
		return ok(showTruckComposition.render(tc));

			}
		catch(Exception e){
		flash("error", "ERROR ATTACHING TRAILERS");
		Logger.error("Creating truck composition error: " + e.getMessage(), e);
		System.out.println("ERROR ATTACHING TRAILER////////////////////////////"+e.getMessage());
		//return redirect("/addtravelorderview");
		return redirect("/showtruckcomposition/"+tc.id);

	   }
	}
	
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

