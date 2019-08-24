package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;

public class SecurityController extends Controller {

    @Inject FormFactory formFactory;

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";


    public static User getUser() {
        User user = (User)Http.Context.current().args.get("user");
        return user;
    }

    // returns an authToken
    public Result login() {
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        System.out.print(loginForm.toString()+ "\tLoginform test\n");
        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }

        Login login = loginForm.get();
        System.out.print(login.toString() + login.emailAddress + login.password + "\tLogin test\n");
        User user = User.findByEmailAddressAndPassword(login.emailAddress, login.password);
        //System.out.print(user.toString() + "\tUser test\n");
        if (user == null) {
            System.out.print("\tIf test\n");
            return unauthorized();
        }
        else {
            String authToken = user.createToken();
            System.out.print(authToken + "\tauthToken testXXXXXXX\n");
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(AUTH_TOKEN, authToken);
            session().clear();
            session("email", login.emailAddress);
            response().setCookie(Http.Cookie.builder(AUTH_TOKEN, authToken).withSecure(ctx().request().secure()).build());
            return ok(authTokenJson);
        }
    }

    //@Security.Authenticated(Secured.class)
    public Result logout() {
        response().discardCookie(AUTH_TOKEN);
        //var result = result.discardingCookies(DiscardingCookie("PLAY_SESSION"), DiscardingCookie("msbUserId"));
        //getUser().deleteAuthToken();
        return redirect("/");
       
         
    }

    public static class Login {

        @Constraints.Required
        @Constraints.Email
        public String emailAddress;

        @Constraints.Required
        public String password;

    }

}