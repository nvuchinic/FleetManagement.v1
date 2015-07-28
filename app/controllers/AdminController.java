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
import helpers.AdminFilter;
import helpers.HashHelper;
import helpers.MailHelper;
import helpers.SuperUserFilter;
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
	public Result changePass(String email) {
		DynamicForm updateForm = Form.form().bindFromRequest();
		if (updateForm.hasErrors()) {
			return redirect("/updateUser ");
		}
		Admin admin = (Admin) SuperUser.getSuperUser(email);
		try{
			String oldPass = updateForm.data().get("password");
			String newPass = updateForm.data().get("newPassword");
			String confPass = updateForm.data().get("confirmPassword");
			
			/* if only one password field is filled out */
			if (oldPass.isEmpty() && !newPass.isEmpty() || newPass.isEmpty()
					&& !oldPass.isEmpty()) {
				flash("error", Messages.get("password.change.emptyField"));
				return redirect("/updateUser ");
			}
			if (!oldPass.isEmpty() && !newPass.isEmpty()) {
				if (HashHelper.checkPass(oldPass, admin.password) == false) {
					flash("error", Messages.get("password.old.incorrect"));
					return redirect("/updateUser ");				}
				if (newPass.length() < 6) {
					flash("error", Messages.get("password.shortPassword"));
					return redirect("/updateUser ");				}
				admin.password = HashHelper.createPassword(newPass);
			}
			if (!newPass.equals(confPass)) {
				flash("error", Messages.get("password.dontMatch"));
				return redirect("/updateUser ");			
			}
			
			if (admin.isAdmin()) {
				admin.save();
				flash("success", Messages.get("password.changed"));
				Logger.info(admin.name + " is updated");
				return ok(profile.render(admin));
			}		
		}catch(Exception e){
			flash("error", "Error at changePass");
			Logger.error("Error at changePass: " + e.getMessage(), e);
			return redirect("/");
		}
		return ok(profile.render(admin));
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
	
	/**
	 * Pulls the input form from the registration form fields and creates a new
	 * user in the Database.
	 * 
	 * @return redirects to the index page with welcome, or renders the page
	 *         repeatedly if any error occurs
	 */
	public Result register() {
		
		Form<Employee> submit = Form.form(Employee.class).bindFromRequest();
		
		if (submit.hasErrors() || submit.hasGlobalErrors()) {
			return ok(signup.render(submit));
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
			int status = userForm.bindFromRequest().get().status;

			
			Employee.createEmployee(name, surname, mail, adress, city, dob, gender, profilePic, status);
			
			
			
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

		Form<Employee> userForm = new Form<Employee>(Employee.class);

		DynamicForm updateForm = Form.form().bindFromRequest();
		if (userForm.hasErrors() || userForm.hasGlobalErrors()) {
			return redirect("/@editUser/:" + id); // provjeriti
		}
		try {
			String username = updateForm.data().get("name");
			String surname = updateForm.data().get("surname");
			String dobString = updateForm.data().get("dob");
			String gender = updateForm.data().get("gender");
			String adress = updateForm.data().get("adress");
			String city = updateForm.data().get("city");
			String email = updateForm.data().get("email");
			String admin = updateForm.data().get("isAdmin");
			Date dob = null;
			Employee cUser = (Employee) SuperUser.findById(id);
			
			if (!dobString.isEmpty()){
				dob = new SimpleDateFormat("yy-mm-dd").parse(dobString);
				cUser.dob = dob;
			}			

			cUser.name = username;
			cUser.surname = surname;
			cUser.dob = dob;
			cUser.gender = gender;
			cUser.adress = adress;
			cUser.city = city;
			cUser.email = email;

			
			cUser.updated = new Date();
			cUser.save();
			flash("success", cUser.name + " " + "updatedSuccessfully");
			Logger.info(session("name") + " updated user: " + cUser.name);
			return ok(index.render(" "));
		} catch (Exception e) {
			flash("error", "error");
			Logger.error("Error at adminUpdateUser: " + " ", e);
			return redirect("/");
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

}
