package controllers;

import java.io.File;
import com.google.inject.Inject;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;
import lib.WebpackBuildFile;
import play.Environment;
import play.api.mvc.AbstractController;
import play.api.mvc.ControllerComponents;
import play.mvc.*;
import play.mvc.Result;

import static play.mvc.Results.ok;


public class FrontController extends AbstractController {
    public Config config;
    public Environment env;


    @Inject
    public FrontController(ControllerComponents cc, Environment env) {
        super(cc);
        config = ConfigFactory.parseFile(new File("conf/frontend.conf")).resolve();
    }

    public Result index() {
        return ok(views.html.index.render(env, config.getInt("webpack.port"), WebpackBuildFile.jsBundle(), WebpackBuildFile.cssBundle()));
    }


}
