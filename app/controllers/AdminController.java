package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Admin;
import models.Employee;
import models.Manager;
import models.ResetPassword;
import models.SuperUser;
import models.Vehicle;
import helpers.AdminFilter;
import helpers.HashHelper;
import helpers.MailHelper;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.i18n.Messages;
import play.Logger;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;

public class AdminController extends Controller {

	static Form<Employee> userForm = new Form<Employee>(Employee.class);
	
	
	@Security.Authenticated(AdminFilter.class)
	public Result changePass(long id) {
		DynamicForm updateForm = Form.form().bindFromRequest();
		if (updateForm.hasErrors()) {
			return redirect("/changePass/" + id);
		}
		Admin admin = Admin.findById(id);
		try{
			String oldPass = updateForm.data().get("password");
			String newPass = updateForm.data().get("newPassword");
			String confPass = updateForm.data().get("confirmPassword");
			
			if(!oldPass.equals(admin.password)) {
				flash("error", "Old password is not correct!");
				return redirect("/changePass/" + id);
			}
			
			if (oldPass.isEmpty() && !newPass.isEmpty() || newPass.isEmpty()
					&& !oldPass.isEmpty()) {
				flash("error", Messages.get("password.change.emptyField"));
				return redirect("/changePass/" + id);
			}
			if (!oldPass.isEmpty() && !newPass.isEmpty()) {
				if (HashHelper.checkPass(oldPass, admin.password) == false) {
					flash("error", Messages.get("password.old.incorrect"));
					return redirect("/changePass/" + id);				}
				if (newPass.length() < 6) {
					flash("error", Messages.get("password.shortPassword"));
					return redirect("/changePass/" + id);				}
				admin.password = HashHelper.createPassword(newPass);
			}
			if (!newPass.equals(confPass)) {
				flash("error", Messages.get("password.dontMatch"));
				return redirect("/changePass/" + id);			
			}
			
			if (admin.isAdmin()) {
				admin.save();
				flash("success", Messages.get("password.changed"));
				Logger.info(admin.name + " is updated");
				return redirect("/");
			}		
		}catch(Exception e){
			flash("error", "Error at changePass");
			Logger.error("Error at changePass: " + e.getMessage(), e);
			return redirect("/");
		}
		return redirect("/");
		}
	
	/**
	 * Search method for users. If search is unsuccessful a flash message is
	 * sent
	 * TODO: Find way to handle exceptions...
	 * @param string
	 * @return renders index with matching coupons
	 * //TODO render a different view for search result
	 */
	@Security.Authenticated(AdminFilter.class)
	public Result searchUsers(String qU) {
		
		List<Employee> employees = Employee.find.where()
				.ilike("username", "%" + qU + "%").findList();
		List<Manager> managers = Manager.find.where().ilike("name", "%" +qU +"%").findList();
		List<SuperUser> merged = new ArrayList<SuperUser>();
		merged.addAll(employees);
		merged.addAll(managers);		
		if (merged.isEmpty()) {
			flash("error", Messages.get("search.noResult"));
			return badRequest("/");
		}
		/* content negotiation */
		return ok("/");

	}
	
	public Result allUsers(long id) {
		Admin a=Admin.findById(id);
		/*
		//List<Manager> managers = new ArrayList<Manager>();
		//managers=Manager.find.all();
		List<SuperUser> allSuperUsers = new ArrayList<SuperUser>();
		
		merged.addAll(employees);
		merged.addAll(managers);		
		if (merged.isEmpty()) {
			flash("error", Messages.get("search.noResult"));
			return badRequest("/");
		}
		 content negotiation */
		return ok("/");

	}
	
	/**
	 * Pulls the input form from the registration form fields and creates a new
	 * user in the Database.
	 * 
	 * @return redirects to the index page with welcome, or renders the page
	 *         repeatedly if any error occurs
	 */
	public Result register() {
		
		Form<Employee> userForm = Form.form(Employee.class).bindFromRequest();
		
		if (userForm.hasErrors() || userForm.hasGlobalErrors()) {
			return ok(signup.render(userForm));
		}

		try {

			String name = userForm.bindFromRequest().get().name;
			String surname = userForm.bindFromRequest().get().surname;
			Date dob = userForm.bindFromRequest().get().dob;
			String gender = userForm.bindFromRequest().get().gender;
			String adress = userForm.bindFromRequest().get().adress;
			String city = userForm.bindFromRequest().get().city;
			String mail = userForm.bindFromRequest().get().email;
			String profilePic = userForm.bindFromRequest().get().profilePicture;
			String status = userForm.bindFromRequest().get().status;
			Date created = new Date();
			if(Employee.findByEmail(mail) != null) {
				flash("error", "Error at registration! Employee with that email already exists!");
				Logger.error("Error at registration: email already exists!");
				return ok(signup.render(userForm));
				
			}
			
			if(Employee.findByEmail(mail) == null) {
			Employee.createEmployee(name, surname, mail, adress, city, dob, gender, created, profilePic, status);
			}
			flash("success", name + " " + surname + " is successfully registered!");
			Logger.info(session("name") + " registered user: " + name + " " + surname);
			return redirect("/employeeList");
			

		} catch (Exception e) {
			flash("error", "Error at registration");
			Logger.error("Error at registration: " + e.getMessage(), e);
			return ok(signup.render(userForm));
		}
		

	}
	
