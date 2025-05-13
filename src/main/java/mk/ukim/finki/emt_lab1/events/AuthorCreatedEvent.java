
package mk.ukim.finki.emt_lab1.events;

import java.time.LocalDateTime;
import org.springframework.context.ApplicationEvent;

public class AuthorCreatedEvent extends ApplicationEvent {
    private LocalDateTime when;

    public AuthorCreatedEvent(Object source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public AuthorCreatedEvent(Object source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}