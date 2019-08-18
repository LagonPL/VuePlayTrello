package v1.post.sync;


import models.Card;
import models.Task;
import play.mvc.Http;
import viewmodels.TaskViewModel;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import Helpers.Helpers;
import com.fasterxml.jackson.databind.JsonNode;

public class TaskController extends Controller{
    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        TaskViewModel tempTask=  (TaskViewModel) Json.fromJson(json.get("TaskViewModel"), TaskViewModel.class);
        if(tempTask==null) {
            return notFound(Helpers.createResponse("Object not valid",false));
        }
        Card parentCard = Card.find.byId(tempTask.parentCard);
        if(parentCard ==null) {
            return notFound(Helpers.createResponse("Card with id " + tempTask.parentCard + "dont exist", false));
        }
        Task task = new Task(parentCard, tempTask.name);
        task.save();
        JsonNode jsonObject = Json.toJson(task);
        return created(Helpers.createResponse(jsonObject, true));
    }

    public Result retrive (int id) {
        models.Task task = Task.find.byId(id);
        if(task ==null) {
            return notFound(Helpers.createResponse("models.Listt with id " + id + " Not found", false));
        }
        JsonNode jsonObject = Json.toJson(task);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        Task task = (Task) Json.fromJson(json.get("Task"), Task.class);
        if (task == null) {
            return notFound(Helpers.createResponse(
                    "Json not valid", false));
        }
        Task oldTask = Task.find.byId(task.id);
        if(oldTask ==null) {
            return  notFound(Helpers.createResponse("models.Task not exist", false));
        }
        oldTask.name=task.name;
        oldTask.update();
        JsonNode jsonObject = Json.toJson(task);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        Task task = Task.find.byId(id);
        if(task==null) {
            return  notFound(Helpers.createResponse("Task not found",  false));
        }
        if(task.delete()) {
            return ok(Helpers.createResponse("Task deleted", true));
        } else {
            return internalServerError(Helpers.createResponse("Cannot delete Task, try again later", false));
        }
    }
    public Result switchDone(int id){
        Task task = Task.find.byId(id);
        if(task==null) {
            return  notFound(Helpers.createResponse("Task not found",  false));
        }
        task.setDone(!task.isDone());
        task.update();
        return ok(Helpers.createResponse("Task state switched to " + task.isDone(), true));
    }

    public Result getAll() {
        java.util.List<Task> taskList = Task.find.all();
        JsonNode jsonObject = Json.toJson(taskList);
        return ok(Helpers.createResponse(jsonObject, true));
    }

}
