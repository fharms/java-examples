#Wildfly 8.2 with Hibernate 5.2

The purpose of this example is to experiment if it possible to use Hibernate 5.2 with WildFly 8.2


#How to run ?

This example work with WildFly 8.x and using Arquillian ecosystem.

For running the example is's required you have installed JDK 8+ and Maven 3+

> mvn install test

> NOTE : It will fail the first time it run the test because the Hibernate 5 module is not installed yet.
 We are able to use the work already done for Wildfly 10 [Hibernate ORM Module](http://in.relation.to/2016/07/07/updating-hibernate-orm-in-wildfly/) with some small modifications.

* Download the [Hibernate ORM Module ZIP](http://search.maven.org/remotecontent?filepath=org/hibernate/hibernate-orm-modules/5.2.1.Final/hibernate-orm-modules-5.2.1.Final-wildfly-10-dist.zip)
* Unzip into the WildFly 8 modules directory eg target/server/wildfly-dist_8.2.0.Final/wildfly-8.2.0.Final/modules
* Copy the directory "org" from [modules/](modules/) into the WildFly target modules directory "modules/"
* Run the mvn test command again.