	/**
	 * Updates the user from the Admin control.
	 * 
	 * @param id
	 *            of the user to update
	 * @return Result render the vies
	 */
	@Security.Authenticated(AdminFilter.class)
	public Result adminUpdateUser(long id) {
		
		Employee cUser = Employee.findById(id);
		List<Admin> admins = Admin.all();
		Form<Employee> updateForm = Form.form(Employee.class).bindFromRequest();
		if (updateForm.hasErrors() || updateForm.hasGlobalErrors()) {
			flash("error", "Error at updating employee, please try again!");
			return badRequest(adminEditUser.render(cUser, admins, updateForm)); // provjeriti
		}
		try {
			String name = updateForm.get().name;
			String surname = updateForm.get().surname;
			Date dob = updateForm.get().dob;
			String gender = updateForm.get().gender;
			String adress = updateForm.get().adress;
			String city = updateForm.get().city;
			String email = updateForm.get().email;
			String status = updateForm.get().status;
			String profPic = updateForm.get().profilePicture;
						
			if(Employee.findByEmail(email) != null && !Employee.findByEmail(email).equals(cUser)) {
				flash("error", "Error at updating employee! Employee with that email already exists!");
				Logger.error("Error at update employee: email already exists!");
				return badRequest(adminEditUser.render(cUser, admins, updateForm));
				
			}
			cUser.name = name;
			cUser.surname = surname;
			cUser.dob = dob;
			cUser.gender = gender;
			cUser.adress = adress;
			cUser.city = city;
			cUser.email = email;
			cUser.status = status;
			cUser.profilePicture = profPic;
			cUser.updated = new Date();
			cUser.save();
			flash("success", cUser.name + " " + cUser.surname + " is successfully updated!");
			Logger.info(session("name") + " updated user: " + cUser.name);
			return ok(employeeList.render(Employee.all()));
		} catch (Exception e) {
			flash("error", "An error has occurred in updating employee!");
			Logger.error("Error at adminUpdateUser: " + " ", e);
			return badRequest(adminEditUser.render(cUser, admins, updateForm));
		}
	}
	
	public Result listOfEmployees() {
		return ok(employeeList.render(Employee.all()));	
	}
	
	/**
	 * TODO Method for sending reset password email.
	 * @return
	 */
//	@Security.Authenticated(AdminFilter.class)
	public Result sendRequest(String email) {
		try{
			Admin admin = Admin.findByEmail(email);
			Manager manager = Manager.findByEmail(email);
			SuperUser superuser;
			
			if (admin == null && manager == null) {
				flash("error", Messages.get("password.reset.invalidEmail"));
				return redirect("/loginpage");
			}
			String password;
			if(admin == null){
				superuser = manager;
				password = manager.password;
			}else{
				superuser = admin;
				password = admin.password;
			}
			
			String verificationEmail = ResetPassword.createRequest(superuser.email);
			MailHelper.send(
					"We received your request", 
					email,
					"Your password is: "
					+ "<br>" 
					+ password);
			flash("success", Messages.get("password.reset.requestSuccess") + email);
			return  redirect("/loginpage");			
		}catch(Exception e){
			flash("error", "error");
			Logger.error("Error at sendRequest: " +e.getMessage(), e);
			return redirect("/");
		}
	}
	
	/**
	 * Renders the admin panel view
	 * @param id of the current user
	 * @return
	 */
	@Security.Authenticated(AdminFilter.class)
	public Result controlPanel(long id) {
		Admin user = Admin.findById(id);
		if (user == null) {
			flash("error", "error");
			return redirect("/");
		}
		if (!user.name.equals(session("name"))) {
			return redirect("/");
		}

		return ok(adminPanel.render(user, null));

	}

