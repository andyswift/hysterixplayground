package no.iterate.application;

import no.iterate.client.HelloWorldClient;

import static spark.Spark.*;

public class StableServer {
    public static void main(String[] args) {
        port(9090);
        get("/text", (req, res) -> new HelloWorldClient("http://localhost:9091").getJsonMessage("/hello"));
    }
}