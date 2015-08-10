package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.Model.Finder.*;

import models.Driver;
import models.Train;
import models.TruckC;
import models.Vehicle;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class TrainController extends Controller {

/*	static Form<Train> TrainForm = Form.form(Train.class);
	
	*//**
	 * Finder for Train class
	 *//*
	public static Finder<Long, Train> find = new Finder<Long, Train>(Long.class,
			Train.class);
	
	*//**
	 * Renders the 'add driver' page
	 * 
	 * @return
	 *//*
	public Result createTrainView() {
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Vehicle> locomotives=new ArrayList<Vehicle>();
		for(Vehicle v:allVehicles){
			if(v.typev.name.equals("locomotive")){
				locomotives.add(v);
			}
		}
		if(locomotives.size()==0){
			flash("noLocomotives", "You cannot create train, there is no locomotives!");
			return redirect("/");
		}
		return ok(createTrainForm.render(locomotives));
	}*/
	
}
