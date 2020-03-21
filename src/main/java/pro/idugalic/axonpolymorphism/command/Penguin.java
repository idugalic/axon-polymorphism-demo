package pro.idugalic.axonpolymorphism.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import pro.idugalic.axonpolymorphism.command.api.FlyCommand;
import pro.idugalic.axonpolymorphism.command.api.HatchPenguinCommand;
import pro.idugalic.axonpolymorphism.command.api.PenguinHatchedEvent;
import pro.idugalic.axonpolymorphism.command.api.UnsupportedOperationEvent;

import java.time.Instant;
import java.util.Optional;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
final class Penguin extends NonFlyingBird {

    @CommandHandler
    Penguin(HatchPenguinCommand command) {
        apply(new PenguinHatchedEvent(command.getId(), Instant.now()));
    }

    @EventSourcingHandler
    void on(PenguinHatchedEvent event) {
        super.on(event);
    }

    @Override
    Optional<Long> neededFoodQuantityInDay() {
        return Optional.of(12L);
    }

    // TODO What about this case?
    // In the `NonFlyingBird` abstract class there is a handler for the same command `final handle(FlyCommand command)`
    // Please note, that this handler in abstract class is `final`. I do not want to `override` the behaviour of handling this command message in concrete classes.
    // This handler will not be triggered, as expected ! Can we print warning for this things somewhere ? What is the sorting/priority of the handlers in this case?
    @CommandHandler
    void handle2(FlyCommand command) {
        apply(new UnsupportedOperationEvent(command.getId(), "Penguins can't fly"));
    }


}
