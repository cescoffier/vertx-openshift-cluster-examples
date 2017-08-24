package io.vertx.openshift.counter;

import io.vertx.core.Handler;
import io.vertx.core.impl.VertxInternal;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.rxjava.ext.web.templ.FreeMarkerTemplateEngine;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class MyMainVerticle extends AbstractVerticle {

    private FreeMarkerTemplateEngine engine;

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        engine = FreeMarkerTemplateEngine.create();

        router.get("/health").handler(rc -> rc.response().end("OK"));
        router.get("/eventbus/*").handler(getSockJsHandler(vertx));
        router.get("/").handler(this::getMainPage);

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(config().getInteger("port", 8080));
    }

    private static Handler<RoutingContext> getSockJsHandler(Vertx vertx) {
        SockJSHandler sockJSHandler = SockJSHandler
            .create(vertx);
        BridgeOptions options = new BridgeOptions();
        options.addInboundPermitted(
            new PermittedOptions().setAddress("data"));
        options.addOutboundPermitted(
            new PermittedOptions().setAddress("data"));
        sockJSHandler.bridge(options);
        return sockJSHandler;
    }

    private void getMainPage(RoutingContext rc) {
        rc.put("appId", getNodeId());
        engine
            .rxRender(rc, "templates/index.ftl")
            .subscribe(
                content -> rc.response().end(content),
                rc::fail
            );
    }

    private String getNodeId() {
        // IMPORTANT - Be aware, this is a hack for presentation purpose, don't do it.
        String hostname = System.getenv("HOSTNAME");
        if (hostname == null) {
            hostname = "localhost";
        }

        String id = ((VertxInternal) vertx.getDelegate()).getClusterManager().getNodeID();
        return hostname + "-" + id;
    }
}
