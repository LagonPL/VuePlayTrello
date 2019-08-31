package v1.post.sync;

import Helpers.Helpers;
import controllers.FrontController;

import com.fasterxml.jackson.databind.JsonNode;
import models.Board;
import models.User;
import models.Team;
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

public class TeamController extends Controller {

    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        
        Team team = (Team) Json.fromJson(json, Team.class);
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        team.setOwnerUser(user.id);
        team.setUserList("");
        team.save();
        return ok();
    }

    public Result edit() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        TeamViewModel teamViewModel = (TeamViewModel) Json.fromJson(json.get("TeamViewModel"), TeamViewModel.class);
        Team team = Team.find.byId(teamViewModel.parentTeam);
        team.name = teamViewModel.name;
        team.save();
        JsonNode jsonObject = Json.toJson(team);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        Team team = Team.find.byId(id);
        System.out.println(team.name);
        team.delete();
        return ok(Helpers.createResponse("Team deleted", true));
    }


    public Result add() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }
        System.out.println(json.toString());
        TeamViewModel teamViewModel = (TeamViewModel) Json.fromJson(json.get("TeamViewModel"), TeamViewModel.class);
        if (teamViewModel == null) {
            return notFound(Helpers.createResponse("Object not valid", false));
        }
        Team team = Team.find.byId(teamViewModel.parentTeam);
        if (team == null) {
            return notFound(Helpers.createResponse("Board with id " + teamViewModel.parentTeam + "dont exist", false));
        }
        User user = User.findByEmail(teamViewModel.mail);
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User userLogged = models.User.findByAuthToken(cookie.value());
        if (user.getEmailAddress() == null || userLogged.getEmailAddress().equals(teamViewModel.mail)) {
            System.out.println("Test if w sprawdzaniu czy mail jest taki sam jak zalogowany user\n");
            return ok();
        }
        String userid = Integer.toString(user.id);
        if (team.getUserList().isEmpty()) {
            team.setUserList(userid);
        } else {
            if (Utils.CheckIfUserAlreadyExist(team.getUserList(), user.id) || team.ownerUserId == user.id) {
                System.out.println("SDAW - Test sprawdzania czy userzy już nalezą do boardu\n");
                return ok();
            }
            team.setUserList(team.getUserList() + ";" + userid);
        }

        team.save();
        return ok();
    }

    public Result getTeams() {
        List<Team> teams1 = Team.find.all();
        List<Team> teams2 = Team.find.all();

        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        // boards.removeIf((Board board) -> board.getOwnerUser().getEmailAddress() !=
        // user.getEmailAddress());
        teams1.removeIf((Team team) -> team.ownerUserId != user.id);
        teams2.removeIf((Team team) -> Utils.Spliter(team.getUserList(), user.id));
        teams1.addAll(teams2);
        teams1 = teams1.stream().distinct().collect(Collectors.toList());
        JsonNode jsonObject = Json.toJson(teams1);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result getUsers(int id) {
        Team teams = Team.find.byId(id);
        User user;
        String userString = teams.getUserList();

        List<String> users = new ArrayList<String>();
        user = User.findById(teams.ownerUserId);
        users.add(user.getEmailAddress());
        if (!userString.isEmpty()) {
            String[] userlist = userString.split(";");
            for (String userid : userlist) {
                System.out.println(userString + "userzy w teamie po kolei");
                user = User.findById(Integer.parseInt(userid));
                users.add(user.getEmailAddress());
            }
        }
        JsonNode jsonObject = Json.toJson(users);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result addToBoard() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null) {
            return badRequest(Helpers.createResponse("Expecting Json data", false));
        }

        String boardId = json.path("boardid").asText();
        String teamName = json.path("teamname").asText();
        Board board = Board.find.byId(Integer.parseInt(boardId));
        Team team = Team.findByName(teamName);
        if(team.getUserList().isEmpty()){
            return ok();
        }
        else if(board.getUserList().isEmpty()){
            String userlist = Utils.AddBoard("", team.getUserList()+";"+team.ownerUserId, board.getOwnerUser().id);
            board.setUserList(userlist);
        }
        else{
            String userlist = Utils.AddBoard(board.getUserList(), team.getUserList()+";"+team.ownerUserId, board.getOwnerUser().id);
            board.setUserList(userlist);
        }
        
        board.save();
        return ok();
    }

}