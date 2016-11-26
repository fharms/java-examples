
# Camel Entity Manager Example
 
This example shows how to use the Camel Entity Manager Proxy [Camel Entity Manager](https://github.com/fharms/camel-jpa-entitymanager)
with Camel. This is useful if you are using JPA in beans from a camel routes, and need it to interact with 
the Camel JPA Entity Manager and the assigned transaction manager.

    Please note for using the Camel Entity Manager post processor you will have to add the dependency below
```xml
 <dependency>
      <artifactId>camel.jpa.entitymanager</artifactId>
      <groupId>com.github.fharms</groupId>
      <version>0.0.2</version>
 </dependency>
```

# Requirements for running the example

* Maven 3.x or higher
* JDK 8 or higher

# Run the example

To run this example you will have to 

* Build and run the unit tests

     ``mvn install``