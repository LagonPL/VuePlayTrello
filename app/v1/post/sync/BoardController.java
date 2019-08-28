package v1.post.sync;

import Helpers.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import models.Board;
import models.EventLog;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import viewmodels.UserViewModel;
import java.util.ArrayList;
import java.net.HttpCookie;
import java.util.List;
import controllers.*;
import java.util.List;
import util.Utils;
import models.Board;
import models.User;
import models.AccountStatus;
import java.util.function.Predicate;
import java.util.Iterator;
import java.util.stream.Collectors; 
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Locale;

public class BoardController extends Controller {

    public SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    public Date date = new Date();

    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        String content = json.path("isPrivate").asText();
        Board board = (Board) Json.fromJson(json, Board.class);
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        board.setOwnerUser(user);
        board.setUserList("");
        board.setPrivate(Boolean.valueOf(content));
        board.save();
        EventLog eventLog = new EventLog(board.id, formatter.format(date) + ": " + user.getEmailAddress() + " utworzyl tablice: " + board.name + ".");
        eventLog.save();
        JsonNode jsonObject = Json.toJson(board);
        return created(Helpers.createResponse(jsonObject, true));
    }

    public Result retrive(int id) {
        Board board = Board.find.byId(id);
        if (board == null) {
            return notFound(Helpers.createResponse("Board with id" + id + "Not found", false));
        }
        JsonNode jsonObject = Json.toJson(board);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        Board board = (Board) Json.fromJson(json.get("Board"), Board.class);
        if (board == null) {
            return notFound(Helpers.createResponse("Json not valid", false));
        }
        Board oldBoard = Board.find.byId(board.id);
        if (oldBoard == null) {
            return notFound(Helpers.createResponse("Board not exist", false));
        }
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        EventLog eventLog = new EventLog(oldBoard.id, formatter.format(date) + ": " + user.getEmailAddress() + " zmienil nazwe tablicy na: " + board.name + ".");
        System.out.println(eventLog.getText());
        eventLog.save();
        oldBoard.name = board.name;
        oldBoard.update();
        JsonNode jsonObject = Json.toJson(board);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        Board board = Board.find.byId(id);
        if (board == null) {
            return notFound(Helpers.createResponse("Board not found", false));
        }
        if (board.delete()) {
            return ok(Helpers.createResponse("Board deleted", true));
        } else {
            return internalServerError(Helpers.createResponse("Cannot delete board, try again later", false));
        }
    }

    public Result getAll() {
        List<Board> boards = Board.find.all();
        List<Board> boards2 = Board.find.all();
        List<Board> boards3 = Board.find.all();
        JsonNode jsonObject;
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        if(cookie == null){
            boards3.removeIf((Board board) -> board.getPrivate());
            jsonObject = Json.toJson(boards3);
            return ok(Helpers.createResponse(jsonObject, true));
        }
        User user = models.User.findByAuthToken(cookie.value());
        
        int size = boards.size();
        int numberOfRemovedBoards = 0;
        boards.removeIf((Board board) -> board.getOwnerUser().getEmailAddress() != user.getEmailAddress());
        boards2.removeIf((Board board) -> Utils.Spliter(board.getUserList(), user.id));
        boards3.removeIf((Board board) -> board.getPrivate());
        boards.addAll(boards2);
        boards.addAll(boards3);
        boards = boards.stream().distinct().collect(Collectors.toList());
        jsonObject = Json.toJson(boards);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result archive(int id) {
        Board board = Board.find.byId(id);

        if (board == null) {
            return notFound(Helpers.createResponse("Board not found", false));
        }
        if (board.getStatus() == Board.Status.VISIBLE) {
            board.setStatus(Board.Status.ARCHIVED);
        } else if (board.getStatus() == Board.Status.ARCHIVED) {
            board.setStatus(Board.Status.DELETED);
        }
        board.update();
        return ok(Helpers.createResponse("Done", true));
    }

    public Result deArchive(int id) {
        Board board = Board.find.byId(id);

        if (board == null) {
            return notFound(Helpers.createResponse("Board not found", false));
        }
        if (board.getStatus() == Board.Status.ARCHIVED) {
            board.setStatus(Board.Status.VISIBLE);
        } else if (board.getStatus() == Board.Status.DELETED) {
            return notFound(Helpers.createResponse("Board is deleted", false));
        }
        board.update();
        return ok(Helpers.createResponse("Done", true));
    }

    public Result addUser() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        UserViewModel userViewModel = (UserViewModel) Json.fromJson(json.get("UserViewModel"), UserViewModel.class);
        if (userViewModel == null) {
            return notFound(Helpers.createResponse("Object not valid", false));
        }
        Board parentBoard = Board.find.byId(userViewModel.parentBoard);
        if (parentBoard == null) {
            return notFound(Helpers.createResponse("Board with id " + userViewModel.parentBoard + "dont exist", false));
        }

        User user = User.findByEmail(userViewModel.mail);
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User userLogged = models.User.findByAuthToken(cookie.value());
        if(user.getEmailAddress()==null || userLogged.getEmailAddress().equals(userViewModel.mail)){
            System.out.println("Test if w sprawdzaniu czy mail jest taki sam jak zalogowany user\n");
            return ok();
        }
        String userid = Integer.toString(user.id);
        if(parentBoard.getUserList().isEmpty()){
            parentBoard.setUserList(userid);
        }
        else{
            if(Utils.CheckIfUserAlreadyExist(parentBoard.getUserList(), user.id) || parentBoard.getOwnerUser().id.equals(user.id)){
                System.out.println("SDAW - Test sprawdzania czy userzy już nalezą do boardu\n");
                return ok();
            }
            parentBoard.setUserList(parentBoard.getUserList()+";"+userid);
        }
        EventLog eventLog = new EventLog(parentBoard.id, formatter.format(date) + ": " + userLogged.getEmailAddress() + " dodal do tablicy uzytkownika: " + userViewModel.mail + ".");
        System.out.println(eventLog.getText());
        eventLog.save();
        parentBoard.save();
        return ok();
    }

    public Result geteventlog(int id){
        System.out.println(Integer.toString(id) + " Test id do geteventlog\n");
        Board board = Board.find.byId(id);
        List<EventLog> eventLogs = EventLog.find.all();
        eventLogs.removeIf((EventLog eventlog) -> eventlog.getBoardId() != board.id);
        for (EventLog log : eventLogs) {
            System.out.println(log.getText());
        }
        JsonNode jsonObject = Json.toJson(eventLogs);
        System.out.println(jsonObject.toString());

        return ok(Helpers.createResponse(jsonObject, true));
    }

}
