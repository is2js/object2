package practice_next_deco_for_adding_cumulative_func;

import goodComposition.Money;

public class AdditionalCalc implements CalculatorStrategy {

    private Money additionalPrice;

    public AdditionalCalc(final Money additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    @Override
    public Money calculateMoney(final Money result) {
        // 외부에서 물품의 가격을 받아와 더한다.
        return result.plus(additionalPrice);
    }
}
