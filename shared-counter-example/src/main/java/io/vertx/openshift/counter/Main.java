package io.vertx.openshift.counter;

import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Main extends Launcher {

    @Override
    public void beforeStartingVertx(VertxOptions options) {
        //Enable clustering programmatically
        options.setClustered(true);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.dispatch(args);
    }
}
