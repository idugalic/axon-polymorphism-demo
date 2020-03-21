package pro.idugalic.axonpolymorphism.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.*;
import pro.idugalic.axonpolymorphism.command.api.AteEvent;
import pro.idugalic.axonpolymorphism.command.api.EagleHatchedEvent;
import pro.idugalic.axonpolymorphism.command.api.EatCommand;
import pro.idugalic.axonpolymorphism.command.api.EatingRefusedEvent;
import pro.idugalic.axonpolymorphism.command.api.FlewEvent;
import pro.idugalic.axonpolymorphism.command.api.FlyCommand;
import pro.idugalic.axonpolymorphism.command.api.FlyingRefusedEvent;
import pro.idugalic.axonpolymorphism.command.api.HatchEagleCommand;

import java.time.Instant;

public class EagleTest {

    private FixtureConfiguration<Eagle> fixture = new AggregateTestFixture<>(Eagle.class);

    @Test
    public void hatchEagleTest() {
        HatchEagleCommand command = new HatchEagleCommand("eagle1");
        EagleHatchedEvent expectedEvent = new EagleHatchedEvent("eagle1", Instant.now());
        fixture.given()
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void eatEagleHappyTest() {
        EagleHatchedEvent givenEvent = new EagleHatchedEvent("eagle1", Instant.now());
        EatCommand command = new EatCommand("eagle1", "worm", 2L);
        AteEvent expectedEvent = new AteEvent("eagle1", "worm", 2L, true);
        fixture.given(givenEvent)
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void eatEagleNotHungryTest() {
        EagleHatchedEvent givenEvent = new EagleHatchedEvent("eagle1", Instant.now());
        EatCommand command = new EatCommand("eagle1", "worm", 11L);
        AteEvent expectedEvent = new AteEvent("eagle1", "worm", 11L, false);
        fixture.given(givenEvent)
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void eatEagleRefusedTest() {
        EagleHatchedEvent givenEvent1 = new EagleHatchedEvent("eagle1", Instant.now());
        AteEvent givenEvent2 = new AteEvent("eagle1", "worm", 11L, false);
        EatCommand command = new EatCommand("eagle1", "worm", 3L);
        EatingRefusedEvent expectedEvent = new EatingRefusedEvent("eagle1", "worm", 3L, "I am not hungry!");
        fixture.given(givenEvent1, givenEvent2)
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void flyEagleHappyTest() {
        EagleHatchedEvent givenEvent = new EagleHatchedEvent("eagle1", Instant.now().minusSeconds(301));
        FlyCommand command = new FlyCommand("eagle1", 2L);
        FlewEvent expectedEvent = new FlewEvent("eagle1", 2L);

        fixture.given(givenEvent)
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void flyEagleRefusedTest() {
        EagleHatchedEvent givenEvent = new EagleHatchedEvent("eagle1", Instant.now().minusSeconds(290));
        FlyCommand command = new FlyCommand("eagle1", 2L);
        FlyingRefusedEvent expectedEvent = new FlyingRefusedEvent("eagle1", "I am too young to fly!");

        fixture.given(givenEvent)
               .when(command)
               .expectEvents(expectedEvent);
    }
}
