package goodComposition.plan.calc;

import goodComposition.DateTimeInterval;
import goodComposition.Money;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Set;

public class DayPrice {

    private final Money price;
    private final Duration duration;
    //평일요금들은, 그 평일들 묶음을 들어오는 interval검사용으로 가지고 있을 것
    //주말요금들이라면, 토,일 묶음을 들어오는 interval검사용으로 가지고 있을 것
    private final Set<DayOfWeek> dayOfWeeks; //NULL객체 초기화하는 것?

    public DayPrice(final Money price, final Duration duration, final Set<DayOfWeek> dayOfWeeks) {
        this.price = price;
        this.duration = duration;
        this.dayOfWeeks = dayOfWeeks;
    }

    Money calculate(final DateTimeInterval[] intervals) {
        Money sum = Money.ZERO;

        //interval한건 한건이, Set에 들어가는 요일에 걸려있는지 확인 후
        //나의 Set(평일Set or 주말Set)에 포함된다면,
        //내가 가진 price, duration을 적용해서,
        //start ~ end Duration계산해서
        //각각의 요금을 더한 값을 return
        return sum;
    }
}
