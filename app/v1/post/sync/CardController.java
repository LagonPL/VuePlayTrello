package v1.post.sync;

import Helpers.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import models.Card;
import models.Listt;
import models.User;
import models.EventLog;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.immutable.List;
import play.mvc.Http;
import java.net.HttpCookie;
import viewmodels.*;
import controllers.*;
import util.Utils;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Locale;

public class CardController extends Controller{

    public SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    public Date date = new Date();

    public Result create() {
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        System.out.println(json.toString() + "test jsona nowej karty");
        CardViewModel tempCard =  (CardViewModel) Json.fromJson(json.get("CardViewModel"), CardViewModel.class);
        if(tempCard == null) {
            return notFound(Helpers.createResponse("Object not valid", false));
        }
        Listt parentListt = Listt.find.byId(tempCard.parentList);
        if(parentListt==null) {
            return notFound(Helpers.createResponse("Parent element not found", false));
        }
        Card card = new Card(tempCard.description, parentListt,tempCard.row, tempCard.name);
        
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        EventLog eventLog = Utils.eLog(card.getParentListt().getParentBoard().id, user.getEmailAddress(), card.name, "nowakarta");
        eventLog.save();
        
        card.save();
        JsonNode jsonObject = Json.toJson(card);
        return created(Helpers.createResponse(jsonObject, true));

    }

    public Result retrive (int id) {
        Card card = Card.find.byId(id);
        if(card ==null) {
            return notFound(Helpers.createResponse("models.Listt with id" + id + "Not found", false));
        }
        JsonNode jsonObject = Json.toJson(card);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        Card card = (Card) Json.fromJson(json.get("Card"), Card.class);
        if (card == null) {
            return notFound(Helpers.createResponse(
                    "Json not valid", false));
        }
        Card oldCard = Card.find.byId(card.id);
        if(oldCard ==null) {
            return  notFound(Helpers.createResponse("models.Card not exist", false));
        }
        if(card.name!=null) {
            oldCard.name=card.name;
        }
        if(card.getDescription()!=null) {
            oldCard.setDescription(card.getDescription());
        }
        oldCard.update();
        JsonNode jsonObject = Json.toJson(card);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        Card card = Card.find.byId(id);
        if(card==null) {
            return  notFound(Helpers.createResponse("Board not found",  false));
        }
        if(card.delete()) {
            return ok(Helpers.createResponse("Board deleted", true));
        } else {
            return internalServerError(Helpers.createResponse("Cannot delete board, try again later", false));
        }
    }

    public Result move() {
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        Position pos = (Position) Json.fromJson(json, Position.class);
        if (pos == null) {
            return notFound(Helpers.createResponse(
                    "Json not valid", false));
        }
        Card oldCard = Card.find.byId(pos.parentElementId);
        if(oldCard ==null) {
            return  notFound(Helpers.createResponse("models.Card not exist", false));
        }
        oldCard.setRow(pos.row);
        //oldCard.
        oldCard.update();

        JsonNode jsonObject = Json.toJson(oldCard);
        return ok(Helpers.createResponse(jsonObject, true));

    }

    public Result changeParentListt() {
        JsonNode json = request().body().asJson();

        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }

        ParentListtSwitchData parentListtSwitch = (ParentListtSwitchData) Json.fromJson(json, ParentListtSwitchData.class);
        if (parentListtSwitch == null) {
            return notFound(Helpers.createResponse(
                    "Json not valid", false));
        }

        Card card = Card.find.byId(parentListtSwitch.cardId);
        if(card ==null) {
            return  notFound(Helpers.createResponse("models.Card not exist", false));
        }

        Listt newParentListt = Listt.find.byId(parentListtSwitch.newParentListtId);
        if (newParentListt == null) {
            return  notFound(Helpers.createResponse("List not found",  false));
        }

        Listt oldParentListt = Listt.find.byId(parentListtSwitch.oldParentListtId);
        if(oldParentListt ==null) {
            return  notFound(Helpers.createResponse("List not exist", false));
        }

        if(card.getParentListt()!= oldParentListt){
            return  notFound(Helpers.createResponse("This Listt is not a parent of this Card", false));
        }
        card.setParentListt(newParentListt);
        card.update();

        JsonNode jsonObject = Json.toJson(card);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result archive(int id) {
        Card card = Card.find.byId(id);

        if(card==null) {
            return  notFound(Helpers.createResponse("Board not found",  false));
        }
        if(card.getStatus() == Card.Status.VISIBLE) {
            card.setStatus(Card.Status.ARCHIVED);
        } else if (card.getStatus() == Card.Status.ARCHIVED) {
            card.setStatus(Card.Status.DELETED);
        }
        card.update();
        return  ok(Helpers.createResponse("Done", true));
    }
    public Result deArchive(int id) {
        Card card = Card.find.byId(id);

        if(card==null) {
            return  notFound(Helpers.createResponse("Card not found",  false));
        }
        if(card.getStatus() == Card.Status.ARCHIVED) {
            card.setStatus(Card.Status.VISIBLE);
        } else if (card.getStatus() == Card.Status.DELETED) {
            return notFound(Helpers.createResponse("Card is deleted",false));
        }
        card.update();
        return  ok(Helpers.createResponse("Done", true));
    }

    public Result addComment() {

        return paymentRequired();
    }
    
    public Result addLabel() {

        return ok();
    }

    public Result getAll() {
        java.util.List<Card> cards = Card.find.all();
        JsonNode jsonObject = Json.toJson(cards);
        return ok(Helpers.createResponse(jsonObject, true));
    }

}