	/**
	 * Method which deleting Employee from DB
	 * @param id of Employee
	 */
	public static void active(long id) {
		Employee user = Employee.findById(id);
		user.status = Employee.DELETED;
		user.save();
	}
	
	/**
	 * Receives a user id, initializes the user, and renders the adminEditUser
	 * passing @_updateUserForm(userForm, employee) the user to the view
	 * TODO: Handle exceptions.
	 * @param id
	 *            of the User (long)
	 * @return Result render adminEditUser
	 */
	@Security.Authenticated(AdminFilter.class)
	public Result adminEditUserView(String email) {

		List<Admin> adminList = Admin.all();		
		Employee userToUpdate = Employee.findByEmail(email);
		if(userToUpdate != null){
			Form<Employee> userForm = Form.form(Employee.class).fill(userToUpdate);
			return ok(adminEditUser.render(userToUpdate, adminList, userForm));
		}else{
			Logger.debug("In employee edit");
			flash("error", "error");
			return redirect("/");
		}
		
	}
	
	/**
	 * Delete employee by id. Delete is possible only for own deletion, or if it's
	 * done by Admin.
	 * 
	 * @param id
	 *            Long
	 * @return Result renders the same view
	 */
	@Security.Authenticated(AdminFilter.class)
	public Result deleteUser(long id) {

		try {
			
			Employee u = Employee.findById(id);				
			u.status = Employee.DELETED;
			u.save();
			return ok(employeeList.render(Employee.all()));
		} catch (Exception e) {
			flash("error", "Error at deleting employee!");
			Logger.error("Error at deleteUser: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	/**
	 * Renders the profile page view
	 * 
	 * @param username
	 * @return Result
	 */
	@Security.Authenticated(AdminFilter.class)
	public Result profilePage(String email) {
			Employee user = Employee.findByEmail(email);
			if (user != null) {
				return ok(profile.render(user));
			} 
			flash("error", "error");
			return redirect("/employeeList");
	}

	/**
	 * TODO check if method works properly.
	 * @return
	 */
	public Result newPassword(long id) {		
		return ok(changePass.render(id));
	}
	
	public Result registerAdmin() {	
		Form<Admin> userForm = Form.form(Admin.class).bindFromRequest();
		
		if (userForm.hasErrors() || userForm.hasGlobalErrors()) {
			return ok(adminRegisterForm.render(userForm));
		}

		try {

			String name = userForm.bindFromRequest().get().name;
			String surname = userForm.bindFromRequest().get().surname;
		
			String adress = userForm.bindFromRequest().get().adress;
			String city = userForm.bindFromRequest().get().city;
			String mail = userForm.bindFromRequest().get().email;
			String rolee = userForm.bindFromRequest().field("role").value();
			boolean role = userForm.bindFromRequest().get().isAdmin;
			String password = userForm.bindFromRequest().get().password;
			String hashPass = HashHelper.createPassword(password);
			String confPass = userForm.bindFromRequest()
					.field("confirmPassword").value();

			if (!password.equals(confPass)) {
				flash("error", "Password don't match!");
				return badRequest(adminRegisterForm.render(userForm));
			}


			else if (Admin.verifyRegistration(name, mail) == true) {
				if(rolee.equals("admin")) {
				Admin.createAdmin(name, surname, mail, hashPass,
						adress, city, true, false);
				flash("success", "Successfully registered admin");
				Logger.info("Successfully registered admin " + mail);
				return ok(index.render(" "));
				} else {
					Manager.createManager(name,surname,mail,hashPass,adress,city,false,true);
					flash("success", "Successfully registered manager");
					Logger.info("Successfully registered admin " + mail);
					return ok(index.render(" "));
				}
				

			} else {
				flash("error", Messages.get("registration.emailAlreadyExists"));
				Logger.info("Email allready exists!");
				return badRequest(adminRegisterForm.render(userForm));
			}
			

		} catch (Exception e) {
			flash("error", "Error at registration");
			Logger.error("Error at registration: " + e.getMessage(), e);
			return ok(adminRegisterForm.render(userForm));
		}
	}
	
	public Result listUsers() {
		List<SuperUser> allUsers = new ArrayList<SuperUser>();
		List<Admin> allAdmins = Admin.all();
		List<Manager> allManagers = Manager.all();
		allUsers.addAll(allAdmins);
		allUsers.addAll(allManagers);
		// flash("addVehicleForMaintenance",
		// "For adding Vehicle Maintenance choose vehicle");
		return ok(listAllUsers.render(allUsers));
	}
}
