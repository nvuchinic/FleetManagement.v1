package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import models.Driver;
import models.Fleet;
import models.Route;
import models.Task;
import models.Vehicle;
import models.WorkOrder;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class TaskController extends Controller {

	public static Finder<Long, Task> find = new Finder<Long, Task>(Task.class);

	static Form<Task> taskForm = Form.form(Task.class);

	/**
	 * Generates view(form) for adding new Task object
	 * 
	 * @return
	 */
	public Result addTaskView() {
		return ok(addTaskForm.render());
	}

	/**
	 * 
	 * Creates a new Task object or renders the view again if any error occurs.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addTask() {
		Form<Task> addTaskForm = Form.form(Task.class).bindFromRequest();
	    DynamicForm dynamicTaskForm = Form.form().bindFromRequest();

		if (taskForm.hasErrors() || taskForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Task");
			flash("error", "Error at Task adding  form!");
			return redirect("/addTaskView");
		}
		java.util.Date utilDate = new java.util.Date();
		   String stringDate;
		   Date returnDate = null;
		try {
			String name = addTaskForm.bindFromRequest().get().name;
			String description = addTaskForm.bindFromRequest().get().description;
			stringDate  = dynamicTaskForm.get("date");
			SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
			 utilDate = format.parse( stringDate );
				returnDate = new java.sql.Date(utilDate.getTime());
			String ids = addTaskForm.bindFromRequest().field("workOrderId")
					.value();
			WorkOrder workOrder = null;
			if (!ids.equals(" ")) {
				long idl = Long.parseLong(ids);
				workOrder = WorkOrder.findById(idl);
				
			
			Task.createTask(name, returnDate, description, workOrder);
			flash("success", "Task added successfully!");
			Logger.info("Task ADDED SUCCESSFULLY///////////////////////");
			return redirect("/allTasks");
			} else {
				Task.createTask(name, returnDate, description);
				flash("success", "Task added successfully!");
				Logger.info("Task ADDED SUCCESSFULLYXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
				return redirect("/allTasks");
			}
		} catch (Exception e) {
			flash("error", "Error at adding task!");
			Logger.error("ADDING TASK ERROR: " + e.getMessage(), e);
			return redirect("/addTaskView");
		}
	}

	/**
	 * Finds Task object based on passed ID parameter and shows it in view
	 * @param id - Task object ID
	 * @return
	 */
	public Result showTask(long id) {
		Task t = Task.findById(id);
		if (t == null) {
			Logger.error("error", "Task IS NULL");
			flash("error", "No such task in database!!!");
			return redirect("/allTasks");
		}
		return ok(showTask.render(t));
	}

	/**
	 * Finds Task object using passed ID parameter and then removes it from
	 * database
	 * 
	 * @param id
	 *            - Task object ID
	 * @return
	 */
	public Result deleteTask(long id) {
		try {
			Task t = Task.findById(id);
			Logger.info("Task DELETED: \"" + t.id);
			Task.deleteTask(id);
			return redirect("/allTasks");
		} catch (Exception e) {
			flash("error", "Error deleting task!");
			Logger.error("ERROR DELETING TASK: " + e.getMessage());
			return redirect("/");
		}
	}

	/**
	 * Renders the view for editing Task object.
	 * 
	 * @param Task
	 *            object ID number
	 * @return
	 */
	public Result editTaskView(long id) {
		Task t = Task.findById(id);
		// Exception handling.
		if (t == null) {
			flash("error", "No such task in database");
			return redirect("/allTasks");
		}
		return ok(editTaskView.render(t));

	}

	/**
	 * Finds the specific Task object using passed ID parameter, and updates it
	 * 
	 * @param id
	 *            ID number of Task object
	 * @return Result
	 */
	public Result editTask(long id) {
		Task t = Task.findById(id);
		Form<Task> taskForm = Form.form(Task.class).bindFromRequest();
		DynamicForm dynamicTaskForm = Form.form().bindFromRequest();
		if (taskForm.hasErrors() || taskForm.hasGlobalErrors()) {
			Logger.info("ROUTE UPDATE ERROR");
			flash("error", "UPDATE ROUTE ERROR");
			return ok(editTaskView.render(t));
		}
		java.util.Date utilDate = new java.util.Date();
		   String stringDate;
		   Date returnDate = null;
		try {

			String name = taskForm.bindFromRequest().get().name;
			stringDate  = dynamicTaskForm.get("date");
			SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
			 utilDate = format.parse( stringDate );
				returnDate = new java.sql.Date(utilDate.getTime());
			String description = taskForm.bindFromRequest().get().description;
			String ids = taskForm.bindFromRequest().field("workOrderId")
					.value();
			WorkOrder workOrder = null;
			if (ids != null) {
				long idl = Long.parseLong(ids);
				workOrder = WorkOrder.findById(idl);
			
			t.name = name;
			t.description = description;
			t.dateTime = returnDate;
			t.workOrder = workOrder;
			t.save();
			Logger.info("TASK UPDATED");
			flash("success", "Task updated successfully!");
			return ok(showTask.render(t));
			} else {
				t.name = name;
				t.description = description;
				t.dateTime = returnDate;
				t.save();
				Logger.info("TASK UPDATED");
				flash("success", "Task updated successfully!");
				return ok(showTask.render(t));
			}
		} catch (Exception e) {
			flash("error", "Error at updating task");
			Logger.error("ERROR UPDATING ROUTE: " + e.getMessage(), e);
			return redirect("/allTasks");
		}
	}

	public Result listTasks() {
		List<Task> allTasks = Task.tasksList();
		if (allTasks != null) {
			return ok(listAllTasks.render(allTasks));
		} else {
			flash("error", "No tasks records in database!");
			return redirect("/");
		}
	}
	
	public Result removeFromWorkOrder(long id) {
		Task t = Task.findById(id);
		Task.deleteTaskFromWO(id);
		flash("success", "Task successfully removed from WorkOrder");

		return ok(editWorkOrderView.render(t.workOrder, Driver.availableDrivers(), Vehicle.availableVehicles()));
	}
}
