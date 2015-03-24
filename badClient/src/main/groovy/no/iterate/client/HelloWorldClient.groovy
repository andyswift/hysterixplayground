package no.iterate.client

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.GET

final class HelloWorldClient {

    private final HTTPBuilder http

    HelloWorldClient(String location) {
        this.http = new HTTPBuilder(location)
        //default error handler
        http.handler.failure = { resp ->
            println "Unexpected failure: ${resp.statusLine}"
        }
    }

    def getAndPrintResponse = this.&get.rcurry (TEXT) { resp, data ->
        assert resp.status == 200
        System.out << data // print response reader
        println()
    }

    public String getJsonMessage(String resource) {
        String result = "Error"
        get(resource, JSON){ resp, json ->
            result = json.message
        }
        result
    }

    private void get(String resource, ContentType contentType, Closure success) {
        try {
            http.request(GET, contentType) {
                req ->
                    uri.path = resource // overrides any path in the default URL
                    headers.'User-Agent' = 'Mozilla/5.0'
                    response.success = success
            }
        }
        catch (Exception e) {
            println("Failed: " + e.getMessage())
        }
    }
}