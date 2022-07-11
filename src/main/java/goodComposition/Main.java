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
            new PricePerTimeCalculator(Money.of(1000D), Duration.ofSeconds(60))
                .setNext(new AmountDiscountCalculator(Money.of(1000D))
                    .setNext(new TexCalculator(0.1))
                )
        );
    }

}
