package goodComposition;

import goodComposition.plan.AmountDiscountCalculator;
import goodComposition.plan.Plan;
import goodComposition.plan.PricePerTimeCalculator;
import goodComposition.plan.TexCalculator;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        final Plan plan = new Plan();
        plan.setCalculator(
            new PricePerTimeCalculator(
                new AmountDiscountCalculator(
                    new TexCalculator(null, 0.01),
                    Money.of(1000.0)
                ),
                Money.of(18.0),
                Duration.ofSeconds(60)
            )
        );
    }

}
