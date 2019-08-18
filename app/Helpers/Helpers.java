package Helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.BaseModel;
import play.libs.Json;

public class Helpers {
    public static ObjectNode createResponse(
            Object response, boolean ok) {

        ObjectNode result = Json.newObject();
        result.put("isSuccessfull", ok);
        if (response instanceof String) {
            result.put("body", (String) response);
        }
        else {
            result.set("body", (JsonNode) response);
        }

        return result;
    }

    public static int random(final int max) {
        return (int) (Math.random() * (double) max);
    }

    public static void wait(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (final Exception e) {}
    }

    public static int block() {
        final int wait = 10 + random(20);
        wait(wait);
        return wait;
    }

}
