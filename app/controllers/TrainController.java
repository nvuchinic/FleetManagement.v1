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
	
	public static Finder<Integer, Train> find= new Finder<Integer, Train>(Integer.class, Train.class);
	
	public Result showTrain(int id) {
		Train trn = TrainController.find.byId(id);
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
		Train trn = find.byId(id);
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
			numOfWagons = newTrainForm.bindFromRequest().get().numOfWagons;	
		} catch(IllegalStateException e) {
			flash("add_train_null_field", Messages.get("Please fill all the fileds in the form!"));
			return redirect("/addtrain");
		}
	System.out.println("Train added: ");
		return redirect("/alltrains");
	}
	
		public Result editTrainView(long id){
			
		Train t = new Train();
	   	  if (t == null) {
	 		Logger.of("train").warn("That train isn't in database!");
	   		return redirect("/");
	   	 }
	   	return ok(editTrainView.render(t));
	}
	
	public Result saveEditedTrain(long id){
		//User u = SessionHelper.getCurrentUser(ctx());
				double latitude=0;
				double longitude=0;
				String licenseNo;
				int numOfWagons;
				double mileage;
				try {

					numOfWagons = newTrainForm.bindFromRequest().get().numOfWagons;

		}
				catch(IllegalStateException e) {
					flash("add_train_null_field", Messages.get("Please fill all the fileds in the form!"));
					return redirect("/addtrain");
				}
		Train t = new Train();
		t.numOfWagons = numOfWagons;
		t.save();
		flash("edit_train_success", Messages.get("You have succesfully updated this train."));
		return redirect("/showtrain/" + id);	
	}
	
	public Result listTrains(){
		List<Train> allTrains = find.all();
		return ok(listAllTrains.render(allTrains));
	}
}
