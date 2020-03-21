package pro.idugalic.axonpolymorphism.command.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EagleHatchedEvent extends BirdHatchedEvent {

    @Builder
    public EagleHatchedEvent(String id, Instant birthday) {
        super(id, birthday);
    }
}
