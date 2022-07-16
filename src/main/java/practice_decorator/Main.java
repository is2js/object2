package practice_decorator;

import goodComposition.Money;
import java.time.Duration;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        //1. 데코객체 이용 클래스는, 컬렉션처럼, 시작특이점을 필드초기화하여 내부에 안고 있는다.
        // -> 어차피 상태변화할 필드이므로, final의 생성자 초기화는 불가능하다.
        final SectionPriceCalc sectionPriceCalc = new SectionPriceCalc();

        //2. setter받기기능 메서드 호출을 통해 다음 타자를 생성하고 필드에 재할당한다. 기존 객체는 prev에 저장되므로 사라지진 않는다.
        // -> 생성된 다음타자를 받는게 아니라, 다음타자 필요 정보를 받아서 내부에서 생성한다.
        //    duration(to), price, (prev -> 이미 내부에 소유함)

        // 0~30초: 초당 10원
        // 30~60초(30초~1분): 20원(기본)
        // 1분~5분: 30원
        // 5분~9999시간: 50원

        sectionPriceCalc.addRule(Duration.ofSeconds(30), Money.of(10D));
        sectionPriceCalc.addRule(Duration.ofMinutes(1), Money.of(20D));
        sectionPriceCalc.addRule(Duration.ofMinutes(5), Money.of(30D));
        sectionPriceCalc.addRule(Duration.ofHours(9999), Money.of(50D));


        // 총 이용시간을 4분이라고 주면
        // 0~30 : 10 x 30
        // 30~1분: 20 x 30      = 900
        // 1분~4분 : 30 x (60x3) = 5400
        final Duration duration = Duration.between(LocalTime.of(0, 0, 0), LocalTime.of(0, 4, 0));
        final Money result = sectionPriceCalc.calculate(Money.ZERO, Duration.ofMinutes(4));
        System.out.println("result = " + result);

        // 만약, 0부터 총 이용시간이 아니라, 중간 구간 1분~2분의 구간처리만 하고 싶다면?
        // 0~2분 - ( 0~1분)
        // 1분당 30원이므로, 1분이니까 30 * 60 = 1800원?
        final Money betweenOneAndTwoMinutes = sectionPriceCalc.calculate(Money.ZERO, Duration.ofMinutes(2))
            .minus(sectionPriceCalc.calculate(Money.ZERO, Duration.ofMinutes(1)));

        System.out.println("betweenOneAndTwoMinutes = " + betweenOneAndTwoMinutes);


        // 0~30초: 초당 10원
        // 30~60초(30초~1분): 20원(기본)
        // 1분~5분: 30원 -> 1~4분 : 25원, 4분~5분:30원 * 중간에 처리구간 추가 *
        // 5분~9999시간: 50원
        sectionPriceCalc.addMiddleRule(Duration.ofMinutes(4), Money.of(25D));

        final Money threeToFour = sectionPriceCalc.calculate(Money.ZERO, Duration.ofMinutes(4))
            .minus(sectionPriceCalc.calculate(Money.ZERO, Duration.ofMinutes(3)));
        System.out.println("threeToFour = " + threeToFour); // 3~4분은 : 60* 25원 = 1500원


    }

    private static Duration fromZeroToDuration(final LocalTime to) {
        return Duration.between(LocalTime.of(0, 0, 0), to);
    }
}
