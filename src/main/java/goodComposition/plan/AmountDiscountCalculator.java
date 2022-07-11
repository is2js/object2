package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class AmountDiscountCalculator implements Calculator {

    private Money amount;
    private Calculator next;

    public AmountDiscountCalculator(final Calculator next, final Money amount) {
        this.amount = amount;
        this.next = next;
    }

    @Override
    public Money calculateCallFee(Money result, final Set<Call> calls) {
        result = result.minus(amount);
        return next == null ? result : next.calculateCallFee(result, calls);
    }
}
