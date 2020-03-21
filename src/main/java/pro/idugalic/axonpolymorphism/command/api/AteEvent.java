package pro.idugalic.axonpolymorphism.command.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AteEvent {

    private String id;
    private String content;
    private Long foodQuantity;
    private boolean hungry;
}
