package pro.idugalic.axonpolymorphism.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import pro.idugalic.axonpolymorphism.command.api.FlewEvent;
import pro.idugalic.axonpolymorphism.command.api.FlyCommand;
import pro.idugalic.axonpolymorphism.command.api.FlyingRefusedEvent;

import java.time.Instant;
import java.util.Optional;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
abstract class FlyingBird extends Bird {

    @CommandHandler
    final void handle(FlyCommand command) {
        if (oldEnoughToFlyInSeconds().isPresent()
                && Instant.now().isAfter(this.getBirthday().plusSeconds(oldEnoughToFlyInSeconds().get()))) {
            apply(new FlewEvent(command.getId(), command.getMetres()));
        } else {
            apply(new FlyingRefusedEvent(command.getId(), "I am too young to fly!"));
        }
    }

    @EventSourcingHandler
    final void on(FlewEvent event) {
        log.info("I was flying");
    }

    abstract Optional<Long> oldEnoughToFlyInSeconds();
}
