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
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class IssueController extends Controller {

	private static final String OPEN = "OPEN";
	private static final String OVERDUE = "OVERDUE";
	private static final String RESOLVED = "RESOLVED";
	private static final String CLOSED = "CLOSED";
	
	/**
	 * finder for Issue object
	 */
	public static Finder<Long, Issue> find = new Finder<Long, Issue>(
			Issue.class);

	/**
	 * Renders view(form) for creating new Issue object
	 * @return
	 */
	public Result addIssueView() {
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Employee> allEmployees=new ArrayList<Employee>();
		allEmployees=Employee.find.all();
		List<Employee> drivers=new ArrayList<Employee>();
		for(Employee emp:allEmployees){
			if(emp.isDriver==true){
				drivers.add(emp);
			}
		}
		if(allVehicles.size()==0){
			flash("error", "CANNOT CREATE NEW ISSUE, NO VEHICLE RECORDS IN DATABASE!" );
			return ok(listAllIssues.render(Issue.find.all()));
		}
		if(drivers.size()==0){
			flash("error", "CANNOT CREATE NEW ISSUE, NO DRIVER RECORDS IN DATABASE!" );
			return ok(listAllIssues.render(Issue.find.all()));
		}
		if(allEmployees.size()==0){
			flash("error", "CANNOT CREATE NEW ISSUE, NO EMPLOYEE RECORDS IN DATABASE!" );
			return ok(listAllIssues.render(Issue.find.all()));
		}
		return ok(addIssueForm.render(allVehicles,drivers, allEmployees));
	}

	/**
	 * 
	 * Creates a new Issue object using values from post request
	 * (collected through the form for creating new Issue object)
	 * @return
	 * @throws ParseException
	 */
	public Result addIssue() {
		DynamicForm dynamicIssueForm = Form.form().bindFromRequest();
		Form<Issue> addIssueForm = Form.form(Issue.class).bindFromRequest();
		/*
		 * if (addIssueForm.hasErrors() || addIssueForm.hasGlobalErrors()) {
		 * Logger.debug("ERROR ADDING ISSUE"); flash("error",
		 * "ERROR ADDING ISSUE!"); return redirect("/allissues"); }
		 */
		SimpleDateFormat format=null, format2=null;
		java.util.Date utilDate = new java.util.Date();
		String stringIssueDate;
		String reportEmployeeName;
		String assignedEmployeeName;
		String vid;
		Vehicle vehicle;
		Date issueDate;
		String summary;
		 String description;
		 long odometer;
		Employee reportingEmployee;
		Employee assignedEmployee;
		try {
			summary = addIssueForm.bindFromRequest().get().summary;
			description = addIssueForm.bindFromRequest().get().description;
			odometer= addIssueForm.bindFromRequest().get().odometer;
			reportEmployeeName = dynamicIssueForm.get("reportEmployeeName");
			System.out.println("//////////////////////PRINTING REPORTING EMPLOYEE NAME: "+reportEmployeeName);
			reportingEmployee=Employee.findByName(reportEmployeeName);
			assignedEmployeeName = dynamicIssueForm.get("assignEmployeeName");
			System.out.println("//////////////////////PRINTING ASSIGNED EMPLOYEE NAME: "+reportEmployeeName);
			assignedEmployee=Employee.findByName(assignedEmployeeName);
			vid = dynamicIssueForm.get("vid");
			vehicle=Vehicle.findByVid(vid);
			//stringIssueDate = dynamicIssueForm.get("issue_date");
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//utilDate = format.parse(stringIssueDate);
			//issueDate = new java.sql.Date(utilDate.getTime());
			stringIssueDate = dynamicIssueForm.get("iDate");
			System.out.println("PRINTING ISSUE DATE:"+stringIssueDate);
			if(stringIssueDate.contains("-")){
				format = new SimpleDateFormat("yyyy-MM-dd");
			}
			else{
				format = new SimpleDateFormat("MM/dd/yyy");
			}
			System.out.println("PRINTING START DATE: "+stringIssueDate);
			utilDate = format.parse(stringIssueDate);
			issueDate = new java.sql.Date(utilDate.getTime());
			System.out.println("PRINTING ISSUE DATE: "+ issueDate);
			Issue i=Issue.saveToDB(vehicle, issueDate, summary, description, odometer, reportingEmployee, assignedEmployee);
			if(i==null){
				System.out.println("ISSUE IS NULL//////////////");
			}
			if(reportingEmployee==null){
				System.out.println("REPORTING EMPLOYEE IS NULL////////////////");
			}
			if(reportingEmployee.reportedIssues==null){
				System.out.println("REPORTED ISSUES FOR EMPLOYEE IS NULL ////////////////////");
			}
			reportingEmployee.reportedIssues.add(i);
			reportingEmployee.save();
			assignedEmployee.assignedIssues.add(i);
			assignedEmployee.save();
			vehicle.issues.add(i);
			vehicle.save();
			System.out.println("ISSUE ADDED SUCCESSFULLY///////////////////////");
			Logger.info("CLIENT ADDED SUCCESSFULLY///////////////////////");
			flash("success", "ISSUE SUCCESSFULLY ADDED");
			return redirect("/allissues");
		} catch (Exception e) {
			flash("error", "ERROR ADDING ISSUE ");
			Logger.error("ADDING ISSUE ERROR: " + e.getMessage(), e);
			return redirect("/addissueview");
		}
	}

	/**
	 * Finds Issue object (if exists) by it's ID number, 
	 * and then shows it in view
	 * @param id - issue object ID
	 * @return
	 */
	public Result showIssue(long id) {
			if (Issue.findById(id) == null) {
			Logger.error("error", "ISSUE IS NULL");
			flash("error", "NO ISSUE RECORD WITH THAT ID!");
			return redirect("/allissues");
		}
		Issue i = Issue.findById(id);
		return ok(showIssue.render(i));
	}

	/**
	 * Finds Issue object by it's ID number passed as argument,
	 * and then deletes it from database
	 * @param id- Issue object ID
	 * @return
	 */
	public Result deleteIssue(long id) {
		try {
			if (Issue.findById(id) == null) {
				Logger.error("error", "ISSUE IS NULL");
				flash("error", "NO ISSUE RECORD WITH THAT ID!");
				return redirect("/allissues");
			}
			Issue i= Issue.findById(id);
			Client.deleteClient(id);
			Logger.info("ISSUE SUCCESSFULLY DELETED" );
			return redirect("/allissues");
		} catch (Exception e) {
			flash("deleteClient	Error", "ERROR DELETING ISSUE!");
			Logger.error("ERROR DELETING ISSUE: " + e.getMessage());
			return redirect("/allissues");
		}
	}

	/**
	 * Renders view (template) for editing Issue object
	 * @param id-ID number of the Issue object
	 * @return
	 */
	public Result editIssueView(long id) {
		if (Issue.findById(id) == null) {
			Logger.error("error", "ISSUE IS NULL");
			flash("error", "NO ISSUE RECORD WITH THAT ID!");
			return redirect("/allissues");
		}
		Issue i= Issue.findById(id);
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		List<Employee> allEmployees=new ArrayList<Employee>();
		allEmployees=Employee.find.all();
		List<Employee> drivers=new ArrayList<Employee>();
		for(Employee emp:allEmployees){
			if(emp.isDriver==true){
				drivers.add(emp);
			}
		}
		return ok(editIssueView.render(i,allVehicles, drivers, allEmployees));

	}

	/**
	 * Finds the Issue object by it's ID number passed as argument,
	 *  and updates it with values from request (collected from editIssue view form).
	 *  @param id- ID number of Issue object
	 * @return Result
	 */
	public Result editIssue(long id) {
		DynamicForm dynamicIssueForm = Form.form().bindFromRequest();
		Form<Issue> addIssueForm = Form.form(Issue.class).bindFromRequest();
		if (Issue.findById(id) == null) {
			Logger.error("error", "ISSUE IS NULL");
			flash("error", "NO ISSUE RECORD WITH THAT ID!");
			return redirect("/allissues");
		}
		Issue i = Issue.findById(id);
		java.util.Date utilDate = new java.util.Date();
		String stringIssueDate;
		String reportingEmployeeName;
		String assignedEmployeeName;
		String vid;
		Vehicle vehicle;
		Date issueDate;
		String summary;
		 String description;
		 long odometer;
		Employee reportingEmployee;
		Employee assignedEmployee;
		try {
			summary = addIssueForm.bindFromRequest().get().summary;
			description = addIssueForm.bindFromRequest().get().description;
			odometer= addIssueForm.bindFromRequest().get().odometer;
			reportingEmployeeName = dynamicIssueForm.get("reportingEmployeeName");
			if(Employee.findByName(reportingEmployeeName)==null){
				flash("error", "NO EMPLOYEE RECORD WITH THE NAME: "+reportingEmployeeName);
				return redirect("/showissue/"+id);
			}
			reportingEmployee=Employee.findByName(reportingEmployeeName);
			assignedEmployeeName = dynamicIssueForm.get("assignedEmployee");
			if(Employee.findByName(assignedEmployeeName)==null){
				flash("error", "NO EMPLOYEE RECORD WITH THE NAME: "+ assignedEmployeeName);
				return redirect("/showissue/"+id);
			}
			assignedEmployee=Employee.findByName(assignedEmployeeName);
			vid = dynamicIssueForm.get("vid");
			if(Vehicle.findByVid(vid)==null){
				flash("error", "NO VEHICLE RECORD WITH ID: "+vid);
				return redirect("/showissue/"+id);
			}
			vehicle=Vehicle.findByVid(vid);
			stringIssueDate = dynamicIssueForm.get("issueDate");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringIssueDate);
			issueDate = new java.sql.Date(utilDate.getTime());
			i.summary=summary;
			i.description=description;
			i.odometer=odometer;
			i.vehicle=vehicle;
			i.reportingEmployee=reportingEmployee;
			i.assignedEmployee=assignedEmployee;
			i.issueDate=issueDate;
			i.save();
			reportingEmployee.reportedIssues.add(i);
			reportingEmployee.save();
			assignedEmployee.assignedIssues.add(i);
			assignedEmployee.save();
			Logger.info("ISSUE UPDATED");
			flash("success", "iSSUE UPDATED SUCCESSFULLY!");
			return ok(showIssue.render(i));
		} catch (Exception e) {
			flash("error", "ERROR ADDING ISSUE ");
			Logger.error("EDITING ISSUE ERROR: " + e.getMessage(), e);
			return redirect("/editissueview");
		}
	}

	public Result listIssues() {
		List<Issue> allIssues = Issue.listOfIssues();
		if (allIssues!= null) {
			return ok(listAllIssues.render(allIssues));
		} else {
			flash("error", "NO ISSUE RECORDS IN DATABASE!");
			return redirect("/");
		}
	}
	
	public Result closeIssue(long id){
		if(Issue.findById(id)==null){
		flash("error", "ISSUE IS NULL!");
		return redirect("/allissues");
		}
		Issue is=Issue.findById(id);
		is.status=CLOSED;
		is.save();
		flash("success", "THIS ISSUE IS CLOSED!");
		return ok(showIssue.render(is));
	}
	
	public Result openIssue(long id){
		if(Issue.findById(id)==null){
		flash("error", "ISSUE IS NULL!");
		return redirect("/allissues");
		}
		Issue is=Issue.findById(id);
		is.status=OPEN;
		is.save();
		flash("success", "THIS ISSUE IS REOPENED!");
		return ok(showIssue.render(is));
	}
}
