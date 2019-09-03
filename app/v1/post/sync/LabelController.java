package v1.post.sync;

import Helpers.Helpers;
import controllers.FrontController;

import com.fasterxml.jackson.databind.JsonNode;
import models.Board;
import models.User;
import models.Card;
import models.Label;
import java.util.ArrayList;
import viewmodels.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.Utils;
import java.net.HttpCookie;
import java.util.List;
import controllers.*;
import java.util.stream.Collectors;

public class LabelController extends Controller {

    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        System.out.println(json.toString() + " - json \n");
        LabelViewModel labelVM = (LabelViewModel) Json.fromJson(json.get("LabelViewModel"), LabelViewModel.class);
        System.out.println(labelVM.color + " - kolor \n" + labelVM.name + " - name\n" );
        Card card = Card.find.byId(labelVM.parentCard);
        Label label =  new Label(card, labelVM.name, labelVM.color);
        label.save();
        return ok();
    }

    public Result edit() {
        //id, name, color
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        Label label = (Label) Json.fromJson(json, Label.class);
        Label oldLabel = Label.find.byId(label.id);
        if(label.name.isEmpty()){
            oldLabel.setColor(label.getColor());
        }
        else{
            oldLabel.name = label.name;
        }
        oldLabel.save();
        JsonNode jsonObject = Json.toJson(oldLabel);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        Label label = Label.find.byId(id);
        label.delete();
        return ok(Helpers.createResponse("Label deleted", true));
    }

    public Result getAll(){
        List<Label> labels = Label.find.all();
        JsonNode jsonObject = Json.toJson(labels);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result getLabels(int id) {
        Card card = Card.find.byId(id);
        List<Label> labels = Label.find.all();
        //boards.removeIf((Board board) -> board.getOwnerUser().getEmailAddress() != user.getEmailAddress());
        labels.removeIf((Label label) -> label.getParentCard().id != id);
        JsonNode jsonObject = Json.toJson(card);
        return ok(Helpers.createResponse(jsonObject, true));
    }


}