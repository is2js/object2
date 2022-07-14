package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.DateTimeInterval;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayOfWeekCalc extends Calc {

    private final Set<DayPrice> prices = new HashSet<>();

    @Override
    protected Money calculate(Money result, final Set<Call> calls) {
        Money sum = Money.ZERO;
        for (final Call call : calls) {
            //각 call이 가진 interval들에 대해
            final List<DateTimeInterval> intervals = call.splitByDay();
            //우리가 가지고 있는 prices를 돌면서
            for (DayPrice price : prices) {
                // 각 요일별 요금들은, 내부에서 intervals를 돌리면서, 어떤 요일인지 확인후 요금을 계산해준다.
                final Money tempResult = price.calculate(intervals);
                if (tempResult.isLessThanOrEqualTo(Money.ZERO)) {
                    throw new RuntimeException("calculate error");
                }

                sum = sum.plus(tempResult);
            }
        }

        if (sum.isLessThanOrEqualTo(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }

        return result.plus(sum);
    }
}
