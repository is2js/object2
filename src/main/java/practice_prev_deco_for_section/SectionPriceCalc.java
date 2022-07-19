package practice_prev_deco_for_section;

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

        rule = new SectionPriceRule(to, price, rule);
    }

    public void addMiddleRule(final Duration to, final Money price) {
        SectionPriceRule targetRule = rule;
        while (targetRule.getPrev() != null) {

            final Duration upperBound = targetRule.getTo();
            final Duration lowerBound = targetRule.getPrev().getTo();
            if (to.compareTo(upperBound) < 0 && to.compareTo(lowerBound) > 0) {
                final SectionPriceRule middleRule = new SectionPriceRule(to, price, targetRule.getPrev());
                targetRule.setPrev(middleRule);
                break;
            }

            targetRule = targetRule.getPrev();
        }
    }


    public Money calculate(final Money result, final Duration duration) {
        // 누적 계산 후 반환할 시, 바로 context에 업데이트하지말고,
        // -> 지역변수로 나만의 계산후 postcondition검증 하고 반환한다.
        Money sum = Money.ZERO;

        SectionPriceRule target = rule;
        do {
            final Money tempResult = target.calculate(duration);
            sum = sum.plus(tempResult);
            target = target.getPrev();
        } while ( target.getPrev() != null);

        if (sum.isLessThanOrEqualTo(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }

        return result.plus(sum);
    }
}
