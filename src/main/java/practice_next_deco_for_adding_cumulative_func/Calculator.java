package practice_next_deco_for_adding_cumulative_func;

import goodComposition.Money;

public class Calculator {

    private final AbstractCalc calculator;

    public Calculator(final AbstractCalc calculator) {
        this.calculator = calculator;
    }

    public Money calculate() {
        Money sum = Money.ZERO;
        AbstractCalc targetCalculator = calculator;
        do {
            sum = targetCalculator.calculateMoney(sum);
            targetCalculator = targetCalculator.getNext();

        } while(targetCalculator != null);
        //sum의 postcondition 검사
        return sum;
    }
}
