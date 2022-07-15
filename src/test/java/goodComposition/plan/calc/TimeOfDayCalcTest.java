package goodComposition.plan.calc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeOfDayCalcTest {

    @DisplayName("")
    @Test
    void calculate() {
        final Duration between = Duration.between(LocalTime.of(10, 30, 15), LocalTime.of(10, 10, 00));
        System.out.println("between.getSeconds() = " + between.getSeconds());
    }
}
