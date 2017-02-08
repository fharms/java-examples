package com.github.fharms;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyNextSpringBootRouter extends FatJarRouter {

    @Override
    public void configure() {
        from("direct:next_route")
            .log("${body}");

    }

    @Bean
    String myBean() {
        return "I'm Spring bean!";
    }

}
