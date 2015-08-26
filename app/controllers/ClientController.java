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

public class ClientController extends Controller{

	/**
	 * finder for Client object
	 */
public static Finder<Long, Client> find = new Finder<>(Client.class);
	
	/**
	 * Generates view(form) for adding new Client object
	 * @return
	 */
	public Result addClientView() {
		return ok(addClientForm.render());
	}
	
	/**
	 * 
	 * Creates a new Client object or
	 * renders the view again if any error occurs.
	 * @return 
	 * @throws ParseException
	 */
	public Result addClient() {
		DynamicForm dynamicClientForm = Form.form().bindFromRequest();
	   Form<Client> addClientForm = Form.form(Client.class).bindFromRequest();
	   	/*if (addClientForm.hasErrors() || addClientForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Client");
			flash("error", "Error at Client adding  form!");
			return redirect("/allclients");
		}*/
	  String cName;
	  String cType;
	  String address;
	  String phone;
	  String email;
		try{	
			cName = addClientForm.bindFromRequest().get().cName;
			cType = addClientForm.bindFromRequest().get().cType;
			address = addClientForm.bindFromRequest().get().address;
			phone = addClientForm.bindFromRequest().get().phone;
			email = addClientForm.bindFromRequest().get().email;
			Client cl= Client.saveToDB(cName, cType,address,phone, email);
			System.out.println("CLIENT ADDED SUCCESSFULLY///////////////////////");
			Logger.info("CLIENT ADDED SUCCESSFULLY///////////////////////");
			return redirect("/allclients");
		}catch(Exception e){
		flash("addRouteError", "ERROR AT ADDING CLIENT ");
		Logger.error("ADDING CLIENT ERROR: " + e.getMessage(), e);
		return redirect("/addclientview");
	   }
	}
	
	/**
	 * Finds Client object based on passed ID number as parameter
	 *  and shows it in view
	 * @param id - Client object ID
	 * @return 
	 */
	public Result showClient(long id) {
		Client c = Client.findById(id);
		if (c == null) {
			Logger.error("error", "CLIENT IS NULL");
			flash("error", "NO SUCH CLIENT RECORD IN DATABASE!!!");
			return redirect("/allclients");
		}
		return ok(showClient.render(c));
	}
	
	/**
	 * Finds Client object using passed ID number as parameter,
	 * and then removes it from database 
	 * @param id - RClient object ID
	 * @return 
	 */
	public Result deleteClient(long id) {
		try {
			Client c= Client.findById(id);
			Logger.info("CLIENT DELETED: \"" + c.id);
			Client.deleteClient(id);
			return redirect("/allclients");
		} catch (Exception e) {
			flash("deleteClient	Error", "ERROR DELETING CLIENT!");
			Logger.error("ERROR DELETING CLIENT: " + e.getMessage());
			return redirect("/allclients");
		}
	}
	
	/**
	 * Renders the view for editing Client object.
	 *  @param Client object ID number
	 * @return 
	 */
	public Result editClientView(long id) {
		Client c = Client.findById(id);
		// Exception handling.
		if (c == null) {
			flash("clientNull", "NO SUCH CLIENT RECORD IN DATABASE");
			return redirect("/allclients");
		}
		return ok(editClientView.render(c));

	}
	
	/**
	 *  finds the specific Client object using passed ID parameter,
	 *  and updates it with information collected from editClient view form
	 * again.
	 * @param id ID number of Client object
	 * @return Result 
	 */
	public Result editClient(long id) {
		   DynamicForm dynamicClienteForm = Form.form().bindFromRequest();
		Form<Client> clientForm = Form.form(Client.class).bindFromRequest();
	Client c  = Client.findById(id);
					String cName;
				  String cType;
				  String address;
				  String phone;
				  String email;
					try{	
						cName = clientForm.bindFromRequest().get().cName;
						cType = clientForm.bindFromRequest().get().cType;
						address = clientForm.bindFromRequest().get().address;
						phone = clientForm.bindFromRequest().get().phone;
						email = clientForm.bindFromRequest().get().email;
						c.cName=cName;
						c.cType=cType;
						c.address=address;
						c.phone=phone;
						c.email=email;
						c.save();
						Logger.info("CLIENT UPDATED");
						flash("ClientUpdateSuccess",   "CLIENT UPDATED SUCCESSFULLY!");
						return ok(showClient.render(c));
		}
					catch(Exception e){
						flash("updateClientError", "ERROR AT EDITING CLIENT ");
						Logger.error("EDITING CLIENT ERROR: " + e.getMessage(), e);
						return redirect("/editclientview");
					   }
	}
	
	
	
	public Result listClients() {
		List<Client> allClients=Client.listOfClients();
		if(allClients!=null){
		return ok(listAllClients.render(allClients));
		}
		else{
			flash("listClientsError", "NO CLIENTS RECORDS IN DATABASE!");
				return redirect("/");
		}
	}
}
