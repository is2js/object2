package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.time.Duration;
import java.time.LocalTime;

public class NightDiscount extends Plan {
    private Money nightPrice; // 통화시작시간이 밤일 때  기준시간당 가격
    private Money dayPrice;  // 통화시작시간이 낮일 때 기준시간당 가격
    private LocalTime nightTime; // 밤의 기준시각
    private Duration second; // 몇초당 요금이 부과될지

    @Override
    protected Money calculateCallFee(final Call call) {
        // 통화시작시간이 22시를 넘으면 ? 할인 요금(야간요금) : 그렇지 않으면 일반요금(주간요금)을 기준으로 삼는다.
        // - 할당이 2가지를 if칠 땐 삼항연산자를 쓴다.
        final Money price = call.isFromOverAndEqualTo(nightTime) ? nightPrice : dayPrice;
        return price.times(call.getDuration().getSeconds() / second.getSeconds());

    }
}
