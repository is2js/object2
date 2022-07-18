package practice_next_deco_for_adding_cumulative_func;

import goodComposition.Money;

public abstract class AbstractCalc {

    private AbstractCalc next;

    public final AbstractCalc setNext(final AbstractCalc next) {
        this.next = next;
        return this;
    }

    public Money calculate(Money result) {
        return calculateMoney(result);
    }

    protected abstract Money calculateMoney(final Money result);

    public AbstractCalc getNext() {
        return next;
    }
}
