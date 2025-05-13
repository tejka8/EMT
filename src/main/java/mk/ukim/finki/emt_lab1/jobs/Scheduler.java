
package mk.ukim.finki.emt_lab1.jobs;

import mk.ukim.finki.emt_lab1.service.domain.BookDomainService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final BookDomainService bookDomainService;

    public Scheduler(BookDomainService bookDomainService) {
        this.bookDomainService = bookDomainService;
    }

    @Scheduled(
            cron = "0 0 * * * *"
    )
    public void refreshMaterializedView() {
        this.bookDomainService.rerefreshMaterializedView();
    }
}
