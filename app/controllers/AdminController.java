package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Admin;
import models.Employee;
import models.Manager;
import models.SuperUser;
import helpers.AdminFilter;
import helpers.HashHelper;
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
			return badRequest(userList.render( SuperUser.allSuperUsers()));
		}
		/* content negotiation */
		return ok(userList.render(merged));

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

			String name = submit.bindFromRequest().get().name;
			String surname = submit.bindFromRequest().get().surname;
			Date dob = submit.bindFromRequest().get().dob;
			String gender = submit.bindFromRequest().get().gender;
			String adress = submit.bindFromRequest().get().adress;
			String city = submit.bindFromRequest().get().city;
			String mail = submit.bindFromRequest().get().email;
			
		
			Employee newEmployee = new Employee(name, surname, mail, adress, city, dob, gender, dob, null, 0);
			newEmployee.created = new Date();
			newEmployee.save();
			
			flash("success", newEmployee.name + " " + Messages.get("updatedSuccessfully"));
			Logger.info(session("name") + " registered user: " + newEmployee.name);
			return ok(userList.render(SuperUser.allSuperUsers()));

		} catch (Exception e) {
			flash("error", "Error at registration");
			Logger.error("Error at registration: " + e.getMessage(), e);
			return badRequest(signup.render(submit));
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
			String username = updateForm.data().get("username");
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
			flash("success", cUser.name + " " + Messages.get("updatedSuccessfully"));
			Logger.info(session("name") + " updated user: " + cUser.name);
			return ok(index.render(" "));
		} catch (Exception e) {
			flash("error", "error");
			Logger.error("Error at adminUpdateUser: " + e.getMessage(), e);
			return redirect("/");
		}
	}

}
