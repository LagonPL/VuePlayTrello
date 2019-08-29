package v1.post.sync;

import Helpers.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import models.Board;
import models.Listt;
import play.mvc.Http;
import viewmodels.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ListController extends Controller {

    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        ListViewModel tempListt=  (ListViewModel) Json.fromJson(json.get("ListViewModel"), ListViewModel.class);
        if(tempListt==null) {
            return notFound(Helpers.createResponse("Object not valid",false));
        }
        Board parentBoard = Board.find.byId(tempListt.parentBoard);
        if(parentBoard ==null) {
            return notFound(Helpers.createResponse("Board with id " + tempListt.parentBoard + "dont exist", false));
        }
        Listt listt = new Listt(parentBoard, tempListt.column, tempListt.name);
        listt.save();
        JsonNode jsonObject = Json.toJson(listt);
        return created(Helpers.createResponse(jsonObject, true));
    }

    public Result retrive (int id) {
        models.Listt listt = Listt.find.byId(id);
        if(listt ==null) {
            return notFound(Helpers.createResponse("models.Listt with id " + id + " Not found", false));
        }
        JsonNode jsonObject = Json.toJson(listt);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        Listt listt = (Listt) Json.fromJson(json.get("Listt"), Listt.class);
        if (listt == null) {
            return notFound(Helpers.createResponse(
                    "Json not valid", false));
        }
        Listt oldListt = Listt.find.byId(listt.id);
        if(oldListt ==null) {
            return  notFound(Helpers.createResponse("models.Listt not exist", false));
        }
        oldListt.name=listt.name;
        oldListt.update();
        JsonNode jsonObject = Json.toJson(listt);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        Listt listt = Listt.find.byId(id);
        if(listt==null) {
            return  notFound(Helpers.createResponse("Board not found",  false));
        }
        if(listt.delete()) {
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
        Listt oldListt = Listt.find.byId(pos.parentElementId);
        if(oldListt ==null) {
            return  notFound(Helpers.createResponse("models.Listt not exist", false));
        }
        oldListt.setColumn(pos.column);
        oldListt.update();

        JsonNode jsonObject = Json.toJson(oldListt);
        return ok(Helpers.createResponse(jsonObject, true));

    }

    public Result archive(int id) {
        Listt listt = Listt.find.byId(id);

        if(listt==null) {
            return  notFound(Helpers.createResponse("Listt not found",  false));
        }
        if(listt.getStatus() == Listt.Status.VISIBLE) {
            listt.setStatus(Listt.Status.ARCHIVED);
        } else if (listt.getStatus() == Listt.Status.ARCHIVED) {
            listt.setStatus(Listt.Status.DELETED);
        }
        listt.update();
        return  ok(Helpers.createResponse("Done", true));
    }
    public Result deArchive(int id) {
        Listt listt = Listt.find.byId(id);

        if(listt==null) {
            return  notFound(Helpers.createResponse("Listt not found",  false));
        }
        if(listt.getStatus() == Listt.Status.ARCHIVED) {
            listt.setStatus(Listt.Status.VISIBLE);
        } else if (listt.getStatus() == Listt.Status.DELETED) {
            return notFound(Helpers.createResponse("Listt is deleted",false));
        }
        listt.update();
        return  ok(Helpers.createResponse("Done", true));
    }

    public Result getAll() {
        java.util.List<Listt> listts = Listt.find.all();
        JsonNode jsonObject = Json.toJson(listts);
        return ok(Helpers.createResponse(jsonObject, true));
    }


}
