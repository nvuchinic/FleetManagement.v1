package controllers;

import java.util.List;

import models.*;
import views.html.*;
import play.Logger;
import play.Play;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.*;
import play.mvc.Controller;

import com.avaje.ebean.Model.*;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.i18n.Messages;

public class VendorController extends Controller {

	@SuppressWarnings("deprecation")
	static Form<Vendor> newVendorForm = new Form<Vendor>(Vendor.class);
	@SuppressWarnings("deprecation")
	public static Finder<Integer, Vendor> findVendor = new Finder<Integer, Vendor>(
			Integer.class, Vendor.class);

	public Result showVendor(int id) {
		Vendor v = VendorController.findVendor.byId(id);
		return ok(showVendor.render(v));
	}

	public Result addVendor() {
		// User u = SessionHelper.getCurrentUser(ctx());
		// User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*
		 * if (currentUser == null) { return
		 * redirect(routes.Application.index()); }
		 */
		// List<Train> allTrains = findTrain.all();
		return ok(addVendorForm.render());
	}

	public Result removeVendor(int id) {
		// User u = SessionHelper.getCurrentUser(ctx());
		// User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*
		 * if (currentUser == null) { return
		 * redirect(routes.Application.index()); }
		 */
		Vendor v = findVendor.byId(id);
		v.delete();
		return redirect("/allvendors");
	}

	public Result createVendor() {
		// User u = SessionHelper.getCurrentUser(ctx());
		String name;
		String address;
		String city;
		String country;
		String phone;
		String email;
		try {
			name = newVendorForm.bindFromRequest().get().name;
			address = newVendorForm.bindFromRequest().get().address;
			city = newVendorForm.bindFromRequest().get().city;
			country = newVendorForm.bindFromRequest().get().country;
			phone = newVendorForm.bindFromRequest().get().phone;
			email = newVendorForm.bindFromRequest().get().email;
		} catch (IllegalStateException e) {
			flash("add_vendor_null_field",
					Messages.get("Please fill all the fileds in the form!"));
			return redirect("/addvendor");
		}
		Vendor v = Vendor.saveToDB(name, address, city, country, phone, email);
		System.out.println("Vendor added: " + v.name);
		return redirect("/allvendors");
	}
	
	public Result listVendors(){
		List<Vendor> allVendors = findVendor.all();
		return ok(listAllVendors.render(allVendors));
	}

}
