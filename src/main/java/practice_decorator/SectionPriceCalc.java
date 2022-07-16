package practice_decorator;

import goodComposition.Money;
import java.time.Duration;

public class SectionPriceCalc {

    private SectionPriceRule rule = new SectionPriceRule(Duration.ZERO,
        Money.ZERO,
        null);


    public void addRule(final Duration to, final Money price) {
        if (rule.getTo().compareTo(to) >= 0) {
            throw new IllegalArgumentException("invalid to");
        }
        if (price.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("invalid price");
        }

        // 현재 rule을 prev로 보고, 다음 rule을 만들어 소유한다.
        // -> 현재 rule은 다음 rule 필드에 저장되어있으므로 안심하고 마지막 타자(다음타자)만 소유한다.
        rule = new SectionPriceRule(to, price, rule);
    }


    public Money calculate(final Money result, final Duration duration) {
        // 누적 계산 후 반환할 시, 바로 context에 업데이트하지말고,
        // -> 지역변수로 나만의 계산후 postcondition검증 하고 반환한다.
        Money sum = Money.ZERO;

        //1. 결과값외에, 데코객체도 prev로 [반복문 속 매번 업데이트] 될 예정이다. -> [지역변수로 빼놓고 시작]한다
        SectionPriceRule target = rule;
        //2. do-while속에서 현재 것 처리후, 다음 것으로 업데이트한다.
        //  -> [시작 특이점 객체]는 prev == null일때며, 그 때는 연산 없이 종료후 누적 결과값이 반환된다.
        do {
            // 각 개별구간처리 데코객체는, 매번 전체구간을 받아서, 자기맡은 부분만 처리하고, 계산값을 누적한다.
            final Money tempResult = target.calculate(duration);
            // 구간별 누적계산처리시, 쪼개놓은 모든 구간을 다 돌아가면서, 주어진 구간과 비교하므로
            // -> 자기해당구간이 아니면, ZERO를 반환하므로, postcondition에서 ZERO검사를 하면 안된다.

            // 누적 결과값 업데이트
            sum = sum.plus(tempResult);

            // 데코객체 업데이트
            target = target.getPrev();

            // 계싼 없는 종착역인 시작특이점 객체(prev가 null)일 때, 종료된다.
        } while ( target.getPrev() != null);

        if (sum.isLessThanOrEqualTo(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }

        return result.plus(sum);
    }
}
