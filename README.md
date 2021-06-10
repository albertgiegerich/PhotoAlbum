# Photo Album CLI

## Prerequisites

This application uses Java 11. Ensure that you are using the correct Java version when building and running the application.

## Building and Running the Application

### Pre-built JAR
A JAR file called photo-album-1.0-jar-with-dependencies.jar has been provided at the root level directory. It can be executed by running: ```java -jar photo-album-1.0-jar-with-dependencies.jar```

### Building the JAR
This project uses Maven to build the JAR. Ensure that Maven is using Java 11 (by default the JAVA_HOME environment variable will be used by Maven). Run the following in the root level directory where pom.xml is located:
```
mvn clean package assembly:single
```

This command will create a single JAR file that includes the open-source Jackson library listed as a dependency in pom.xml. The JAR file will be created as target/photo-album-1.0-jar-with-dependencies.jar

It can be executed by running: ```java -jar target/photo-album-1.0-jar-with-dependencies.jar```

### Running the Tests

The mvn command to build the JAR will run the tests. The tests can be run independently by running: ```mvn test```

