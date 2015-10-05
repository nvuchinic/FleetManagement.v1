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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
public class Application extends Controller {

	
	static String loginSuccess = Messages.get("login.Success");
	
	
	/**
	 * @return render the index page
	 */
	public Result index() {
		//String user= request().getHeader("Auth-User");
		//return ok("Zdravo "+user);
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

		Form<Login> loginForm = new Form<Login>(Login.class);

		return ok(index.render(" "));
		
//				Form<Login> loginForm = new Form<Login>(Login.class);
//				if (loginForm.hasGlobalErrors()) {
//					Logger.info("Login global error");
//					flash("error", Messages.get("login.Error01"));
//
//					return badRequest(Loginpage.render(" "));
//				}
//				try {
//					String mail = loginForm.bindFromRequest().get().email;
//					String password = loginForm.bindFromRequest().get().password;
//					
//					if (Admin.verifyLogin(mail, password) == true) {
//						Admin user = Admin.findByEmail(mail);
//						session().clear();
//						session("name", user.name);
//						session("email", user.email);
//						Logger.info(user.name + " logged in");
//						flash("success", mail + " successfully logged in.");
//						return ok(index.render(" "));
//
//					}
//					if (Manager.verifyLogin(mail, password) == true) {
//						Manager manager = (Manager) SuperUser.findByEmail(mail);
//						
//						if(manager.isManager == false && manager.isAdmin ==  false) {
//							Logger.info("not admin or manager");
//							flash("error", "Your are not admin or manager.");
//							return badRequest(Loginpage.render(" "));
//						}
//						if(manager.isManager == true && manager.isAdmin == false) {
//	        					session().clear();
//	        					session("name", manager.name);
//	        					session("email", manager.email);
//	        					flash("success", mail + " successfully logged in.");
//	        					Logger.info(manager.name + " logged in");
//	        					return ok(index.render(" "));
//						}
//					}
//
//					flash("error", "Invalid Email Or Password");
//					Logger.info("User tried to login with invalid email or password");
//					return badRequest(Loginpage.render(""));
//
//				} catch (Exception e) {
//					flash("error", "An error has occurred!");
//					Logger.error("Error has occured at login: " + e.getMessage(), e);
//					return redirect("/");
//				}
		
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
		// Handling exceptions
		if (name == null) {
			flash("error.msg.01");
			return redirect("/loginpage");
		}
		Logger.info(name + " has logged out");
		session().clear();
		flash("success", Messages.get("logoutSuccess"));
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
		return badRequest(loginToComplete.render(Messages
				.get("loginToComplete")));
	}

}
