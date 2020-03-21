package pro.idugalic.axonpolymorphism.command.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = "birthday")
public abstract class BirdHatchedEvent {

    private String id;
    private Instant birthday;
}
