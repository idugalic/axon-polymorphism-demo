package pro.idugalic.axonpolymorphism.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import pro.idugalic.axonpolymorphism.command.api.AteEvent;
import pro.idugalic.axonpolymorphism.command.api.BirdHatchedEvent;
import pro.idugalic.axonpolymorphism.command.api.EatCommand;
import pro.idugalic.axonpolymorphism.command.api.EatingRefusedEvent;

import java.time.Instant;
import java.util.Optional;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PACKAGE)
abstract class Bird {

    @AggregateIdentifier
    private String id;
    private boolean hungry = true;
    private Long eatenFoodQuantityInDay;
    @Getter(AccessLevel.PACKAGE)
    private Instant birthday;

    @CommandHandler
    final void handle(EatCommand command) {
        if (hungry) {
            apply(new AteEvent(command.getId(),
                               command.getContent(),
                               command.getQuantity(),
                               (this.eatenFoodQuantityInDay + command.getQuantity()) <= neededFoodQuantityInDay().orElse(10L)));
        } else {
            apply(new EatingRefusedEvent(command.getId(),
                                         command.getContent(),
                                         command.getQuantity(),
                                         "I am not hungry!"));
        }
    }

    @EventSourcingHandler
    final void on(AteEvent event) {
        this.hungry = event.isHungry();
        this.eatenFoodQuantityInDay = this.eatenFoodQuantityInDay + event.getFoodQuantity();
    }

    final void on(BirdHatchedEvent event) {
        this.id = event.getId();
        this.hungry = true;
        this.eatenFoodQuantityInDay = 0L;
        this.birthday = event.getBirthday();
    }

    abstract Optional<Long> neededFoodQuantityInDay();

}
