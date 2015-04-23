# Hibernate OGM with JPA and Infinispan on Wildfly 8.2

This example/guide shows how you can install and run Hibernate OGM together with JPA and Infinispan on Wildfly 8.2
with minimum effort.

The Hibernate OGM 4.1.3.Final require Infinispan 7.x but since Wildfly 8.2 comes with Infinispan 6.x
you will have to install the Hibernate OGM and the Infinispan Wildfly module. Fortunately this is really 
easy to do. When you install the modules it will co-exist with the current version of Infinispan 6,
the down side of this is you will have to maintain two set of configuration files, if you want to customize
Hibernate OGM and Infinispan. But the module will work out of the box with default configuration.

#How to run ?
This example work with Wildfly 8.2 and using Arquillian ecosystem.

For running the example is's required you have installed JDK 7+ and Maven 3+ 

> 
mvn install -P wildfly

This command will install the Wildfly server in "target/wildfly-8.2.0.Final" with the Hibernate OGM
and Infinispan modules

#Manual install the Hibernate OGM & Infinispan
This small guide shows how you can manual update Wildfly with the Hibernate OGM & Infinispan
module.

* Follow the guide [Packaging Hibernate OGM applications for WildFly 8.2](https://docs.jboss.org/hibernate/ogm/4.1/reference/en-US/html/ogm-configuration.html#_packaging_hibernate_ogm_applications_for_wildfly_8_2 "Packaging Hibernate OGM applications for WildFly 8.2")
* Next you need to download the [Infinispan 7 Wildfly module](http://downloads.jboss.org/infinispan/7.1.1.Final/infinispan-as-embedded-modules-7.1.1.Final.zip "Infinispan 7 Wildfly module") and unzip in the "module/" directory
* A finally download the [Hibernate Search 5.1 Wildfly module](https://repository.jboss.org/nexus/service/local/repositories/releases/content/org/hibernate/hibernate-search-modules/5.1.0.Final/hibernate-search-modules-5.1.0.Final-wildfly-8-dist.zip "Hibernate Search Wildfly module") and unzip in the "module/" directory

Your Wildfly is now updated and ready for Hibernate OGM
