package pro.idugalic.axonpolymorphism.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;
import pro.idugalic.axonpolymorphism.command.api.FlyCommand;
import pro.idugalic.axonpolymorphism.command.api.UnsupportedOperationEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
abstract class NonFlyingBird extends Bird {

    @CommandHandler
    final void handle(FlyCommand command) {
        apply(new UnsupportedOperationEvent(command.getId(), "These birds can't fly"));
    }
}
