package io.vertx.openshift.counter;

import io.vertx.core.impl.VertxInternal;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.shareddata.Counter;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
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
        router.get("/counter").handler(this::getCounter);
        router.get("/counter/inc").handler(this::incrementAndGetCounter);
        router.get("/").handler(this::getMainPage);

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(config().getInteger("port", 8080));
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

    private void incrementAndGetCounter(RoutingContext rc) {
        vertx.sharedData().rxGetCounter("my-counter")
            .flatMap(Counter::rxIncrementAndGet)
            .map(count -> new JsonObject().put("value", count).put("appId", getNodeId()))
            .subscribe(
                json -> rc.response().end(json.encode()),
                rc::fail
            );
    }

    private void getCounter(RoutingContext rc) {
        vertx.sharedData().rxGetCounter("my-counter")
            .flatMap(Counter::rxGet)
            .map(count -> new JsonObject().put("value", count).put("appId", getNodeId()))
            .subscribe(
                json -> rc.response().end(json.encode()),
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
