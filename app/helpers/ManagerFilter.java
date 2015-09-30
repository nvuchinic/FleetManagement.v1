package helpers;

import models.Manager;
import models.SuperUser;
import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * This class is a controller filter and ensures that only a logged in user can
 * perform certain actions.
 * 
 * @author Emir
 *
 */
public class ManagerFilter extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		if (!ctx.session().containsKey("name"))
			return null;
		String name = ctx.session().get("name");
		Manager manager = Manager.findByName(name);
		if (manager != null)
			return manager.name;
		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		Logger.error("Login To Complete, only users allowed to change this pw.");
		return redirect("/loginToComplete");
	}

}
