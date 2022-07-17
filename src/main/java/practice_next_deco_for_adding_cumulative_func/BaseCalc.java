package practice_next_deco_for_adding_cumulative_func;

import goodComposition.Money;

public class BaseCalc extends AbstractCalc{

    @Override
    protected Money calculateMoney(final Money result) {
        //기본요금이 5백원
        return result.plus(Money.of(500D));
    }
}
