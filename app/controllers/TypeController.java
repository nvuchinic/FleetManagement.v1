package controllers;

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

public class TypeController extends Controller{


	/**
	 * Generates view(form) for adding new Type object
	 * @return
	 */
	public Result addTypeView() {
		return ok(addTypeForm.render());
	}
	
	/**
	 * 
	 * Creates a new Type object or
	 * renders the view again if any error occurs.
	 * @return 
	 * @throws ParseException
	 */
	public Result addType() {
		DynamicForm dynamicTypetForm = Form.form().bindFromRequest();
	   Form<Type> addTypeForm = Form.form(Type.class).bindFromRequest();
	   	/*if (addTypeForm.hasErrors() || addTypeForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Type");
			flash("error", "Error at Type adding  form!");
			return redirect("/allclients");
		}*/
	  String typeName;
	  
		try{	
			typeName = addTypeForm.bindFromRequest().get().name;
			
			Type.createType(typeName);
			System.out.println("TYPE ADDED SUCCESSFULLY///////////////////////");
			Logger.info("TYPE ADDED SUCCESSFULLY///////////////////////");
			return redirect("/alltypes");
		}catch(Exception e){
		flash("addRouteError", "ERROR AT ADDING TYPE");
		Logger.error("ADDING TYPE ERROR: " + e.getMessage(), e);
		return redirect("/addtypeview");
	   }
	}
	
	/**
	 * Finds Type object based on passed ID number as parameter
	 *  and shows it in view
	 * @param id - Type object ID
	 * @return 
	 */
	public Result showType(long id) {
		Type t = Type.findById(id);
		if (t == null) {
			Logger.error("error", "Type IS NULL");
			flash("error", "NO SUCH TYPE RECORD IN DATABASE!!!");
			return redirect("/alltypes");
		}
		return ok(showType.render(t));
	}
	
	/**
	 * Finds Type object using passed ID number as parameter,
	 * and then removes it from database 
	 * @param id - Type object ID
	 * @return 
	 */
	public Result deleteType(long id) {
		try {
			Type t= Type.findById(id);
			Logger.info("TYPE DELETED: \"" + t.name);
			Type.deleteType(id);
			return redirect("/alltypes");
		} catch (Exception e) {
			flash("deleteTypeError", "ERROR DELETING TYPE!");
			Logger.error("ERROR DELETING TYPE: " + e.getMessage());
			return redirect("/alltypes");
		}
	}
	
	/**
	 * Renders the view for editing Type object.
	 *  @param Type object ID number
	 * @return 
	 */
	public Result editTypeView(long id) {
		Type t = Type.findById(id);
		// Exception handling.
		if (t == null) {
			flash("TypeNull", "NO SUCH TYPE RECORD IN DATABASE");
			return redirect("/allclients");
		}
		return ok(editTypeView.render(t));

	}
	
	/**
	 *  finds the specific Type object using passed ID parameter,
	 *  and updates it with information collected from editType view form
	 * again.
	 * @param id ID number of Type object
	 * @return Result 
	 */
	public Result editType(long id) {
		   DynamicForm dynamicTypeeForm = Form.form().bindFromRequest();
		Form<Type> typeForm = Form.form(Type.class).bindFromRequest();
	Type t  = Type.findById(id);
					String name;
				 
					try{	
						name = typeForm.bindFromRequest().get().name;
						t.name=name;
						
						t.save();
						Logger.info("TYPE UPDATED");
						flash("TypeUpdateSuccess",   "TYPE UPDATED SUCCESSFULLY!");
						return ok(showType.render(t));
		}
					catch(Exception e){
						flash("updateTypeError", "ERROR AT EDITING TYPE ");
						Logger.error("EDITING TYPE ERROR: " + e.getMessage(), e);
						return redirect("/edittypeview");
					   }
	}
	
	
	
	public Result listTypes() {
		List<Type> allTypes=Type.listOfTypes();
		if(allTypes!=null){
		return ok(listAllTypes.render(allTypes));
		}
		else{
			flash("listTypesError", "NO TYPES RECORDS IN DATABASE!");
				return redirect("/");
		}
	}
}


