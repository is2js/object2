package goodComposition.plan;

import static org.assertj.core.api.Assertions.assertThat;

import goodComposition.Money;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @DisplayName("")
    @Test
    void setNext() {
        final Plan plan = new Plan();

        final Calculator calculator = new PricePerTimeCalculator(Money.of(1000D), Duration.ofSeconds(60))
            .setNext(new AmountDiscountCalculator(Money.of(2000D))
                .setNext(new TexCalculator(0.1)));

        plan.setCalculator(
            calculator
        );

        final Calculator firstCalculator = plan.getCalculator();

        assertThat(firstCalculator).extracting("next")
            .isInstanceOf(AmountDiscountCalculator.class);
    }
}
