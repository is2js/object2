package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.DateTimeInterval;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.util.Set;

public class DayOfWeekCalc extends Calc {

    private final Set<DayOfWeekRule> rules;

    public DayOfWeekCalc(final Set<DayOfWeekRule> rules) {
        this.rules = rules;
    }

    @Override
    protected Money calculate(final Money result, final Set<Call> calls) {
        // rules를 반복문 돌리는데, 미리 쪼개놓은 모든 경우에 대해서, 처리안되는 구간도 있으니
        // 누적계산되게 해야한다.
        Money sum = Money.ZERO;
        for (final Call call : calls) {
            for (final DateTimeInterval interval : call.splitByDay()) {
                // 모든 경우의 수 rule을 돌린다.
                // -> 개별 계산된 값은 해당없을 때 ZERO를 반환하여 누적시킨다.
                // -> 경우 중에 interval은 1일 단위니, 1개만 걸려서 계산될 것이다.
                for (final DayOfWeekRule rule : rules) {
                    final Money tempResult = rule.calculate(interval);
                    //postcondition
                    sum = sum.plus(tempResult);
                }
            }
        }
        return result.plus(sum);

    }
}
