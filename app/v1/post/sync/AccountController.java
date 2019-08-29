package v1.post.sync;

import Helpers.Helpers;
import controllers.FrontController;

import com.fasterxml.jackson.databind.JsonNode;
import models.Board;
import models.User;
import viewmodels.AccountStatusViewModel;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.net.HttpCookie;
import java.util.List;
import controllers.*;

public class AccountController extends Controller{



    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        
        User user =  (User) Json.fromJson(json, User.class);
        JsonNode jsonObject = Json.toJson(user);
        user.save();
        //return redirect(controllers.FrontController.index());
        return created(Helpers.createResponse(jsonObject, true));
    }

    public Result getList() {
        List<User> users = User.find.all();
        JsonNode jsonObject = Json.toJson(users);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result getMail () {
        AccountStatusViewModel status;
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        if(cookie==null){
            status = new AccountStatusViewModel("", false, false, ""); 
            JsonNode jsonObject = Json.toJson(status);
            return ok(Helpers.createResponse(jsonObject, true));
        }
        User user = models.User.findByAuthToken(cookie.value());
        if(user.getEmailAddress()==null){
            status = new AccountStatusViewModel("", false, false, "");  
            JsonNode jsonObject = Json.toJson(status);
            return ok(Helpers.createResponse(jsonObject, true));
        }
        else{
            status = new AccountStatusViewModel(user.getEmailAddress(), true, false, ""); 
            JsonNode jsonObject = Json.toJson(status);
            return ok(Helpers.createResponse(jsonObject, true));
        }
    }

    public Result getStatus (int id) {
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        AccountStatusViewModel status;
        if(cookie==null){
            status = new AccountStatusViewModel("", false, false, ""); 
            JsonNode jsonObject = Json.toJson(status);
            return ok(Helpers.createResponse(jsonObject, true));
        }
        User user = models.User.findByAuthToken(cookie.value());
        if(user.getEmailAddress()==null){
            status = new AccountStatusViewModel("", false, false, "");  
            JsonNode jsonObject = Json.toJson(status);
            return ok(Helpers.createResponse(jsonObject, true));
        }
        Board board = Board.find.byId(id);
        User owner = board.getOwnerUser();
        
        //System.out.println(user.getEmailAddress() + " Zalogowany");
        if(user.getEmailAddress()==null){
            return ok();
        }
        else{
            
            if(owner.id == user.id){
                status = new AccountStatusViewModel(user.getEmailAddress(), true, true, ""); 
            }
            else{
                status = new AccountStatusViewModel(user.getEmailAddress(), true, false, ""); 
            }
            JsonNode jsonObject = Json.toJson(status);
            return ok(Helpers.createResponse(jsonObject, true));
        }
    }
}
