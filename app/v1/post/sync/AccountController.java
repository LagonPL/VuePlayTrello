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
        User test = user.findByEmailAddressAndPassword("kamil", "kamil");
        System.out.print("\nxD" + test + "\n" );
        return created(Helpers.createResponse(jsonObject, true));
    }


}
