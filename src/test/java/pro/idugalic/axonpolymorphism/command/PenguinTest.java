package pro.idugalic.axonpolymorphism.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.*;
import pro.idugalic.axonpolymorphism.command.api.AteEvent;
import pro.idugalic.axonpolymorphism.command.api.EatCommand;
import pro.idugalic.axonpolymorphism.command.api.EatingRefusedEvent;
import pro.idugalic.axonpolymorphism.command.api.FlyCommand;
import pro.idugalic.axonpolymorphism.command.api.HatchPenguinCommand;
import pro.idugalic.axonpolymorphism.command.api.PenguinHatchedEvent;
import pro.idugalic.axonpolymorphism.command.api.UnsupportedOperationEvent;

import java.time.Instant;

public class PenguinTest {

    private FixtureConfiguration<Penguin> fixture = new AggregateTestFixture<>(Penguin.class);

    @Test
    public void hatchPenguinTest() {
        HatchPenguinCommand command = new HatchPenguinCommand("penguin1");
        PenguinHatchedEvent expectedEvent = new PenguinHatchedEvent("penguin1", Instant.now());
        fixture.given()
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void eatPenguinHappyTest() {
        PenguinHatchedEvent givenEvent = new PenguinHatchedEvent("penguin1", Instant.now());
        EatCommand command = new EatCommand("penguin1", "worm", 2L);
        AteEvent expectedEvent = new AteEvent("penguin1", "worm", 2L, true);
        fixture.given(givenEvent)
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void eatPenguinNotHungryTest() {
        PenguinHatchedEvent givenEvent = new PenguinHatchedEvent("penguin1", Instant.now());
        EatCommand command = new EatCommand("penguin1", "worm", 13L);
        AteEvent expectedEvent = new AteEvent("penguin1", "worm", 13L, false);
        fixture.given(givenEvent)
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void eatPenguinRefusedTest() {
        PenguinHatchedEvent givenEvent1 = new PenguinHatchedEvent("penguin1", Instant.now());
        AteEvent givenEvent2 = new AteEvent("penguin1", "worm", 13L, false);
        EatCommand command = new EatCommand("penguin1", "worm", 3L);
        EatingRefusedEvent expectedEvent = new EatingRefusedEvent("penguin1", "worm", 3L, "I am not hungry!");
        fixture.given(givenEvent1, givenEvent2)
               .when(command)
               .expectEvents(expectedEvent);
    }

    @Test
    public void flyPenguinTest() {
        PenguinHatchedEvent givenEvent = new PenguinHatchedEvent("penguin1", Instant.now());
        FlyCommand command = new FlyCommand("penguin1", 2L);
        UnsupportedOperationEvent expectedEvent1 = new UnsupportedOperationEvent(command.getId(), "Penguins can't fly");
        UnsupportedOperationEvent expectedEvent2 = new UnsupportedOperationEvent(command.getId(), "These birds can't fly");

        fixture.given(givenEvent)
               .when(command)
               .expectEvents(expectedEvent2);
    }
}
