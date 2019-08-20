package v1.post.sync;

import Helpers.Helpers;
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
        System.out.print("SIemaneczko1\n");
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        System.out.print(json.toString());
        User user =  (User) Json.fromJson(json, User.class);
        System.out.print(user.toString());
        JsonNode jsonObject = Json.toJson(user);
        
        User testuser = new User("kamil", "kamil", "kamil");
        testuser.save();
        return created(Helpers.createResponse(jsonObject, true));
    }

    public Result getList() {
        List<User> users = User.find.all();
        JsonNode jsonObject = Json.toJson(users);
        return ok(Helpers.createResponse(jsonObject, true));
    }


}
