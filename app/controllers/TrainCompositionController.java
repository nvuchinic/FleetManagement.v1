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

public class TrainCompositionController extends Controller {

	static Form<TrainComposition> trainCmpForm = Form.form(TrainComposition.class);

	/**
	 * Finder for TrainComposition class
	 */
	public static Finder<Long, TrainComposition> find = new Finder<Long, TrainComposition>(TrainComposition.class);
	
	
	/**
	 * Renders the view for creating TrainComposition object
	 * @return
	 */
	public Result createTrainCompositionView() {
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Vehicle> availableWagons=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equalsIgnoreCase("wagon") && v.isLinked==false){
				availableWagons.add(v);
			}
		}
		if(availableWagons.size()==0){
			flash("noWagons",  "NO AVAILABLE WAGONS!");
			System.out.println("NO AVAILABLE WAGONS  ////////////////////");
			return redirect("/");
		}
		List<Vehicle> availableTrains=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equalsIgnoreCase("train") && v.isLinked==false){
				availableTrains.add(v);
			}
		}
		
		return ok(createTrainCompView.render(availableTrains,availableWagons));
	}
	
	
	public Result createCompositionAndAttachWagonView(long trainId) {
		Vehicle train =Vehicle.findById(trainId);
		TrainComposition tc=TrainComposition.saveToDB(train);
		train.trainComposition=tc;
		train.isLinked=true;
		train.position=tc.trainVehicles.indexOf(train)+1;
		train.save();
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Vehicle> wagons=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equalsIgnoreCase("wagon") && (!v.isLinked)){
				wagons.add(v);
			}
		}
		if(wagons.size()==0){
			flash("noWagons",  "NO WAGONS");
			System.out.println("NO WAGONS ////////////////////");
			return redirect("/showtrainkcomposition/"+tc.id);
		}
		return ok(attachWagonView.render(tc,wagons));
	}
	
	
	public Result createTrainComposition(){
		DynamicForm dynamicTrainCompForm = Form.form().bindFromRequest();
		Long trainId;
		Long wagonId;
		String trainIdStr;
		String wagonIdStr;
		Vehicle train;
		Vehicle wagon;
		try 
		{
			trainIdStr=dynamicTrainCompForm.get("trainID");
			trainId= Long.parseLong(trainIdStr);
			train=Vehicle.findById(trainId);
			wagonIdStr=dynamicTrainCompForm.get("wagonID");
			wagonId= Long.parseLong(wagonIdStr);
			wagon=Vehicle.findById(wagonId);
		} catch (IllegalStateException e) {
			flash("add_TrainComposition_null_field",
					Messages.get("Please fill all the fields in the form!"));
			return redirect("/createTrainCompositionview");
		}
		TrainComposition tc=TrainComposition.saveToDB();
		tc.trainVehicles.add(train);
		train.isLinked=true;
		train.save();
		tc.trainVehicles.add(wagon);
		wagon.isLinked=true;
		wagon.save();
		tc.numOfVehicles=tc.trainVehicles.size();
		tc.save();
		System.out.println("train COMPOSITION ADDED: " + tc.id);
		return ok(showTrainComposition.render(tc));
	}
	
	
	public Result attachWagonView(long trainCompID) {
		TrainComposition tc=TrainComposition.find.byId(trainCompID);
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Vehicle> wagons=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equalsIgnoreCase("wagon") && (!v.isLinked)){
				wagons.add(v);
			}
		}
		if(wagons.size()==0){
			flash("nowagons",  "NO WAGONS");
			System.out.println("NO WAGON OBJECTS ////////////////////");
			return redirect("/showtraincomposition/"+tc.id);
		}
		return ok(attachWagonView.render(tc,wagons));
		}
	
	
	public Result attachWagon(long tcId) {
		DynamicForm dynamAttachWagonForm = Form.form().bindFromRequest();
		TrainComposition tc=TrainComposition.findById(tcId);
	   Form<TrainComposition> attachWagonForm = Form.form(TrainComposition.class).bindFromRequest();
		String wagonName;
		try{	
			String t = attachWagonForm.bindFromRequest().field("t").value();
			//int num = 0;
			//Vehicle wagon;
			String[] vids = t.split(",");
			List<Vehicle> wagons = new ArrayList<Vehicle>();
			String vi = null;
			for(int i = 0; i < vids.length; i++) {
				vi = vids[i];
				System.out.println("ISPISUJEM NIZ ID STRINGOVA U ATTACH_WAGON METODI:"+vi);

				long wagonId=Long.parseLong(vi);
				Vehicle wagon=Vehicle.findById(wagonId);
				//num = Integer.parseInt(attachwagonForm.bindFromRequest().field(vi).value());
				tc.trainVehicles.add(wagon);
				tc.numOfVehicles=tc.trainVehicles.size();
				tc.save();
				wagon.trainComposition=tc;
				wagon.isLinked=true;
				wagon.position=tc.trainVehicles.indexOf(wagon)+1;
				wagon.save();
			}
			
		return ok(showTrainComposition.render(tc));

			}
		catch(Exception e){
		flash("error", "ERROR ATTACHING WAGONS");
		Logger.error("Creating train composition error: " + e.getMessage(), e);
		System.out.println("ERROR ATTACHING WAGON////////////////////////////"+e.getMessage());
		//return redirect("/addtravelorderview");
		return redirect("/showtraincomposition/"+tc.id);

	   }
	}
	
	public Result editTrainCompositionView(long id) {
		TrainComposition tc = TrainComposition.findById(id);
		// Exception handling.
		if (tc == null) {
			flash("error", "NO TRAIN COMPOSITION RECORD IN DATABASE");
			return redirect("/");
		}
		//Form<Vehicle> form = Form.form(Vehicle.class).fill(v);
		return ok(editTrainCompositionView.render(tc));

	}
	
	public Result editTrainComposition(long id) {
		DynamicForm updateTrainForm = Form.form().bindFromRequest();
		Form<TrainComposition> form = Form.form(TrainComposition.class).bindFromRequest();
		TrainComposition tc = TrainComposition.findById(id);
		try {
			if (form.hasErrors() || form.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editTrainCompositionView.render(tc));
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
			Logger.info(session("name") + " updated vehicle: " + tc.id);
			//flash("success", v.vid + " successfully updated!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
	public Result listTrainCompositions() {
		//List<TrainComposition> alltrainComps=new ArrayList<TrainComposition>();
		List<TrainComposition> allTrainComps=TrainComposition.allTrainComps();
		if(allTrainComps!=null){
		return ok(listAllTrainComps.render(allTrainComps));
		}
		else{
			flash("listTOerror", "NO TRAIN COMPOSITIONS RECORDS IN DATABASE!");
				return redirect("/");
		}
	}
	
	public Result showTrainComposition(long id) {
		TrainComposition tc = TrainComposition.findById(id);
		if (tc == null) {
			Logger.error("TRAIN COMPOSITION NULL ///////////////////");
			flash("error", "TRAIN COMPOSITION NULL!");
			return redirect("/");
		}
		return ok(showTrainComposition.render(tc));
	}
	
//	public Result removewagon(long trainCompId){
//		TrainComposition tc=TrainComposition.findById(trainCompId);
//		if(tc==null){
//			System.out.println("train COMPOSITION NULL AT REMOVING wagon");
//			return redirect("/showTrainComposition");
//		}
//		int size=tc.trainVehicles.size();
//		try{
//		Vehicle removedWagon=	tc.trainVehicles.remove((size-1));
//		removedWagon.isLinked=false;
//		removedWagon.TrainComposition=null;
//		//removedWagon.update();
//		tc.update();
//		return ok(showTrainComposition.render(tc));
//		}
//		catch(Exception e) {
//			Logger.error("ERROR REMOVING wagon: " + e.getMessage(), e);
//			System.out.println("ERROR REMOVING wagon "+ e.getMessage()+e);
//			return redirect("/showTrainComposition/"+tc.id);
//		}
//	}
	
	public Result removeWagon(long trainCompId){
		TrainComposition tc=TrainComposition.findById(trainCompId);
		if(tc==null){
			System.out.println("TRAIN COMPOSITION NULL AT REMOVING WAGON");
			return redirect("/showTrainComposition");
		}
		int size=tc.trainVehicles.size();
		try{
		Vehicle removedWagon=	tc.trainVehicles.get(size-1);
		tc.trainVehicles.remove(removedWagon);
		removedWagon.isLinked = false;
		removedWagon.trainComposition = null;
		removedWagon.save();
		tc.save();
		//removedWagon.update();
		//tc.update();
		return ok(showTrainComposition.render(tc));
		}
		catch(Exception e) {
			Logger.error("ERROR REMOVING wagon: " + e.getMessage(), e);
			System.out.println("ERROR REMOVING wagon "+ e.getMessage()+e);
			return redirect("/showTrainComposition/"+tc.id);
		}
	}
	
	public Result getNextVehicle(long vId){
		Vehicle v=Vehicle.findById(vId);
		TrainComposition tc=v.trainComposition;
		int vIndex=tc.trainVehicles.indexOf(v);
		int nextVehicleInd=vIndex+1;
		Vehicle nextVehicle=tc.trainVehicles.get(nextVehicleInd);
		return ok(showVehicle.render(nextVehicle));
	}
	
	public Result getPrevVehicle(long vId){
		Vehicle v=Vehicle.findById(vId);
		TrainComposition tc=v.trainComposition;
		int vIndex=tc.trainVehicles.indexOf(v);
		int prevVehicleInd=vIndex-1;
		Vehicle prevVehicle=tc.trainVehicles.get(prevVehicleInd);
		return ok(showVehicle.render(prevVehicle));
	}
	
//	public Result deleteTrainComposition(long id) {
//		try {
//			TrainComposition tc = TrainComposition.findById(id);
//			Logger.info("Deleted train composition: \"" + tc.id + "\"");
//			tc.delete();
//			TrainComposition.deleteTrainComposition(id);
//			return redirect("/");
//		} catch (Exception e) {
//			flash("error", "Error at deleting train composition!");
//			Logger.error("Error at deleting train composition: " + e.getMessage());
//			return redirect("/");
//		}
//	}

	
	}

