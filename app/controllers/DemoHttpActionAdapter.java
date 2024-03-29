package controllers;

import Helpers.Helpers;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import play.mvc.Result;

import static play.mvc.Results.*;

public class DemoHttpActionAdapter extends DefaultHttpActionAdapter {

    @Override
    public Result adapt(int code, PlayWebContext context) {
        if (code == HttpConstants.UNAUTHORIZED) {
            return unauthorized();
        } else if (code == HttpConstants.FORBIDDEN) {
            return forbidden(Helpers.createResponse("Forbiden", false));
        } else {
            return super.adapt(code, context);
        }
    }
}