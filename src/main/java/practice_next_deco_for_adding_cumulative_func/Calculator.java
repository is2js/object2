package practice_next_deco_for_adding_cumulative_func;

import goodComposition.Money;
import java.util.HashSet;
import java.util.Set;

public class Calculator {

    private final Set<CalculatorStrategy> calculators = new HashSet<>();

    public Calculator(final CalculatorStrategy calculator) {
        this.calculators.add(calculator);
    }

    public Calculator setNext(final CalculatorStrategy calculator){
        this.calculators.add(calculator);
        return this;
    }

    public Money calculate() {
        Money sum = Money.ZERO;

        for (final CalculatorStrategy calculator : calculators) {
            sum = calculator.calculateMoney(sum);
        }

        //sum의 postcondition 검사
        return sum;
    }
}
