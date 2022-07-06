package goodComposition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Call {

    private final LocalDateTime from;
    private final LocalDateTime to;

    public Call(final LocalDateTime from, final LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public Duration getDuration() {
        return Duration.between(from, to);
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public boolean isFromOverAndEqualTo(final LocalTime nightTime) {
        return from.getHour() >= nightTime.getHour();
    }
}
