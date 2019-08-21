package v1.post.sync;

import Helpers.Helpers;
import controllers.FrontController;

import com.fasterxml.jackson.databind.JsonNode;
import models.Board;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import java.util.List;

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


}
