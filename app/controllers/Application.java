package controllers;

import models.Admin;
import models.Employee;
import models.Login;
import models.Manager;
import models.SuperUser;
import play.*;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	
	static String loginSuccess = Messages.get("login.Success");
	
	
	/**
	 * @return render the index page
	 */
	public Result index() {
		
		return ok(index.render(" "));

	}

	/**
	 * @return Renders the registration view
	 */
	public Result signup() {
		return ok(signup.render(new Form<Employee>(Employee.class)));
	}
	
	/**
	 * @return Renders the registration view
	 */
	public Result signupAdmin() {
		return ok(adminRegisterForm.render(new Form<Admin>(Admin.class)));
	}
	
	/**
	 * Pulls the value from two login fields and verifies if the mail exists and
	 * the password is valid by calling the verifyLogin() method from the User
	 * class.
	 * 
	 * @return redirects to index if the verification is ok, or reloads the
	 *         login page with a warning message.
	 */
	public Result login() {
		
			
			return ok(index.render(" "));
	}

	/**
	 * @return renders the loginpage view
	 */
	public Result loginpage() {
		return ok(Loginpage.render(" "));
	}

	/**
	 * Logs out the User and clears the session
	 * 
	 * @return redirects to index
	 */
	public Result logout() {
		String name = session("name");
		//Handling exceptions
		if(name == null){
			flash("error.msg.01");
			return redirect("/loginpage");
		}
		Logger.info(name + " has logged out");
		session().clear();
		flash("success",Messages.get("logoutSuccess"));
		return redirect("/loginpage");
	}

	
	/**
	 * Renders the info page if user tries to access a page without needed
	 * permission
	 * 
	 * @return
	 */
	public Result loginToComplete() {
		Logger.info("Login to complete page previewed");
		return badRequest(loginToComplete
				.render(Messages.get("loginToComplete")));
	}

}
