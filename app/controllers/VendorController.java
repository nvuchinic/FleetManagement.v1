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

/**
 * Class that defines operations that can be performed on Vendor objects
 * 
 * @author nermin vucinic
 *
 */
public class VendorController extends Controller {

	@SuppressWarnings("deprecation")
	//static Form<Vendor> newVendorForm = new Form<Vendor>(Vendor.class);
	public static Finder<Integer, Vendor> findVendor = new Finder<Integer, Vendor>(
			Vendor.class);

	/**
	 * Generates view(form) that showing particular Vendor object, based on
	 * passed parameter
	 * 
	 * @param id
	 * @return
	 */
	public Result showVendor(int id) {
		Vendor v = VendorController.findVendor.byId(id);
		return ok(showVendor.render(v));
	}

	/**
	 * Generates view(form) for adding new Vendor object
	 * 
	 * @return
	 */
	public Result addVendorView() {
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

	/**
	 * Finds Vendor object based on passed parameter, and removes it from
	 * database
	 * 
	 * @param id
	 * @return
	 */
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

	/**
	 * Creates new Vendor object based on data inserted on the apropriate
	 * view(form)
	 * 
	 * @return new Vendor object
	 */
	public Result createVendor() {
		Form<Vendor> newVendorForm = Form.form(Vendor.class)
				.bindFromRequest();
		String name;
		String type;
		String address;
		String city;
		String country;
		String phone;
		String email;
		try {
			name = newVendorForm.bindFromRequest().get().name;
			type = newVendorForm.bindFromRequest()
					.field("vType").value();
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
		Vendor v = Vendor.saveToDB(name, type, address, city, country, phone, email);
		System.out.println("Vendor added: " + v.name);
		return redirect("/allvendors");
	}

	/**
	 * Generates view(form) for editing Vendor object whose ID is passed as
	 * parameter
	 * 
	 * @param id
	 * @return
	 */
	public Result editVendorView(int id) {
		Vendor v = findVendor.byId(id);
		if (v == null) {
			Logger.of("vendor").warn("That vendor isn't in database!");
			return redirect("/");
		}
		return ok(editVendorView.render(v));
	}

	/**
	 * Collects data from apropriate view(form) for updating, updates Vendor
	 * object, and stores it to database
	 * 
	 * @param id
	 * @return
	 */
	public Result saveEditedVendor(int id) {
		Form<Vendor> newVendorForm = Form.form(Vendor.class)
				.bindFromRequest();
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
					Messages.get("Please fill all the fields in the form!"));
			return redirect("/addvendor");
		}
		Vendor v = findVendor.byId(id);
		v.setName(name);
		v.setAddress(address);
		v.setCity(city);
		v.setCountry(country);
		v.setPhone(phone);
		v.setEmail(email);
		v.save();
		flash("edit_vendor_success",
				Messages.get("You have succesfully updated this vendor."));
		return redirect("/showvendor/" + id);
	}

	/**
	 * Finds all Vendor object in database
	 * 
	 * @return all Vendor objects in database
	 */
	public Result listVendors() {
		List<Vendor> allVendors = findVendor.all();
		return ok(listAllVendors.render(allVendors));
	}

}
