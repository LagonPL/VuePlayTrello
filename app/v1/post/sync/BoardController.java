package v1.post.sync;

import Helpers.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import models.Board;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import java.net.HttpCookie;
import java.util.List;
import controllers.*;
import java.util.List;
import models.Board;
import models.User;
import models.AccountStatus;
import java.util.function.Predicate;

public class BoardController extends Controller{


    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        System.out.println(json.toString() + "\ntest\n");
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        Board board=  (Board) Json.fromJson(json, Board.class);
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        board.setOwnerUser(user);
        board.save();
        JsonNode jsonObject = Json.toJson(board);
        return created(Helpers.createResponse(jsonObject, true));
    }

    public Result retrive(int id) {
        System.out.println(Integer.toString(id));
        Board board = Board.find.byId(id);
        if(board ==null) {
            return notFound(Helpers.createResponse("Board with id" + id + "Not found", false));
        }
        JsonNode jsonObject = Json.toJson(board);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        Board board = (Board) Json.fromJson(json.get("Board"), Board.class);
        if (board == null) {
            return notFound(Helpers.createResponse(
                    "Json not valid", false));
        }
        Board oldBoard = Board.find.byId(board.id);
        if(oldBoard==null) {
            return  notFound(Helpers.createResponse("Board not exist", false));
        }
        oldBoard.name=board.name;
        oldBoard.update();
        JsonNode jsonObject = Json.toJson(board);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        System.out.println(Integer.toString(id));
        Board board = Board.find.byId(id);
        if(board==null) {
            return  notFound(Helpers.createResponse("Board not found",  false));
        }
        if(board.delete()) {
            return ok(Helpers.createResponse("Board deleted", true));
        } else {
            return internalServerError(Helpers.createResponse("Cannot delete board, try again later", false));
        }
    }

    public Result getAll() {
        List<Board> boards = Board.find.all();
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        for(int i = 0; i < boards.size(); i++){
            if(boards.get(i).getOwnerUser().getEmailAddress() == user.getEmailAddress()){
            boards.remove(i);
            }     
        }
        

        JsonNode jsonObject = Json.toJson(boards);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result archive(int id) {
        Board board = Board.find.byId(id);

        if(board==null) {
            return  notFound(Helpers.createResponse("Board not found",  false));
        }
        if(board.getStatus() == Board.Status.VISIBLE) {
            board.setStatus(Board.Status.ARCHIVED);
        } else if (board.getStatus() == Board.Status.ARCHIVED) {
            board.setStatus(Board.Status.DELETED);
        }
        board.update();
        return  ok(Helpers.createResponse("Done", true));
    }
    public Result deArchive(int id) {
        Board board = Board.find.byId(id);

        if(board==null) {
            return  notFound(Helpers.createResponse("Board not found",  false));
        }
        if(board.getStatus() == Board.Status.ARCHIVED) {
            board.setStatus(Board.Status.VISIBLE);
        } else if (board.getStatus() == Board.Status.DELETED) {
            return notFound(Helpers.createResponse("Board is deleted",false));
        }
        board.update();
        return  ok(Helpers.createResponse("Done", true));
    }






}
