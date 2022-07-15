package goodComposition.plan.calc;

import goodComposition.Money;
import java.time.Duration;

public class DurationPriceRule {

    private final Money price; // 구간별 할인될 요금
    private final Duration to; // 0에서부터 내가 처리할 구간의 끝
    //데코레이터 객체가, 시간 0~0까지 최초객체가 정해져있다면, prev를 안고 다음타자를 만들어야한다.
    private final DurationPriceRule prev;

    public DurationPriceRule(final Money price,
                             final Duration to,
                             final DurationPriceRule prev) {
        this.price = price;
        this.to = to;
        this.prev = prev;
    }

    //구간별 처리를 해주려면, [전체 처리할 구간]을 데코행위에서 받아야한다.
    // -> call에 있는 .getDuration()으로 전체 구간을 받아올 것이다.
    //    통화가 몇일인지가 궁금한게 아니라 얼마나 길게 구간이 형성되어있는지가 관심사다. -> duration을 받아야한다.
    Money calculate(Duration duration) {
        // 쉴드패턴으로 데코객체 특이점을 처리해준다. -> 현재 데코객체가 일을 안해도 될 때 2가지(직전객체가 null인 최초데코객체로서 구간0으로 처리없이 초기값반환하고 터미네이팅 되었거나, 처리 구간범위가 직전에서 커버될때)
        // -> (1) 안고 태어나는 null이 아닌 prev객체가, null이면 최초데코객체란 뜻이다 -> 연쇄처리없이 초기값을 반환하고 연쇄처리를 터미네이팅한다.
        // -> (2) 구간처리로서, 처리해야할 전체구간duration이, 안고태어난 prev의 to보다 작거나 같은 범위라면, 나는 일을 안해도 된다.
        if (prev == null || duration.compareTo(prev.to) <= 0) { // prev데코객체는 같은형 연산으로서 필드를 편하게 꺼낸다.
            return Money.ZERO;
        }
        // 쉴드로 처리할 일들을 줄여놓고, 구간처리를 시작한다. -> 나(to)는 처리할 수 있는 범위가 정해져있으니,
        // -> 상한으로서 to까지 처리할지/아니면 duration을 다 처리할 수 있는지 확인해서 상한을 정한다.

        // 일단, duration이 직전(prev.to)보다는 큰 상태인데, 나(to)의 구간처리보다 더 큰 구간이면, 상한을 짤라서 처리해야한다.
        // -> 나는 3분까지만 처리하는데 구간이 5분이다 -> 내가 상한이 된다. -> to까지만 처리
        // --> 그게 아니라 나의 상한에 들어온다면(3분이하), 다 처리한다. -> duration 다 처리
        final Duration upperbound = duration.compareTo(to) > 0 ? to : duration;
        // duration이라는 구간은, compareTo외에 minus도 지원한다.
        // -> 상한 - 하한(직전구간의 to)로 내가 처리할 구간만 정한 뒤 -> 그 구간을 처리할 seconds로 변환한다.
        // 몇초당 요금할인이 아니라 1초당 요금할인인가보다. 초duration을 따로 안가지고 있고 price만 있으니 price만 곱한다.
        return price.times(upperbound.minus(prev.to).getSeconds());
    }
}
