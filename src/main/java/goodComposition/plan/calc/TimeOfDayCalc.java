package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.DateTimeInterval;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

public class TimeOfDayCalc extends Calc {

    //1. 하루의 시간대구간을 미리쪼개놓고, 전체 구간을 받아, 개별구간마다 처리해줄 preve데코객체
    //   - rule을 만든다. 1개만 가지면 된다. start는 없이 end(처리할 구간) + prev + 나머지 구간별 처리에 필요한 정보 2개만 필요하다
    //   - 같은형의 prev는 맨 마지막 인자로 주고, 내가 처리할 구간의 끝은 그 직전에 주자.
    //   - Null객체로 이루어진 시작특이점 객체를 미리 소유하고 있는다.
    private TimeOfDayRule rule = new TimeOfDayRule(
        Duration.ZERO,       // 개별구간마다 몇초당 요금부과할지 단위시간 (개별구간에서 나눠줄 값)
        Money.ZERO,          // 단위시간당 부과할 금액
        LocalTime.of(0, 0, 0), // 내가 처리할 구간(to)
        null                 // prev
    );

    public TimeOfDayCalc() {
    }

    public TimeOfDayCalc addRule(final Duration duration, final Money price, final LocalTime to) {
        //다음구간의 구간 끝(to)은  rule(예비prev)의 구간끝보다 길어야한다.
        if (rule.getTo().compareTo(to) >= 0) {
            throw new IllegalArgumentException("invalid to");
        }
        // duration, price가 NULL객체 하한객체가 아님 검사는 생략한다.
        rule = new TimeOfDayRule(duration, price, to, rule);

        return this;
    }

    @Override
    protected Money calculate(final Money result, final Set<Call> calls) {
        Money sum = Money.ZERO;
        for (final Call call : calls) {
            for (final DateTimeInterval interval : call.splitByDay()) {
                //1) 처리할 구간을 받는다.(하루 0~24시안에서 localdatetime의 from + to 사이로 만든 객체)

                //2) 데코객체들은 반복문으로 돌아가며, 자기구간만 처리하는데,
                //   다음타자를 field로 들고 있는 특성상, do-while문으로 나 처리후 -> 내필드 속 다음타자로 바꾸고 -> 검사한다.
                //   -> 그러려면, 나 자신도 업데이트 되어야하기 때문에, 초기값을 변수로 빼놔야한다.
                TimeOfDayRule targetRule = rule;

                do {
                    //3) 개별 prev데코객체는, 전체 처리할 구간을 인자로 받아서, 자기분량만 처리하고 반환해준다.
                    //   localdatetiem from + to로 Duration.between을 이용하여, 처리할 구간을 구간객체로 반환받는다.
                    //   -> 구간의 비교는 start가 없이 0~끝까지의 구간만 받으면 된다.
                    //   -> start가 0인 구간 비교 -> 끝비교...?
                    //처리해야할 구간이 0부터 시작안하는 경우? 첫날 [오후8시부터~ 12시], 2,3,4 마지막날 0시~4시
                    // -> 첫날의 경우, 0부터 시작하는 구간이 아니므로 처리를 해줘야한다
                    // --> a~b -> (0~b 구간처리) - (a~b 구간처리)를
                    Money tempResult = interval.isMiddleDuration()      // 처리해야할 구간이 0부터 시작이 아니라면, 0부터시작하는 구간2개로 나누고 2번 계산해서 차이
                        ? calculateMiddleDuration(interval, targetRule)
                        : targetRule.calculate(interval.duration());

                    System.out.println("tempResult = " + tempResult);

                    //tempResult검사
                    sum = sum.plus(tempResult);

                    //4) 다음타자로 업데이트한다.
                    targetRule = targetRule.getPrev();

                    //5) 다음타자가 null로 잡힌, 연산없는 시작특이점객체에 도달하면, 종료한다.
                } while (targetRule.getPrev() != null);
            }
        }
        if (sum.isLessThanOrEqualTo(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }
        return result.plus(sum);
    }

    private Money calculateMiddleDuration(final DateTimeInterval interval, final TimeOfDayRule targetRule) {
        return targetRule.calculate(interval.getLargerDuration())
            .minus(targetRule.calculate(interval.getSmallerDuration()));
    }

    private LocalTime from(final DateTimeInterval interval, final LocalTime localTime) {
        return interval.getFrom().toLocalTime().isBefore(localTime)
            ? localTime : interval.getFrom().toLocalTime();
    }

    private LocalTime to(final DateTimeInterval interval, final LocalTime localTime) {
        return interval.getTo().toLocalTime().isAfter(localTime)
            ? localTime : interval.getTo().toLocalTime();
    }
}
