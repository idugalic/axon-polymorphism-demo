# axon-polymorphism

Inheritance and polymorphism are important concepts in object-oriented programming. 
You are able to [use these concepts in Axon Framework](https://docs.axoniq.io/reference-guide/implementing-domain-logic/command-handling/aggregate-polymorphism) (since version 4.3).

Please mind that this is a demo application, and that it does not favor inheritance over composition in general. 
It demonstrate the usage of these OOP concepts in Axon applications from the technical point of view.

Don't be caught only using the "is-a-kind-of" guideline for inheritance - it can lead you down a very timely and expensive path ;)

## Development

This project is driven using [maven].

### Run Axon Server

You can [download](https://download.axoniq.io/axonserver/AxonServer.zip) a ZIP file with AxonServer as a standalone JAR. This will also give you the AxonServer CLI and information on how to run and configure the server.

Alternatively, you can run the following command to start AxonServer in a Docker container:

```
$ docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
```

### Run locally

You can run the following command to start your project locally:

```
$ ./mvnw spring-boot:run
```

### Run tests

This project comes with some rudimentary tests as a good starting
point for writing your own. Use the following command to execute the
tests using Maven:

```
$ ./mvnw test
```
---

[maven]: https://maven.apache.org/ (Maven)
[axon]: https://axoniq.io/ (Axon)

