#Wildfly 9 or 10 with Hibernate 5

The purpose of this example is to show how to install and test the hibernate wildfly module. 
The build comes with various profiles for testing different wildfly and hibernate versions.

Current existing profiles :
* wildfly10-bibernate511
* wildfly10-bibernate521
* wildfly10-bibernate522
* wildfly9-bibernate521


#How to run ?

This example work with WildFly 9.x and 10.x and using Arquillian ecosystem.

For running the example is's required you have installed JDK 8+ and Maven 3+

> mvn install test -P <wildfly10-bibernate511 | wildfly10-bibernate521 | wildfly10-bibernate522 | wildfly9-bibernate521>
