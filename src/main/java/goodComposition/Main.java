package goodComposition;

import goodComposition.plan.calc.AmountDiscountCalc;
import goodComposition.plan.Calculator;
import goodComposition.plan.Plan;
import goodComposition.plan.calc.PricePerTimeCalc;
import goodComposition.plan.calc.TexCalc;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        final Plan plan = new Plan();

        plan.setCalculator(
            new Calculator(new PricePerTimeCalc(Money.of(1000D), Duration.ofSeconds(60)))
                .setNext(new AmountDiscountCalc(Money.of(1000D)))
                .setNext(new TexCalc(0.1))
        );
    }

}
