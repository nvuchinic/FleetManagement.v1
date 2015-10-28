package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class CurrencyController extends Controller{

	/**
	 * finder for Currencyy object
	 */
	public static Finder<Long, Currencyy> find = new Finder<Long, Currencyy>(
			Currencyy.class);

	/**
	 * Generates view(form) for adding new Currencyy object
	 * @return
	 */
	public Result addCurrencyView() {
		return ok(addCurrencyForm.render());
	}

	/**
	 * 
	 * Creates a new Currencyy object using data 
	 * from request collected through the addCurrencyForm form
	 * @return
	 * @throws ParseException
	 */
	public Result addCurrency() {
		DynamicForm dynamicCurrencyForm = Form.form().bindFromRequest();
		Form<Currencyy> addCurrencyForm = Form.form(Currencyy.class).bindFromRequest();
		/*
		 * if (addCurrencyForm.hasErrors() || addCurrencyForm.hasGlobalErrors()) {
		 * Logger.debug("ERROR ADDING CURRENCY"); flash("error",
		 * "ERROR AT ADDING CURRENCY FORM!"); return redirect("/allcurrencies"); }
		 */
		String name;
		String symbol;
		String description;
		try {
			name=addCurrencyForm.bindFromRequest().get().name;
			symbol=addCurrencyForm.bindFromRequest().get().symbol;	
			description=addCurrencyForm.bindFromRequest().get().description;
			Currencyy c=Currencyy.saveToDB(name, symbol, description);
			System.out.println("CURRENCY ADDED SUCCESSFULLY///////////////////////");
			Logger.info("CURRENCY ADDED SUCCESSFULLY///////////////////////");
			flash("success", "CURRENCY SUCCESSFULLY ADDED");
			return redirect("/allcurrencies");
		} catch (Exception e) {
			flash("error", "ERROR AT ADDING CURRENCY ");
			Logger.error("ADDING CURRENCY ERROR: " + e.getMessage(), e);
			return redirect("/allcurrencies");
		}
	}

	/**
	 * Finds Currencyy object by it's ID number
	 * passed as parameter and shows it
	 * in view
	 * @param id- Currencyy object ID
	 * @return
	 */
	public Result showCurrency(long id) {
		Currencyy c = Currencyy.findById(id);
		if (c== null) {
			Logger.error("error", "CURRENCY IS NULL");
			flash("error", "NO CURRENCY RECORD IN DATABASE!!!");
			return redirect("/allcurrencies");
		}
		return ok(showCurrency.render(c));
	}

	/**
	 * Finds Currencyy object by it's ID number
	 * passed as parameter, and then removes
	 * it from database
	 * 
	 * @param id - Currency object ID
	 * @return
	 */
	public Result deleteCurrency(long id) {
		Currencyy c=null;
		try {
			c = Currencyy.findById(id);
			VehicleBrand.deleteVehicleBrand(id);
			Logger.info("CURRENCY DELETED: \"" + c.name);
			return redirect("/allcurrencies");
		} catch (Exception e) {
			flash("error", "ERROR DELETING CURRENCY!");
			Logger.error("ERROR DELETING CURRENCY: " + e.getMessage());
			return redirect("/allcurrencies");
		}
	}

	/**
	 * Renders the view for editing Currencyy object.
	 * @param id-Currency object ID number
	 * @return
	 */
	public Result editCurrencyView(long id) {
		Currencyy c = Currencyy.findById(id);
		// Exception handling.
		if (c == null) {
			flash("error", "NO CURRENCY RECORD IN DATABASE");
			return redirect("/allcurrencies");
		}
		return ok(editCurrencyView.render(c));

	}

	/**
	 * finds the specific Currency object by it's ID number
	 *  passed as parameter, and updates
	 * it with data from request collected from editCurrencyView form 
	 * @param id- ID number of Currency object
	 * @return Result
	 */
	public Result editCurrency(long id) {
		DynamicForm dynamicCurrencyForm = Form.form().bindFromRequest();
		Form<Currencyy> currencyForm = Form.form(Currencyy.class).bindFromRequest();
		Currencyy c = Currencyy.findById(id);
		String name;
		String symbol;
		String description;
		try {
			name= currencyForm.bindFromRequest().get().name;
			symbol= currencyForm.bindFromRequest().get().symbol;
			description= currencyForm.bindFromRequest().get().description;
			c.name = name;
			c.symbol = symbol;
			c.save();
			Logger.info("CURRENCY UPDATED");
			flash("success", "CURRENCY UPDATED SUCCESSFULLY!");
			return ok(showCurrency.render(c));
		} catch (Exception e) {
			flash("error", "ERROR AT EDITING CURRENCY ");
			Logger.error("EDITING CURRENCY ERROR: " + e.getMessage(), e);
			return redirect("/editcurrencyview");
		}
	}

	public Result listCurrencies() {
		List<Currencyy> allCurrencies = Currencyy.listOfCurrencies();
		if (allCurrencies != null) {
			return ok(listAllCurrencies.render(allCurrencies));
		} else {
			flash("error", "NO CURRENCY RECORDS IN DATABASE!");
			return redirect("/");
		}
	}
}
