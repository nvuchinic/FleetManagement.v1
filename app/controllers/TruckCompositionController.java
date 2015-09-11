package controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	public Result createCompositionAndAttachTrailerView(long truckId) {
		Vehicle truck =Vehicle.findById(truckId);
		TruckComposition tc=TruckComposition.saveToDB(truck);
		truck.truckComposition=tc;
		truck.isLinked=true;
		truck.position=tc.truckVehicles.indexOf(truck)+1;
		truck.save();
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
			return redirect("/showtruckcomposition/"+tc.id);
		}
		return ok(attachTrailerView.render(tc,trailers));
	}
	
	
	public Result createTruckComposition(){
		DynamicForm dynamicTruckCompForm = Form.form().bindFromRequest();
		Long truckId;
		Long trailerId;
		String truckIdStr;
		String trailerIdStr;
		Vehicle truck;
		Vehicle trailer;
		try 
		{
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
		TruckComposition tc=TruckComposition.saveToDB();
		tc.truckVehicles.add(truck);
		truck.isLinked=true;
		truck.save();
		tc.truckVehicles.add(trailer);
		trailer.isLinked=true;
		trailer.save();
		tc.numOfVehicles=tc.truckVehicles.size();
		tc.save();
		System.out.println("TRUCK COMPOSITION ADDED: " + tc.id);
		return ok(showTruckComposition.render(tc));
	}
	
	
	public Result attachTrailerView(long truckCompID) {
		TruckComposition tc=TruckComposition.find.byId(truckCompID);
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
			return redirect("/showtruckcomposition/"+tc.id);
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
			
			String[] vids = t.split(",");
			List<Vehicle> trailers = new ArrayList<Vehicle>();
			String vi = null;
			for(int i = 0; i < vids.length; i++) {
				vi = vids[i];
				System.out.println("ISPISUJEM NIZ ID STRINGOVA U ATTACH_TRAILER METODI:"+vi);

				long trailerId=Long.parseLong(vi);
				Vehicle trailer=Vehicle.findById(trailerId);
				tc.truckVehicles.add(trailer);
				tc.numOfVehicles=tc.truckVehicles.size();
				tc.save();
				trailer.truckComposition=tc;
				trailer.isLinked=true;
				trailer.position=tc.truckVehicles.indexOf(trailer)+1;
				trailer.save();
			}
			
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
		//List<TruckComposition> allTruckComps=new ArrayList<TruckComposition>();
		List<TruckComposition> allTruckComps=TruckComposition.allTruckComps();
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
	
//	public Result removeTrailer(long truckCompId){
//		TruckComposition tc=TruckComposition.findById(truckCompId);
//		if(tc==null){
//			System.out.println("TRUCK COMPOSITION NULL AT REMOVING TRAILER");
//			return redirect("/showtruckcomposition");
//		}
//		int size=tc.truckVehicles.size();
//		try{
//		Vehicle removedTrailer=	tc.truckVehicles.remove((size-1));
//		removedTrailer.isLinked=false;
//		removedTrailer.truckComposition=null;
//		//removedTrailer.update();
//		tc.update();
//		return ok(showTruckComposition.render(tc));
//		}
//		catch(Exception e) {
//			Logger.error("ERROR REMOVING TRAILER: " + e.getMessage(), e);
//			System.out.println("ERROR REMOVING TRAILER "+ e.getMessage()+e);
//			return redirect("/showtruckcomposition/"+tc.id);
//		}
//	}
	
	public Result removeTrailer(long truckCompId){
		TruckComposition tc=TruckComposition.findById(truckCompId);
		if(tc==null){
			System.out.println("TRUCK COMPOSITION NULL AT REMOVING TRAILER");
			return redirect("/showtruckcomposition");
		}
		int size=tc.truckVehicles.size();
		try{
		Vehicle removedTrailer=	tc.truckVehicles.get(size-1);
		tc.truckVehicles.remove(removedTrailer);
		removedTrailer.isLinked = false;
		removedTrailer.truckComposition = null;
		removedTrailer.save();
		tc.save();
		return ok(showTruckComposition.render(tc));
		}
		catch(Exception e) {
			Logger.error("ERROR REMOVING TRAILER: " + e.getMessage(), e);
			System.out.println("ERROR REMOVING TRAILER "+ e.getMessage()+e);
			return redirect("/showtruckcomposition/"+tc.id);
		}
	}
	
	public Result getNextVehicle(long vId){
		Vehicle v=Vehicle.findById(vId);
		TruckComposition tc=v.truckComposition;
		int vIndex=tc.truckVehicles.indexOf(v);
		int nextVehicleInd=vIndex+1;
		Vehicle nextVehicle=tc.truckVehicles.get(nextVehicleInd);
		return ok(showVehicle.render(nextVehicle));
	}
	
	public Result getPrevVehicle(long vId){
		Vehicle v=Vehicle.findById(vId);
		TruckComposition tc=v.truckComposition;
		int vIndex=tc.truckVehicles.indexOf(v);
		int prevVehicleInd=vIndex-1;
		Vehicle prevVehicle=tc.truckVehicles.get(prevVehicleInd);
		return ok(showVehicle.render(prevVehicle));
	}
	
	
	public Result moveUp(long vId,long tId){
		Vehicle v=Vehicle.findById(vId);
		//Vehicle[] vehiclesArray=null;
		if(v==null){
			System.out.println("THIS VEHICLE IS NULL/////////");
		}
		System.out.println("ID I NAME OVOG VOZILA: "+v.id+"("+v.name+")");
		TruckComposition tc=null;
	try{	
		tc=TruckComposition.findById(tId);
		for(Vehicle vhc:tc.truckVehicles){
			System.out.println("VEHICLE INDEXES BEFORE REORDER "+vhc.vid+"("+vhc.truckComposition.truckVehicles.indexOf(vhc)+")");
		}
		Vehicle vehiclesArray[] = new Vehicle[tc.truckVehicles.size()];
		  // list2 = arrlist.toArray(list2);
		vehiclesArray=tc.truckVehicles.toArray(vehiclesArray);
		System.out.println("ISPISUJEM NIZ VOZILA PRIJE ZAMJENE MJESTA://////////");
		for(int i=0;i<vehiclesArray.length;i++){
			System.out.println(vehiclesArray[i].name+"///////////");
		}
		int thisVehicleInd=tc.truckVehicles.indexOf(v);
		System.out.println("///////////////////INDEX OVOG VOZILA: "+thisVehicleInd);
		int prevVehicleInd=thisVehicleInd-1;
		System.out.println("/////////////////////////INDEX PRETHODNOG VOZILA: "+prevVehicleInd);
		//Vehicle prevVehicle=tc.truckVehicles.get(prevVehicleInd);
		Vehicle prevVehicle=tc.truckVehicles.get(prevVehicleInd);
		if(prevVehicle==null){
			System.out.println("PREVIOUS VEHICLE IS NULL ////////////////");
		}
		Vehicle thisVeh=vehiclesArray[thisVehicleInd];
		Vehicle prevVeh=vehiclesArray[prevVehicleInd];
		vehiclesArray[thisVehicleInd]=prevVeh;
		vehiclesArray[prevVehicleInd]=thisVeh;
		System.out.println("ISPISUJEM NIZ VOZILA POSLE ZAMJENE MJESTA://////////");
		for(int i=0;i<vehiclesArray.length;i++){
			System.out.println(vehiclesArray[i].name+"///////////");
		}
		ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>(Arrays.asList(vehiclesArray));
		tc.truckVehicles=new ArrayList<Vehicle>();
		//tc.save();
		tc.truckVehicles=vehiclesList;
		tc.update();
		//v.truckComposition=tc;
//		tc.truckVehicles.remove(thisVehicleInd);
//		v.truckComposition=null;
//		tc.save();
//		v.save();
//		tc.truckVehicles.remove(prevVehicleInd);
//		tc.save();
//		prevVehicle.truckComposition=null;
//		prevVehicle.save();
		//v.save();
		//RADI DO OVOG DJELA (BRISANJE TRAILER OBJEKTA)///////////////
//		tc.truckVehicles.add(prevVehicleInd, v);
//		tc.save();
//		prevVehicle.truckComposition=tc;
//		prevVehicle.save();
//		tc.truckVehicles.add(thisVehicleInd, prevVehicle);
		//tc.save();
		//v.save();
		//tc.truckVehicles.add(thisVehicleInd, prevVehicle);
		//tc.truckVehicles.add(prevVehicle);
		//tc.save();
		//prevVehicle.truckComposition=tc;
		//prevVehicle.save();
		//v.truckComposition.truckVehicles=truckVehicles;
		for(Vehicle vhc:tc.truckVehicles){
			if(vhc==null){
				System.out.println("VEHICLE IS NULL "+vhc.name);
			}
			if(tc==null){
				System.out.println("TRAIN COMPOSITION IS NULL//////////");
			}
			System.out.println("VEHICLE INDEXES AFTER REORDER "+vhc.vid+"("+vhc.truckComposition.truckVehicles.indexOf(vhc)+")");
		}
		//v.truckComposition.save();
		return ok(showTruckComposition.render(tc));
		}
		catch(Exception e) {
			Logger.error("ERROR MOVING UP TRAILER: " + e.getMessage(), e);
			System.out.println("ERROR MOVING UP TRAILER "+ e.getMessage()+e);
			return redirect("/showtruckcomposition/"+tc.id);
	}
		//return redirect("/showtruckcomposition/"+tc.id);
	}
	
//	public Result moveUp(long id){
//		Vehicle v=Vehicle.findById(id);
//		TruckComposition tc=null;
//	try{	
//		tc=v.truckComposition;
//		for(Vehicle vhc:tc.truckVehicles){
//			System.out.println("VEHICLE INDEXES BEFORE REORDER "+vhc.vid+"("+vhc.truckComposition.truckVehicles.indexOf(vhc)+")");
//		}
//		int thisVehicleInd=tc.truckVehicles.indexOf(v);
//		int prevVehicleInd=thisVehicleInd-1;
//		//Vehicle prevVehicle=tc.truckVehicles.get(prevVehicleInd);
//		Vehicle prevVehicle=tc.truckVehicles.get(prevVehicleInd);
//		tc.truckVehicles.set(prevVehicleInd, v);
//		tc.save();
//		//prevVehicle.truckComposition=null;
//		//prevVehicle.save();
//		//RADI DO OVOG DJELA (BRISANJE TRAILER OBJEKTA)///////////////
//		//tc.truckVehicles.add(thisVehicleInd, prevVehicle);
//		tc.truckVehicles.set(thisVehicleInd, prevVehicle);
//		tc.save();
//	//	prevVehicle.truckComposition=tc;
//	//	prevVehicle.save();
//		//v.truckComposition.truckVehicles=truckVehicles;
//	//	v.save();
//		for(Vehicle vhc:tc.truckVehicles){
//			System.out.println("VEHICLE INDEXES AFTER REORDER "+vhc.vid+"("+vhc.truckComposition.truckVehicles.indexOf(vhc)+")");
//		}
//		//v.truckComposition.save();
//		return ok(showTruckComposition.render(tc));
//		}
//		catch(Exception e) {
//			Logger.error("ERROR MOVING UP TRAILER: " + e.getMessage(), e);
//			System.out.println("ERROR MOVING UP TRAILER "+ e.getMessage()+e);
//			return redirect("/showtruckcomposition/"+tc.id);
//	}
//	}
	
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
//	}Vehicle removedTrailer=	tc.truckVehicles.get(size-1);
	//tc.truckVehicles.remove(removedTrailer);

	
	}

