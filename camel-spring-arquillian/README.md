#Camel System Test with Arquillian Spring extension

The purpose of this example is to show how you can easily create system tests
with Arquillian Spring extension, and inject in-jvm camel context for mocking
and asserting.

> This is different from writing a normal camel spring test because
the camel routes are deployed to a full blown Java EE server with Spring

#How to run ?

This example work with Wildfly 9.x and using Arquillian ecosystem.

For running the example is's required you have installed JDK 8+ and Maven 3+

> mvn install test
