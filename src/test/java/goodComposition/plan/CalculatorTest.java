package goodComposition.plan;

import static org.assertj.core.api.Assertions.assertThat;

import goodComposition.Money;
import goodComposition.plan.calc.AmountDiscountCalc;
import goodComposition.plan.calc.PricePerTimeCalc;
import goodComposition.plan.calc.TexCalc;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @DisplayName("")
    @Test
    void setNext() {
        final Plan plan = new Plan();

        final Calculator calculator = new Calculator(
            new PricePerTimeCalc(Money.of(1000D), Duration.ofSeconds(60)))
            .setNext(new AmountDiscountCalc(Money.of(2000D)))
            .setNext(new TexCalc(0.1));

        plan.setCalculator(
            calculator
        );

        final Calculator actual = plan.getCalculator();

        assertThat(actual.getCalcs()).hasSize(3);
    }
}
