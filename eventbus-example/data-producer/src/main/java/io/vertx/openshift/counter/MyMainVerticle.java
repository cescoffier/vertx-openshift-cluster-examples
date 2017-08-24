package io.vertx.openshift.counter;

import io.vertx.core.impl.VertxInternal;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;

import java.util.Random;

/**
 * A verticle producing data periodically.
 */
public class MyMainVerticle extends AbstractVerticle {

    private Random random = new Random();

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(1000, l -> {
            JsonObject data = new JsonObject();
            data.put("data", random.nextDouble())
                .put("id", getNodeId());
            vertx.eventBus().send("data", data);
        });
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
