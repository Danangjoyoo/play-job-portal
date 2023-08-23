package controllers;

import middlewares.ResponseTimeMiddleware;
import play.Logger;
import play.mvc.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private Logger.ALogger logger = Logger.of("play");

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("hello");
    }

    public Result home() {
        return ok("this is home");
    }

    @With({
        ResponseTimeMiddleware.class
    })
    public Result gasThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Long threadId = Thread.currentThread().getId();
        executorService.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.printf("[%s] running in background : count=%s\n", threadId, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        });

        return ok("its running!");
    }

    public Result gasrpc() {
        return ok("its gRPC!");
    }

}
