package controllers;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import models.User;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get(SecurityController.AUTH_TOKEN);
        System.out.println(authTokenHeaderValues.toString());
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            User user = models.User.findByAuthToken(authTokenHeaderValues[0]);
            System.out.print("testowanie getusername");
            if (user != null) {
                ctx.args.put("user", user);
                return user.getEmailAddress();
            }
        }
        return null;
    }


    @Override
    public Result onUnauthorized(Context ctx) {
        System.out.print("testowanie onUnauthorized");
        return unauthorized();
    }

}