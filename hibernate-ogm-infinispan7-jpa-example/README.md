# Hibernate OGM with JPA and Infinispan on Wildfly 8.2

For the blog post follow the link to [Hibernate OGM with JPA and Infinispan on Wildfly 8.2](http://fharms.github.io/2015/05/16/HibernateOGM-Infinispan-Wildfly/)

#How to run ?

This example work with Wildfly 8.2 and using Arquillian ecosystem.

For running the example is's required you have installed JDK 7+ and Maven 3+ 

> 
mvn install -P wildfly

This command will install in "target/wildfly-8.2.0.Final" with the Hibernate OGM and Infinispan modules, and run the integration tests
