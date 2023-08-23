package services;

import play.Logger;
import play.gr

import io.grpc.Server;

public class GreetService {
    private Logger.ALogger logger = Logger.of("play");
    private Server server;
    private Integer port;
}
