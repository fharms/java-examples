
# Camel Twitter Example
 
This example shows how to use Apache Camel with Wildfly Swarm. The demo search
for tweets with the keyword getsmarterday and re-post them to a slack channel.

# Requirements for running the example

For the demo you will have to setup your own twitter app
[Twitter Apps](https://apps.twitter.com/) and insert the generate :
 - consumerKey
 - consumerSecret
 - accessToken
 - accessTokenSecret=

If you don't have a slack account create one here [slack](https://slack.com/)
and then a [slack-webhooks](https://slack.com/signin?redir=%2Fservices%2Fnew%2Fincoming-webhook%2F)

```java
    static public String slackUri = "slack:#twitter";
    //if you don't to create slack account you can replace the line in RiddingCamelTwitterSlackBuilder
    //to sent it to a file instead
    static public String slackUri = "file://twitter"; 
    
```

The final step is to uncomment the lines in the test case [RiddingCamelTwitterSlackTest](https://github.com/fharms/java-examples/blob/camel_twitter_slack/camel-twitter-slack/src/test/java/integration/RiddingCamelTwitterSlackTest.java#L73)

* Maven 3.x or higher
* JDK 8 or higher

# Run the example

To run this example you will have to 

* Build and run only the unit tests

     ``mvn install``