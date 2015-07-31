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
	
	static Form<Train> newTrainForm = new Form<Train>(Train.class);
	
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
		double latitude=0;
		double longitude=0;
		String licenseNo;
		int numOfWagons;
		double mileage;
		try {
			licenseNo = newTrainForm.bindFromRequest().get().licenseNo;
			numOfWagons = newTrainForm.bindFromRequest().get().numOfWagons;	
			mileage=newTrainForm.bindFromRequest().get().mileage;
		} catch(IllegalStateException e) {
			flash("add_train_null_field", Messages.get("Please fill all the fileds in the form!"));
			return redirect("/addtrain");
		}
		Train trn = Train.saveToDB(licenseNo,latitude, longitude, numOfWagons,mileage);
	System.out.println("Train added: "+trn.licenseNo);
		return redirect("/alltrains");
	}
	
	public Result editTrainView(int id){
		Train t = findTrain.byId(id);
	   	  if (t == null) {
	 		Logger.of("train").warn("That train isn't in database!");
	   		return redirect("/");
	   	 }
	   	return ok(editTrainView.render(t));
	}
	
	public Result saveEditedTrain(int id){
		//User u = SessionHelper.getCurrentUser(ctx());
				double latitude=0;
				double longitude=0;
				String licenseNo;
				int numOfWagons;
				double mileage;
				try {
					licenseNo = newTrainForm.bindFromRequest().get().licenseNo;
					numOfWagons = newTrainForm.bindFromRequest().get().numOfWagons;
					mileage=newTrainForm.bindFromRequest().get().mileage;
		}
				catch(IllegalStateException e) {
					flash("add_train_null_field", Messages.get("Please fill all the fileds in the form!"));
					return redirect("/addtrain");
				}
		Train t=findTrain.byId(id);
		t.licenseNo = licenseNo;
		t.numOfWagons = numOfWagons;
		t.save();
		flash("edit_train_success", Messages.get("You have succesfully updated this train."));
		return redirect("/showtrain/" + id);	
	}
	
	public Result listTrains(){
		List<Train> allTrains = findTrain.all();
		return ok(listAllTrains.render(allTrains));
	}
}
