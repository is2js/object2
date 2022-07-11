package goodComposition;

import goodComposition.plan.AmountDiscountCalculator;
import goodComposition.plan.Plan;
import goodComposition.plan.PricePerTimeCalculator;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        final Plan plan = new Plan();
        final PricePerTimeCalculator pricePerTimeCalculator = new PricePerTimeCalculator(
            Money.of(18.0),
            Duration.ofSeconds(60)
        );
        pricePerTimeCalculator.setNext(new AmountDiscountCalculator(Money.of(1000D)));

        plan.setCalculator(
            pricePerTimeCalculator
        );
    }

}
