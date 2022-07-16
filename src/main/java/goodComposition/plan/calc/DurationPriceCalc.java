package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.time.Duration;
import java.util.Set;

public class DurationPriceCalc extends Calc {

    private DurationPriceRule rule = new DurationPriceRule(
        Money.ZERO,
        Duration.ZERO,
        null);

    public void addRule(Money price, Duration to) {
        if (rule.getTo().compareTo(to) >= 0) {
            throw new IllegalArgumentException("invalid to");
        }
        if (price.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("invalid money");
        }
        rule = new DurationPriceRule(price, to, rule);
        // 다음타자 데코객체를 만든다. prev는 현재소유한 데코객체인 rule을 집어넣어 만든다.
        // -> 데코객체이용클래스의 데코객체 변수는 값객체처럼 재할당이 운명이다. 동적으로 기능을 추가한 뒤 사용변수에 재할당해서 업데이트된 기능을 보유하게 한다.
        // --> 여기서는, 새로운 기능을 동적으로 추가하는 것이 아니라, 현재 소유 데코객체(prev)를 동적으로 다음타자로 업데이트한 것을 변수에 할당해준다.
        // ---> 동적으로 새로운 기능을 업데이트해주고, 그것만 호출하는 것이 아니라.
        //      prev(rule) 기능 사용 -> [다음타자로 업데이트]addRule-> [다음타자 사용]으로
        //      사용 -> 동적 업데이트 -> 사용 -> 동적 업데이트의 [실행기 분리후, 실행후 업데이트]를 가지는 것 같다
        // 동적으로 소유객체를 prev속성으로 next객체로 업데이트하는 것도 데코레이터 패턴이다.
        // 이 메서드에서는 나는 새로운 다음타자 rule만 만들어서 소유하면 된다.
        // -> 기존의 prev였던 rule은, 다음타자의 속성으로 들어가서 저장되어있을 것이기 때문이다.
        // --> 특이점데코객체(시작데코객체)를 소유했지만,
        //     현재 메소드를 통해, [다음번 타자를 만들어서 필드로 소유] + [이전것은 속성으로 저장해놓기]의 형태가 반복된다.
        //     맨 끝만 소유하게 될 것이고 -> prev로 저장된 직전객체는, 소유객체 계산시 반복문을 통해 최초객체까지 불러오면서 계산시킬 것이다.
        // 여기서 데코레이터는 메소드에서 직접 다음번 데코레이터를 부르진 않고,
        //   단지 값을 계산할 때, 현재객체로 해당구간 처리후 prev를 불러 값을 처리한다.
        //  왜냐면, 실행기가 따로 있기 때문이다.
        // 실행기가 합쳐지면, next와 prev를 불러서 한꺼번에 취합을 할텐데, 이 경우, aggregate 객체로 분리해놓고
        // 각각의 객체가 맡은 구간만 해결하도록 만들어놔서 -> 나중에 한꺼번에 불러서 처리할테니, 현재는 다음타자만 가지고, 직전은 prev에 저장만 한다.

        // 코드가 자유로워지면,
        // (1) 데코레이터가 순환적인 재귀루프를 돌게 할 수도 있고 -> 끝이 제한되는 경우, 스택오버플로우 위험 없을 때
        // *(2) 데코레이터를 재귀안돌리고, 자기일만 하고 빠져나오게 반복문을 돌린다.-> 끝을 모르는 경우, 스택오버플로우 위험이 있을 때

        // 데코레이터패턴도,
        // *(1)loop로 처리하도록 실행기 메서드를 따로 빼고 -> 메서드로 직전타자를 저장하며 다음타자로 업데이트하여 마지막타자만 소유하는 방법
        // (2) 데코레이터가 직접 다음루프로 가는 재귀의 방법 -> 생성자에서 원하는 수만큼의 다음타자를 직접 생성하여 저장해놓고, 처음타자를 소유하되, 처리후 재귀로 다음타자를 부른다
        // -> 만약, 데코레이터 타자들이 1000개가 넘는, 세밀한 구간처리 요금제를 만들었다고 가정하자
        //    끝을 모르는..경우로서,  addRule로 구간을 잘게쪼게해서 만들어넣을 수 있음.
        //    다음타자를 재귀로호출하면, 스택오버플로우가 걸린다.
        // --> 기업코드들은, 모두 실행기를 불리해놓고 반복문으로 처리한다.
    }

    @Override
    protected Money calculate(Money result, final Set<Call> calls) {
        Money sum = Money.ZERO;
        for (final Call call : calls) {
            //순차적으로 rule -> prev를 꺼내 새로운 rule로서 순환하며 처리
            DurationPriceRule target = rule;

            do {
                // 계산로직
                final Money tempResult = target.calculate(call.getDuration());
                sum = sum.plus(tempResult);
                // 개별구간 처리시, 자기 구간아니면, ZERO를 반환하므로, 0원 postcondition은 넣으면 안된다.
//                if (sum.isLessThanOrEqualTo(Money.ZERO)) {
//                    throw new RuntimeException("calculate error");
//                }
                // 반복문의 처리자를 prev를 꺼내서 업데이트해준다.
                target = target.getPrev();
                // 반복조건은, 꺼낸 prev인 target이 null(이면 현재 최초객체 -> 특이점객체는 연산안한다) 아닐 때까지
            } while (target.getPrev() != null);
        }

        return result.plus(sum);
    }
}
