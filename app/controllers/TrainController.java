package controllers;

import java.util.List;

import models.*;
import views.html.*;
import play.Logger;
import play.Play;
import play.data.Form;
import play.db.ebean.*;
import play.mvc.Controller;

import com.avaje.ebean.Model.*;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.i18n.Messages;

public class TrainController extends Controller {
	@SuppressWarnings("deprecation")
	static Form<Train> newTrainForm = new Form<Train>(Train.class);
	@SuppressWarnings("deprecation")
	public static Finder<Integer, Train> findTrain= new Finder<Integer, Train>(Integer.class, Train.class);
	
	public Result showTrain(int id) {
		Train trn = TrainController.findTrain.byId(id);
			return ok(showTrain.render(trn));
		}
	
	public Result addTrain() {
		//User u = SessionHelper.getCurrentUser(ctx());
		//User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*if (currentUser == null) {
			return redirect(routes.Application.index());
		}*/
		//List<Train> allTrains = findTrain.all();
		return ok(addTrainForm.render());
	}
	
	public Result removeTrain(int id) {
		//User u = SessionHelper.getCurrentUser(ctx());
		//User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*if (currentUser == null) {
			return redirect(routes.Application.index());
		}*/
		Train trn=findTrain.byId(id);
		trn.delete();
		return redirect("/alltrains");
		}
	
	public Result createTrain() {
		//User u = SessionHelper.getCurrentUser(ctx());
		long latitude=0;
		long longitude=0;
		String licenseNo;
		String make;
		String model;
		String year;
		int numOfWagons;
		try {
			licenseNo = newTrainForm.bindFromRequest().get().licenseNo;
			numOfWagons = newTrainForm.bindFromRequest().get().numOfWagons;	
		} catch(IllegalStateException e) {
			flash("add_truck_null_field", Messages.get("Please fill all the fileds in the form!"));
			return redirect("/addtruck");
		}
		Train trn = Train.saveToDB(licenseNo,latitude, longitude, numOfWagons);
	System.out.println("Train added: "+trn.licenseNo);
		return redirect("/alltrains");
	}
	
	public Result listTrains(){
		List<Train> allTrains = findTrain.all();
		return ok(listAllTrains.render(allTrains));
	}
}
