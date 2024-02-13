# Simple Talking

Small project to send messages between user A and user B.

## Getting Started

### Requirements

* [Docker](https://www.docker.com/)
* [Docker compose](https://docs.docker.com/compose/)

### Start application

After you clone the project in the root directory execute the script ``./start.sh`` to start the project.

### Stop application

To stop and clean the application execute the script ``./stop.sh`` to stop the project and clean the resources.

## API Docs

To access to the API documentation got to the link [http://localhost:8081/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html).

## Improvements for a new Version

1. Add a feature to reply a message.
   * We can add a new table ``conversations`` to be the parent of all the messages.
   * Add new field to messages table ``conversation_id`` and join all the messages.
2. Endpoint to get a timeline of the conversation.
3. Increase code coverage.
   * Create classes to mock fake data
4. Add GlobalHandlerException
5. Use secrets for Docker deployment as now it's using plain text to set up configurations in the ``application.properties``.
6. Level up the abstraction for the async implementation ``RabbitMQ``, as right now only allow to work with ``RabbitMQ`` and doesn't allow to change to a different message broker.
