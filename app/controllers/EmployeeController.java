package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import com.avaje.ebean.Model.Finder;

import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints.Email;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class EmployeeController extends Controller{

	/**
	 * Finder for Employee object
	 */
	public static Finder<Long, Employee> find = new Finder<Long, Employee>(
			Employee.class);

	static Form<Employee> employeeForm = Form.form(Employee.class);

	/**
	 * Renders the view(form) for creating new Employee object
	 * @return
	 */
	public Result addEmployeeView() {
	return ok(addEmployeeForm.render());
	}

	
	 /**
	 * Finds Employee object by it's ID number and shows it in view
	 * @param id - Employee object id
	 * @return
	 */
	 public Result showEmployee(long id) {
	 Employee emp = Employee.find.byId(id);
	 if (emp == null) {
	 Logger.error("error", "EMPLOYEE IS NULL");
	 flash("error", "EMPLOYEE IS NULL!");
	 return redirect("/");
	 }
	 return ok(showEmployee.render(emp));
	 }

	 
	/**
	 * Finds Employee object by it's ID number passed as parameter,
	 *  and then deletes it from database
	 *  @param id - Employee object id
	 * @return 
	 */
	public Result deleteEmployee(long id) {
		try {
			Employee emp = Employee.find.byId(id);
			Logger.info("EMPLOYEE SUCCESSFULLY DELETED");
			emp.delete();
			return redirect("/allemployees");
		} catch (Exception e) {
			flash("error", "ERROR DELETING EMPLOYEE!");
			Logger.error("ERROR DELETING EMPLOYEE: " + e.getMessage());
			return redirect("/allemployees");
		}
	}

	/**
	 * First finds Employee object by it's ID number, 
	 * and then sends it to the rendered
	 * template view for editing
	 * 	 * @param id- ID number of Employee object
	 * @return
	 */
	public Result editEmployeeView(long id) {
		Employee emp = Employee.find.byId(id);
		// Exception handling.
		if (emp == null) {
			Logger.error("EMPLOYEE IS NULL");
			flash("error", "EMPLOYEE IS NULL");
			return ok(editEmployeeView.render(emp));
		}
		return ok(editEmployeeView.render(emp));

	}

	/**
	 * Finds Employee object by it's ID, 
	 * then updates it with values obtained from request(entered through form for editing),
	 * @param id- ID number of the Employee object
	 * @return Result
	 */
	public Result editEmployee(long id) {
		Employee emp = Employee.find.byId(id);
		DynamicForm dynamicEmployeeForm = Form.form().bindFromRequest();
		Form<Employee> employeeForm = Form.form(Employee.class)
				.bindFromRequest();
		String firstName=null;
		String lastName=null;
		String address=null;
		String phone=null;
		String email;
		java.util.Date utilDate = new java.util.Date();
		String stringDOB=null;
		Date dob= null;
		String isDriver;
		try {
			if (employeeForm.hasErrors() || employeeForm.hasGlobalErrors()) {
			Logger.info("EMPLOYEE EDIT FORM ERROR");
				flash("error", "EMPLOYEE EDIT FORM ERROR");
				return ok(editEmployeeView.render(emp));
			}
			isDriver = employeeForm.bindFromRequest()
					.field("driver").value();
			System.out.println("IS_DRIVER CHECKBOX VALUE:"+isDriver);
			firstName = employeeForm.bindFromRequest().get().firstName;
			lastName = employeeForm.bindFromRequest().get().lastName;
			address = dynamicEmployeeForm.get("address");
			phone = dynamicEmployeeForm.get("phoneNumber");
			email= employeeForm.bindFromRequest().get().email;
			stringDOB = dynamicEmployeeForm.get("dateOfBirth");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDOB);
			dob = new java.sql.Date(utilDate.getTime());
			emp.firstName=firstName;
			emp.lastName=lastName;
			emp.address=address;
			emp.phone=phone;
			emp.email=email;
			if(isDriver.equalsIgnoreCase("true")){
				emp.isDriver=true;
			}
			emp.save();
			flash("success", "EMPLOYEE SUCCESSFULLY EDITED!");
			return ok(showEmployee.render(emp));
		} catch (Exception e) {
			flash("error", "ERROR EDITING EMPLOYEE");
			Logger.error("ERROR EDITING EMPLOYEE: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	/**
	 * Creates new Employee object using values from post request(collected through form)
		 * @return
	 * @throws ParseException
	 */
	public Result addEmployee() {
		DynamicForm dynamicEmployeeForm = Form.form().bindFromRequest();
		Form<Employee> addEmployeeForm = Form.form(Employee.class)
				.bindFromRequest();
//		if (addEmployeeForm.hasErrors() || addEmployeeForm.hasGlobalErrors()) {
//			Logger.debug("ERROR AT EMPLOYEE ADD FORM");
//			flash("error", "ERROR AT EMPLOYEE ADD FORM!");
//			return redirect("/addemployeeview");
//		}
		String firstName=null;
		String lastName=null;
		String username=null;
		String address=null;
		String phone=null;
		String email;
		java.util.Date utilDate = new java.util.Date();
		String stringDOB=null;
		Date dob= null;
		String isDriver;
		try {
			SimpleDateFormat format=null, format2=null;
			isDriver = employeeForm.bindFromRequest()
					.field("driver").value();
			System.out.println("IS_DRIVER CHECKBOX VALUE:"+isDriver);
			firstName = employeeForm.bindFromRequest().get().firstName;
			lastName = employeeForm.bindFromRequest().get().lastName;
			username = dynamicEmployeeForm.get("uName");
			System.out.println("PRINTING USERNAME: "+username);
			address = dynamicEmployeeForm.get("address");
			phone = dynamicEmployeeForm.get("phoneNumber");
			email= employeeForm.bindFromRequest().get().email;
			stringDOB = dynamicEmployeeForm.get("dateOB");
			System.out.println("PRINTING DATE OF BIRTH:"+stringDOB);
			if(stringDOB.contains("-")){
				format = new SimpleDateFormat("yyyy-MM-dd");
			}
			else{
				format = new SimpleDateFormat("MM/dd/yyy");
			}
			System.out.println("PRINTING START DATE: "+stringDOB);
			utilDate = format.parse(stringDOB);
			dob = new java.sql.Date(utilDate.getTime());
//			stringDOB = dynamicEmployeeForm.get("dateOfBirth");
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			utilDate = format.parse(stringDOB);
//			dob = new java.sql.Date(utilDate.getTime());
			Employee emp= Employee.savetoDB(firstName, lastName, dob,  address, phone, email);
			if(isDriver!=null){
				emp.isDriver=true;
				emp.save();
			}
			if(username!=null && !(username.isEmpty())){
			emp.userName=username;
			emp.save();
			}
			if (emp != null) {
				flash("success", "EMPLOYEE SUCCESSFULLY ADDED!");
				 return ok(showEmployee.render(emp));
			} else {
				flash("error", "ERROR ADDING EMPLOYEE ");
				return redirect("/addemployeeview");
			}
		} catch (Exception e) {
			flash("error", "ERROR ADDING EMPLOYEE ");
			Logger.error("ERROR ADDING EMPLOYEE: " + e.getMessage(), e);
			return redirect("/addemployeeview");
		}
	}

	public Result listEmployees() {
		List<Employee> allEmployees = Employee.find.all();
		if (allEmployees != null) {
			return ok(listAllEmployees.render(allEmployees));
		} else {
			flash("error", "NO EMPLOYEES IN DATABASE!");
			return redirect("/");
		}
	}
}
