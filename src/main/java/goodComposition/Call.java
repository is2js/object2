package goodComposition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Call {

    private final DateTimeInterval interval;

    public Call(final LocalDateTime from, final LocalDateTime to) {
        this.interval = DateTimeInterval.of(from, to);
    }

    public Duration getDuration() {
        return interval.duration();
    }

    public boolean isFromOverAndEqualTo(final LocalTime localTime) {
        return getFrom().getHour() >= localTime.getHour();
    }

    public List<DateTimeInterval> splitByDay() {
        return interval.splitByDay();
    }

    public DateTimeInterval getInterval() {
        return interval;
    }

    public LocalDateTime getFrom(){
        return interval.getFrom();
    }

    public LocalDateTime getTo(){
        return interval.getTo();
    }
}
