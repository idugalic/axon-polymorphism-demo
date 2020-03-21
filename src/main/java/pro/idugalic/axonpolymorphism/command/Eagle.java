package pro.idugalic.axonpolymorphism.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import pro.idugalic.axonpolymorphism.command.api.EagleHatchedEvent;
import pro.idugalic.axonpolymorphism.command.api.HatchEagleCommand;

import java.time.Instant;
import java.util.Optional;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
final class Eagle extends FlyingBird {

    @CommandHandler
    Eagle(HatchEagleCommand command) {
        apply(new EagleHatchedEvent(command.getId(), Instant.now()));
    }

    @EventSourcingHandler
    void on(EagleHatchedEvent event) {
        super.on(event);
    }

    @Override
    Optional<Long> neededFoodQuantityInDay() {
        return Optional.of(10L);
    }

    @Override
    Optional<Long> oldEnoughToFlyInSeconds() {
        return Optional.of(300L);
    }
}
