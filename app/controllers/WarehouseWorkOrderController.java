package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import models.Client;
import models.Driver;
import models.Task;
import models.Vehicle;
import models.WarehouseWorkOrder;
import models.WorkOrder;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class WarehouseWorkOrderController extends Controller {

	/**
	 * finder for Client object
	 */
	public static Finder<Long, WarehouseWorkOrder> find = new Finder<Long, WarehouseWorkOrder>(
			WarehouseWorkOrder.class);

	/**
	 * Form for creating/editing WorkOrder object
	 */
	static Form<WarehouseWorkOrder> workOrderForm = Form
			.form(WarehouseWorkOrder.class);

	/**
	 * Renders the 'add WorkOrder' page
	 * 
	 * @return
	 */
	public Result addWorkOrderView() {
		return ok(addWarehouseWorkOrderForm.render());
	}

	/**
	 * Finds WorkOrder object by id and shows it
	 * 
	 * @param id
	 *            - WorkOrder object id
	 * @return
	 */
	public Result showWorkOrder(long id) {
		WarehouseWorkOrder wo = WarehouseWorkOrder.find.byId(id);
		if (wo == null) {
			Logger.error("error", "WORKORDER NULL");
			flash("error", "WORKORDER NULL!");
			return redirect("/");
		}
		return ok(showWarehouseWorkOrder.render(wo));
	}

	/**
	 * Finds WorkOrder object by ID passed as parameter, and then deletes it
	 * from database
	 * 
	 * @param id
	 *            - Work Order object id
	 * @return redirect to index after delete
	 */
	public Result deleteWorkOrder(long id) {
		try {
			WarehouseWorkOrder wo = WarehouseWorkOrder.find.byId(id);
			Logger.info("WorkOrder deleted: \"" + wo.id + "\"");
			wo.delete();
			return redirect("/allWarehouseWorkorders");
		} catch (Exception e) {
			flash("error", "Error at deleting WorkOrder!");
			Logger.error("ERROR AT DELETING WORKORDER: " + e.getMessage());
			return redirect("/allWarehouseWorkorders");
		}
	}

	/**
	 * first finds WorkOrder object by ID, and then sends it to the rendered
	 * template view for editing
	 * 
	 * @param id
	 *            long
	 * @return
	 */
	public Result editWorkOrderView(long id) {
		WarehouseWorkOrder wo = WarehouseWorkOrder.find.byId(id);
		// Exception handling.
		if (wo == null) {
			Logger.error("ERROR AT RENDERING WORKORDER EDITING VIEW  ");
			flash("error", "WORKORDER NULL");
			return ok(editWarehouseWorkOrderView.render(wo));
		}

		return ok(editWarehouseWorkOrderView.render(wo));

	}

	/**
	 * Finds WorkOrder object by ID, then takes and binds data from
	 * editWorkOrder view, and updates WorkOrder object with them
	 * 
	 * @param id
	 *            ID of the WorkOrder object
	 * @return Result
	 */
	public Result editWorkOrder(long id) {
		DynamicForm dynamicWorkOrderForm = Form.form().bindFromRequest();
		Form<WarehouseWorkOrder> workOrderform = Form.form(
				WarehouseWorkOrder.class).bindFromRequest();
		WarehouseWorkOrder wo = WarehouseWorkOrder.find.byId(id);

		String content;
		String location;

		try {
			if (workOrderform.hasErrors() || workOrderform.hasGlobalErrors()) {
				Logger.info("WORKORDER EDIT FORM ERROR");
				flash("error", "WORKORDER EDIT FORM ERROR");
				return ok(editWarehouseWorkOrderView.render(wo));
			}
			content = workOrderForm.bindFromRequest().get().content;
			location = workOrderForm.bindFromRequest().get().location;

			wo.content = content;
			wo.location = location;

			wo.save();

			Logger.info(session("name") + " EDITED WORK ORDER: " + wo.id);
			List<WarehouseWorkOrder> allWorkOrders = WarehouseWorkOrder.find
					.all();
			flash("success", "WORK ORDER SUCCESSFULLY EDITED!");
			return ok(listAllWarehouseWorkOrders.render(allWorkOrders));
		} catch (Exception e) {
			flash("error", "Error at editing TravelOrder");
			Logger.error("ERROR EDITING WORK ORDER: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	/**
	 * Creates the form for binding data from AddWorkOrderForm view, and uses
	 * that data for creating new WorkOrder object
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addWorkOrder() {
		DynamicForm dynamicWorkOrderForm = Form.form().bindFromRequest();
		Form<WarehouseWorkOrder> addWorkOrderForm = Form.form(
				WarehouseWorkOrder.class).bindFromRequest();

		if (addWorkOrderForm.hasErrors() || addWorkOrderForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Work Order");
			flash("error", "Error at Work Order form!");
			return redirect("/addWarehouseWorkorderView");
		}
		String content;
		String location;
		try {
			content = workOrderForm.bindFromRequest().get().content;
			location = workOrderForm.bindFromRequest().get().location;
			WarehouseWorkOrder wo = WarehouseWorkOrder.find
					.byId(WarehouseWorkOrder.createWarehouseWorkOrder(content,
							location));
			if (wo != null) {
				Logger.info(session("name") + " CREATED WORK ORDER ");
				flash("success", "WORK ORDER SUCCESSFULLY ADDED!");
				return redirect("/allWarehouseWorkorders");
			} else {
				flash("error", "ERROR ADDING WORK ORDER ");
				return redirect("/addWarehouseWorkorderView");
			}
		} catch (Exception e) {
			flash("error", "ERROR ADDING WORK ORDER ");
			Logger.error("ERROR ADDING WORK ORDER: " + e.getMessage(), e);
			return redirect("/addWarehouseWorkorderView");
		}
	}

	public Result listWorkOrders() {
		List<WarehouseWorkOrder> allWorkOrders = WarehouseWorkOrder.find.all();
		if (allWorkOrders != null) {
			return ok(listAllWarehouseWorkOrders.render(allWorkOrders));
		} else {
			flash("error", "NO WORK ORDERS IN DATABASE!");
			return redirect("/");
		}
	}
}
