package Helpers;

import models.SuperUser;
import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class SuperUserFilter extends Security.Authenticator {

	public String getName(Context ctx) {
		if(!ctx.session().containsKey("email"))
			return null;
		String email = ctx.session().get("email");
		SuperUser currentUser = SuperUser.getSuperUser(email);
		if (currentUser != null)
			return currentUser.email;
		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		Logger.error("You dont have access to this page");
		return redirect("/loginToComplete");
	}
	
}
