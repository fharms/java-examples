package com.github.fharms;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     * file:plain-java/src/data?noop=true
     */
    public void configure() {

        from("file:plain-java/src/data?noop=true&fileName=message1.xml")
            .choice()
                .when(xpath("/person/city = 'London'"))
                    .log("UK message ${body}")
                    .to("file:target/messages/uk")
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others");
    }

}
