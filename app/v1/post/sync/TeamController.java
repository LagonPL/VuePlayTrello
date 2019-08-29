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
public class TeamController extends Controller{

    public Result create(){
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }

        Team team = (Team) Json.fromJson(json, Team.class);

        team.save();
        return ok();
    }

    public Result add(){
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }

        TeamViewModel teamViewModel = (TeamViewModel) Json.fromJson(json.get("TeamViewModel"), TeamViewModel.class);
        if (teamViewModel == null) {
            return notFound(Helpers.createResponse("Object not valid", false));
        }
        Team team = Team.find.byId(teamViewModel.teamId);
        if (team == null) {
            return notFound(Helpers.createResponse("Board with id " + teamViewModel.teamId + "dont exist", false));
        }
        User user = User.findByEmail(teamViewModel.mail);
        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User userLogged = models.User.findByAuthToken(cookie.value());
        if(user.getEmailAddress()==null || userLogged.getEmailAddress().equals(teamViewModel.mail)){
            System.out.println("Test if w sprawdzaniu czy mail jest taki sam jak zalogowany user\n");
            return ok();
        }
        String userid = Integer.toString(user.id);
        if(team.getUserList().isEmpty()){
            team.setUserList(userid);
        }
        else{
            if(Utils.CheckIfUserAlreadyExist(team.getUserList(), user.id) || team.ownerUserId == user.id){
                System.out.println("SDAW - Test sprawdzania czy userzy już nalezą do boardu\n");
                return ok();
            }
            team.setUserList(team.getUserList()+";"+userid);
        }

        team.save();
        return ok();
    }

    public Result getTeams(){
        List<Team> teams1 = Team.find.all();
        List<Team> teams2 = Team.find.all();

        Http.Cookie cookie = request().cookies().get(SecurityController.AUTH_TOKEN);
        User user = models.User.findByAuthToken(cookie.value());
        //boards.removeIf((Board board) -> board.getOwnerUser().getEmailAddress() != user.getEmailAddress());
        teams1.removeIf((Team team) -> team.ownerUserId != user.id);
        teams2.removeIf((Team team) -> Utils.Spliter(team.getUserList(), user.id));
        teams1.addAll(teams2);
        teams1 = teams1.stream().distinct().collect(Collectors.toList());
        JsonNode jsonObject = Json.toJson(teams1);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result getUsers(){
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }

        TeamViewModel teamViewModel = (TeamViewModel) Json.fromJson(json.get("TeamViewModel"), TeamViewModel.class);
        if (teamViewModel == null) {
            return notFound(Helpers.createResponse("Object not valid", false));
        }
        Team teams = Team.find.byId(teamViewModel.teamId); 
        User user;
        String userString = teams.getUserList();
        String[] userlist = userString.split(";");
        List<String> users = new ArrayList<String>();
        for (String userid : userlist) {
            user = User.findById(Integer.parseInt(userid));
            users.add(user.getEmailAddress());
        }
        JsonNode jsonObject = Json.toJson(users);
        return ok(Helpers.createResponse(jsonObject, true));
    }

}