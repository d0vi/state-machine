# State Machine

API-First oriented microservice that manages order events using a self-made state machine ([FSM](https://en.wikipedia.org/wiki/Finite-state_machine)) implementation.

### ⚙️ FSM implementation

#### The possibilities

There are a lot of ways to implement a state machine. Even more, there are already implementations
that can save you the work of coding it yourself. In Java, the [State Pattern](https://www.baeldung.com/java-state-design-pattern) 
can be implemented in different ways, each having its pros and cons.

- Enums: since an enum in its simplest form is just a list of constants, it can be a good place to define the states.
  Behavior through methods provide the transition implementation between states.
  
- Interfaces and implementation classes: using this design, we can encapsulate the logic in dedicated classes,
  apply the Single Responsibility Principle and Open/Closed Principle and have cleaner and more maintainable code.
  However, this means having a significant amount of code to develop and maintain.

If you don't feel like programming an FSM by yourself, you can rely on one of the existing implementations,
e.g. [Spring Statemachine](https://projects.spring.io/spring-statemachine/). Spring Statemachine is a framework for 
application developers to use state machine concepts with Spring applications.

#### My choice

Now that we know the options that we have available to build a state machine, it is time to make a decision.

For this project I decided to take the _service_ path, i.e., implementing the status transitions and database related
job in a domain service. This service will be in charge of checking for the current order status, available transitions
for an order, updating an order status and persisting it into the database. All this transitions must be kept tracked,
which is also a responsibility of the aforementioned service.

### ❓ Why API-First?

In an API-First methodology, services are defined first as contracts. An API-First approach involves developing APIs that are consistent and reusable, which can be accomplished by using an API description language to establish a contract for how the API is supposed to behave. The benefits of using API-First:

- Development teams can work in parallel.
- Reduces the cost of developing apps.
- Increases the speed to market.
- Ensures good developer experiences.
- Reduces the risk of failure.

### 💡 Enhancements

One of the possible changes on the application architecture that I have weighted thinking that could benefit the overall design is the *hexagonal architecture*.

The hexagonal architecture is an architectural pattern used in software design. It aims at creating loosely coupled application components. In the hexagonal architecture style, it makes sense to promote use cases to first-class citizens of our codebase. A use case in this sense is a class that handles everything around a certain use case.

As an example let’s consider the use case “send money from one account to another” in a banking application. We’d create a class *SendMoneyUseCase* with a distinct API that allows a user to transfer money. The code contains all the business rule validations and logic that are specific to the use case and thus cannot be implemented within the domain objects. Everything else is delegated to the domain objects.

### ❗ Caveats

One requirement that could not be filled was the ability to accept both JSON and XML in the `/order/tracking` resource. This was caused due to a limitation on the OpenAPI Generator, and I decided that I did not care enough to bother and fixing it.

[This issue](https://github.com/OpenAPITools/openapi-generator/issues/144) explains that limitation in depth.