package helpers;

import controllers.routes;
import models.Admin;
import models.Manager;
import models.SuperUser;
import play.mvc.Security;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * This class is a session controller. It allows us to get info on currently
 * logged in user and his attributes.
 */
public class Sesija extends Security.Authenticator {

	/**
	 * Returns name of user that is in the current session
	 * 
	 * @return name String
	 */
	public String getName(Context ctx) {
		if (!ctx.session().containsKey("name")) {
			return null;
		}
		long id = Long.parseLong(ctx.session().get("name"));
		SuperUser su = SuperUser.findById(id);
		if (su != null) {
			return su.name;
		}
		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.index());
	}

	/**
	 * Returns admin from an active session or null the session is empty
	 * @param ctx
	 * @return admin or null
	 */
	public static Admin getCurrentAdmin(Context ctx) {
		if (!ctx.session().containsKey("name")) {
			return null;
		}
		String name = ctx.session().get("name");
		Admin u = (Admin) SuperUser.findByName(name);
		return u;
	}

	/**
	 * Checks if the user from the current session is Admin
	 * 
	 * @param ctx
	 * @return true or false
	 */
	public static boolean adminCheck(Context ctx) {
		if (getCurrent(ctx) == null)
			return false;
		return getCurrent(ctx).isAdmin();
	}

	
	/**
	 * Returns manager from an active session or null the session is empty
	 * @param ctx
	 * @return manager or null
	 */
	public static Manager getCurrentManager(Context ctx) {
		if (!ctx.session().containsKey("name")) {
			return null;
		}
		String name = ctx.session().get("name");
		Manager m = (Manager) SuperUser.findByName(name);
		return m;
	}
	/**
	 * Returns name from the user that is in the current session
	 * 
	 * @return name String
	 */
	public String getUserName(Context ctx) {
		if (!ctx.session().containsKey("name")) {
			return null;
		}
		long id = Long.parseLong(ctx.session().get("name"));
		Admin admin = (Admin) SuperUser.findById(id);
		if (admin != null) {
			return admin.name;
		}
		Manager manager = (Manager) SuperUser.findById(id);
		if (manager != null) {
			return manager.name;
		}
		else 
			return null;
	}
	
	/**
	 * Returns current superUser, whether it is a admin or manager
	 * @param ctx
	 * @return
	 */
	public static SuperUser getCurrent(Context ctx){
		Admin admin = getCurrentAdmin(ctx);
		Manager manager = getCurrentManager(ctx);
		
		if(manager != null){
			return manager;
		}else if( admin != null){
			return  admin;
		}else{
			return null;
		}
		
	}
	
	/**
	 * Returns true if manager is the current user 
	 * @param ctx
	 * @return
	 */
	public static boolean managerCheck(Context ctx) {

		if (getCurrentManager(ctx) == null)
			return false;
		return true;
	}
}
