package no.iterate.application

import groovyx.gpars.GParsPool
import no.iterate.client.HelloWorldClient

class App {

    public static void main(String[] args) {
        parallel()
        println "done"
    }

    public static void parallel() {
        GParsPool.withPool(1000){
            (1..5000).eachParallel {  new HelloWorldClient('http://localhost:9090').getAndPrintResponse("/text") }
        }
    }

    public static void sequential() {
        (1..10).each { new HelloWorldClient('http://localhost:9090').getAndPrintResponse("/text") }
    }
}