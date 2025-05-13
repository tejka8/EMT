
package mk.ukim.finki.emt_lab1.listners;

import mk.ukim.finki.emt_lab1.events.AuthorCreatedEvent;
import mk.ukim.finki.emt_lab1.service.domain.AuthorDomainService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventHandlers {
    private final AuthorDomainService authorDomainService;

    public AuthorEventHandlers(AuthorDomainService authorDomainService) {
        this.authorDomainService = authorDomainService;
    }

    @EventListener
    public void onAuthorCreated(AuthorCreatedEvent event) {
        this.authorDomainService.refreshMaterializedView();
    }
}
