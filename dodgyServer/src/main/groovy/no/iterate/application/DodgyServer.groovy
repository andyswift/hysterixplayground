package no.iterate.application

import com.google.gson.Gson
import groovy.transform.Immutable
import no.iterate.util.FlakeyHelper
import spark.ResponseTransformer

import static spark.Spark.get
import static spark.SparkBase.port

/**
 * Provides an unreliable service which is usefull for hysterix testing
 */
class DodgyServer {

    static private FlakeyHelper helper = new FlakeyHelper(System.currentTimeMillis())

    public static void main(String[] args) {
        port(9091);
        get "/hello", "application/json", { req, res ->
            helper.throwSlowException()
            new Message(message : "HelloWorld")
        }, new JsonTransformer();

    }

    @Immutable
    public static class Message { String message }

    public static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }

    }
}
