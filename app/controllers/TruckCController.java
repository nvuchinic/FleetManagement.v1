package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Driver;
import models.Fleet;
import models.Owner;
import models.TravelOrder;
import models.TruckC;
import models.Type;
import models.Vehicle;
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
		List<Vehicle> trailers=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equals("trailer")){
				trailers.add(v);
			}
		}
		Vehicle truckHead=Vehicle.findById(truckHeadId);
		return ok(attachTrailerView.render(truckHead,trailers));
	}
	
	
	public Result attachTrailer(long id) {
		DynamicForm attachTrailerForm = Form.form().bindFromRequest();
		Vehicle truckHead=Vehicle.findById(id);
	   Form<TruckC> TruckCform = Form.form(TruckC.class).bindFromRequest();
		/*if (addTravelOrderForm.hasErrors() || addTravelOrderForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Travel Order");
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
	}

