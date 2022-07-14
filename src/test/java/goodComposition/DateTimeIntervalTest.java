package goodComposition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeIntervalTest {

    @DisplayName("")
    @Test
    void of() {
        final DateTimeInterval interval = DateTimeInterval.of(LocalDateTime.of(2022, 07, 13, 14, 22, 00),
            LocalDateTime.of(2022, 07, 15, 15, 00, 00));

        final Duration duration = interval.duration();
        System.out.println("duration = " + duration);

        final int days = interval.days();
        System.out.println("days = " + days);

        final List<DateTimeInterval> splitDays = interval.split(days);
        System.out.println("split(days) = " + splitDays);

        final List<DateTimeInterval> splitByDay = interval.splitByDay();
        System.out.println("splitByDay = " + splitByDay);


    }
}
