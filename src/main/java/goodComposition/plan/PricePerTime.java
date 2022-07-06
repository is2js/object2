package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.time.Duration;

public class PricePerTime extends Plan {

    private Money price; // 초당 요금
    private Duration second; // 총 통화시간에 대해 몇초마다 적용할 것인지

    // 2. 자식은 개별구현에 필요한 것을 생성자로 받아도 된다.(부모만 안된다. becuase of super)
    //    -> super()를 받게 되면, 본인(부모)의 상태관리를 자식이 대신해주는데, 자식은 부모를 몰라야한다.
    public PricePerTime(final Money price, final Duration second) {
        this.price = price;
        this.second = second;
    }

    //1. 좋은 자식의 조건: 메서드에 @Override + protected만 달려있는지
    @Override
    protected Money calculateCallFee(final Call call) {
        return price.times(call.getDuration().getSeconds() / second.getSeconds());
    }
}
