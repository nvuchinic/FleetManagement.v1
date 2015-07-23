package controllers;

import models.Employee;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Đe se kupaš?."));
    }

	/**
	 * @return Renders the registration view
	 */
	public Result signup() {
		return ok(signup.render(new Form<Employee>(Employee.class)));
	}
}
