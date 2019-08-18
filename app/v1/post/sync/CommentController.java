package v1.post.sync;

import Helpers.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.libs.Json;
import viewmodels.CommentViewModel;
import models.Card;
import models.Comment;

public class CommentController extends Controller {

    public Result create() {
        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        CommentViewModel tempComment=  (CommentViewModel) Json.fromJson(json.get("CommentViewModel"), CommentViewModel.class);

        Card parentCard = Card.find.byId(tempComment.parentCard);

        if(parentCard == null){
            return notFound(Helpers.createResponse("Card not found", false));
        }

        Comment comment = new Comment(null/*no users yet*/, tempComment.text, parentCard, tempComment.name);
        comment.save();
        JsonNode jsonObject = Json.toJson(comment);
        return created(Helpers.createResponse(jsonObject, true));
    }

    public Result retrive (int id) {
        Comment comment = Comment.find.byId(id);
        if(comment ==null) {
            return notFound(Helpers.createResponse("models.Comment with id" + id + "Not found", false));
        }
        JsonNode jsonObject = Json.toJson(comment);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Helpers.createResponse(
                    "Expecting Json data", false));
        }
        Comment comment = (Comment) Json.fromJson(json.get("Comment"), Comment.class);
        if (comment == null) {
            return notFound(Helpers.createResponse(
                    "Json not valid", false));
        }
        Comment oldComment = Comment.find.byId(comment.id);
        if(oldComment ==null) {
            return  notFound(Helpers.createResponse("models.Comment does not exist", false));
        }
        oldComment.setText(comment.getText());
        oldComment.update();
        JsonNode jsonObject = Json.toJson(comment);
        return ok(Helpers.createResponse(jsonObject, true));
    }

    public Result delete(int id) {
        Comment comment = Comment.find.byId(id);
        if(comment==null) {
            return  notFound(Helpers.createResponse("Comment not found",  false));
        }
        if(comment.delete()) {
            return ok(Helpers.createResponse("Comment deleted", true));
        } else {
            return internalServerError(Helpers.createResponse("Cannot delete comment, try again later", false));
        }
    }

    public Result getAll() {
        java.util.List<Comment> commentList = Comment.find.all();
        JsonNode jsonObject = Json.toJson(commentList);
        return ok(Helpers.createResponse(jsonObject, true));
    }
}
